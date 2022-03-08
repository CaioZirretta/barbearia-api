package com.barbearia.service.utils;

import com.barbearia.enums.Paises;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.service.factory.EnderecoFactory;
import com.barbearia.service.factory.IEndereco;

public class EnderecoUtils {

	public static boolean validaEndereco(String codigoPostal) {
		if (codigoPostal.matches(RegexUtils.brPostalRegex) || codigoPostal.matches(RegexUtils.caPostalRegex))
			return true;
		return false;
	}

	public static EnderecoDto montarEndereco(NovaPessoaDto novaPessoaDto) {
		IEndereco endereco = EnderecoFactory.enderecoFactory(novaPessoaDto.getOrigem());
		return endereco.requestEndereco(novaPessoaDto.getCodigoPostal());
	}

	public static String paisOrigem(String origem) {
		for (Paises pais : Paises.todos()) {
			if (origem.equalsIgnoreCase(pais.getPais())) {
				return pais.toString();
			}
		}
		return null;
	}
}
