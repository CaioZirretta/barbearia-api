package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.common.Common;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository prestadorRepository;

	public List<Prestador> listarTodos() {
		return prestadorRepository.findAll();
	}

	public Prestador adicionar(Prestador prestador) throws ApiRequestException {
		prestador.setCpf(Common.formataCpf(prestador.getCpf()));

		if (verificaSePrestadorExiste(prestador.getCpf()))
			throw new ApiRequestException("Prestador já existe!");

		if (!Common.validaCpf(prestador.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00 ");
		
		if(prestador.getNome().isEmpty())
			throw new ApiRequestException("O nome não pode estar vazio");

		return prestadorRepository.save(prestador);
	}

	public Prestador detalharPrestador(String cpf) {
		if (!verificaSePrestadorExiste(cpf))
			throw new ApiRequestException("Prestador não existe");

		return prestadorRepository.findByCpf(cpf);
	}

	public Prestador alterarPrestador(PessoaDto pessoaDto) {
		pessoaDto.setCpf(Common.formataCpf(pessoaDto.getCpf()));

		if (!Common.validaCpf(pessoaDto.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00 ");

		if (!verificaSePrestadorExiste(pessoaDto.getCpf()))
			throw new ApiRequestException("Prestador não existe");
		
		if(pessoaDto.getNomeNovo().isEmpty())
			throw new ApiRequestException("O nome não pode estar vazio");

		Prestador prestador = prestadorRepository.findByCpf(pessoaDto.getCpf());

		prestador.setCpf(pessoaDto.getCpfNovo());
		prestador.setNome(pessoaDto.getNomeNovo());

		return prestador;
	}

	// Validações
	
	public boolean verificaSePrestadorExiste(String cpf) {
		// Verifica se um prestador já existe
		if (prestadorRepository.findByCpf(cpf) != null)
			return true;
		return false;
	}
}
