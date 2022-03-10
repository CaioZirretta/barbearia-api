package com.barbearia.model;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.service.factory.IEndereco;
import com.barbearia.service.utils.RequestExterno;

import lombok.Data;

@Data
public class EnderecoCA implements IEndereco {

	public EnderecoDto requestEndereco(String codigoPostal) {
		String url = "https://geocoder.ca/" + codigoPostal + "?json=1";
		EnderecoDto endereco = RequestExterno.getRestTemplate().getForObject(url, EnderecoDto.class);

		if (endereco.getPostal() == null)
			throw new ApiRequestException("Formato inválido da resposta");

		return endereco;
	}
}
