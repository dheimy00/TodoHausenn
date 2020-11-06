package com.todoHausenn.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.todoHausenn.model.generic.BaseEntity;

public interface Service<E extends BaseEntity> {

	E salvar(E entidade);

	List<E> listar();
	
	Optional<E> obter(Long id);
	
	void excluir(Long id);
	
	void todos_excluir();
	
	
}
