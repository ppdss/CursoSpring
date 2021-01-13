package com.curso.spring.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.curso.spring.domain.Categoria;
import com.curso.spring.dto.CategoriaDTO;
import com.curso.spring.repositories.CategoriaRepository;
import com.curso.spring.servicies.exceptions.DataIntegrityException;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	// quando se declara uma dependencia dentro de uma
	// classe e anota com o AutoWired ela sera intanciada
	// pelo spring por causa da injeção de dependencia
	@Autowired
	private CategoriaRepository repo;

	// Optional é uma classe container que devolve null 
	// em vez de retornar null pointer exception 
	// ao não encontrar o objeto com o id indicado
	public Categoria find(Integer id) throws  ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " + Categoria.class.getName()));
	}
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	// metodo save serve tanto pra inserir quanto para atualizar
	// se o id do obj for nulo ele insere, se n, atualiza
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	public void  delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	// Page é uma classse do spring para encapsular informação
	// e operações sobre a paginação
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
}
