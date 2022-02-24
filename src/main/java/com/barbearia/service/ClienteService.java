package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.model.EnderecoBR;
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.utils.Utils;

import net.bytebuddy.asm.Advice.Thrown;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PrestadorRepository prestadorRepository;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public ClienteService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
	public List<Cliente> listarTodos() {
		if(clienteRepository.findAll() == null)
			throw new ApiRequestException("Não há clientes cadastrados");
		return clienteRepository.findAll();
	}

	public Cliente adicionar(NovaPessoaDto novaPessoaDto) throws ApiRequestException {
		novaPessoaDto.setCpf(Utils.formataCpf(novaPessoaDto.getCpf()));

		if (!Utils.validaCpf(novaPessoaDto.getCpf()))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00 ");

		if (novaPessoaDto.getNome().isEmpty())
			throw new ApiRequestException("O nome não pode estar vazio");
		
		if (prestadorRepository.findByCpf(novaPessoaDto.getCpf()) != null)
			throw new ApiRequestException("CPF pertence a um prestador");

		if (verificaSeClienteExiste(novaPessoaDto.getCpf()))
			throw new ApiRequestException("Cliente já existe!");

		if(!validaEndereco(novaPessoaDto.getCodigoPostal()))
			throw new ApiRequestException("Endereço inválido");
		
		return null;
	}

	public Cliente detalharCliente(String cpf) {
		if (!verificaSeClienteExiste(cpf))
			throw new ApiRequestException("Cliente não existe");

		return clienteRepository.findByCpf(cpf);
	}

	public Cliente alterarCliente(AlteracaoPessoaDto pessoaDto) {
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

	public boolean validaEndereco(String codigoPostal){
		// TODO validações de endereço
		// TODO enviar req para retornar endereço
		
		String url = "https://viacep.com.br/ws/01001000/json/";
		EnderecoBR endBr = restTemplate.getForObject(url, EnderecoBR.class);
		return false;
	}
}
