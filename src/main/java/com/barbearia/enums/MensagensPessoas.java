package com.barbearia.enums;

public enum MensagensPessoas {
  TABELA_CLIENTES_VAZIA("Não há clientes cadastrados"),
  TABELA_PRESTADORES_VAZIA("Não há prestadores cadastrados"),
  CPF_INVALIDO("CPF não é válido"),
  CPF_NOVO_INVALIDO("CPF novo não é válido"),
  CPF_CLIENTE_INVALIDO("CPF de cliente inválido"),
  CPF_PRESTADOR_INVALIDO("CPF de prestador inválido"),
  NOME_VAZIO("O nome não pode estar vazio"),
  NOME_INVALIDO("Nome inválido"),
  CPF_DE_PRESTADOR("O CPF pertence a um prestador"),
  CPF_DE_CLIENTE("O CPF pertence a um cliente"),
  CLIENTE_JA_EXISTE("O cliente já foi cadastrado"),
  PRESTADOR_JA_EXISTE("O prestador já foi cadastrado"),
  CLIENTE_NAO_EXISTE("Cliente não cadastrado"),
  PRESTADOR_NAO_EXISTE("Prestador não cadastrado"),
  CODIGO_POSTAL_INVALIDO("Código postal inválido"),
  COMPLEMENTO_GRANDE("Tamanho máximo de 50 caracteres no complemento ultrapassado"),
  ORIGEM_INVALIDA("Origem inválida."); 

  private String mensagem;

  MensagensPessoas(String mensagem) {
    this.mensagem = mensagem;
  }

  public String getMensagem() {
    return mensagem;
  }

}
