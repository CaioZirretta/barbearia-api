package com.barbearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbearia.model.Agendamento;
import com.barbearia.service.AgendamentoService;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

	@Autowired
	private AgendamentoService agendamentoService;

	@GetMapping("/listar/todos")
	public ResponseEntity<List<Agendamento>> listarTodos() {
		return new ResponseEntity<List<Agendamento>>(agendamentoService.listarTodos(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/listar/cliente/{cpf}")
	public ResponseEntity<List<Agendamento>> procurarPorCpfCliente(@PathVariable String cpf) {
		return new ResponseEntity<List<Agendamento>>(agendamentoService.procurarPorCpfCliente(cpf), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/listar/prestador/{cpf}")
	public ResponseEntity<List<Agendamento>> procurarPorCpfPrestador(@PathVariable String cpf) {
		return new ResponseEntity<List<Agendamento>>(agendamentoService.procurarPorCpfPrestador(cpf), HttpStatus.ACCEPTED);	
	}

	@PostMapping("/agendar")
	public ResponseEntity<Agendamento> agendar(@RequestBody Agendamento agendamento){
		return new ResponseEntity<Agendamento>(agendamentoService.agendar(agendamento), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deletar/todos")
	public ResponseEntity<String> deletarTudo() {
		return new ResponseEntity<String>(agendamentoService.deletarTudo(), HttpStatus.ACCEPTED);
	}

}