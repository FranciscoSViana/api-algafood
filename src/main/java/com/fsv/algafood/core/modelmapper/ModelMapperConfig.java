package com.fsv.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fsv.algafood.api.model.EnderecoModel;
import com.fsv.algafood.api.model.input.ItemPedidoInput;
import com.fsv.algafood.domain.model.Endereco;
import com.fsv.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				EnderecoSrc -> EnderecoSrc.getCidade().getEstado().getNome(), 
				(EnderecoModelDest, value) -> EnderecoModelDest.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
