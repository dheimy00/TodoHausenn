package com.todoHausenn.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public TodoHausenn salvar(TodoHausenn todoHausenn) {
		return todoHausennRepository.save(todoHausenn);
	}
		
	@Override
	public void excluir(Integer id) {
		todoHausennRepository.deleteById(Integer.valueOf(id));
	}

	@Override
	public Optional<TodoHausenn> obter(Integer id) {
		return todoHausennRepository.findById(id);
	}

	@Override
	public void todos_excluir() {
		todoHausennRepository.deleteAll();

	}
	
	public Page<TodoHausenn> listar(Pageable pageable) {
		return todoHausennRepository.findAll(pageable);
	}

	@Override
	public List<TodoHausenn> findByName(String name) {
		return todoHausennRepository.findByName(name);
	}

	@Override
	public TodoHausenn atualizar( TodoHausenn todoHausenn) {
		return todoHausennRepository.save(todoHausenn);
	}


}
