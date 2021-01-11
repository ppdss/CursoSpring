package com.curso.spring.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.spring.domain.Categoria;
import com.curso.spring.repositories.CategoriaRepository;

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
	public Categoria buscar(Integer id) throws  ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " + Categoria.class.getName()));
	}
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
