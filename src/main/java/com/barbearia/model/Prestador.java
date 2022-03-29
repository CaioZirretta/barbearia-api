package com.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prestadores")
public class Prestador {

	@Id
	private String cpf;
	
	@Column(nullable = false) 
	private String nome;
	
	@Embedded
	@NotNull
	private Endereco endereco;

}
