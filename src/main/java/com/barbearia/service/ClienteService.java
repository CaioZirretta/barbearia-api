package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
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
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PrestadorRepository prestadorRepository;

	public List<Cliente> listarTodos() {
		if (clienteRepository.findAll() == null)
			throw new ApiRequestException(MensagensPessoas.TABELA_CLIENTES_VAZIA.getMensagem());
		return clienteRepository.findAll();
	}

	public Cliente adicionar(NovaPessoaDto novaPessoaDto) throws ApiRequestException {

		IEndereco endereco = RequestExterno.requestEndereco(
				EnderecoFactory.enderecoFactory(novaPessoaDto.getCodigoPostal()), novaPessoaDto.getCodigoPostal());

		if (!EnderecoUtils.validaEndereco(endereco))
			throw new ApiRequestException("Endereço inválido.");

		novaPessoaDto.setCpf(CpfUtils.formataCpf(novaPessoaDto.getCpf()));

		if (!CpfUtils.validaCpf(novaPessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

		if (novaPessoaDto.getNome().isEmpty())
			throw new ApiRequestException(MensagensPessoas.NOME_VAZIO.getMensagem());

		if (prestadorRepository.findByCpf(novaPessoaDto.getCpf()) != null)
			throw new ApiRequestException(MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());

		if (verificaSeClienteExiste(novaPessoaDto.getCpf()))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_JA_EXISTE.getMensagem());

		return clienteRepository.save(new Cliente(novaPessoaDto.getCpf(), novaPessoaDto.getNome(), endereco));
	}

	public Cliente detalharCliente(String cpf) {
		if (!verificaSeClienteExiste(cpf))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());

		return clienteRepository.findByCpf(cpf);
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
