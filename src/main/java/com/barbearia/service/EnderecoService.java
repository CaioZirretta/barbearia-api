package com.barbearia.service;

import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.factory.EnderecoFactory;
import com.barbearia.service.factory.IEndereco;
import com.barbearia.service.utils.EnderecoUtils;

public class EnderecoService {
	public static EnderecoDto montarEndereco(NovaPessoaDto novaPessoaDto) {
		IEndereco endereco = EnderecoFactory.enderecoFactory(novaPessoaDto.getOrigem());
		EnderecoDto enderecoDto = endereco.requestEndereco(novaPessoaDto.getCodigoPostal());

		enderecoDto.setCountry(EnderecoUtils.paisOrigem(novaPessoaDto.getOrigem()));
		enderecoDto.setComplemento(novaPessoaDto.getComplemento());

		return enderecoDto;
	}
}
