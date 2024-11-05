package br.com.fiap.dao;

import br.com.fiap.exception.IDNaoEncontradoException;
import br.com.fiap.model.Diagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {

    private static final String TABLE_NAME = "T_UPIT_DIAGNOSTICO";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " " +
            "(id_diagnostico, id_veiculo, vl_diagnostico, dt_diagnostico, ds_diagnostico, tipo_diagnostico) " +
            "VALUES (diagnostico_seq.NEXTVAL, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE_NAME;

    private static final String SELECT_ALL_BY_CAR_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_veiculo = ?";

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_diagnostico = ?";

    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " " +
            "SET id_veiculo = ?, vl_diagnostico = ?, dt_diagnostico = TO_DATE(?, 'dd/MM/yyyy'), ds_diagnostico = ?, tipo_diagnostico = ?" +
            "WHERE id_diagnostico = ?";

    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id_diagnostico = ?";

    private Connection conexao;

    public DiagnosticoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    private Diagnostico parseDiagnostico(ResultSet rs) throws SQLException {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setId(rs.getInt("id_diagnostico"));
        diagnostico.setIdVeiculo(rs.getInt("id_veiculo"));
        diagnostico.setValor(rs.getDouble("vl_diagnostico"));
        diagnostico.setData(rs.getDate("dt_diagnostico"));
        diagnostico.setDescricao(rs.getString("ds_diagnostico"));
        diagnostico.setTipo(rs.getString("tipo_diagnostico").charAt(0));
        return diagnostico;
    }

    private void preencherStatementComDiagnostico(PreparedStatement stmt, Diagnostico diagnostico) throws SQLException {
        stmt.setInt(1, diagnostico.getIdVeiculo());
        stmt.setDouble(2, diagnostico.getValor());
        stmt.setObject(3, diagnostico.getData());
        stmt.setString(4, diagnostico.getDescricao());
        stmt.setString(5, String.valueOf(diagnostico.getTipo()));
    }

    public void cadastrar(Diagnostico diagnostico) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(INSERT_SQL);
        preencherStatementComDiagnostico(stmt, diagnostico);
        stmt.executeUpdate();
    }

    public List<Diagnostico> listarTodos() throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = stmt.executeQuery();

        List<Diagnostico> listaDiagnosticos = new ArrayList<>();
        while (rs.next()) {
            listaDiagnosticos.add(parseDiagnostico(rs));
        }

        return listaDiagnosticos;
    }

    public List<Diagnostico> listarTodosPorIdCarro(int idCarro) throws SQLException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_ALL_BY_CAR_ID_SQL);
        stmt.setInt(1, idCarro);
        ResultSet rs = stmt.executeQuery();

        List<Diagnostico> listaDiagnosticos = new ArrayList<>();
        while (rs.next()) {
            listaDiagnosticos.add(parseDiagnostico(rs));
        }

        return listaDiagnosticos;
    }

    public Diagnostico pesquisarPorId(int id) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(SELECT_BY_ID_SQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) throw new IDNaoEncontradoException("Diagnostico não encontrado");
        return parseDiagnostico(rs);
    }

    public void atualizar(Diagnostico diagnostico) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(UPDATE_SQL);
        preencherStatementComDiagnostico(stmt, diagnostico);
        stmt.setInt(6, diagnostico.getId());
        if (stmt.executeUpdate() == 0) throw new IDNaoEncontradoException("Diagnostico não encontrado");
    }

    public void remover(int id) throws SQLException, IDNaoEncontradoException {
        PreparedStatement stmt = conexao.prepareStatement(DELETE_SQL);
        stmt.setInt(1, id);
        if (stmt.executeUpdate() == 0) throw new IDNaoEncontradoException("Diagnostico não encontrado");
    }
}
