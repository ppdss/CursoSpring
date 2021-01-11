package com.curso.spring.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.spring.domain.Cliente;
import com.curso.spring.servicies.ClienteService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
//url do endpoint da requisição
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	// value é uma prop  do requestMapping que adiciona os parametros da requição
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	
	/*
	// ResponseEntity encapsula respostas REST
	// parâmetro precisa da anotação  @PathVariable para mapear o id vindo da url para o id que será buscado
	 */
	public ResponseEntity<Cliente> find (@PathVariable Integer id) throws ObjectNotFoundException {

	
		Cliente obj = service.find(id);
		
		return ResponseEntity.ok(obj);
	}
}
