package com.totvs.barbearia.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.EnderecoService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class EnderecoServiceTest {

  @MockBean
  private IEndereco iEndereco;

  @Test
  public void sucesso_enderecoBr() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setOrigem("BR");
    novaPessoaDto.setCodigoPostal("74815435");
    novaPessoaDto.setComplemento("");

    Assertions.assertAll(() -> EnderecoService.montarEndereco(novaPessoaDto));
  }

  @Test
  public void sucesso_enderecoCa() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setOrigem("CA");
    novaPessoaDto.setCodigoPostal("M5H2N2");
    novaPessoaDto.setComplemento("");

    Assertions.assertAll(() -> EnderecoService.montarEndereco(novaPessoaDto));
  }

  @Test
  public void falha_instanciaNula() {
    NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
    novaPessoaDto.setOrigem("");
    novaPessoaDto.setCodigoPostal("M5H2N2");
    novaPessoaDto.setComplemento("");

    Assertions.assertThrows(NullPointerException.class, () -> 
      EnderecoService.montarEndereco(novaPessoaDto)
    );
  }
}
