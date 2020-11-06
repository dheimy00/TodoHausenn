package com.todoHausenn.restController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todoHausenn.controler.generic.Controller;
import com.todoHausenn.dto.error.ErrorDetail;
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
	@ApiOperation(value = "Cria um novo Todo", notes = "O ID do todo recém-criado será enviado no cabeçalho de resposta do local", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Todo criado com sucesso", response = Void.class),
			@ApiResponse(code = 500, message = "Erra criar Todo", response = ErrorDetail.class) })
	public ResponseEntity<TodoHausenn> salvar(@Valid @RequestBody TodoHausenn todoHausenn) {
		todoHausenn = getService().salvar(todoHausenn);
		return new ResponseEntity<>(todoHausenn, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	@ApiOperation(value = "Recupera todos os Todo", response = TodoHausenn.class, responseContainer = "Lista")
	public ResponseEntity<List<TodoHausenn>> listar() {
		List<TodoHausenn> todoHausenns = getService().listar();
		return new ResponseEntity<>(todoHausenns, HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza determinados Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ErrorDetail.class) })
	public ResponseEntity<?> atualizar(@RequestBody TodoHausenn todoHausenn, @PathVariable Long id) {
		verificarID(id);
		todoHausenn = getService().salvar(todoHausenn);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Recupera dado Todo", response = TodoHausenn.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = TodoHausenn.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ErrorDetail.class) })
	public ResponseEntity<?> obter(@PathVariable Long id) {
		verificarID(id);
		Optional<TodoHausenn> todoHausenn = getService().obter(id);
		return new ResponseEntity<>(todoHausenn, HttpStatus.OK);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Excluir Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ErrorDetail.class) })
	public ResponseEntity<String> excluir(@PathVariable Long id) {
		verificarID(id);
		getService().excluir(id);
		return new ResponseEntity<>("Todo foram excluído com sucesso", HttpStatus.OK);
	}

	@RequestMapping(value = "/todo", method = RequestMethod.DELETE)
	@ApiOperation(value = "Todos excluidos Todo", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = Void.class),
			@ApiResponse(code = 404, message = "Não encontra Todo", response = ErrorDetail.class) })
	public ResponseEntity<String> todos_excluir() {
		getService().todos_excluir();
		return new ResponseEntity<>("Todos os Todo foram excluídos com sucesso", HttpStatus.OK);
	}

	protected void verificarID(Long id) throws ResourceNotFoundException {
		Optional<TodoHausenn> optional = getService().obter(id);
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("Todo com id " + id + " não encontra!");
		}
	}

}
