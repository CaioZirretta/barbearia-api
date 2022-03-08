package com.barbearia.service.factory;

import com.barbearia.model.dto.EnderecoDto;

public interface IEndereco{
	public EnderecoDto requestEndereco(String codigoPostal);
}
