package com.barbearia.model;

import javax.persistence.Embeddable;

import com.barbearia.service.factory.IEndereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnderecoBR implements IEndereco {
	String cep;
	String uf;
	String bairro;
	String logradouro;
	String localidade;
	String complemento;
	// String ibge;
	// String gia;
	// String ddd;
	// String siafi;
}
