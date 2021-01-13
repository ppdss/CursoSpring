package com.curso.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.domain.Cliente;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	// não necessita de uma transação de banco de dados
	// fica mais rapido e diminui o locking no gerenciamento
	// de transações do banco de dados
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
