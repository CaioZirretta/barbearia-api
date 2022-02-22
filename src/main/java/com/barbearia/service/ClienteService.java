package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.utils.Utils;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PrestadorRepository prestadorRepository;

	public List<Cliente> listarTodos() {
		if(clienteRepository.findAll() == null)
			throw new ApiRequestException("Não há clientes cadastrados");
		return clienteRepository.findAll();
	}

	public Cliente adicionar(Cliente cliente) throws ApiRequestException {
		cliente.setCpf(Utils.formataCpf(cliente.getCpf()));

		if (!Utils.validaCpf(cliente.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00 ");

		if (cliente.getNome().isEmpty())
			throw new ApiRequestException("O nome não pode estar vazio");
		
		if (prestadorRepository.findByCpf(cliente.getCpf()) != null)
			throw new ApiRequestException("CPF pertence a um prestador");

		if (verificaSeClienteExiste(cliente.getCpf()))
			throw new ApiRequestException("Cliente já existe!");

		return clienteRepository.save(cliente);
	}

	public Cliente detalharCliente(String cpf) {
		if (!verificaSeClienteExiste(cpf))
			throw new ApiRequestException("Cliente não existe");

		return clienteRepository.findByCpf(cpf);
	}

	public Cliente alterarCliente(PessoaDto pessoaDto) {
		pessoaDto.setCpf(Utils.formataCpf(pessoaDto.getCpf()));

		if (!Utils.validaCpf(pessoaDto.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00 ");

		if (!verificaSeClienteExiste(pessoaDto.getCpf()))
			throw new ApiRequestException("Cliente não existe");

		if (pessoaDto.getNomeNovo().isEmpty())
			throw new ApiRequestException("O nome não pode estar vazio");

		Cliente cliente = clienteRepository.findByCpf(pessoaDto.getCpf());

		cliente.setCpf(pessoaDto.getCpfNovo());
		cliente.setNome(pessoaDto.getNomeNovo());

		return cliente;
	}

	public boolean verificaSeClienteExiste(String cpf) {
		// Verifica se o cliente já existe
		if (clienteRepository.findByCpf(cpf) != null)
			return true;
		return false;
	}
}
