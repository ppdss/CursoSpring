package com.curso.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;

	
	// @JsonBackReference Utilizado para sinalizar que já foi referenciada essa tabela do outro lado
	// no caso @categoria, para que nao haja referencia ciclica 
	// Ex: Categoria busca produtos e produtos procura categorias
	// @JoinTable  define tabela de relação entre produtos e categorias (n para n)
	@JsonBackReference
	@ManyToMany	
	@JoinTable(name = "PRODUTO_CATEGORIA", // nome da tabela n para n com as relações entre categorias e produtos 
	joinColumns = @JoinColumn(name = "produto_id"), // nome da chave estrangeira que referencia a classe
	inverseJoinColumns = @JoinColumn(name = "categoria_id") // nome da chave estrangeira da categoria, 'outra classe'
			)
	private List<Categoria> categorias = new ArrayList<>();
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco ) {
		super();
		this.id= id;
		this.nome = nome;
		this.preco = preco;		
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj )
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}