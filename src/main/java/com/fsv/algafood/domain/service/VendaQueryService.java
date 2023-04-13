package com.fsv.algafood.domain.service;

import java.util.List;

import com.fsv.algafood.domain.filter.VendaDiariaFilter;
import com.fsv.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
