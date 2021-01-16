package com.curso.spring.resources;


import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.curso.spring.domain.Pedido;
import com.curso.spring.servicies.PedidoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
//url do endpoint da requisição
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	// value é uma prop  do requestMapping que adiciona os parametros da requição
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	
	/*
	// ResponseEntity encapsula respostas REST
	// parâmetro precisa da anotação  @PathVariable para mapear o id vindo da url para o id que será buscado
	 */
	public ResponseEntity<Pedido> find (@PathVariable Integer id) throws ObjectNotFoundException {

	
		Pedido obj = service.find(id);
		
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	/*@RequestBody FAZ O JSON SER CONVERTIDO PARA OBJ JAVA AUTOMATICAMENTE
	 * @Valid faz com que o objDto seja validado com as anotações
	 * criadas no CategoriaDto antes de entrar no método
	 * */
	public ResponseEntity<Void> insert(@Valid @RequestBody  Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
