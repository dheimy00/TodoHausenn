package com.todoHausenn.serviceTest;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.repository.TodoHausennRepository;
import com.todoHausenn.service.service.TodoHausennService;
import com.todoHausenn.util.TodoHausennCreator;

@ExtendWith(SpringExtension.class)
public class TodoHausennServiceTest {

	@InjectMocks
	private TodoHausennService todoHausennService;

	@Mock
	private TodoHausennRepository todoHausennRepositoryMock;


	@BeforeEach
	public void setUp() {
		PageImpl<TodoHausenn> todoHausennPage = new PageImpl<>(List.of(TodoHausennCreator.createValidTodoHausenn()));
		BDDMockito.when(todoHausennRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(todoHausennPage);

		BDDMockito.when(todoHausennRepositoryMock.findById(ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(TodoHausennCreator.createValidTodoHausenn()));

		BDDMockito.when(todoHausennRepositoryMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(List.of(TodoHausennCreator.createValidTodoHausenn()));

		BDDMockito.when(todoHausennRepositoryMock.save(TodoHausennCreator.createTodoHausennTobeSave()))
				.thenReturn(TodoHausennCreator.createValidTodoHausenn());
		
		BDDMockito.when(todoHausennRepositoryMock.save(TodoHausennCreator.createValidTodoHausenn()))
		.thenReturn(TodoHausennCreator.createValidUpdateTodoHausenn());
		
		BDDMockito.doNothing().when(todoHausennRepositoryMock).deleteById(ArgumentMatchers.anyInt());
		
		
	}

	@Test
	@DisplayName("ListAll returns a pageable  list of  animes when successful")
	public void listAll_returnListOfTodoHausen_whenSuccessful() {
		String exectedName = TodoHausennCreator.createValidTodoHausenn().getName();
		Page<TodoHausenn> todoHausennPage = todoHausennService.listar(PageRequest.of(1, 1));
		Assertions.assertThat(todoHausennPage).isNotNull();
		Assertions.assertThat(todoHausennPage.toList()).isNotEmpty();
		Assertions.assertThat(todoHausennPage.toList().get(0).getName()).isEqualTo(exectedName);
	}

	@Test
	@DisplayName("FindById return an todoHausen when successful")
	public void findById_return_TodoHausen_whenSuccessful() {
		Integer exectedId = TodoHausennCreator.createValidTodoHausenn().getId();

		Optional<TodoHausenn> todoHausenn =  todoHausennService.obter(1);

		Assertions.assertThat(todoHausenn.get()).isNotNull();
		Assertions.assertThat(todoHausenn.get().getId()).isNotNull();
		Assertions.assertThat(todoHausenn.get().getId()).isEqualTo(exectedId);
	}
		
	@Test
	@DisplayName("FindByName return a pageable  todoHausen when successful")
	public void findByName_return_TodoHausen_whenSuccessful() {
		String exectedName = TodoHausennCreator.createValidTodoHausenn().getName();

		List<TodoHausenn> todoHausennList = todoHausennService.findByName("Taks1");

		Assertions.assertThat(todoHausennList).isNotNull();
		Assertions.assertThat(todoHausennList).isNotEmpty();
		Assertions.assertThat(todoHausennList.get(0).getName()).isEqualTo(exectedName);
	}

	@Test
	@DisplayName("Save create an TodoHausen when successful")
	public void save_createTodoHausen_whenSuccessful() {
		Integer exectedId = TodoHausennCreator.createValidTodoHausenn().getId();

		TodoHausenn save = TodoHausennCreator.createTodoHausennTobeSave();

		TodoHausenn todoHausenn = todoHausennService.salvar(save);

		Assertions.assertThat(todoHausenn).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isEqualTo(exectedId);
	}

	@Test
	@DisplayName("Delete by id an TodoHausen when successful")
	public void deleteById_TodoHausen_whenSuccessful() {
		
		Assertions.assertThatCode(() -> todoHausennService.excluir(1)).doesNotThrowAnyException();

	}
	
	@Test
	@DisplayName("Delete all an TodoHausen when successful")
	public void deleteAll_TodoHausen_whenSuccessful() {
		
		Assertions.assertThatCode(() -> todoHausennService.todos_excluir()).doesNotThrowAnyException();

	}
	
	@Test
	@DisplayName("Update save update TodoHausen when successful")
	public void update_saveUpdateTodoHausen_whenSuccessful() {
		TodoHausenn update = TodoHausennCreator.createValidUpdateTodoHausenn();
		 
		String exectedName = update.getName(); 		
		TodoHausenn todoHausenn = todoHausennService.salvar(TodoHausennCreator.createValidTodoHausenn());

		Assertions.assertThat(todoHausenn).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isNotNull();
		Assertions.assertThat(todoHausenn.getName()).isEqualTo(exectedName);
	}
	
	
	



}
