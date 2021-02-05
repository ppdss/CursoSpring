package com.curso.spring.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.spring.domain.Estado;
import com.curso.spring.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAll()  {
		List<Estado> estados = repo.findAllByOrderByNome();
		return estados;
	}
}
