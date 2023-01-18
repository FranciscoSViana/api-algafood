package com.fsv.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsv.algafood.domain.exception.NegocioException;
import com.fsv.algafood.domain.exception.PedidoNaoEncontradoException;
import com.fsv.algafood.domain.model.Cidade;
import com.fsv.algafood.domain.model.FormaPagamento;
import com.fsv.algafood.domain.model.Pedido;
import com.fsv.algafood.domain.model.Produto;
import com.fsv.algafood.domain.model.Restaurante;
import com.fsv.algafood.domain.model.Usuario;
import com.fsv.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	CadastroFormaPagamentoService pagamentoService;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}
	
	private void validarPedido(Pedido pedido) {
		Cidade cidade =  cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario usuario = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(usuario);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.", 
					formaPagamento.getDescricao()));
		}
	}
	
	public void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	public Pedido buscarOuFalhar(String codigoPedigo) {
		return pedidoRepository.findByCodigo(codigoPedigo)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedigo));
	}
}
