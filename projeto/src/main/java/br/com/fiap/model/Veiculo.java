package br.com.fiap.model;

import lombok.*;

import java.util.List;

@Getter @Setter
public class Veiculo {
    private int id;
    private int idCliente;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String motor;
    private int km;
}
