package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Prestador;
import com.barbearia.model.dto.PessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.utils.EnderecoUtils;
import com.barbearia.service.utils.PessoaUtils;

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

  public Prestador adicionar(PessoaDto novaPessoaDto) throws ApiRequestException {
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

    if (verificaSeClienteExiste(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_DE_CLIENTE.getMensagem());

    if (verificaSePrestadorExiste(novaPessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.PRESTADOR_JA_EXISTE.getMensagem());

    return prestadorRepository.save(new Prestador(PessoaUtils.formataCpf(novaPessoaDto.getCpf()),
      novaPessoaDto.getNome(), 
      EnderecoService.montarEndereco(novaPessoaDto)));
  }

  public Prestador detalharPrestador(String cpf) {
    if (!verificaSePrestadorExiste(cpf))
      throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());

    return prestadorRepository.findByCpf(cpf);
  }

  public Prestador alterarPrestador(String cpf, PessoaDto pessoaDto) {
    if (!PessoaUtils.validaCpf(cpf))
      throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());
    
    if (!PessoaUtils.validaCpf(pessoaDto.getCpf()))
      throw new ApiRequestException(MensagensPessoas.CPF_NOVO_INVALIDO.getMensagem());
    
    if (!PessoaUtils.validaNome(pessoaDto.getNome()))
      throw new ApiRequestException(MensagensPessoas.NOME_INVALIDO.getMensagem());
    
    if (!verificaSePrestadorExiste(cpf))
      throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());
    
    if (verificaSeClienteExiste(PessoaUtils.formataCpf(cpf)))
      throw new ApiRequestException(MensagensPessoas.CPF_DE_CLIENTE.getMensagem());
    
    if (pessoaDto.getCodigoPostal() != null) {
      if (!EnderecoUtils.validaCodigoPostal(pessoaDto.getCodigoPostal()))
        throw new ApiRequestException(MensagensPessoas.CODIGO_POSTAL_INVALIDO.getMensagem());
      
      if (!EnderecoUtils.validaComplemento(pessoaDto.getComplemento()))
        throw new ApiRequestException(MensagensPessoas.COMPLEMENTO_GRANDE.getMensagem());
      
      if (!EnderecoUtils.validaOrigem(pessoaDto.getOrigem()))
        throw new ApiRequestException(MensagensPessoas.ORIGEM_INVALIDA.getMensagem());
    }
    
    Prestador prestador = prestadorRepository.findByCpf(cpf);

    prestador.setCpf(pessoaDto.getCpf());
    prestador.setNome(pessoaDto.getNome());
    
    if (pessoaDto.getCodigoPostal() != null)
      prestador.setEndereco(EnderecoService.montarEndereco(pessoaDto));

    return prestador;
  }

  public List<Prestador> listarTodosPorCodigoPostal(String codigoPostal) {
    return prestadorRepository.findAllByPostal(codigoPostal);
  }
  
  public void apagarPrestador(String cpf) {
    if (!PessoaUtils.validaCpf(cpf))
      throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());
    
    if (!verificaSePrestadorExiste(PessoaUtils.formataCpf(cpf)))
      throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());
    
    prestadorRepository.deleteByCpf(cpf);  
  }

  // Validações de repositório

  public boolean verificaSePrestadorExiste(String cpf) {
    if (prestadorRepository.findByCpf(cpf) != null)
      return true;
    return false;
  }

  public boolean verificaSeClienteExiste(String cpf) {
    if (clienteRepository.findByCpf(cpf) != null)
      return true;
    return false;
  }
}
