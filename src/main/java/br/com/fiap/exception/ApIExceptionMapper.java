package br.com.fiap.exception;

import br.com.fiap.dto.erro.MensagemErroDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApIExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof IDNaoEncontradoException){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new MensagemErroDTO(e.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensagemErroDTO(e.getMessage()))
                    .build();
    }
}
