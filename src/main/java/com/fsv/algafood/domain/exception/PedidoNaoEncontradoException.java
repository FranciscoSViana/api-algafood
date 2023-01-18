package com.fsv.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String codigoPedigo) {
		super(String.format("Não existe um pedido com o código %s", codigoPedigo));
	}

}
