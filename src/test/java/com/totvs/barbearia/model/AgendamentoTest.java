package com.totvs.barbearia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.application.BarbeariaApiApplication;
import com.barbearia.model.Agendamento;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class AgendamentoTest {

  @Test
  public void agendamento_sucessoCriacao() {
    Assertions.assertAll(() -> {
      Agendamento agendamento = new Agendamento();
      agendamento.setId(null);
      agendamento.setDia(null);
      agendamento.setHorario(null);
      agendamento.setCpfCliente(null);
      agendamento.setCpfPrestador(null);
      
      agendamento.getClass();
      agendamento.getCpfCliente();
      agendamento.getCpfPrestador();
      agendamento.getDia();
      agendamento.getHorario();
      agendamento.getId();
    });
  }
  
  @Test
  public void agendamento_sucessoEquals() {
    Assertions.assertTrue(new Agendamento().equals(new Agendamento()));
  }
}
