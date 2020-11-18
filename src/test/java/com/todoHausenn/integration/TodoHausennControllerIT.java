package com.todoHausenn.integration;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.todoHausenn.model.PageableResponse;
import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.repository.TodoHausennRepository;
import com.todoHausenn.util.TodoHausennCreator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TodoHausennControllerIT {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;
	
	@MockBean
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
		
	 Page<TodoHausenn> todoHausennPage = testRestTemplate.exchange("/v1/todo", HttpMethod.GET, null,
	                new ParameterizedTypeReference<PageableResponse<TodoHausenn>>() {
	                }).getBody();
		 
		Assertions.assertThat(todoHausennPage).isNotNull();
		Assertions.assertThat(todoHausennPage.toList()).isNotEmpty();
		Assertions.assertThat(todoHausennPage.toList().get(0).getName()).isEqualTo(exectedName);
	}

	@Test
	@DisplayName("FindById return an  todoHausen when successful")
	public void findById_return_TodoHausen_whenSuccessful() {
		Integer exectedId = TodoHausennCreator.createValidTodoHausenn().getId();

	 TodoHausenn todoHausenn = testRestTemplate.getForObject("/v1/todo/1", TodoHausenn.class);

	    Assertions.assertThat(todoHausenn).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isEqualTo(exectedId);
	}
	
	@Test
	@DisplayName("FindByName return an todoHausen when successful")
	public void findByName_return_TodoHausen_whenSuccessful() {
		String exectedName = TodoHausennCreator.createValidTodoHausenn().getName();

		List<TodoHausenn> todoHausennList = testRestTemplate.exchange("/v1/todo/search/Taks1'", HttpMethod.GET,null, new ParameterizedTypeReference<List<TodoHausenn>>() {}).getBody();

		Assertions.assertThat(todoHausennList).isNotNull();
		Assertions.assertThat(todoHausennList).isNotEmpty();
		Assertions.assertThat(todoHausennList.get(0).getName()).isEqualTo(exectedName);
	}

	@Test
	@DisplayName("Save create an TodoHausen when successful")
	public void save_createTodoHausen_whenSuccessful() {
		Integer exectedId = TodoHausennCreator.createValidTodoHausenn().getId();

		TodoHausenn save = TodoHausennCreator.createTodoHausennTobeSave();

		TodoHausenn todoHausenn = testRestTemplate.exchange("/v1/todo/", HttpMethod.POST,
				createJsonHttpEntity(save),TodoHausenn.class).getBody();

		Assertions.assertThat(todoHausenn).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isNotNull();
		Assertions.assertThat(todoHausenn.getId()).isEqualTo(exectedId);
	}
	
	@Test
	@DisplayName("Delete by id an TodoHausen when successful")
	public void deleteById_TodoHausen_whenSuccessful() {
		
		ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/v1/todo/1", HttpMethod.DELETE,null,Void.class);

		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(responseEntity.getBody()).isNull();
	}
	
	@Test
	@DisplayName("Delete all an TodoHausen when successful")
	public void deleteAll_TodoHausen_whenSuccessful() {
		
		ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/v1/todo", HttpMethod.DELETE,null,Void.class);

		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(responseEntity.getBody()).isNull();
	}
	
	
	@Test
	@DisplayName("Update save update TodoHausen when successful")
	public void update_saveUpdateTodoHausen_whenSuccessful() {
		TodoHausenn validTodoHausenn = TodoHausennCreator.createValidTodoHausenn();
		  		
		ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/v1/todo/1", HttpMethod.PUT,
				createJsonHttpEntity(validTodoHausenn),Void.class);

		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(responseEntity.getBody()).isNull();
	}
	
	
	
	private HttpEntity<TodoHausenn> createJsonHttpEntity(TodoHausenn todoHausenn){
		
		return new  HttpEntity<>(todoHausenn,createJsonHeaders());
	}
	
	private static  HttpHeaders createJsonHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
		
	}


}
