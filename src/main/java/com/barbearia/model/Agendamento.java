package com.barbearia.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
// import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "agendamentos" /*, @UniqueConstraint(columnNames = { "dia", "horario", "cpfCliente"})*/)
public class Agendamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private LocalDate dia;
	@NotNull
	private LocalTime horario;

	@Column(name = "cpf_cliente")
	private String cpfCliente;
	
	@Column(name = "cpf_prestador")
	private String cpfPrestador;

	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		this.dia = dia;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getCpfPrestador() {
		return cpfPrestador;
	}

	public void setCpfPrestador(String cpfPrestador) {
		this.cpfPrestador = cpfPrestador;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		return Objects.equals(id, other.id);
	}

}
