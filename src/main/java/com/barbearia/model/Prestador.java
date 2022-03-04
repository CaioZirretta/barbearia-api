package com.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

import com.barbearia.service.factory.IEndereco;

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
	private IEndereco endereco;

}
