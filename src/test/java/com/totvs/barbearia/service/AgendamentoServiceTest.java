package com.totvs.barbearia.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.application.BarbeariaApiApplication;
import com.barbearia.enums.MensagensAgendamento;
import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Agendamento;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.service.AgendamentoService;
import com.barbearia.service.ClienteService;
import com.barbearia.service.PrestadorService;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class AgendamentoServiceTest {

  @Autowired
  private AgendamentoService agendamentoService;

  @Autowired
  private PrestadorService prestadorService;

  @Autowired
  private ClienteService clienteService;
  
  // listarTodos()
  
  @Test
  public void listar_falhaTabelaVazia() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.listarTodos();
    });

    assertEquals(exception.getMessage(), MensagensAgendamento.TABELA_AGENDAMENTO_VAZIA.getMensagem());
  }

  // listarHorarioVagoMes()

  @Test
  public void listarHorarioVagosMes_falhaAnoMesInvalidos() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.listarHorarioVagoMes(null, null);
    });

    assertEquals(exception.getMessage(), MensagensAgendamento.ANO_MES_INVALIDOS.getMensagem());
  }

  @Test
  public void listarHorarioVagosMes_sucesso() {
    Assertions.assertAll(() -> {
      agendamentoService.listarHorarioVagoMes(2022, 8);
    });
  }

  // listarHorarioVagoDiaPrestador()

  @Test
  public void listarHorarioVagoDiaPrestador_falhaCpfInvalido() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.listarHorarioVagoDiaPrestador("2022-08-22", "01");
    });

    assertEquals(exception.getMessage(), MensagensPessoas.CPF_PRESTADOR_INVALIDO.getMensagem());
  }

  @Test
  public void listarHorarioVagoDiaPrestador_sucesso() {
    PessoaDto novaPessoaDto = dummyNovaPessoaDto();
    novaPessoaDto.setCpf("56521617796");
    
    prestadorService.adicionar(novaPessoaDto);
    
    Assertions.assertAll(() -> {
      agendamentoService.listarHorarioVagoDiaPrestador("2022-08-22", "56521617796");
    });
  }

  // procurarPorCpfCliente()

  @Test
  public void procurarPorCpfCliente_falhaCpfInvalido() {
    ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () -> {
      agendamentoService.procurarPorCpfCliente("0");
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CPF_INVALIDO
      .getMensagem()));
  }

  @Test
  public void procurarPorCpfCliente_falhaClienteNaoExiste() {
    ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () -> {
      agendamentoService.procurarPorCpfCliente("33632725616");
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CLIENTE_NAO_EXISTE
      .getMensagem()));
  }

  @Test
  public void procurarPorCpfCliente_sucesso() {
    PessoaDto novaPessoaDto = dummyNovaPessoaDto();
    novaPessoaDto.setCpf("32583911902");
    
    clienteService.adicionar(novaPessoaDto);
    
    Assertions.assertAll(() -> {
      agendamentoService.procurarPorCpfCliente("32583911902");
    });
  }

  // procurarPorCpfPrestador()

  @Test
  public void procurarPorCpfPrestador_falhaCpfInvalido() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.procurarPorCpfPrestador("0");
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CPF_INVALIDO
      .getMensagem()));
  }
//
  @Test
  public void procurarPorCpfPrestador_falhaPrestadorNaoExiste() {
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.procurarPorCpfPrestador("33632725616");
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.PRESTADOR_NAO_EXISTE
      .getMensagem()));
  }

  @Test
  public void procurarPorCpfPrestador_sucesso() {
    PessoaDto novaPessoaDto = dummyNovaPessoaDto();
    novaPessoaDto.setCpf("70050701274");
    
    prestadorService.adicionar(novaPessoaDto);
    
    Assertions.assertAll(() -> {
      agendamentoService.procurarPorCpfPrestador("70050701274");
    });
  }

  // cancelarAgendamento()

  @Test
  public void cancelarAgendamento_falhaAgendamentoInexistente() {
    PessoaDto novaPessoaDtoCliente = dummyNovaPessoaDto();
    novaPessoaDtoCliente.setCpf("18042610505");
    
    clienteService.adicionar(novaPessoaDtoCliente);
    
    PessoaDto novaPessoaDtoPrestador = dummyNovaPessoaDto();
    novaPessoaDtoPrestador.setCpf("42623273618");
    
    Agendamento agendamento = dummyAgendamento();
    agendamento.setCpfCliente(novaPessoaDtoCliente.getCpf());
    agendamento.setCpfPrestador(novaPessoaDtoPrestador.getCpf());
    
    prestadorService.adicionar(novaPessoaDtoPrestador);
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.cancelarAgendamento(agendamento);
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(
      MensagensAgendamento.AGENDAMENTO_NAO_ENCONTRADO.getMensagem()));
  }

  @Test
  public void cancelarAgendamento_sucesso() {
    PessoaDto novaPessoaDtoCliente = dummyNovaPessoaDto();
    novaPessoaDtoCliente.setCpf("47465556805");
    
    clienteService.adicionar(novaPessoaDtoCliente);
    
    PessoaDto novaPessoaDtoPrestador = dummyNovaPessoaDto();
    novaPessoaDtoPrestador.setCpf("54742636600");
    
    prestadorService.adicionar(novaPessoaDtoPrestador);
    
    Agendamento agendamento = dummyAgendamento();
    agendamento.setCpfCliente(novaPessoaDtoCliente.getCpf());
    agendamento.setCpfPrestador(novaPessoaDtoPrestador.getCpf());
    
    agendamentoService.agendar(agendamento);
    
    Assertions.assertAll(() -> {
      agendamentoService.cancelarAgendamento(agendamento);
    });
  }

  // agendar()

  @Test
  public void agendar_falhaCpfClienteInvalido() {
    Agendamento agendamento = new Agendamento();
    agendamento.setCpfCliente("3");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.agendar(agendamento);
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CPF_CLIENTE_INVALIDO
      .getMensagem()));
  }

  @Test
  public void agendar_falhaCpfPrestadorInvalido() {
    Agendamento agendamento = new Agendamento();
    agendamento.setCpfCliente("76788707286");
    agendamento.setCpfPrestador("3");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.agendar(agendamento);
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CPF_PRESTADOR_INVALIDO
      .getMensagem()));
  }
    
  @Test
  public void agendar_falhaClienteInexistente() {
    Agendamento agendamento = new Agendamento();
    agendamento.setCpfCliente("52653452480");
    agendamento.setCpfPrestador("82677010526");
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.agendar(agendamento);
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CLIENTE_NAO_EXISTE
      .getMensagem()));
  }
  
  @Test
  public void agendar_falhaPrestadorInexistente() {
    Agendamento agendamento = new Agendamento();
    agendamento.setCpfCliente("52653452480");
    agendamento.setCpfPrestador("82677010526");
    
    PessoaDto novaPessoaDto = dummyNovaPessoaDto();
    novaPessoaDto.setCpf("14172471269");
    
    clienteService.adicionar(novaPessoaDto);
    
    ApiRequestException exception = assertThrows(ApiRequestException.class, () -> {
      agendamentoService.agendar(agendamento);
    });

    assertTrue(exception.getMessage().equalsIgnoreCase(MensagensPessoas.CLIENTE_NAO_EXISTE
      .getMensagem()));
  }
  
  private PessoaDto dummyNovaPessoaDto() {
    PessoaDto novaPessoaDto = new PessoaDto();
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("complemento");
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setNome("Poste");
    
    return novaPessoaDto;
  }
  
  private Agendamento dummyAgendamento() {
    Agendamento agendamento = new Agendamento();
    agendamento.setDia(LocalDate.of(2022, 8, 12));
    agendamento.setHorario(LocalTime.of(16, 0));
    agendamento.setId(Long.parseLong("1"));
    
    return agendamento;
  }

}
