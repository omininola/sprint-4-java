package br.com.fiap.dto.cliente;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class DetalhesClienteDTO {
    private int id;
    private String email;
    private String nome;
    private String user;
    private String senha;
    private Date dataNascimento;
    private String statusConta;
}
