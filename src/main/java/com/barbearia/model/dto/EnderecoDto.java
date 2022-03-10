package com.barbearia.model.dto;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Data;

@Data
@Embeddable
public class EnderecoDto {
	String country;

	// Endereço BR
	String cep;
	String uf;
	String bairro;
	String logradouro;
	String localidade;
	String complemento;

	// Endereço CA
	@Embedded
	StandardDto standard;
	String postal;
}
