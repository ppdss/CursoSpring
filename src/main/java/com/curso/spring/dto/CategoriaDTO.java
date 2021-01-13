package com.curso.spring.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.curso.spring.domain.Categoria;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5,max=80,message="Tamanho deve ser entre 5 e 80 caractéres")
	private String nome;
	
	public CategoriaDTO() {	
	}
	
	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
