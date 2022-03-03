package com.barbearia.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbearia.model.Cliente;
import com.barbearia.model.EnderecoCA;
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.ClienteService;
import com.barbearia.service.factory.EnderecoFactory;
import com.barbearia.service.factory.IEndereco;
import com.barbearia.service.utils.RequestExterno;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/listar/todos")
	public ResponseEntity<List<Cliente>> listarTodos(){
		return new ResponseEntity<List<Cliente>>(clienteService.listarTodos(), HttpStatus.OK); 
	}
	
	@GetMapping("/{cpf}")
	public ResponseEntity<Cliente> detalharCliente(@PathVariable String cpf) {
		return new ResponseEntity<Cliente>(clienteService.detalharCliente(cpf), HttpStatus.OK);
	}
	
	@PostMapping("/adicionar")
	public ResponseEntity<Cliente> adicionar(@RequestBody NovaPessoaDto novaPessoaDto)  {
		return new ResponseEntity<Cliente>(clienteService.adicionar(novaPessoaDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/alterar")
	@Transactional
	public ResponseEntity<Cliente> alterarCliente(@RequestBody AlteracaoPessoaDto pessoaDto) {
		return new ResponseEntity<Cliente>(clienteService.alterarCliente(pessoaDto), HttpStatus.OK);
	}

	@GetMapping("/teste/{codigoPostal}")
	public ResponseEntity<Cliente> testeExterno(@PathVariable String codigoPostal) {
		return new ResponseEntity<Cliente>(clienteService.testeIEndereco(codigoPostal), HttpStatus.OK);
	}
}
