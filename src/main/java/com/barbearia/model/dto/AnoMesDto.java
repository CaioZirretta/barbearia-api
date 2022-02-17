package com.barbearia.model.dto;

public class AnoMesDto {
	// Contém o ano e o mês de uma data em informações separadas
	private int mes;
	private int ano;

	public AnoMesDto() {
	}

	public AnoMesDto(Integer mes, Integer ano) {
		super();
		this.mes = mes;
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

}
