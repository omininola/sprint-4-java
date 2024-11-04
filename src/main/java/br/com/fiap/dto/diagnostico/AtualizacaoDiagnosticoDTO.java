package br.com.fiap.dto.diagnostico;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class AtualizacaoDiagnosticoDTO {

    @NotNull(message = "O campo idVeiculo é obrigatório")
    @Positive(message = "O idVeiculo deve ser um número positivo")
    private int idVeiculo;

    @NotNull(message = "O campo valor é obrigatório")
    @Positive(message = "O valor deve ser um número positivo")
    private double valor;

    @NotNull(message = "O campo data é obrigatório")
    @Past(message = "A data deve ser no passado")
    @JsonbDateFormat("dd/MM/yyyy")
    private Date data;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "O campo tipo é obrigatório")
    private char tipo;
}
