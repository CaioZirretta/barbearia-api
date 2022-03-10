package com.barbearia.model.dto;

import lombok.Data;

@Data
public class EnderecoBrDto {
	String country;
	
	String cep;
	String uf;
	String bairro;
	String logradouro;
	String localidade;
	String complemento;
}
