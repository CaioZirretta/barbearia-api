package com.barbearia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovaPessoaDto {
	private String cpf;
	private String nome;
	private String codigoPostal;
	private String complemento;
	private String origem;
}
