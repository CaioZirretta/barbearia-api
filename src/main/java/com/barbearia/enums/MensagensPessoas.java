package com.barbearia.enums;

public enum MensagensPessoas {
	TABELA_CLIENTES_VAZIA("Não há clientes cadastrados"),
	TABELA_PRESTADORES_VAZIA("Não há prestadores cadastrados"),
	CPF_INVALIDO("CPF não é válido"),
	NOME_VAZIO("O nome não pode estar vazio"),
	CPF_DE_PRESTADOR("O CPF pertence a um prestador"),
	CPF_DE_CLIENTE("O CPF pertence a um cliente"),
	CLIENTE_JA_EXISTE("O cliente já foi cadastrado"),
	PRESTADOR_JA_EXISTE("O prestador já foi cadastrado"),
	CLIENTE_NAO_EXISTE("Cliente não cadastrado"),
	PRESTADOR_NAO_EXISTE("Prestador não cadastrado"),
	CODIGO_POSTAL_INVALIDO("Código postal inválido");
	
	private String mensagem;
	
	MensagensPessoas(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

}
