package br.com.fiap.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginClienteDTO {

    @NotBlank(message = "O campo email é obrigatório")
    @Email
    private String email;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;
}
