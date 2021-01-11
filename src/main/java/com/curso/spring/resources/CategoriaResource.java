package com.curso.spring.resources;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.curso.spring.domain.Categoria;
import com.curso.spring.dto.CategoriaDTO;
import com.curso.spring.servicies.CategoriaService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
//url do endpoint da requisição
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	// value é uma prop  do requestMapping que adiciona os parametros da requição
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	
	/*
	// ResponseEntity encapsula respostas REST
	// parâmetro precisa da anotação  @PathVariable para mapear o id vindo da url para o id que será buscado
	 */
	public ResponseEntity<Categoria> find (@PathVariable Integer id) throws ObjectNotFoundException {

	
		Categoria obj = service.find(id);
		
		return ResponseEntity.ok(obj);
	}
	

	@RequestMapping(method= RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		
		// Stream é um recurso para percorrer a lista
		// Map faz uma operação para cada item da lista
		// cada obj cria um objeto DTO
		// Collectors.toList() retorna a stream para lista
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	} 
	
	@RequestMapping(method=RequestMethod.POST)
	/*@RequestBod y FAZ O JSON SER CONVERTIDO PARA OBJ JAVA AUTOMATICAMENTE*/
	public ResponseEntity<Void> insert(@RequestBody  Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody  Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	@RequestMapping(value= "/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
