package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Cidade;
import com.example.demo.domain.Cliente;
import com.example.demo.domain.Endereco;
import com.example.demo.domain.Estado;
import com.example.demo.domain.Produto;
import com.example.demo.domain.enums.TipoCliente;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.CidadeRepository;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.repositories.EnderecoRepository;
import com.example.demo.repositories.EstadoRepository;
import com.example.demo.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner{
	// ComandLineRunner permite implementar um método auxiliar para 
	// executar alguma ação quando a aplicação iniciar, no caso o Run
	
	
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		Estado est1 = new Estado(null, "Minas Gerais"); 
		Estado est2 = new Estado(null, "São Paulo"); 
		Estado est3 = new Estado(null, "Recife");
		Estado est4 = new Estado(null, "Rio Grande do Norte");
		Estado est5 = new Estado(null, "Espirito Santo");
		Estado est6 = new Estado(null, "Rio de Janeiro");
		
		Cidade c1 = new Cidade(null, "Belo Horizonte", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		Cliente cli1 = new Cliente(null, "Pedro Paulo", "pp@gmail.com", "13023589907",
				TipoCliente.PESSOAFISICA);
		
		//adicionando telefones
		cli1.getTelefones().addAll(Arrays.asList("31-971283523", "31-34224145"));
		
		Endereco e1 = new Endereco(null, "Rua Bejupirá", "106", "Apto 102", "Porto de Galinhas" , "5555555", cli1 , c1);
		Endereco e2 = new Endereco(null, "Avenida Antonio Carlos", "265", "Apto 405", "Lagoinha" , "5555555", cli1 , c2);
						
		
		/* 
		 * retorna lista de cidades e adiciona todos os objetos na 
		 * forma de array 
		 * */ 
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
 
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2,est3,est4,est5,est6));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}
