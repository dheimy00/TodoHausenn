package com.todoHausenn.controllerTest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.restController.TodoHausennRestController;
import com.todoHausenn.service.interfaces.ITodoHausennService;
import com.todoHausenn.util.TodoHausennCreator;

@ExtendWith(SpringExtension.class)
public class TodoHausennControllerTest {

	@InjectMocks
	private TodoHausennRestController todoHausennRestController;

	@Mock
	private ITodoHausennService todoHausennServiceMock;

	@AfterEach
	public void teardown() {
		RequestContextHolder.resetRequestAttributes();
	}

	@BeforeEach
	public void setUp() {
		PageImpl<TodoHausenn> todoHausennPage = new PageImpl<>(List.of(TodoHausennCreator.createValidTodoHausenn()));
		BDDMockito.when(todoHausennServiceMock.listar(ArgumentMatchers.any())).thenReturn(todoHausennPage);

		BDDMockito.when(todoHausennServiceMock.obter(ArgumentMatchers.anyInt()))
				.thenReturn(Optional.of(TodoHausennCreator.createValidTodoHausenn()));

		BDDMockito.when(todoHausennServiceMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(List.of(TodoHausennCreator.createValidTodoHausenn()));

		BDDMockito.when(todoHausennServiceMock.salvar(TodoHausennCreator.createTodoHausennTobeSave()))
				.thenReturn(TodoHausennCreator.createValidTodoHausenn());
		
		BDDMockito.when(todoHausennServiceMock.salvar(TodoHausennCreator.createValidTodoHausenn()))
		.thenReturn(TodoHausennCreator.createValidUpdateTodoHausenn());
		
		BDDMockito.doNothing().when(todoHausennServiceMock).excluir(ArgumentMatchers.anyInt());

		HttpServletRequest httpServletRequestMock = new MockHttpServletRequest();
		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(httpServletRequestMock);
		RequestContextHolder.setRequestAttributes(servletRequestAttributes);
	}

	@Test
	@DisplayName("ListAll returns a pageable  list of  animes when successful")
	public void listAll_returnListOfTodoHausen_whenSuccessful() {
		String exectedName = TodoHausennCreator.createValidTodoHausenn().getName();
		Page<TodoHausenn> todoHausennPage = todoHausennRestController.listar(null).getBody();
		Assertions.assertThat(todoHausennPage).isNotNull();
		Assertions.assertThat(todoHausennPage.toList()).isNotEmpty();
		Assertions.assertThat(todoHausennPage.toList().get(0).getName()).isEqualTo(exectedName);
	}

	@Test
	@DisplayName("FindById return a pageable  todoHausen when successful")
	public void findById_return_TodoHausen_whenSuccessful() {
		Integer exectedId = TodoHausennCreator.createValidTodoHausenn().getId();

	  Optional<TodoHausenn> todoHausenn = todoHausennRestController.obter(1).getBody();

	    Assertions.assertThat(todoHausenn.get()).isNotNull();
		Assertions.assertThat(todoHausenn.get().getId()).isNotNull();
		Assertions.assertThat(todoHausenn.get().getId()).isEqualTo(exectedId);
	}

	@Test
	@DisplayName("FindByName return a pageable  todoHausen when successful")
	public void findByName_return_TodoHausen_whenSuccessful() {
		String exectedName = TodoHausennCreator.createValidTodoHausenn().getName();

		List<TodoHausenn> todoHausennList = todoHausennRestController.findByName("Taks1").getBody();

		Assertions.assertThat(todoHausennList).isNotNull();
		Assertions.assertThat(todoHausennList).isNotEmpty();
		Assertions.assertThat(todoHausennList.get(0).getName()).isEqualTo(exectedName);
	}

	@Test
	@DisplayName("Save create an TodoHausen when successful")
	public void save_createTodoHausen_whenSuccessful() {
		Integer exectedId = TodoHausennCreator.createValidTodoHausenn().getId();

		TodoHausenn save = TodoHausennCreator.createTodoHausennTobeSave();

		TodoHausenn todoHausenn = todoHausennRestController.salvar(save).getBody();

		Assertions.assertThat(todoHausenn).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isEqualTo(exectedId);
	}
	
	@Test
	@DisplayName("Delete by id an TodoHausen when successful")
	public void deleteById_TodoHausen_whenSuccessful() {
		
		ResponseEntity<Void> responseEntity = todoHausennRestController.excluir(1);

		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(responseEntity.getBody()).isNull();
	}
	
	@Test
	@DisplayName("Delete all an TodoHausen when successful")
	public void deleteAll_TodoHausen_whenSuccessful() {
		
		ResponseEntity<Void> responseEntity = todoHausennRestController.todos_excluir();

		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(responseEntity.getBody()).isNull();
	}
	
	
	@Test
	@DisplayName("Update save update TodoHausen when successful")
	public void update_saveUpdateTodoHausen_whenSuccessful() {
		TodoHausenn update = TodoHausennCreator.createValidUpdateTodoHausenn();
		 
		String exectedName = update.getName(); 		
		TodoHausenn todoHausenn = todoHausennRestController.salvar(TodoHausennCreator.createValidTodoHausenn()).getBody();

		Assertions.assertThat(todoHausenn).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isNotNull();
		Assertions.assertThat(todoHausenn.getName()).isEqualTo(exectedName);
	}
	
	


}
