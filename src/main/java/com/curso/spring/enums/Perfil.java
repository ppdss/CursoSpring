package com.curso.spring.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"), //Spring exige o prefixo ROLE_ antes de cada perfil
	CLIENTE(2, "ROLE_CLIE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		// .values percorre todos os valores possíveis de Perfil
		for (Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x; 
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
