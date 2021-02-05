package com.curso.spring.servicies;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curso.spring.domain.Cidade;
import com.curso.spring.domain.Cliente;
import com.curso.spring.domain.Endereco;
import com.curso.spring.dto.ClienteDTO;
import com.curso.spring.dto.ClienteNewDTO;
import com.curso.spring.enums.Perfil;
import com.curso.spring.enums.TipoCliente;
import com.curso.spring.repositories.ClienteRepository;
import com.curso.spring.repositories.EnderecoRepository;
import com.curso.spring.security.UserSS;
import com.curso.spring.servicies.exceptions.AuthorizationException;
import com.curso.spring.servicies.exceptions.DataIntegrityException;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	// quando se declara uma dependencia dentro de uma
	// classe e anota com o AutoWired ela sera intanciada
	// pelo spring por causa da injeção de dependencia
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	// Optional é uma classe container que devolve null 
	// em vez de retornar null pointer exception 
	// ao não encontrar o objeto com o id indicado
	public Cliente find(Integer id) throws  ObjectNotFoundException {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " + Cliente.class.getName()));

	}
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	// metodo save serve tanto pra inserir quanto para atualizar
	// se o id do obj for nulo ele insere, se n, atualiza
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	public void  delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}

	}
	public List<Cliente> findAll(){
		return repo.findAll();
	}

	// Page é uma classse do spring para encapsular informação
	// e operações sobre a paginação
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		jpgImage =imageService.croupSquare(jpgImage);
		
		jpgImage =imageService.resize(jpgImage, size);
		
		// Definindo o nome do arquivo que sera mandado pro S3
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
	}
}
