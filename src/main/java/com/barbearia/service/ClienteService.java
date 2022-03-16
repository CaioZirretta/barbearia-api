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
import com.barbearia.service.utils.EnderecoUtils;
import com.barbearia.service.utils.PessoaUtils;

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

    if (!EnderecoUtils.validaCodigoPostal(novaPessoaDto.getCodigoPostal()))
      throw new ApiRequestException(MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());

    if (!EnderecoUtils.validaComplemento(novaPessoaDto.getComplemento()))
      throw new ApiRequestException(MensagensPessoas.COMPLEMENTO_GRANDE.getMensagem());

    if (!EnderecoUtils.validaOrigem(novaPessoaDto.getOrigem()))
      throw new ApiRequestException(MensagensPessoas.ORIGEM_INVALIDA.getMensagem());

    if (!PessoaUtils.validaCpf(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

    if (!PessoaUtils.validaNome(novaPessoaDto.getNome()))
      throw new ApiRequestException(MensagensPessoas.NOME_INVALIDO.getMensagem());

    if (verificaSePrestadorExiste(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());

    if (verificaSeClienteExiste(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CLIENTE_JA_EXISTE.getMensagem());

    return clienteRepository.save(new Cliente(PessoaUtils.formataCpf(novaPessoaDto.getCpf()),
      novaPessoaDto.getNome(),
      EnderecoService.montarEndereco(novaPessoaDto)));
  }

  public Cliente detalharCliente(String cpf) {
    if (!verificaSeClienteExiste(cpf))
      throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());

    return clienteRepository.findByCpf(cpf);
  }

  public Cliente alterarCliente(AlteracaoPessoaDto pessoaDto) {

    if (!PessoaUtils.validaCpf(pessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());
    
    if (!PessoaUtils.validaCpf(pessoaDto.getCpfNovo()))
      throw new ApiRequestException(MensagensPessoas.CPF_NOVO_INVALIDO.getMensagem());

    if (!PessoaUtils.validaNome(pessoaDto.getNomeNovo()))
      throw new ApiRequestException(MensagensPessoas.NOME_INVALIDO.getMensagem());
    
    if (verificaSePrestadorExiste(pessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_DE_PRESTADOR.getMensagem());
    
    if (!verificaSeClienteExiste(PessoaUtils.formataCpf(pessoaDto.getCpf())))
      throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());
    
    
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

  public boolean verificaSePrestadorExiste(String cpf) {
    if (prestadorRepository.findByCpf(cpf) != null)
      return true;
    return false;
  }
}
