package br.com.fiap.resource;

import br.com.fiap.dao.DiagnosticoDAO;
import br.com.fiap.dto.diagnostico.AtualizacaoDiagnosticoDTO;
import br.com.fiap.dto.diagnostico.CadastroDiagnosticoDTO;
import br.com.fiap.dto.diagnostico.DetalhesDiagnosticoDTO;
import br.com.fiap.exception.IDNaoEncontradoException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Diagnostico;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

//http://localhost:8080/diagnosticos
@Path("/diagnosticos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagnosticoResource {

    private DiagnosticoDAO diagnosticoDAO;
    private ModelMapper modelMapper;

    public DiagnosticoResource() throws Exception {
        diagnosticoDAO = new DiagnosticoDAO(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroDiagnosticoDTO dto, @Context UriInfo uriInfo) throws SQLException {
        Diagnostico diagnostico = modelMapper.map(dto, Diagnostico.class);
        diagnosticoDAO.cadastrar(diagnostico);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(diagnostico.getId()));

        return Response.created(builder.build()).build();
    }

    @GET
    public List<DetalhesDiagnosticoDTO> listarTodos() throws SQLException {
        return diagnosticoDAO.listarTodos().stream().map(d -> modelMapper.map(d, DetalhesDiagnosticoDTO.class)).collect(Collectors.toList());
    }

    @GET
    @Path("/todos/{idVeiculo}")
    public List<DetalhesDiagnosticoDTO> listarTodosPorVeiculo(@PathParam("idVeiculo") int idVeiculo) throws SQLException {
        return diagnosticoDAO.listarTodosPorIdCarro(idVeiculo).stream().map(d -> modelMapper.map(d, DetalhesDiagnosticoDTO.class)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesDiagnosticoDTO pesquisarPorId(@PathParam("id") int id) throws SQLException, IDNaoEncontradoException {
        return modelMapper.map(diagnosticoDAO.pesquisarPorId(id), DetalhesDiagnosticoDTO.class);
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizacaoDiagnosticoDTO dto) throws SQLException, IDNaoEncontradoException {
        Diagnostico diagnostico = modelMapper.map(dto, Diagnostico.class);
        diagnostico.setId(id);
        diagnosticoDAO.atualizar(diagnostico);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, IDNaoEncontradoException {
        diagnosticoDAO.remover(id);
        return Response.noContent().build();
    }
}
