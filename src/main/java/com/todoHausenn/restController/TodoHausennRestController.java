package com.todoHausenn.restController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todoHausenn.controler.generic.Controller;
import com.todoHausenn.exception.ResourceNotFoundDetails;
import com.todoHausenn.exception.ResourceNotFoundException;
import com.todoHausenn.model.TodoHausenn;
import com.todoHausenn.service.interfaces.ITodoHausennService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1")
@Api(value = "todo", description = "Todo API")
public class TodoHausennRestController extends Controller<TodoHausenn, ITodoHausennService> {

	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("todoHausennService")
	private ITodoHausennService todoHausennService;

	@Override
	public ITodoHausennService getService() {
		return todoHausennService;
	}

	@RequestMapping(value = "/todo", method = RequestMethod.POST)
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Cria um novo Todo", notes = "O ID do todo recém-criado será enviado no cabeçalho de resposta do local", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Todo criado com sucesso", response = Void.class),
			@ApiResponse(code = 500, message = "Erra criar Todo", response = ResourceNotFoundDetails.class) })
	public ResponseEntity<TodoHausenn> salvar(@Valid @RequestBody TodoHausenn todoHausenn) {
		todoHausenn = getService().salvar(todoHausenn);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newTodoHausennUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(todoHausenn.getId()).toUri();
		responseHeaders.setLocation(newTodoHausennUri);

		return new ResponseEntity<>(todoHausenn, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	@ApiOperation(value = "Recupera todos os Todo", response = TodoHausenn.class, responseContainer = "Lista")
	public ResponseEntity<Page<TodoHausenn>> listar(Pageable pageable) {
		Page<TodoHausenn> todoHausenns = getService().listar(pageable);
		return new ResponseEntity<>(todoHausenns, HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza determinados Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ResourceNotFoundDetails.class) })
	public ResponseEntity<TodoHausenn> atualizar(@PathVariable("id") Integer id, @RequestBody TodoHausenn todoHausenn) {
		verificarID(id);
		todoHausenn = getService().atualizar(todoHausenn);
		return new ResponseEntity<>(todoHausenn, HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Recupera dado Todo", response = TodoHausenn.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = TodoHausenn.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ResourceNotFoundDetails.class) })
	public ResponseEntity<Optional<TodoHausenn>> obter(@PathVariable Integer id) {
		verificarID(id);
		Optional<TodoHausenn> todoHausenn = getService().obter(id);
		return new ResponseEntity<>(todoHausenn, HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Excluir Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ResourceNotFoundDetails.class) })
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		verificarID(id);
		getService().excluir(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/todo", method = RequestMethod.DELETE)
	@ApiOperation(value = "Todos excluidos Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ResourceNotFoundDetails.class) })
	public ResponseEntity<Void> todos_excluir() {
		getService().todos_excluir();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/search/{name}", method = RequestMethod.GET)
	@ApiOperation(value = "Todos excluidos Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ResourceNotFoundDetails.class) })
	public ResponseEntity<List<TodoHausenn>> findByName(@PathVariable final String name) {

		List<TodoHausenn> todoHausenn = getService().findByName(name);
		return new ResponseEntity<>(todoHausenn, HttpStatus.OK);
	}

	protected void verificarID(Integer id) throws ResourceNotFoundException {
		Optional<TodoHausenn> todoHausenn = getService().obter(id);
		if (!todoHausenn.isPresent()) {
			throw new ResourceNotFoundException("Todo with id " + id + " not found!");
		}
	}

}
