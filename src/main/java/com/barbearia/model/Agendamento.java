package com.barbearia.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
// import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "agendamentos")
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

}
