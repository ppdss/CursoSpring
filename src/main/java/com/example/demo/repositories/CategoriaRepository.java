package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Categoria;

/*Interface capaz de realizar as operações no banco de dados extendendo JpaRepository,
 *  que é um tipo especial do Spring capaz de acessar os dados do banco com base em um tipo passado.
 *  No exemplo utilizado será a classe de domínio categoria e o seu atributo identificador que no caso é um Integer Id*/
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	
}
