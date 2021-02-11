package com.curso.spring.servicies.exceptions;

public class QuantidadeProdutoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QuantidadeProdutoException(String msg) {
		super(msg);
	}
	
	public QuantidadeProdutoException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
