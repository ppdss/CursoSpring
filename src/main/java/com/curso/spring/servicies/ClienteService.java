package com.curso.spring.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.curso.spring.domain.Cliente;
import com.curso.spring.dto.ClienteDTO;
import com.curso.spring.repositories.ClienteRepository;
import com.curso.spring.servicies.exceptions.DataIntegrityException;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	// quando se declara uma dependencia dentro de uma
	// classe e anota com o AutoWired ela sera intanciada
	// pelo spring por causa da injeção de dependencia
	@Autowired
	private ClienteRepository repo;

	// Optional é uma classe container que devolve null 
	// em vez de retornar null pointer exception 
	// ao não encontrar o objeto com o id indicado
	public Cliente find(Integer id) throws  ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " + Cliente.class.getName()));
		
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
			return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null, null);
		}
		
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}
}
