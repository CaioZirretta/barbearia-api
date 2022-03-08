package com.barbearia.model.dto;

import java.util.HashMap;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EnderecoDto {
	// Endereço BR 
	String cep;
	String uf;
	String bairro;
	String logradouro;
	String localidade;
	String complemento;
	
	// Endereço CA
	HashMap<Object, Object> standard;
	String postal;
}
