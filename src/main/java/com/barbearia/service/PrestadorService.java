package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.utils.PessoaUtils;
import com.barbearia.service.utils.EnderecoUtils;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository prestadorRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Prestador> listarTodos() {
		if (prestadorRepository.findAll().isEmpty())
			throw new ApiRequestException(MensagensPessoas.TABELA_PRESTADORES_VAZIA.getMensagem());
		return prestadorRepository.findAll();
	}

	public Prestador adicionar(NovaPessoaDto novaPessoaDto) throws ApiRequestException {
		if (!EnderecoUtils.validaCodigoPostal(novaPessoaDto.getCodigoPostal()))
			throw new ApiRequestException(MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());

		if (!EnderecoUtils.validaComplemento(novaPessoaDto.getComplemento()))
			throw new ApiRequestException(MensagensPessoas.COMPLEMENTO_GRANDE.getMensagem());

		EnderecoDto enderecoDto = EnderecoService.montarEndereco(novaPessoaDto);

		novaPessoaDto.setCpf(PessoaUtils.formataCpf(novaPessoaDto.getCpf()));

		if (!PessoaUtils.validaCpf(novaPessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

		if (novaPessoaDto.getNome().isEmpty())
			throw new ApiRequestException(MensagensPessoas.NOME_VAZIO.getMensagem());

		if (verificaSePrestadorExiste(novaPessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.PRESTADOR_JA_EXISTE.getMensagem());

		if (clienteRepository.findByCpf(novaPessoaDto.getCpf()) != null)
			throw new ApiRequestException(MensagensPessoas.CPF_DE_CLIENTE.getMensagem());
		try {
			return prestadorRepository.save(new Prestador(novaPessoaDto.getCpf(), novaPessoaDto.getNome(), enderecoDto));
		} catch (org.springframework.transaction.TransactionSystemException e) {
			throw new ApiRequestException("CPF inválido");
		}
	}

	public Prestador detalharPrestador(String cpf) {
		if (!verificaSePrestadorExiste(cpf))
			throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());

		return prestadorRepository.findByCpf(cpf);
	}

	public Prestador alterarPrestador(AlteracaoPessoaDto pessoaDto) {
		pessoaDto.setCpf(PessoaUtils.formataCpf(pessoaDto.getCpf()));

		if (!PessoaUtils.validaCpf(pessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

		if (!verificaSePrestadorExiste(pessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());

		if (pessoaDto.getNomeNovo().isEmpty())
			throw new ApiRequestException(MensagensPessoas.NOME_VAZIO.getMensagem());

		Prestador prestador = prestadorRepository.findByCpf(pessoaDto.getCpf());

		prestador.setCpf(pessoaDto.getCpfNovo());
		prestador.setNome(pessoaDto.getNomeNovo());

		return prestador;
	}

	public List<Prestador> listarTodosPorCodigoPostal(String codigoPostal) {
		return prestadorRepository.findAllByPostal(codigoPostal);
	}

	// Validações

	public boolean verificaSePrestadorExiste(String cpf) {
		if (prestadorRepository.findByCpf(cpf) != null)
			return true;
		return false;
	}

}
