package com.curso.spring.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.curso.spring.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PagamentoComBoleto extends Pagamento {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonFormat(pattern="dd/MM/yyy HH:mm")
	private Date dataVencimento;
	@JsonFormat(pattern="dd/MM/yyy HH:mm")
	private Date dataPagamento;
	
	
	public PagamentoComBoleto() {
		// TODO Auto-generated constructor stub
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
	
}
