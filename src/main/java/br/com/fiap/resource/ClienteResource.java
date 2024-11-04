package br.com.fiap.resource;

import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.dto.cliente.AtualizacaoClienteDTO;
import br.com.fiap.dto.cliente.CadastroClienteDTO;
import br.com.fiap.dto.cliente.DetalhesClienteDTO;
import br.com.fiap.dto.cliente.LoginClienteDTO;
import br.com.fiap.exception.CredenciaisIncorretasException;
import br.com.fiap.exception.IDNaoEncontradoException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

// http://localhost:8080/clientes
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class    ClienteResource {

    private ClienteDAO clienteDAO;
    private ModelMapper modelMapper;

    public ClienteResource() throws Exception {
        clienteDAO = new ClienteDAO(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(@Valid CadastroClienteDTO dto, @Context UriInfo uriInfo) throws SQLException {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        clienteDAO.cadastrar(cliente);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(cliente.getId()));

        return Response.created(builder.build()).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginClienteDTO dto) throws SQLException, CredenciaisIncorretasException {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        cliente = clienteDAO.login(cliente);

        return Response.ok().entity(modelMapper.map(cliente, DetalhesClienteDTO.class)).build();
    }

    @GET
    public List<DetalhesClienteDTO> listarTodos() throws SQLException {
        return clienteDAO.listarTodos().stream().map(c -> modelMapper.map(c, DetalhesClienteDTO.class)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public DetalhesClienteDTO pesquisarPorId(@PathParam("id") int id) throws SQLException, IDNaoEncontradoException {
        return modelMapper.map(clienteDAO.pesquisarPorId(id), DetalhesClienteDTO.class);
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizacaoClienteDTO dto) throws SQLException, IDNaoEncontradoException {
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        cliente.setId(id);
        clienteDAO.atualizar(cliente);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, IDNaoEncontradoException {
        clienteDAO.remover(id);
        return Response.noContent().build();
    }
}
