package br.com.fiap.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class NotaFiscal {
    private int id;
    private int idFormaPagto;
    private Date dataEmissao;
    private int valor;
}
