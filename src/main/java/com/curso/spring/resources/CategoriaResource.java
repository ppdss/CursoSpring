package com.curso.spring.resources;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.curso.spring.domain.Categoria;
import com.curso.spring.dto.CategoriaDTO;
import com.curso.spring.servicies.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.tools.rmi.ObjectNotFoundException;

/*
 **ANOTAÇÕES**
 * @RestController -> anota que classe será um contrador REST;
 * @RequestMapping(value="/categorias") -> url do endpoint da requisição Ex: localhost:8080/categorias;
 * @ResponseEntity -> encapsula respostas REST;
 * @PathVariable Integer id-> mapea o id vindo da url para o id que será buscado;
 * @Autowired -> instancia objeto da classe sem precisar dar new;
 * 
 		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		// Stream é um recurso para percorrer a lista
		// Map faz uma operação para cada item da lista
		// cada obj cria um objeto DTO
		// Collectors.toList() retorna a stream para lista
		
 *@RequestBody FAZ O JSON SER CONVERTIDO PARA OBJ JAVA AUTOMATICAMENTE;
 *@Valid faz com que o objDto seja validado com as anotações criadas no DT antes de entrar no método;
 *@PreAuthorize("hasAnyRole('ADMIN')") -> filtra requisição para saber se perfil é a autorizado ou n;
 * 
 * */


@RestController
@RequestMapping(value="/categorias")
@Api(value = "API REST E-COMMERCE")
@CrossOrigin(origins = "*") // qualquer dominio acessa essa api
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	@ApiOperation(value="Retorna uma categoria por ID")
	public ResponseEntity<Categoria> find (@PathVariable Integer id) throws ObjectNotFoundException {
		Categoria obj = service.find(id);
		return ResponseEntity.ok(obj);
	}
	

	@RequestMapping(method= RequestMethod.GET)
	@ApiOperation(value="Retorna uma lista de categorias")
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	} 
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.POST)
	@ApiOperation(value="Insere uma categoria")
	public ResponseEntity<Void> insert(@Valid @RequestBody  CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
	@ApiOperation(value="Atualiza uma categoria")
	public ResponseEntity<Void> update(@Valid @RequestBody  CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj =service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value= "/{id}", method= RequestMethod.DELETE)
	@ApiOperation(value="Deleta uma categoria")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	@ApiOperation(value="Busca paginada de uma categorias")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")  String direction) {
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
	}
}
