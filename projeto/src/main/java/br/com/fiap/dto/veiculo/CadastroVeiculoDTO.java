package br.com.fiap.dto.veiculo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CadastroVeiculoDTO {

    @NotNull(message = "O campo idCliente é obrigatório")
    private int idCliente;

    @NotNull(message = "O campo placa é obrigatório")
    private String placa;

    @NotNull(message = "O campo marca é obrigatório")
    private String marca;

    @NotNull(message = "O campo modelo é obrigatório")
    private String modelo;

    @NotNull(message = "O campo ano é obrigatório")
    private int ano;

    @NotNull(message = "O campo motor é obrigatório")
    private String motor;

    @NotNull(message = "O campo km é obrigatório")
    private int km;
}
