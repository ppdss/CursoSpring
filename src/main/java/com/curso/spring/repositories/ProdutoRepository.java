package com.curso.spring.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.domain.Categoria;
import com.curso.spring.domain.Produto;

/*Interface capaz de realizar as operações no banco de dados*/
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	
	/*@Query -> Anotação para criar a query sem ter que implementar ela em outra classe, como é comum em interfaces
	 * */

	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat in :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	// criando metodo com padrão de nomes do spring data
	// containing faz o papel do likes
	// CategoriasIn
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat in :categorias")
	// query sobrepõe nome do método
	@Transactional(readOnly=true) // apenas uma consulta então n é necessário fazer uma transação
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn (String nome,  List<Categoria> categorias, Pageable pageRequest);
}
