package com.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.barbearia.model.dto.EnderecoDto;

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
	@CPF
	private String cpf;
	
	@Column(nullable = false) 
	private String nome;
	
	@Embedded
	@NotNull
	private EnderecoDto endereco;

}
