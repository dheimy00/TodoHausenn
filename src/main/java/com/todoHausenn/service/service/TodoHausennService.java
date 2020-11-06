package com.todoHausenn.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.repository.TodoHausennRepository;
import com.todoHausenn.service.interfaces.ITodoHausennService;

@Service("todoHausennService")
public class TodoHausennService implements ITodoHausennService {

	@Autowired
	@Qualifier("todoHausennRepository")
	private TodoHausennRepository todoHausennRepository;

	@Override
	public TodoHausenn salvar(TodoHausenn entidade) {
		return todoHausennRepository.save(entidade);
	}
	
	@Override
	public List<TodoHausenn> listar() {
		return (List<TodoHausenn>) todoHausennRepository.findAll();
	}
		
	@Override
	public void excluir(Long id) {
		todoHausennRepository.deleteById(id);
	}

	@Override
	public Optional<TodoHausenn> obter(Long id) {
		Optional<TodoHausenn> optional = todoHausennRepository.findById(id);
		return optional;
	}

	@Override
	public void todos_excluir() {
		todoHausennRepository.deleteAll();

	}

}
