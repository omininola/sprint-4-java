package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
public class Agendamento {
    private int id;
    private int idCarro;
    private int idCliente;
    private int idMecanica;
    private Date data;
    private String descricao;
    private Time hora;
    private String endereco;
    private char status;
}
