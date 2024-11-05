package br.com.fiap.model;

import lombok.*;

import java.sql.Date;

@Getter @Setter
public class Diagnostico {
    private int id;
    private int idVeiculo;
    private double valor;
    private Date data;
    private String descricao;
    private char tipo;
}
