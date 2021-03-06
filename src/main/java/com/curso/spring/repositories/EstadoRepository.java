package com.curso.spring.repositories;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.domain.Estado;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
}
