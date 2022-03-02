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
	String logradouro;
	String complemento;
	String bairro;
	String localidade;
	String uf;
	String ibge;
	String gia;
	String ddd;
	String siafi;
}
