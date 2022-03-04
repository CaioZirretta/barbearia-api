package com.barbearia.enums;

public enum Mensagens {
	ERRO("");
	
	private String mensagem;
	
	Mensagens(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

}
