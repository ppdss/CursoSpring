package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Endereco;;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
	
}
