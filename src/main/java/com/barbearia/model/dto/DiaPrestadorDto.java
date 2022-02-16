package com.barbearia.model.dto;

import java.time.LocalDate;

public class DiaPrestadorDto {
	private LocalDate dia;
	private String cpfPrestador;

	public DiaPrestadorDto() {
	}

	public DiaPrestadorDto(LocalDate data, String cpfPrestador) {
		this.dia = data;
		this.cpfPrestador = cpfPrestador;
	}

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public String getCpfPrestador() {
		return cpfPrestador;
	}

	public void setCpfPrestador(String cpfPrestador) {
		this.cpfPrestador = cpfPrestador;
	}

}
