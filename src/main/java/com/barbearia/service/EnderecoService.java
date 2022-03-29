package com.barbearia.service;

import com.barbearia.model.Endereco;
import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.service.factory.EnderecoFactory;
import com.barbearia.service.factory.IEndereco;
import com.barbearia.service.utils.EnderecoUtils;
import com.barbearia.service.utils.PessoaUtils;

public class EnderecoService {
  public static Endereco montarEndereco(PessoaDto novaPessoaDto) {
    IEndereco enderecoInstancia = EnderecoFactory.enderecoFactory(novaPessoaDto.getOrigem());
    EnderecoDto enderecoDto = enderecoInstancia.requestEndereco(PessoaUtils.formataString(
      novaPessoaDto.getCodigoPostal()));

    enderecoDto.setCountry(EnderecoUtils.paisOrigem(novaPessoaDto.getOrigem()));
    enderecoDto.setComplemento(novaPessoaDto.getComplemento());

    if (enderecoInstancia instanceof EnderecoBR)
      return montarEnderecoBr(enderecoDto);

    if (enderecoInstancia instanceof EnderecoCA)
      return montarEnderecoCa(enderecoDto);

    return null;
  }

  private static Endereco montarEnderecoCa(EnderecoDto enderecoDto) {
    Endereco endereco = new Endereco();
    
    endereco.setBairro(null);
    endereco.setCep(enderecoDto.getPostal());
    endereco.setComplemento(enderecoDto.getComplemento());
    endereco.setLocalidade(enderecoDto.getStandard().getCity());
    endereco.setLogradouro(null);
    endereco.setPais(enderecoDto.getCountry());
    endereco.setUf(enderecoDto.getStandard().getProv());
    
    return endereco;
  }

  public static Endereco montarEnderecoBr(EnderecoDto enderecoDto) {
    Endereco endereco = new Endereco();
    
    endereco.setBairro(enderecoDto.getBairro());
    endereco.setCep(enderecoDto.getCep());
    endereco.setComplemento(enderecoDto.getComplemento());
    endereco.setLocalidade(enderecoDto.getLocalidade());
    endereco.setLogradouro(enderecoDto.getLogradouro());
    endereco.setPais(enderecoDto.getCountry());
    endereco.setUf(enderecoDto.getUf());
    
    return endereco;
  }
}
