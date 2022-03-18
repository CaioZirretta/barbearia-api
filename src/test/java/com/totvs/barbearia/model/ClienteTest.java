package com.totvs.barbearia.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.model.Cliente;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.StandardDto;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class ClienteTest {

  @Test
  public void cliente_sucessoCriacao() {
    Assertions.assertAll(() -> {
      Cliente cliente = new Cliente();
      cliente.setCpf(null);
      cliente.setEndereco(new EnderecoDto());
      
//      cliente.getEndereco().setBairro(null);
//      cliente.getEndereco().setCep(null);
//      cliente.getEndereco().setComplemento(null);
//      cliente.getEndereco().setCountry(null);
//      cliente.getEndereco().setLocalidade(null);
//      cliente.getEndereco().setLogradouro(null);
//      cliente.getEndereco().setPostal(null);
//      cliente.getEndereco().setStandard(new StandardDto());
//      cliente.getEndereco().setUf(null);
//      
//      cliente.getEndereco().getStandard().setCity(null);
//      cliente.getEndereco().getStandard().setProv(null);
      
      cliente.getClass();
      cliente.getCpf();
      cliente.getNome();
    });
  }
  
  @Test
  public void cliente_sucessoEquals() {
    Assertions.assertTrue(new Cliente().equals(new Cliente()));
  }
}
