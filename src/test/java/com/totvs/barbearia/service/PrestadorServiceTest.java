package com.totvs.barbearia.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.ClienteService;
import com.barbearia.service.PrestadorService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrestadorServiceTest {

  @Mock
  private IEndereco iEndereco;

  @Autowired
  @InjectMocks
  private ClienteService clienteService;

  @Autowired
  @InjectMocks
  private PrestadorService prestadorService;

  @Autowired
  @Mock
  private PrestadorRepository prestadorRepository;

  @Autowired
  @Mock
  private ClienteRepository clienteRepository;
  
  // Listar

  @Test
  @Order(1)
  public void listarTodos_falhaTabelaVazia() {
    Assertions.assertThrows(ApiRequestException.class, () -> {
      prestadorService.listarTodos();
    });
  }
  
  @Test
  @Order(100) // Evitar static
  public void listarTodos_sucessoTabelaPopulada() {
    Assertions.assertAll(() -> {
      prestadorService.listarTodos();
    });
  }

  // Adicionar

  @Test
  public void adicionar_falhaCodigoPostalNulo() {
    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(new NovaPessoaDto());
    });
  }

  @Test
  public void adicionar_falhaComplementoNulo() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaOrigemNulo() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    Assertions.assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
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
      prestadorService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaClienteComMesmoCpf() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("45010739852");
    novaPessoaDto.setNome("Teste");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
    
    clienteService.adicionar(novaPessoaDto);

    Assertions.assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });
  }

  @Test
  public void adicionar_falhaPrestadorJaExiste() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("35842224941");
    novaPessoaDto.setNome("Poste");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());

    Prestador prestador = new Prestador();
    prestador .setCpf("35842224941");

    prestadorRepository.save(new Prestador(novaPessoaDto.getCpf(), novaPessoaDto.getNome(),
        iEndereco.requestEndereco(novaPessoaDto.getCodigoPostal())));

    Assertions.assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });
  }

  @Test
  @Order(2)
  public void adicionar_sucessoSalvarPrestador() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("43732624110");
    novaPessoaDto.setNome("Poste");

    Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());

    Assertions.assertAll(() -> {
      prestadorService.adicionar(novaPessoaDto);
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
    
    Assertions.assertThrows(ApiRequestException.class,() -> {
      prestadorService.adicionar(novaPessoaDto);
    });
  }
}
