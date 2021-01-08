package com.curso.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.spring.domain.ItemPedido;

/*Interface capaz de realizar as operações no banco de dados extendendo JpaRepository,
 *  que é um tipo especial do Spring capaz de acessar os dados do banco com base em um tipo passado.
 *  No exemplo utilizado será a classe de domínio categoria e o seu atributo identificador que no caso é um Integer Id*/
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{
	
}
