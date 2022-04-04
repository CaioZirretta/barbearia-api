package com.totvs.barbearia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.model.Cliente;
import com.barbearia.model.Endereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class ClienteTest {

  @Test
  public void cliente_sucessoCriacao() {
    Assertions.assertAll(() -> {
      Cliente cliente = new Cliente();
      cliente.setCpf(null);
      cliente.setEndereco(new Endereco());

      cliente.getCpf();
      cliente.getNome();
    });
  }
  
  @Test
  public void cliente_sucessoEquals() {
    Assertions.assertTrue(new Cliente().equals(new Cliente()));
  }
}
