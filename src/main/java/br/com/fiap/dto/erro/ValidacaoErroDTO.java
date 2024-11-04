package br.com.fiap.dto.erro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidacaoErroDTO {

    private String mensagem;
    private List<CampoErroDTO> campos;
}
