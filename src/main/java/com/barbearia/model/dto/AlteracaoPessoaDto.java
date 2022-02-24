package com.barbearia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlteracaoPessoaDto {
	// Contém informações de clientes e prestadores
	private String cpf;
	private String nomeNovo;
	private String cpfNovo;
}
