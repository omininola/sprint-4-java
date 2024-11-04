package br.com.fiap.dto.cliente;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class CadastroClienteDTO {

    @NotBlank(message = "O campo email é obrigatório")
    @Email
    private String email;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo user é obrigatório")
    private String user;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;

    @NotNull(message = "O campo data de nascimento é obrigatório")
    @Past(message = "A data de nascimento deve ser no passado")
    @JsonbDateFormat("dd/MM/yyyy")
    private Date dataNascimento;

    @NotBlank(message = "O campo status é obrigatório")
    private char statusConta;
}
