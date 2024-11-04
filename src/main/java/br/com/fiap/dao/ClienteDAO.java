package br.com.fiap.dao;

import br.com.fiap.exception.CredenciaisIncorretasException;
import br.com.fiap.exception.IDNaoEncontradoException;
import br.com.fiap.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String TABLE_NAME = "T_UPIT_CLIENTE";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " " +
            "(id_cliente, email_cliente, nm_cliente, user_cliente, senha_cliente, dt_nascimento, st_conta) " +
            "VALUES (clientes_seq.NEXTVAL, ?, ?, ?, ?, TO_DATE(?, 'dd/MM/yyyy'), ?)";

    private static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE_NAME;

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_cliente = ?";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " " +
            "SET email_cliente = ?, nm_cliente = ?, user_cliente = ?, senha_cliente = ?, dt_nascimento = TO_DATE(?, 'dd/MM/yyyy'), st_conta = ? " +
            "WHERE id_cliente = ?";

    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id_cliente = ?";

    private Connection conexao;

    public ClienteDAO(Connection conexao) {
        this.conexao = conexao;
    }

    private Cliente parseCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id_cliente"));
        cliente.setEmail(rs.getString("email_cliente"));
        cliente.setNome(rs.getString("nm_cliente"));
        cliente.setUser(rs.getString("user_cliente"));
        cliente.setSenha(rs.getString("senha_cliente"));
        cliente.setDataNascimento(rs.getDate("dt_nascimento"));
        cliente.setStatusConta(rs.getString("st_conta").charAt(0));
        return cliente;
    }

    private void preencherStatementComCliente(PreparedStatement stmt, Cliente cliente) throws SQLException {
        stmt.setString(1, cliente.getEmail());
        stmt.setString(2, cliente.getNome());
        stmt.setString(3, cliente.getUser());
        stmt.setString(4, cliente.getSenha());
        stmt.setDate(5, cliente.getDataNascimento());
        stmt.setString(6, String.valueOf(cliente.getStatusConta()));
    }

    public void cadastrar(Cliente cliente) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
        preencherStatementComCliente(stmt, cliente);
        stmt.executeUpdate();
    }

    public Cliente login(Cliente cliente) throws SQLException, CredenciaisIncorretasException {
        PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE email_cliente = ? AND senha_cliente = ?");
        stmt.setString(1, cliente.getEmail());
        stmt.setString(2, cliente.getSenha());
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) throw new CredenciaisIncorretasException("Credenciais incorretas");
        return parseCliente(rs);
    }

    public List<Cliente> listarTodos() throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = stmt.executeQuery();

        List<Cliente> listaCarros = new ArrayList<>();
        while (rs.next()) {
            listaCarros.add(parseCliente(rs));
        }

        return listaCarros;
    }

    public Cliente pesquisarPorId(int id) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_BY_ID_SQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) throw new IDNaoEncontradoException("Cliente não encontrado");
        return parseCliente(rs);
    }

    public void atualizar(Cliente cliente) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);
        preencherStatementComCliente(stmt, cliente);
        stmt.setInt(7, cliente.getId());
        if (stmt.executeUpdate() == 0) throw new IDNaoEncontradoException("Cliente não encontrado");
    }

    public void remover(int id) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL);
        stmt.setInt(1, id);
        if (stmt.executeUpdate() == 0) throw new IDNaoEncontradoException("Cliente não encontrado");
    }
}
