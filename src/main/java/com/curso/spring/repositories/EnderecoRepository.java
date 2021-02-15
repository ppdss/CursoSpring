package com.curso.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curso.spring.domain.Endereco;;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
	
	@Query("SELECT obj FROM Endereco obj INNER JOIN obj.cliente cli WHERE obj.id = :id AND cli.id=:cliId")
	Optional<Endereco> findAddressFromCli(@Param("id") Integer id,@Param("cliId") Integer cliId);

}
