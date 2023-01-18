package com.fsv.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fsv.algafood.api.model.input.RestauranteInput;
import com.fsv.algafood.domain.model.Cidade;
import com.fsv.algafood.domain.model.Cozinha;
import com.fsv.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}

	public RestauranteInput toInputObject(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteInput.class);
	}

	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha());

		if (restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}

		modelMapper.map(restauranteInput, restaurante);
	}
}
