package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Estado;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
}
