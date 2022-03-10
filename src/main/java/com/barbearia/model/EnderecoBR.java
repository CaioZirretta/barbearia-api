package com.barbearia.model;

import org.springframework.web.client.HttpClientErrorException;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.service.factory.IEndereco;
import com.barbearia.service.utils.RequestExterno;

import lombok.Data;

@Data
public class EnderecoBR implements IEndereco {

	public EnderecoDto requestEndereco(String codigoPostal) {
		String url = "https://viacep.com.br/ws/" + codigoPostal + "/json/";
		EnderecoDto endereco = null;

		try {
			endereco = RequestExterno.getRestTemplate().getForObject(url, EnderecoDto.class);
		} catch (HttpClientErrorException e) {
			throw new ApiRequestException("Formato inválido da resposta: " + e.getCause());
		}
		return endereco;
	}
}
