package com.fsv.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsv.algafood.api.model.input.PedidoInput;
import com.fsv.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	public void copyToDomainObject(Pedido pedido, PedidoInput pedidoInput) {
		modelMapper.map(pedidoInput, pedido);
	}
}
