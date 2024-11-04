package br.com.fiap.exception;

import br.com.fiap.dto.erro.CampoErroDTO;
import br.com.fiap.dto.erro.ValidacaoErroDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider
public class ApiValidationMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<CampoErroDTO> erroCampos = new ArrayList<>();

        for (ConstraintViolation<?> c : e.getConstraintViolations()){
            CampoErroDTO campoErroDto = new CampoErroDTO();
            campoErroDto.setCampo(c.getPropertyPath().toString());
            campoErroDto.setMensagem(c.getMessage());
            erroCampos.add(campoErroDto);
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ValidacaoErroDTO("Dados inválidos", erroCampos))
                .build();
    }
}
