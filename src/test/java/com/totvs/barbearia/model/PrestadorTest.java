package com.totvs.barbearia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.model.Prestador;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class PrestadorTest {

  @Test
  public void agendamento_sucessoCriacao() {
    Assertions.assertAll(() -> {
      Prestador prestador = new Prestador();
      prestador.setCpf(null);
      prestador.setEndereco(null);
      prestador.setEndereco(null);
      
      prestador.getClass();
      prestador.getCpf();
      prestador.getNome();
    });
  }
  
  @Test
  public void prestador_sucessoEquals() {
    Assertions.assertTrue(new Prestador().equals(new Prestador()));
  }
}