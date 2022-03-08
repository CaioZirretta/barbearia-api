package com.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.barbearia.model.dto.EnderecoDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	// @CPF
	private String cpf;
	
	@Column(nullable = false) 
	private String nome;
	
	@Embedded
	private EnderecoDto endereco;

}
