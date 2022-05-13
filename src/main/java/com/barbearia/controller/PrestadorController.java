package com.barbearia.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barbearia.model.Prestador;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.service.PrestadorService;

@RestController
@RequestMapping("/prestadores")
public class PrestadorController {

  @Autowired
  private PrestadorService prestadorService;

  @GetMapping("/todos")
  public ResponseEntity<List<Prestador>> listarTodos() {
    return new ResponseEntity<List<Prestador>>(prestadorService.listarTodos(), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Prestador> detalharPrestador(@RequestParam String cpf) {
    return new ResponseEntity<Prestador>(prestadorService.detalharPrestador(cpf), HttpStatus.OK);
  }

  @GetMapping("/{codigoPostal}")
  public ResponseEntity<List<Prestador>> listarTodosPorCodigoPostal(
      @PathVariable String codigoPostal) {
    return new ResponseEntity<List<Prestador>>(prestadorService.listarTodosPorCodigoPostal(
      codigoPostal), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Prestador> adicionar(@RequestBody PessoaDto novaPessoaDto) {
    return new ResponseEntity<Prestador>(prestadorService.adicionar(novaPessoaDto),
      HttpStatus.CREATED);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<Prestador> alterarPrestador(
      @RequestParam String cpf,
      @RequestBody PessoaDto pessoaDto) {
    return new ResponseEntity<Prestador>(prestadorService.alterarPrestador(cpf, pessoaDto),
      HttpStatus.OK);
  }
  
  @DeleteMapping
  public ResponseEntity<String> apagarTodos(@RequestParam String cpf) {
    prestadorService.apagarPrestador(cpf);
    return new ResponseEntity<String>(HttpStatus.OK);
  }

}
