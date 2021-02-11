package com.curso.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


/*
 **ANOTAÇÕES**
 * @JsonBackReference -> Utilizado para sinalizar que já tabela já foi serializada por outra
 * @JoinTable  define tabela de relação entre duas tabelas
 * joinColumns = @JoinColunm() -> indica o nome da chave estrangeira da classe atual.
 * inverseJoinColumns -> indica o nome da chave estrangeira da classe externa
 * @JsonIgnore -> Por padrão do Spring, qualquer função get é serializada, essa anotação faz o objeto não ser serializado
 * Utiliza-se no lugar do JsonBackReference para não serializar uma tabela que ja foi serializada por outra
 * 
 * */
@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	private Integer qtdEstoque;

	
	@JsonIgnore
	@ManyToMany	
	@JoinTable(name = "PRODUTO_CATEGORIA", // nome da tabela n para n com as relações entre categorias e produtos 
	joinColumns = @JoinColumn(name = "produto_id"), // nome da chave estrangeira que referencia a classe
	inverseJoinColumns = @JoinColumn(name = "categoria_id") // nome da chave estrangeira da categoria, 'outra classe'
			)
	private List<Categoria> categorias = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco, Integer qtdEstoque ) {
		super();
		this.id= id;
		this.nome = nome;
		this.preco = preco;		
		this.qtdEstoque = qtdEstoque;
	}

	@JsonIgnore 
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		for (ItemPedido x: itens) {
			lista.add(x.getPedido());
		}
		return lista;
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
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	@JsonIgnore 
	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtd_estoque) {
		this.qtdEstoque = qtd_estoque;
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
