package com.curso.spring.servicies;

import org.springframework.mail.SimpleMailMessage;

import com.curso.spring.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
