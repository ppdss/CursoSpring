package com.curso.spring.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.curso.spring.domain.Categoria;
import com.curso.spring.domain.Produto;
import com.curso.spring.repositories.CategoriaRepository;
import com.curso.spring.repositories.ProdutoRepository;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	// quando se declara uma dependencia dentro de uma
	// classe e anota com o AutoWired ela sera intanciada
	// pelo spring por causa da injeção de dependencia
	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	// Optional é uma classe container que devolve null 
	// em vez de retornar null pointer exception 
	// ao não encontrar o objeto com o id indicado
	public Produto find(Integer id) throws  ObjectNotFoundException {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " +  Produto.class.getName()));
		
	}
	
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	
	}

}
