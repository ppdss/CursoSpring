package com.curso.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.spring.domain. Pagamento;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	
}
