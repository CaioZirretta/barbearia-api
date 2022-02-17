package com.barbearia.model.dto;

public class PessoaDto {
	// Contém informações de clientes e prestadores
	private String cpf;
	private String nomeNovo;
	private String cpfNovo;

	public PessoaDto() {

	}

	public PessoaDto(String cpf, String nomeNovo, String cpfNovo) {
		this.cpf = cpf;
		this.nomeNovo = nomeNovo;
		this.cpfNovo = cpfNovo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeNovo() {
		return nomeNovo;
	}

	public void setNomeNovo(String nomeNovo) {
		this.nomeNovo = nomeNovo;
	}

	public String getCpfNovo() {
		return cpfNovo;
	}

	public void setCpfNovo(String cpfNovo) {
		this.cpfNovo = cpfNovo;
	}

}
