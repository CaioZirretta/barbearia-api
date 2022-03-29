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
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.service.ClienteService;
import com.barbearia.service.PrestadorService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class PrestadorServiceTest {

  @MockBean
  private IEndereco iEndereco;

  @Autowired
  private PrestadorService prestadorService;

  @Autowired
  private ClienteService clienteService;

  // Listar

  @Test
  public void listarTodos_sucessoTabelaPopulada() {
    Assertions.assertAll(() -> {
      prestadorService.listarTodos();
    });
  }

  // Adicionar

  @Test
  public void adicionar_falhaCodigoPostalNulo() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(new PessoaDto());
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());
  }

  @Test
  public void adicionar_falhaComplementoNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.COMPLEMENTO_GRANDE.getMensagem());
  }

  @Test
  public void adicionar_falhaOrigemNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
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
      prestadorService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CPF_INVALIDO.getMensagem());
  }

  @Test
  public void adicionar_falhaNomeNulo() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("74417162506");

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.NOME_INVALIDO.getMensagem());
  }

  @Test
  public void adicionar_falhaClienteComMesmoCpf() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("42887594559");
    novaPessoaDto.setNome("Poste");

    clienteService.adicionar(novaPessoaDto);

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CPF_DE_CLIENTE.getMensagem());
  }

  @Test
  public void adicionar_falhaPrestadorJaExiste() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("61512752410");
    novaPessoaDto.setNome("Poste");

    prestadorService.adicionar(novaPessoaDto);

    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.adicionar(novaPessoaDto);
    });

    assertEquals(exception.getMessage(), MensagensPessoas.PRESTADOR_JA_EXISTE.getMensagem());
  }

  @Test
  public void adicionar_sucessoSalvarPrestador() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("06372486946");
    novaPessoaDto.setNome("Poste");

    Assertions.assertAll(() -> {
      prestadorService.adicionar(novaPessoaDto);
    });
  }

  // Detalhar

  @Test
  public void detalhar_falhaCpfInvalido() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.detalharPrestador("123");
    });

    assertEquals(exception.getMessage(), MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());
  }

  @Test
  public void detalhar_sucesso() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("43322036154");
    novaPessoaDto.setNome("Poste");
    
    prestadorService.adicionar(novaPessoaDto);
    
    Assertions.assertAll(() -> {
      prestadorService.detalharPrestador("43322036154");
    });
  }

  // Alterar

  @Test
  public void alterar_falhaCpfInvalido() {
    AlteracaoPessoaDto pessoaDto = new AlteracaoPessoaDto();
    pessoaDto.setCpf("123");
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.alterarPrestador(pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CPF_INVALIDO.getMensagem());
  }

  @Test
  public void alterar_falhaCpfNovoInvalido() {
    AlteracaoPessoaDto pessoaDto = new AlteracaoPessoaDto();
    pessoaDto.setCpf("26063180990");
    pessoaDto.setCpfNovo("123");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.alterarPrestador(pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CPF_NOVO_INVALIDO.getMensagem());
  }
  
  @Test
  public void alterar_falhaNomeInvalido() {
    AlteracaoPessoaDto pessoaDto = new AlteracaoPessoaDto();
    pessoaDto.setCpf("14362820558");
    pessoaDto.setCpfNovo("67016748529");
    pessoaDto.setNomeNovo(null);
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.alterarPrestador(pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.NOME_INVALIDO.getMensagem());
  }

  @Test
  public void alterar_falhaCpfDePrestador() {
    AlteracaoPessoaDto pessoaDto = new AlteracaoPessoaDto();
    pessoaDto.setCpf("83178758143");
    pessoaDto.setCpfNovo("61723146536");
    pessoaDto.setNomeNovo("Dummy");
    
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("83178758143");
    novaPessoaDto.setNome("Poste");
    
    clienteService.adicionar(novaPessoaDto);
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.alterarPrestador(pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.CPF_DE_CLIENTE.getMensagem());
  }
  
  @Test
  public void alterar_falhaClienteNaoEncontrado() {
    AlteracaoPessoaDto pessoaDto = new AlteracaoPessoaDto();
    pessoaDto.setCpf("87586421889");
    pessoaDto.setCpfNovo("63525332300");
    pessoaDto.setNomeNovo("Dummy");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      prestadorService.alterarPrestador(pessoaDto);
    });
    
    assertEquals(exception.getMessage(), MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());
  }

  @Test
  public void alterar_sucesso() {
    AlteracaoPessoaDto pessoaDto = new AlteracaoPessoaDto();
    pessoaDto.setCpf("25852206806");
    pessoaDto.setNomeNovo("Poste");
    pessoaDto.setCpfNovo("32264088877");
    
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCpf("25852206806");
    novaPessoaDto.setNome("Poste");
    
    prestadorService.adicionar(novaPessoaDto);

    Assertions.assertAll(() -> {
      prestadorService.alterarPrestador(pessoaDto);
    });
  }
}
