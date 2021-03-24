package com.curso.spring.servicies.validation;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.curso.spring.domain.Cliente;
import com.curso.spring.dto.ClienteNewDTO;
import com.curso.spring.enums.TipoCliente;
import com.curso.spring.repositories.ClienteRepository;
import com.curso.spring.resources.exception.FieldMessage;
import com.curso.spring.servicies.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		//TIPO CRIADO NO RESOURCEEXCEPTIONS PRA EXECESSÃO
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) &&
				!BR.isValidCPF(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj","CPF inválido"));
		}

		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) &&
				!BR.isValidCNPJ(objDto.getCpfOuCnpj())){
			list.add(new FieldMessage("cpfOuCnpj","CNPJ inválido"));
		}

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux !=null) {
			list.add(new FieldMessage("email", "Email já cadastrado"));
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