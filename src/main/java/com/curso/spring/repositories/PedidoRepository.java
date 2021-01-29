package com.curso.spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.domain.Cliente;
import com.curso.spring.domain.Pedido;

/*Interface capaz de realizar as operações no banco de dados extendendo JpaRepository,
 *  que é um tipo especial do Spring capaz de acessar os dados do banco com base em um tipo passado.
 *  No exemplo utilizado será a classe de domínio categoria e o seu atributo identificador que no caso é um Integer Id*/
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	
	
	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
