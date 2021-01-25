package com.curso.spring.servicies;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.curso.spring.domain.Pedido;

/*
 * Padrão de projeto TEMPLATE METHOD 
 * 
 * */
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	// implementação do sendOrderConfirmation do EmailService
	// sendOrderConfirmationEmail faz referencia ao sendEmail sem instanciar a implementação
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		// instancia de SimpleMailMessage a partir de um pedido
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;

	}
	
}
