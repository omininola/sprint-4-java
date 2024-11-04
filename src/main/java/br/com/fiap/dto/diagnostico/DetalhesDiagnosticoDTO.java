package br.com.fiap.dto.diagnostico;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class DetalhesDiagnosticoDTO {
    private int id;
    private int idVeiculo;
    private double valor;
    private Date data;
    private String descricao;
    private char tipo;
}
