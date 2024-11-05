package br.com.fiap.resource;

import br.com.fiap.dao.VeiculoDAO;
import br.com.fiap.dto.veiculo.AtualizacaoVeiculoDTO;
import br.com.fiap.dto.veiculo.CadastroVeiculoDTO;
import br.com.fiap.dto.veiculo.DetalhesVeiculoDTO;
import br.com.fiap.exception.IDNaoEncontradoException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Veiculo;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

//http://localhost:8080/carros
@Path("/carros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VeiculoResource {

    private VeiculoDAO veiculoDAO;
    private ModelMapper modelMapper;

    public VeiculoResource() throws Exception {
        veiculoDAO = new VeiculoDAO(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroVeiculoDTO dto, @Context UriInfo uriInfo) throws SQLException {
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        veiculoDAO.cadastrar(veiculo);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(veiculo.getId()));

        return Response.created(builder.build()).build();
    }

    @GET
    public List<DetalhesVeiculoDTO> listarTodos() throws SQLException {
        return veiculoDAO.listarTodos().stream().map(c -> modelMapper.map(c, DetalhesVeiculoDTO.class)).collect(Collectors.toList());
    }

    @GET
    @Path("/todos/{idCliente}")
    public List<DetalhesVeiculoDTO> listarTodosPorCliente(@PathParam("idCliente") int idCliente) throws SQLException {
        return veiculoDAO.listarTodosPorIdCliente(idCliente).stream().map(c -> modelMapper.map(c, DetalhesVeiculoDTO.class)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesVeiculoDTO pesquisarPorId(@PathParam("id") int id) throws SQLException, IDNaoEncontradoException {
        return modelMapper.map(veiculoDAO.pesquisarPorId(id), DetalhesVeiculoDTO.class);
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizacaoVeiculoDTO dto) throws SQLException, IDNaoEncontradoException {
        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);
        veiculo.setId(id);
        veiculoDAO.atualizar(veiculo);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, IDNaoEncontradoException {
        veiculoDAO.remover(id);
        return Response.noContent().build();
    }
}
