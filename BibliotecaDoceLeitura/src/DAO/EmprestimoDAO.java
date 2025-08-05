
package DAO;

import model.Emprestimo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Livro;

public class EmprestimoDAO {
    private Conexao conexao;
    
    public int registrar(Emprestimo emprestimo) {
        conexao = new Conexao();
        int status = 0;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return 0;
            }

            String sql = "INSERT INTO emprestimo (dt_emprestimo, dt_prevista_devolucao, cliente_id, livro_id) VALUES (?, ?, ?, ?)";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setDate(1, java.sql.Date.valueOf(emprestimo.getDataEmprestimo()));
            conexao.pst.setDate(2, java.sql.Date.valueOf(emprestimo.getDataPrevistaDevolucao()));
            conexao.pst.setInt(3, emprestimo.getCliente().getId());
            conexao.pst.setInt(4, emprestimo.getLivro().getId());


            status = conexao.pst.executeUpdate();
            
            // Atualiza status do livro para indisponível
            String sqlLivro = "UPDATE livro SET status_disponibilidade = 'Indisponível' WHERE id = ?";
            conexao.pst = conexao.conn.prepareStatement(sqlLivro);
            conexao.pst.setInt(1, emprestimo.getLivro().getId());
            conexao.pst.executeUpdate();
            

        } catch (SQLException e) {
            System.out.println("Erro ao registrar emprestimo: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return status;
    }
     
     
     public List<Emprestimo> getLista() {
        List<Emprestimo> lista = new ArrayList<>();
        conexao = new Conexao();

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return lista;
            }

            String sql = "SELECT * FROM emprestimo";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Cliente cliente = new Cliente();
                Livro livro = new Livro();
                               
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(conexao.rs.getInt("id"));
                cliente.setId(conexao.rs.getInt("cliente_id"));
                livro.setId(conexao.rs.getInt("livro_id"));
                emprestimo.setDataEmprestimo(conexao.rs.getDate("dt_emprestimo").toLocalDate());
                emprestimo.setDataPrevistaDevolucao(conexao.rs.getDate("dt_prevista_devolucao").toLocalDate());
                
                emprestimo.setCliente(cliente);
                emprestimo.setLivro(livro);
                
                 emprestimo.setStatusVigor(conexao.rs.getString("status_vigor"));

                lista.add(emprestimo);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar emprestimos: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return lista;
    }
     
     
     public List<Emprestimo> getListaAtivos() {
        List<Emprestimo> lista = new ArrayList<>();
        conexao = new Conexao();

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return lista;
            }

            String sql = "SELECT * FROM emprestimo WHERE status_vigor = 'Ativo'";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Cliente cliente = new Cliente();
                Livro livro = new Livro();
                         
                Emprestimo emprestimo = new Emprestimo();
                
                emprestimo.setId(conexao.rs.getInt("id"));
                cliente.setId(conexao.rs.getInt("cliente_id"));
                livro.setId(conexao.rs.getInt("livro_id"));
                emprestimo.setDataEmprestimo(conexao.rs.getDate("dt_emprestimo").toLocalDate());
                emprestimo.setDataPrevistaDevolucao(conexao.rs.getDate("dt_prevista_devolucao").toLocalDate());
                
                emprestimo.setCliente(cliente);
                emprestimo.setLivro(livro);
                
                emprestimo.setStatusVigor(conexao.rs.getString("status_vigor"));

                lista.add(emprestimo);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar emprestimos: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return lista;
    }
    
}
