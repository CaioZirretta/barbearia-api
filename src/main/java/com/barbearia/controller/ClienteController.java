package com.barbearia.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/listar/todos")
	public ResponseEntity<List<Cliente>> listarTodos(){
		return clienteService.listarTodos(); 
	}
	
	@PostMapping("/adicionar")
	public ResponseEntity<Cliente> adicionar(@RequestBody Cliente cliente)  {
		return clienteService.adicionar(cliente);
	}
	
	@PostMapping("/adicionarVarios")
	public ResponseEntity<List<Cliente>> adicionarVarios(@RequestBody List<Cliente> clientes) throws ApiRequestException {
		return clienteService.adicionarVarios(clientes);
	}
	
	@GetMapping("/detalhar/{cpf}")
	public ResponseEntity<Cliente> detalhar(@PathVariable String cpf) {
		return clienteService.detalharCliente(cpf);
	}
	
	@DeleteMapping("/deletartudo")
	public ResponseEntity<String> deletarTudo(){
		return clienteService.deletarTudo();
	}
	
	@PutMapping("/alterar/{cpf}")
	@Transactional
	public ResponseEntity<Cliente> alterarCliente(@PathVariable String cpf, @RequestBody Cliente clienteAtualizado) {
		return clienteService.alterarCliente(cpf, clienteAtualizado);
	}

}
