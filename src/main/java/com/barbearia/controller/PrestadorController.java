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

import com.barbearia.model.Prestador;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.service.PrestadorService;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {
	
	@Autowired
	private PrestadorService prestadorService;
	
	@GetMapping("/listar/todos")
	public ResponseEntity<List<Prestador>> listarTodos(){
		return new ResponseEntity<List<Prestador>>(prestadorService.listarTodos(), HttpStatus.OK); 
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<Prestador> detalhar(@PathVariable String cpf) {
		return new ResponseEntity<Prestador>(prestadorService.detalharPrestador(cpf), HttpStatus.OK);
	}
	
	@PostMapping("/adicionar")
	public ResponseEntity<Prestador> adicionar(@RequestBody Prestador prestador)  {
		return new ResponseEntity<Prestador>(prestadorService.adicionar(prestador), HttpStatus.CREATED);
	}
	
	@PutMapping("/alterar")
	@Transactional
	public ResponseEntity<Prestador> alterarCliente(@RequestBody PessoaDto pessoaDto) {
		return new ResponseEntity<Prestador>(prestadorService.alterarPrestador(pessoaDto), HttpStatus.OK);
	}

}
