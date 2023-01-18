package com.fsv.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.fsv.algafood.domain.model.Pedido;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {
	
	
	Optional<Pedido> findByCodigo(String codigo);

	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
}