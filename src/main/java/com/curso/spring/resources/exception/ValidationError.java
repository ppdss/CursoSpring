package com.curso.spring.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> list = new ArrayList<>();
	
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		
	}

	// no java, o nome após o get (Ex: getErros -> erros) 
	// é que o Json irá exibir
	public List<FieldMessage> getErros() {
		return list;
	}


	public void setList(List<FieldMessage> list) {
		this.list = list;
	}

	public void addError(String fieldName, String message) {
		list.add(new FieldMessage(fieldName, message));
	}

}