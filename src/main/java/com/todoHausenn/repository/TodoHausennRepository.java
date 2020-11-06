package com.todoHausenn.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todoHausenn.model.TodoHausenn;

@Repository("todoHausennRepository")
public interface TodoHausennRepository extends CrudRepository<TodoHausenn, Long> {

}
