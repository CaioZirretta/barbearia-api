package com.barbearia.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.barbearia.model.Agendamento;
import com.barbearia.service.AgendamentoService;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

  @Autowired
  private AgendamentoService agendamentoService;

  @GetMapping
  public ResponseEntity<List<Agendamento>> listarTodos() {
    return new ResponseEntity<List<Agendamento>>(agendamentoService.listarTodos(), HttpStatus.OK);
  }

  @GetMapping("/cliente")
  public ResponseEntity<List<Agendamento>> procurarPorCpfCliente(@RequestParam String cpf) {
    return new ResponseEntity<List<Agendamento>>(agendamentoService.procurarPorCpfCliente(cpf),
      HttpStatus.OK);
  }

  @GetMapping("/prestador")
  public ResponseEntity<List<Agendamento>> procurarPorCpfPrestador(@RequestParam String cpf) {
    return new ResponseEntity<List<Agendamento>>(agendamentoService.procurarPorCpfPrestador(cpf),
      HttpStatus.OK);
  }

  @GetMapping("/horarios")
  public ResponseEntity<List<LocalTime>> listarHorarioVagoDiaPrestador(
      @RequestParam("data") String data,
      @RequestParam("cpfPrestador") String cpfPrestador) {
    return new ResponseEntity<List<LocalTime>>(agendamentoService.listarHorarioVagoDiaPrestador(data,
      cpfPrestador),
      HttpStatus.OK);
  }

  @GetMapping("/horarios/mes")
  public ResponseEntity<List<LocalDate>> listarHorarioVagoMes(
      @RequestParam("mes") Integer mes,
      @RequestParam("ano") Integer ano) {
    return new ResponseEntity<List<LocalDate>>(agendamentoService.listarHorarioVagoMes(mes, ano),
      HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Agendamento> agendar(@RequestBody Agendamento agendamento) {
    return new ResponseEntity<Agendamento>(agendamentoService.agendar(agendamento),
      HttpStatus.CREATED);
  }

  @DeleteMapping
  public ResponseEntity<String> cancelarAgendamento(@RequestBody Agendamento agendamento) {
    agendamentoService.cancelarAgendamento(agendamento);
    return new ResponseEntity<String>(HttpStatus.OK);
  }
}
