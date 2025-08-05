
package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Devolucao;
import model.Emprestimo;


public class DevolucaoDAO {
    private Conexao conexao;

    
    public List<Devolucao> getLista() {
        List<Devolucao> lista = new ArrayList<>();
        conexao = new Conexao();

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return lista;
            }

            String sql = "SELECT * FROM devolucao";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Devolucao devolucao = new Devolucao();
                devolucao.setId(conexao.rs.getInt("id"));

                
                int idEmprestimo = conexao.rs.getInt("emprestimo_id");
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(idEmprestimo);
                devolucao.setEmprestimo(emprestimo);

                devolucao.setDataDevolucao(conexao.rs.getDate("dt_devolucao").toLocalDate());
                devolucao.setQtdeDiasAtraso(conexao.rs.getInt("qt_dias_atraso"));
                devolucao.setValorMulta(conexao.rs.getDouble("vl_multa"));

                lista.add(devolucao);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar devoluções: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return lista;
    }
    
    
    public int registrar(Devolucao devolucao) {
        conexao = new Conexao();
        int status = 0;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return 0;
            }

            // Inserir na tabela devolução
            String sql = "INSERT INTO devolucao (emprestimo_id, dt_devolucao, qt_dias_atraso, vl_multa) VALUES (?, ?, ?, ?)";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setInt(1, devolucao.getEmprestimo().getId());
            conexao.pst.setDate(2, java.sql.Date.valueOf(devolucao.getDataDevolucao()));
            conexao.pst.setInt(3, devolucao.getQtdeDiasAtraso());
            conexao.pst.setDouble(4, devolucao.getValorMulta());

            status = conexao.pst.executeUpdate();

            // Atualizar status do livro para Disponível
            String sqlUpdateLivro = "UPDATE livro SET status_disponibilidade = 'Disponível' WHERE id = (SELECT livro_id FROM emprestimo WHERE id = ?)";
            conexao.pst = conexao.conn.prepareStatement(sqlUpdateLivro);
            conexao.pst.setInt(1, devolucao.getEmprestimo().getId());
            conexao.pst.executeUpdate();
            
            
            // Atualizar status do emprestimo para Inativo
            String sqlUpdateEmprestimo = "UPDATE emprestimo SET status_vigor = 'Inativo' WHERE id = ?";
            conexao.pst = conexao.conn.prepareStatement(sqlUpdateEmprestimo);
            conexao.pst.setInt(1, devolucao.getEmprestimo().getId());
            conexao.pst.executeUpdate();
            
            
            // Atualizar status do cliente para habilitado          
            if(devolucao.getQtdeDiasAtraso() > 10){
                String sqlUpdateCliente= "UPDATE cliente SET status_acesso = 'Habilitado' WHERE id = (SELECT cliente_id FROM emprestimo WHERE id = ?)";
                conexao.pst = conexao.conn.prepareStatement(sqlUpdateCliente);
                conexao.pst.setInt(1, devolucao.getEmprestimo().getId());
                conexao.pst.executeUpdate();
            }
           

        } catch (SQLException ex) {
            System.out.println("Erro ao registrar devolução: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return status;
    }

}
