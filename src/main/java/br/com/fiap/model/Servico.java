package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class Servico {
    private int id;
    private int idAgendamento;
    private int idDiagnostico;
    private String nome;
    private int valor;
    private char status;
    private Date dataInicial;
    private Date dataFinal;
}
