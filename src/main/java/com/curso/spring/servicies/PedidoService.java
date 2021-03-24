package com.curso.spring.servicies;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.domain.Cliente;
import com.curso.spring.domain.Endereco;
import com.curso.spring.domain.ItemPedido;
import com.curso.spring.domain.PagamentoComBoleto;
import com.curso.spring.domain.Pedido;
import com.curso.spring.enums.EstadoPagamento;
import com.curso.spring.repositories.ItemPedidoRepository;
import com.curso.spring.repositories.PagamentoRepository;
import com.curso.spring.repositories.PedidoRepository;
import com.curso.spring.security.UserSS;
import com.curso.spring.servicies.exceptions.AuthorizationException;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;
import com.curso.spring.servicies.exceptions.QuantidadeProdutoException;

@Service
public class PedidoService {

	// quando se declara uma dependencia dentro de uma
	// classe e anota com o AutoWired ela sera intanciada
	// pelo spring por causa da injeção de dependencia
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository; 
	
	@Autowired
	private BoletoService boletoService;

	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	// Optional é uma classe container que devolve null 
	// em vez de retornar null pointer exception 
	// ao não encontrar o objeto com o id indicado
	public Pedido find(Integer id) throws  ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException(
				"Objeto não encontrado! " + id +
				" Tipo: " + Pedido.class.getName()));
		
	}
	@Transactional
	public Pedido insert(Pedido obj) {
		
		for(ItemPedido ip : obj.getItens()) {
			Integer qtdEstoque = produtoService.find(ip.getProduto().getId()).getQtdEstoque();
			if(qtdEstoque < ip.getQuantidade()) {
				throw new QuantidadeProdutoException("Quantidade de produtos em estoque insuficiente.");
			}
		}
		
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		Endereco end = enderecoService.findAddressFromCli(obj.getEnderecoDeEntrega().getId(), obj.getCliente().getId());
		obj.setEnderecoEntrega(end);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			Integer qtdEstoque = produtoService.find(ip.getProduto().getId()).getQtdEstoque();
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
			ip.getProduto().setQtdEstoque(qtdEstoque - ip.getQuantidade());
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if(user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}
