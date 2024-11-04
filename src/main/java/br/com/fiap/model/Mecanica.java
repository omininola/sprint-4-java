package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Mecanica {
    private int id;
    private String cnpj;
    private String nome;
    private String endereco;
    private char status;
}
