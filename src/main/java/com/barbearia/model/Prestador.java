package com.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
@Entity
@Table(name = "prestadores")
public class Prestador {

	@Id
	@CPF
	private String cpf;
	
	@Column(nullable = false) 
	private String nome;

}
