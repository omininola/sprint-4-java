package br.com.fiap.dto.veiculo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetalhesVeiculoDTO {
    private int id;
    private int idCliente;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String motor;
    private int km;
}
