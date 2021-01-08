package com.curso.spring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.curso.spring.domain.Categoria;
import com.curso.spring.domain.Cidade;
import com.curso.spring.domain.Cliente;
import com.curso.spring.domain.Endereco;
import com.curso.spring.domain.Estado;
import com.curso.spring.domain.ItemPedido;
import com.curso.spring.domain.Pagamento;
import com.curso.spring.domain.PagamentoComBoleto;
import com.curso.spring.domain.PagamentoComCartao;
import com.curso.spring.domain.Pedido;
import com.curso.spring.domain.Produto;
import com.curso.spring.enums.EstadoPagamento;
import com.curso.spring.enums.TipoCliente;
import com.curso.spring.repositories.CategoriaRepository;
import com.curso.spring.repositories.CidadeRepository;
import com.curso.spring.repositories.ClienteRepository;
import com.curso.spring.repositories.EnderecoRepository;
import com.curso.spring.repositories.EstadoRepository;
import com.curso.spring.repositories.ItemPedidoRepository;
import com.curso.spring.repositories.PagamentoRepository;
import com.curso.spring.repositories.PedidoRepository;
import com.curso.spring.repositories.ProdutoRepository;

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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
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
						
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("07/01/2021 23:03"), cli1, e2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("07/01/2021 23:03"), null);
		ped2.setPagamento(pagto2);
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		
		
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
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2,est3,est4,est5,est6));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}

}
