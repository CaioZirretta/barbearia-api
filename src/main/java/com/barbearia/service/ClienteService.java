package com.barbearia.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Cliente adicionar(Cliente cliente) throws ApiRequestException {
		cliente.setCpf(formataCpf(cliente.getCpf()));
		validaCpf(cliente.getCpf());
		validaSeClienteExiste(cliente.getCpf());

		return clienteRepository.save(cliente);
	}

	public List<Cliente> adicionarVarios(List<Cliente> clientes) {
		clientes.forEach(cliente -> {
			cliente.setCpf(formataCpf(cliente.getCpf()));

			validaCpf(cliente.getCpf());
			validaSeClienteExiste(cliente.getCpf());

			clienteRepository.save(cliente);
		});

		return clientes;
	}

	public Cliente detalharCliente(String cpf) {
		validaSeClienteNaoExiste(cpf);

		return clienteRepository.findByCpf(cpf);
	}

	public String deletarTudo() {
		JSONObject json = new JSONObject();
		json.put("message", "Registros apagados");

		clienteRepository.deleteAll();

		return json.toString();
	}

	public Cliente alterarCliente(String cpf, Cliente clienteAtualizado) {
		cpf = formataCpf(cpf);

		validaCpf(cpf);
		validaSeClienteNaoExiste(cpf);

		Cliente cliente = clienteRepository.findByCpf(cpf);

		cliente.setCpf(clienteAtualizado.getCpf());
		cliente.setNome(clienteAtualizado.getNome());

		return cliente;
	}

	public void validaCpf(String cpf) {
		String cpfRegex = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";
		String formatosAceitos = "Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00";

		if (cpf.isEmpty() || cpf.isBlank())
			throw new ApiRequestException("O campo CPF não pode estar vazio. " + formatosAceitos, HttpStatus.FORBIDDEN);

		if (!cpf.matches(cpfRegex))
			throw new ApiRequestException("CPF não é válido. " + formatosAceitos, HttpStatus.FORBIDDEN);
	}

	public String formataCpf(String cpf) {
		cpf.trim();
		cpf.replace("-", "");
		cpf.replace("/", "");
		cpf.replace(".", "");
		return cpf;
	}

	// Use para saber se o cliente já existe
	public void validaSeClienteExiste(String cpf) {
		if (clienteRepository.findByCpf(cpf) != null)
			throw new ApiRequestException("Cliente " + cpf + " já existe!", HttpStatus.FORBIDDEN);
	}

	// Use para saber se o cliente não existe
	public void validaSeClienteNaoExiste(String cpf) {
		if (clienteRepository.findByCpf(cpf) == null)
			throw new ApiRequestException("Cliente " + cpf + " não encontrado!", HttpStatus.NOT_FOUND);
	}
}
