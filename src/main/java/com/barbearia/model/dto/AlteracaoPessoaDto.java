package com.barbearia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlteracaoPessoaDto {
	// Contém informações novas de clientes e prestadores
	private String cpf;
	private String nome;
}
