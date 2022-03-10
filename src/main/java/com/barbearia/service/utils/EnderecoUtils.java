package com.barbearia.service.utils;

import com.barbearia.enums.Paises;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.factory.EnderecoFactory;
import com.barbearia.service.factory.IEndereco;

public class EnderecoUtils {

  public static EnderecoDto montarEndereco(NovaPessoaDto novaPessoaDto) {
    // Instancia
    IEndereco endereco = EnderecoFactory.enderecoFactory(novaPessoaDto.getOrigem());
    // Valores
    EnderecoDto enderecoDto = endereco.requestEndereco(novaPessoaDto.getCodigoPostal());

    enderecoDto.setCountry(EnderecoUtils.paisOrigem(novaPessoaDto.getOrigem()));
    enderecoDto.setComplemento(novaPessoaDto.getComplemento());

    return enderecoDto;
  }

  public static boolean validaEndereco(String codigoPostal) {
    if (codigoPostal.matches(RegexUtils.brPostalRegex)
        || codigoPostal.matches(RegexUtils.caPostalRegex))
      return true;
    return false;
  }

  public static boolean validaComplemento(String complemento) {
    if (complemento.length() > 50)
      return false;
    return true;
  }

  public static String paisOrigem(String origem) {
    for (Paises pais : Paises.getListaPaises()) {
      if (origem.equalsIgnoreCase(pais.getSigla()))
        return pais.toString();
    }
    return null;
  }

}
