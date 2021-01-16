package com.curso.spring.servicies;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.domain.ItemPedido;
import com.curso.spring.domain.PagamentoComBoleto;
import com.curso.spring.domain.Pedido;
import com.curso.spring.enums.EstadoPagamento;
import com.curso.spring.repositories.ItemPedidoRepository;
import com.curso.spring.repositories.PagamentoRepository;
import com.curso.spring.repositories.PedidoRepository;
import com.curso.spring.servicies.exceptions.ObjectNotFoundException;

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
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
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
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
