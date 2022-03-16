package com.barbearia.enums;

public enum MensagensAgendamento {
	TABELA_AGENDAMENTO_VAZIA("Não existem agendamentos marcados."),
	AGENDAMENTO_NAO_ENCONTRADO("Agendamento não encontrado."),
	ANO_MES_INVALIDOS("Informações inválidas."),
	HORARIO_OCUPADO("Não há horários vagos no dia selecionado."),
	CLIENTE_SEM_AGENDAMENTO("Não foram encontrados agendamentos para este cliente."),
	HORARIO_FUTURO_INVALIDO("Não é possível agendar para datas ou horários passados"),
	HORARIO_NAO_COMERCIAL("Horário inválido. Horários disponíveis entre 8 e 17."),
	DATA_NAO_UTIL("Data inválida. Sábados e domingos não funcionam."),
	CLIENTE_HORARIO_OCUPADO_DIA("Cliente já possui horário marcado no dia."),
	CLIENTE_HORARIO_OCUPADO("Horário já ocupada pelo cliente neste dia"),
	PRESTADOR_HORARIO_OCUPADO("Horário não disponível para o prestador."), 
	DATA_INVALIDA("Data inválida");
	
	private String mensagem;
	
	MensagensAgendamento(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
}
