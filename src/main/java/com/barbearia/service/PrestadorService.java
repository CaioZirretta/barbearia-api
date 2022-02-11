package com.barbearia.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Prestador;
import com.barbearia.repository.PrestadorRepository;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository prestadorRepository;

	public ResponseEntity<List<Prestador>> listarTodos() {
		return new ResponseEntity<List<Prestador>>(prestadorRepository.findAll(), HttpStatus.ACCEPTED);
	}

	public ResponseEntity<Prestador> adicionar(Prestador prestador) throws ApiRequestException {
		prestador.setCpf(formataCpf(prestador.getCpf()));
		validaCpf(prestador.getCpf());
		validaSePrestadorExiste(prestador.getCpf());

		return new ResponseEntity<Prestador>(prestadorRepository.save(prestador), HttpStatus.CREATED);
	}

	public ResponseEntity<List<Prestador>> adicionarVarios(List<Prestador> prestadores) {
		prestadores.forEach(prestador -> {
			prestador.setCpf(formataCpf(prestador.getCpf()));

			validaCpf(prestador.getCpf());
			validaSePrestadorExiste(prestador.getCpf());

			prestadorRepository.save(prestador);
		});

		return new ResponseEntity<List<Prestador>>(prestadores, HttpStatus.CREATED);
	}

	public ResponseEntity<Prestador> detalharPrestador(String cpf) {
		validaSePrestadorNaoExiste(cpf);

		return new ResponseEntity<Prestador>(prestadorRepository.findByCpf(cpf), HttpStatus.ACCEPTED);
	}

	public ResponseEntity<String> deletarTudo() {
		JSONObject json = new JSONObject();
		json.put("message", "Registros apagados");

		prestadorRepository.deleteAll();

		return new ResponseEntity<String>(json.toString(), HttpStatus.ACCEPTED);
	}

	public ResponseEntity<Prestador> alterarCliente(String cpf, Prestador prestadorAtualizado) {
		cpf = formataCpf(cpf);

		validaCpf(cpf);
		validaSePrestadorNaoExiste(cpf);

		Prestador prestador = prestadorRepository.findByCpf(cpf);

		prestador.setCpf(prestadorAtualizado.getCpf());
		prestador.setNome(prestador.getNome());

		return new ResponseEntity<Prestador>(prestador, HttpStatus.CREATED);
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

	// Use para saber se o prestador já existe
	public void validaSePrestadorExiste(String cpf) {
		if (prestadorRepository.findByCpf(cpf) != null)
			throw new ApiRequestException("Prestador " + cpf + " já existe!", HttpStatus.FORBIDDEN);
	}

	// Use para saber se o prestador não existe
	public void validaSePrestadorNaoExiste(String cpf) {
		if (prestadorRepository.findByCpf(cpf) == null)
			throw new ApiRequestException("Prestador " + cpf + " não encontrado!", HttpStatus.NOT_FOUND);
	}
}
