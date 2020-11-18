package com.todoHausenn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoHausenn.model.TodoHausenn;

@Repository("todoHausennRepository")
public interface TodoHausennRepository extends JpaRepository<TodoHausenn, Integer> {
	
	List<TodoHausenn> findByName(String name);
	

}
