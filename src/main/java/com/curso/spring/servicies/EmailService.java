package com.curso.spring.servicies;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

import com.curso.spring.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	// SimpleMailMessage versão de texto plano para email 
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);

	// MimeMessage email HTML
	void sendHtmlEmail(MimeMessage msg);
	
}
