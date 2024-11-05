package br.com.fiap.dao;

import br.com.fiap.exception.IDNaoEncontradoException;
import br.com.fiap.model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    private static final String TABLE_NAME = "T_UPIT_VEICULO";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " " +
            "(id_veiculo, id_cliente, nr_placa, marca_veiculo, ds_modelo, ano_veiculo, motor_veiculo, km_veiculo) " +
            "VALUES (veiculo_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE_NAME;

    private static final String SELECT_ALL_BY_CLIENT_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_cliente = ?";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_veiculo = ?";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " " +
            "SET id_cliente = ?, nr_placa = ?, marca_veiculo = ?, ds_modelo = ?, ano_veiculo = ?, motor_veiculo = ?, km_veiculo = ? " +
            "WHERE id_veiculo = ?";

    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id_veiculo = ?";

    private Connection conexao;

    public VeiculoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    private Veiculo parseVeiculo(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(rs.getInt("id_veiculo"));
        veiculo.setIdCliente(rs.getInt("id_cliente"));
        veiculo.setPlaca(rs.getString("nr_placa"));
        veiculo.setMarca(rs.getString("marca_veiculo"));
        veiculo.setModelo(rs.getString("ds_modelo"));
        veiculo.setAno(rs.getInt("ano_veiculo"));
        veiculo.setMotor(rs.getString("motor_veiculo"));
        veiculo.setKm(rs.getInt("km_veiculo"));
        return veiculo;
    }

    private void preencherStatementComVeiculo(PreparedStatement stmt, Veiculo veiculo) throws SQLException {
        stmt.setInt(1, veiculo.getIdCliente());
        stmt.setString(2, veiculo.getPlaca());
        stmt.setString(3, veiculo.getMarca());
        stmt.setString(4, veiculo.getModelo());
        stmt.setInt(5, veiculo.getAno());
        stmt.setString(6, veiculo.getMotor());
        stmt.setLong(7, veiculo.getKm());
    }

    public void cadastrar(Veiculo veiculo) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
        preencherStatementComVeiculo(stmt, veiculo);
        stmt.executeUpdate();
    }

    public List<Veiculo> listarTodos() throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = stmt.executeQuery();

        List<Veiculo> listaVeiculos = new ArrayList<>();
        while (rs.next()) {
            listaVeiculos.add(parseVeiculo(rs));
        }

        return listaVeiculos;
    }

    public List<Veiculo> listarTodosPorIdCliente(int idCliente) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ALL_BY_CLIENT_ID_SQL);
        stmt.setInt(1, idCliente);
        ResultSet rs = stmt.executeQuery();

        List<Veiculo> listaVeiculos = new ArrayList<>();
        while (rs.next()) {
            listaVeiculos.add(parseVeiculo(rs));
        }

        return listaVeiculos;
    }

    public Veiculo pesquisarPorId(int id) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_BY_ID_SQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) throw new IDNaoEncontradoException("Carro não encontrado");
        return parseVeiculo(rs);
    }

    public void atualizar(Veiculo veiculo) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);
        preencherStatementComVeiculo(stmt, veiculo);
        stmt.setInt(8, veiculo.getId());
        if (stmt.executeUpdate() == 0) throw new IDNaoEncontradoException("Carro não encontrado");
    }

    public void remover(int id) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL);
        stmt.setInt(1, id);
        if (stmt.executeUpdate() == 0) throw new IDNaoEncontradoException("Carro não encontrado");
    }
}
