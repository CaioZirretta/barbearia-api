package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.factory.EnderecoFactory;
import com.barbearia.service.factory.IEndereco;
import com.barbearia.service.utils.CpfUtils;
import com.barbearia.service.utils.EnderecoUtils;
import com.barbearia.service.utils.RequestExterno;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Prestador> listarTodos() {
		if(prestadorRepository.findAll() == null)
			throw new ApiRequestException("Não há prestadores cadastrados");
		return prestadorRepository.findAll();
	}

	public Prestador adicionar(NovaPessoaDto novaPessoaDto) throws ApiRequestException {
		IEndereco endereco = RequestExterno.requestEndereco(
				EnderecoFactory.enderecoFactory(novaPessoaDto.getCodigoPostal()), novaPessoaDto.getCodigoPostal());

		if (!EnderecoUtils.validaEndereco(endereco))
			throw new ApiRequestException("Endereço inválido.");
		
		novaPessoaDto.setCpf(CpfUtils.formataCpf(novaPessoaDto.getCpf()));

		if (!CpfUtils.validaCpf(novaPessoaDto.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido.");
		
		if (novaPessoaDto.getNome().isEmpty())
			throw new ApiRequestException("O nome não pode estar vazio");

		if (verificaSePrestadorExiste(novaPessoaDto.getCpf()))
			throw new ApiRequestException("Prestador já existe!");

		if (clienteRepository.findByCpf(novaPessoaDto.getCpf()) != null)
			throw new ApiRequestException("CPF pertence a um cliente");

		
		return prestadorRepository.save(new Prestador(novaPessoaDto.getCpf(), novaPessoaDto.getNome(), endereco));
	}

	public Prestador detalharPrestador(String cpf) {
		if (!verificaSePrestadorExiste(cpf))
			throw new ApiRequestException("Prestador não existe");

		return prestadorRepository.findByCpf(cpf);
	}

	public Prestador alterarPrestador(AlteracaoPessoaDto pessoaDto) {
		pessoaDto.setCpf(CpfUtils.formataCpf(pessoaDto.getCpf()));

		if (!CpfUtils.validaCpf(pessoaDto.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00 ");

		if (!verificaSePrestadorExiste(pessoaDto.getCpf()))
			throw new ApiRequestException("Prestador não existe");

		if (pessoaDto.getNomeNovo().isEmpty())
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
