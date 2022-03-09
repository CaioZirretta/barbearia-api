package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.utils.CpfUtils;
import com.barbearia.service.utils.EnderecoUtils;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PrestadorRepository prestadorRepository;

	public List<Cliente> listarTodos() {
		if (clienteRepository.findAll().isEmpty())
			throw new ApiRequestException(MensagensPessoas.TABELA_CLIENTES_VAZIA.getMensagem());
		return clienteRepository.findAll();
	}

	public Cliente adicionar(NovaPessoaDto novaPessoaDto) throws ApiRequestException {
		if (!EnderecoUtils.validaEndereco(novaPessoaDto.getCodigoPostal()))
			throw new ApiRequestException(MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());

		EnderecoDto enderecoDto = EnderecoUtils.montarEndereco(novaPessoaDto);

		enderecoDto.setCountry(EnderecoUtils.paisOrigem(novaPessoaDto.getOrigem()));
		enderecoDto.setComplemento(novaPessoaDto.getComplemento());


		novaPessoaDto.setCpf(CpfUtils.formataCpf(novaPessoaDto.getCpf()));

		if (!CpfUtils.validaCpf(novaPessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

		if (novaPessoaDto.getNome().isEmpty())
			throw new ApiRequestException(MensagensPessoas.NOME_VAZIO.getMensagem());

		if (prestadorRepository.findByCpf(novaPessoaDto.getCpf()) != null)
			throw new ApiRequestException(MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());

		if (verificaSeClienteExiste(novaPessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_JA_EXISTE.getMensagem());

		try {
			return clienteRepository.save(new Cliente(novaPessoaDto.getCpf(), novaPessoaDto.getNome(), enderecoDto));
		} catch (org.springframework.transaction.TransactionSystemException e) {
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());
		}
	}

	public Cliente detalharCliente(String cpf) {
		if (!verificaSeClienteExiste(cpf))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());

		return clienteRepository.findByCpf(cpf);
	}

	public List<Prestador> procurarPrestadores(String codigoPostal) {
		return prestadorRepository.findAllByPostal(codigoPostal);
	}

	public Cliente alterarCliente(AlteracaoPessoaDto pessoaDto) {
		pessoaDto.setCpf(CpfUtils.formataCpf(pessoaDto.getCpf()));

		if (!CpfUtils.validaCpf(pessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

		if (!verificaSeClienteExiste(pessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());

		if (pessoaDto.getNomeNovo().isEmpty())
			throw new ApiRequestException(MensagensPessoas.NOME_VAZIO.getMensagem());

		Cliente cliente = clienteRepository.findByCpf(pessoaDto.getCpf());

		cliente.setCpf(pessoaDto.getCpfNovo());
		cliente.setNome(pessoaDto.getNomeNovo());

		return cliente;
	}

	// Validações

	public boolean verificaSeClienteExiste(String cpf) {
		// Verifica se o cliente já existe
		if (clienteRepository.findByCpf(cpf) != null)
			return true;
		return false;
	}

}
