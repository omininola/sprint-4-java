package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Fornecedor {
    private int id;
    private int idMecanica;
    private String nome;
    private String endereco;
    private String contrato;
}
