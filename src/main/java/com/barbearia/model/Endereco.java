package com.barbearia.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Endereco {
  String pais;
  String cep;
  String uf;
  String bairro;
  String logradouro;
  String localidade;
  String complemento;
}
