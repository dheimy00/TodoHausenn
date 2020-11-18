package com.todoHausenn.repositoryTest;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.repository.TodoHausennRepository;
import com.todoHausenn.util.TodoHausennCreator;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@DisplayName("TodoHausenn Repository Test")
public class TodoHausennRepositoryTest {

	@Autowired
	@Qualifier("todoHausennRepository")
	private TodoHausennRepository todoHausennRepository;
	
	
	@Test
	@DisplayName("Save create TodoHausenn when successful")
	public void Save_CreateTodoHausenn_whenSuccessful() {
		TodoHausenn t = TodoHausennCreator.createTodoHausennTobeSave();
		TodoHausenn save =todoHausennRepository.save(t);
		
		Assertions.assertThat(save.getId()).isNotNull();
		Assertions.assertThat(save.getName()).isNotNull();
		Assertions.assertThat(save.getName()).isEqualTo(t.getName());

		
	}
	
	
	
	@Test
	@DisplayName("Save update TodoHausenn when successful")
	public void  Save_UpdateTodoHausenn_whenSuccessful() {
		TodoHausenn t = TodoHausennCreator.createTodoHausennTobeSave();
		TodoHausenn save =todoHausennRepository.save(t);
		save.setName("task1");
		TodoHausenn update =todoHausennRepository.save(save);
		
		Assertions.assertThat(save.getId()).isNotNull();
		Assertions.assertThat(save.getName()).isNotNull();
		Assertions.assertThat(save.getName()).isEqualTo(update.getName());

		
	}
	
	@Test
	@DisplayName("Delete removes TodoHausenn when successful")
	public void  Delete_RemoveTodoHausenn_whenSuccessful() {
		TodoHausenn t = TodoHausennCreator.createTodoHausennTobeSave();
		
		TodoHausenn save = todoHausennRepository.save(t);
		
		todoHausennRepository.deleteById(save.getId());
		
		Optional<TodoHausenn> todoHausennOptional = this.todoHausennRepository.findById(save.getId());
		
		Assertions.assertThat(todoHausennOptional.isEmpty()).isTrue();
		
	}
	
	@Test
	@DisplayName("Delete all removes TodoHausenn when successful")
	public void  DeleteAll_RemoveTodoHausenn_whenSuccessful() {
		TodoHausenn t = TodoHausennCreator.createTodoHausennTobeSave();
		TodoHausenn save =todoHausennRepository.save(t);
		this.todoHausennRepository.deleteAll();
		Optional<TodoHausenn> todoHausennOptional = this.todoHausennRepository.findById(save.getId());
		
		Assertions.assertThat(todoHausennOptional.isEmpty()).isTrue();
		
	}
	
	@Test
	@DisplayName("Find id TodoHausenn when successful")
	public void  FindById_TodoHausenn_whenSuccessful() {
		TodoHausenn t = TodoHausennCreator.createTodoHausennTobeSave();
		TodoHausenn save = todoHausennRepository.save(t);
		Optional<TodoHausenn> todoHausennOptional = this.todoHausennRepository.findById(save.getId());	
		Assertions.assertThat(todoHausennOptional).isNotEmpty();
		Assertions.assertThat(todoHausennOptional).contains(save);
	}
	
	@Test
	@DisplayName("Find by name return todoHausenns hen successful ")
	public void  FindByName_ReturnTodoHausenn_whenSuccessful() {
		TodoHausenn t = TodoHausennCreator.createTodoHausennTobeSave();
		TodoHausenn save = todoHausennRepository.save(t);	
		String name = save.getName(); 
		List<TodoHausenn> todoHausennss = this.todoHausennRepository.findByName(name);
	
		Assertions.assertThat(todoHausennss).isNotEmpty();
		Assertions.assertThat(todoHausennss).contains(save);
		
	}
	
	@Test
	@DisplayName("Find by name return empty list when no todoHausenns is found ")
	public void  FindByName_ReturnEmptyList_whenTodoHausennsNotfound() {
		String name = "fake-name";
		List<TodoHausenn> todoHausennss = this.todoHausennRepository.findByName(name);
	
		Assertions.assertThat(todoHausennss).isEmpty();
		
	}
	
	
	
}
