package br.com.fiap.model;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter @Setter
public class Cliente {
    private int id;
    private String email;
    private String nome;
    private String user;
    private String senha;
    private Date dataNascimento;
    private char statusConta;
}
