package com.totvs.barbearia.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.service.ClienteService;
import com.barbearia.service.PrestadorService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class ClienteServiceTest {

  @MockBean
  private IEndereco iEndereco;

  @Autowired
  private ClienteService clienteService;

  @Autowired
  private PrestadorService prestadorService;

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
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(new PessoaDto());
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());
  }

  @Test
  public void adicionar_falhaComplementoNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.COMPLEMENTO_GRANDE.getMensagem());
  }

  @Test
  public void adicionar_falhaOrigemNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.ORIGEM_INVALIDA.getMensagem());
  }

  @Test
  public void adicionar_falhaCpfNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CPF_INVALIDO.getMensagem());
  }

  @Test
  public void adicionar_falhaNomeNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("41248598806");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.NOME_INVALIDO.getMensagem());
  }

  @Test
  public void adicionar_falhaPrestadorComMesmoCpf() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("36116037674");
    novaPessoaDto.setNome("Poste");

    prestadorService.adicionar(novaPessoaDto);

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());
  }

  @Test
  public void adicionar_falhaClienteJaExiste() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("79302642208");
    novaPessoaDto.setNome("Poste");

    clienteService.adicionar(novaPessoaDto);

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CLIENTE_JA_EXISTE.getMensagem());
  }

  @Test
  public void adicionar_sucessoSalvarCliente() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("17283242384");
    novaPessoaDto.setNome("Poste");

    Assertions.assertAll(() -> {
      clienteService.adicionar(novaPessoaDto);
    });
  }

  // Detalhar

  @Test
  public void detalhar_falhaCpfInvalido() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.detalharCliente("123");
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());
  }

  @Test
  public void detalhar_sucesso() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("47816853580");
    novaPessoaDto.setNome("Poste");
    
    clienteService.adicionar(novaPessoaDto);
    
    Assertions.assertAll(() -> {
      clienteService.detalharCliente("47816853580");
    });
  }

  // Alterar
  // Valores de CPF errados, avaliar caso a caso 

  @Test
  public void alterar_falhaCpfInvalido() {
    PessoaDto pessoaDto = new PessoaDto();
    pessoaDto.setCpf("123");
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.alterarCliente("123", pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CPF_INVALIDO.getMensagem());
  }

  @Test
  public void alterar_falhaCpfNovoInvalido() {
    PessoaDto pessoaDto = new PessoaDto();
    pessoaDto.setCpf("43176353723");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.alterarCliente("43176353723", pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CPF_NOVO_INVALIDO.getMensagem());
  }
  
  @Test
  public void alterar_falhaNomeInvalido() {
    PessoaDto pessoaDto = new PessoaDto();
    pessoaDto.setCpf("43176353723");
    pessoaDto.setNome(null);
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.alterarCliente("43176353723" ,pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.NOME_INVALIDO.getMensagem());
  }

  @Test
  public void alterar_falhaCpfDePrestador() {
    PessoaDto pessoaDto = new PessoaDto();
    pessoaDto.setCpf("36360825902");
    pessoaDto.setNome("Dummy");
    
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("36360825902");
    novaPessoaDto.setNome("Poste");
    
    prestadorService.adicionar(novaPessoaDto);
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.alterarCliente("36360825902", pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());
  }
  
  @Test
  public void alterar_falhaClienteNaoEncontrado() {
    PessoaDto pessoaDto = new PessoaDto();
    pessoaDto.setCpf("87586421889");
    pessoaDto.setNome("Dummy");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      clienteService.alterarCliente("87586421889", pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());
  }

  @Test
  public void alterar_sucesso() {
    PessoaDto pessoaDto = new PessoaDto();
    pessoaDto.setCpf("49176835642");
    pessoaDto.setNome("Poste");
    
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("49176835642");
    novaPessoaDto.setNome("Poste");
    
    clienteService.adicionar(novaPessoaDto);

    Assertions.assertAll(() -> {
      clienteService.alterarCliente("49176835642", pessoaDto);
    });
  }
}
