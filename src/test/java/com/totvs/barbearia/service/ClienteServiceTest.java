package com.totvs.barbearia.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.ClienteService;
import com.barbearia.service.EnderecoService;
import com.barbearia.service.PrestadorService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteServiceTest {

  @MockBean
  private IEndereco iEndereco;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private PrestadorService prestadorService;


  // Listar

  @Test
  @Order(0)
  public void listarTodos_falhaTabelaVazia() {
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.listarTodos();
    });
  }

  @Test
  @Order(100) // Evitar static
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
    novaPessoaDto.setCpf("93888053480");

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

    prestadorService.adicionar(novaPessoaDto);

    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  @Order(1)
  public void adicionar_falhaClienteJaExiste() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("23355576611");
    novaPessoaDto.setNome("Poste");
    
    clienteService.adicionar(novaPessoaDto);

    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  @Test
  @Order(2)
  public void adicionar_sucessoSalvarCliente() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("35842224941");
    novaPessoaDto.setNome("Poste");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());

    Assertions.assertAll(() -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }
  
  @Test
  @Order(1)
  public void adicionar_sucessoClienteInfoGravadas() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("65576447930");
    novaPessoaDto.setNome("Poste");
    
    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(null);

    Cliente cliente = new Cliente();
    cliente.setCpf("65576447930");
    cliente.setNome("Poste");
    cliente.setEndereco(EnderecoService.montarEndereco(novaPessoaDto)); 
    
    Assertions.assertEquals(cliente, clienteService.adicionar(novaPessoaDto));
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
  @Order(3)
  public void detalhar_falhaCpfInvalido() {
    Assertions.assertThrows(ApiRequestException.class, () -> {
      clienteService.detalharCliente("13929521903");
    });
  }

  @Test
  @Order(3)
  public void detalhar_sucesso() {

  }

}
