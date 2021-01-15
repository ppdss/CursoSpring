package com.curso.spring.servicies.validation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.curso.spring.domain.Cliente;
import com.curso.spring.dto.ClienteDTO;
import com.curso.spring.repositories.ClienteRepository;
import com.curso.spring.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		

		// pegando os parametros da uri
		// atributos de uma requisição são armazenados em um map
		// request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		// PEGA OS ATRIBUTOS DA REQ
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId =Integer.parseInt(map.get("id"));
		
		//TIPO CRIADO NO RESOURCEEXCEPTIONS PRA EXECESSÃO
				List<FieldMessage> list = new ArrayList<>();
				
		// inclua os testes aqui, inserindo erros na lista
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux !=null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já cadastrado."));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		// se houver erros essa lista não estará vazia e o metodo
		// retorna falso
		return list.isEmpty();
	}
}