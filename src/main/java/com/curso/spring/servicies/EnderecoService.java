package com.curso.spring.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.spring.domain.Endereco;
import com.curso.spring.repositories.EnderecoRepository;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repo;
	
	public Endereco findAddressFromCli(Integer id, Integer cliId) throws ObjectNotFoundException {
		Optional<Endereco> obj = repo.findAddressFromCli(id, cliId);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " + Endereco.class.getName()));
	}
}
