package com.todoHausenn.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.model.generic.BaseEntity;

public interface Service<E extends BaseEntity> {

	E salvar(E entidade);
	
	E atualizar(E entidade);

	Page<E> listar(Pageable pageable);
	
	Optional<TodoHausenn> obter(Integer id);
	
	void excluir(Integer id);
	
	void todos_excluir();
	
	List<TodoHausenn> findByName(String name);
	
	
}
