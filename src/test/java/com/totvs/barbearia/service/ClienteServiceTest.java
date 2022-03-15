package com.totvs.barbearia.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.ClienteService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class ClienteServiceTest {

  @MockBean
  private IEndereco iEndereco;

  @Autowired
  private ClienteService clienteService;

  // Listar

  @Test
  public void listarTodos_sucessoTabelaPopulada() {
    Assertions.assertAll(() -> {
      clienteService.listarTodos();
    });
  }

  // Adicionar

  @Test
  public void adicionar_falhaCodigoPostalNulo() {
    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(new NovaPessoaDto());
    });
  }

  @Test
  public void adicionar_falhaComplementoNulo() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaOrigemNulo() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaNomeNulo() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("41248598806");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaPrestadorComMesmoCpf() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("36116037674");
    novaPessoaDto.setNome("Poste");

    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaClienteJaExiste() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("79302642208");
    novaPessoaDto.setNome("Poste");
    
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_sucessoSalvarCliente() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("43043489225"); // Alterar para um novo
    novaPessoaDto.setNome("Poste");
    
    Assertions.assertAll(() -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }
  
  @Test
  public void adicionar_falhaCpfInvalido() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("0");
    novaPessoaDto.setNome("Poste");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());

    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void detalhar_falhaCpfInvalido() {
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.detalharCliente("13929521903");
    });
  }
  
  @Test
  public void detalhar_sucesso() {
    Assertions.assertAll(() -> {
      clienteService.detalharCliente("65576447930");
    });
  }

}
