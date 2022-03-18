package com.totvs.barbearia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.EnderecoBR;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class EnderecoBRTest {
  @Test
  public void request_falhaRespostaInvalida(){
    Assertions.assertThrows(ApiRequestException.class, () -> {
      EnderecoBR endBr = new EnderecoBR();
      endBr.requestEndereco("0123");
    });
  }
  
  @Test
  public void request_sucesso() {
    Assertions.assertAll(() -> {
      EnderecoBR endBr = new EnderecoBR();
      endBr.requestEndereco("74815435");
    });
  }
}
