package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Cliente;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.AlteracaoPessoaDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.utils.PessoaUtils;
import com.barbearia.service.utils.EnderecoUtils;

@Service
public class ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private PrestadorService prestadorService;

  public List<Cliente> listarTodos() {
    if (clienteRepository.findAll().isEmpty())
      throw new ApiRequestException(MensagensPessoas.TABELA_CLIENTES_VAZIA.getMensagem());
    return clienteRepository.findAll();
  }

  public Cliente adicionar(NovaPessoaDto novaPessoaDto) throws ApiRequestException {

    if (!EnderecoUtils.validaCodigoPostal(novaPessoaDto.getCodigoPostal()))
      throw new ApiRequestException(MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());

    if (!EnderecoUtils.validaComplemento(novaPessoaDto.getComplemento()))
      throw new ApiRequestException(MensagensPessoas.COMPLEMENTO_GRANDE.getMensagem());

    if(!EnderecoUtils.validaOrigem(novaPessoaDto.getOrigem()))
    	throw new ApiRequestException(MensagensPessoas.ORIGEM_INVALIDA.getMensagem());
    
    if (!PessoaUtils.validaCpf(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

    if (!PessoaUtils.validaNome(novaPessoaDto.getNome()))
      throw new ApiRequestException(MensagensPessoas.NOME_VAZIO.getMensagem());

    if(!prestadorService.verificaSePrestadorExiste(novaPessoaDto.getCpf()))
    	throw new ApiRequestException(MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());
    
    if (verificaSeClienteExiste(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CLIENTE_JA_EXISTE.getMensagem());

    try {
      return clienteRepository.save(
        new Cliente(PessoaUtils.formataCpf(novaPessoaDto.getCpf()),
          novaPessoaDto.getNome(),
          EnderecoUtils.montarEndereco(novaPessoaDto)));
    } catch (TransactionSystemException e) {
      throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());
    }
  }

  public Cliente detalharCliente(String cpf) {
    if (!verificaSeClienteExiste(cpf))
      throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());

    return clienteRepository.findByCpf(cpf);
  }

  public List<Prestador> procurarPrestadores(String codigoPostal) {
    return prestadorService.listarTodosPorCodigoPostal(codigoPostal);
  }

  public Cliente alterarCliente(AlteracaoPessoaDto pessoaDto) {
    pessoaDto.setCpf(PessoaUtils.formataCpf(pessoaDto.getCpf()));

    if (!PessoaUtils.validaCpf(pessoaDto.getCpf()))
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
    if (clienteRepository.findByCpf(cpf) != null)
      return true;
    return false;
  }
}
