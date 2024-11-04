package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FormaPagamento {
    private int id;
    private int idServico;
    private String titular;
    private char tipo;
    private String bandeira;
    private char status;
}
