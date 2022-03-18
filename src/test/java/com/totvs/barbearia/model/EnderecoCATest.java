package com.totvs.barbearia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.EnderecoCA;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class EnderecoCATest {

  @Test
  public void request_falhaRespostaInvalida(){
    Assertions.assertThrows(ApiRequestException.class, () -> {
      EnderecoCA endCa = new EnderecoCA();
      endCa.requestEndereco("0123");
    });
  }
  
  @Test
  public void request_sucesso() {
    Assertions.assertAll(() -> {
      EnderecoCA endCa = new EnderecoCA();
      endCa.requestEndereco("T0H2N2");
    });
  }
}
