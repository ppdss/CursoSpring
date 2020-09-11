package com.example.demo.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Categoria;
import com.example.demo.servicies.CategoriaService;

@RestController
//url do endpoint da requisição
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	// ResponseEntity encapsula respostas REST
	// metodo de listar com id, value é uma prop  do
	// requestMapping que adiciona os parametros da requição
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find 
	/*indica que o Id da Url vai para a variável id do método*/
	(@PathVariable Integer id) {

	
		Categoria obj = service.find(id);
		
		return ResponseEntity.ok(obj);
	}
}
