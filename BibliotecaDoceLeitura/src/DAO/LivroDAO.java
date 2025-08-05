package DAO;

import java.util.List;
import model.Livro;
import java.sql.SQLException;
import java.util.ArrayList;


public class LivroDAO {
    private Conexao conexao;

    public int cadastrar(Livro livro) {
        conexao = new Conexao();
        int status = 0;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return 0;
            }

            String sql = "INSERT INTO livro (titulo, autor, genero, ano_publicacao, descricao) VALUES (?, ?, ?, ?, ?)";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setString(1, livro.getTitulo());
            conexao.pst.setString(2, livro.getAutor());
            conexao.pst.setString(3, livro.getGenero());
            conexao.pst.setString(4, livro.getAnoPublicacao());
            conexao.pst.setString(5, livro.getDescricao());

            status = conexao.pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar livro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return status;
    }
    
    
    public List<Livro> getLista() {
        List<Livro> lista = new ArrayList<>();
        conexao = new Conexao();

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return lista;
            }

            String sql = "SELECT * FROM livro";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Livro livro = new Livro();
                livro.setId(conexao.rs.getInt("id"));
                livro.setTitulo(conexao.rs.getString("titulo"));
                livro.setAutor(conexao.rs.getString("autor"));
                livro.setGenero(conexao.rs.getString("genero"));
                livro.setAnoPublicacao(conexao.rs.getString("ano_publicacao"));
                livro.setDescricao(conexao.rs.getString("descricao"));
                livro.setStatusDisponibilidade(conexao.rs.getString("status_disponibilidade"));

                lista.add(livro);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar livros: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return lista;
    }
    
    
    public List<Livro> buscarLivros(String termo) {
        conexao = new Conexao();
        List<Livro> listaLivros = new ArrayList<>();
        String sql;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return listaLivros;
            }

            // Se o termo for número, busca por ID
            else if (termo.matches("\\d+")) {
                sql = "SELECT * FROM livro WHERE id = ?";
                conexao.pst = conexao.conn.prepareStatement(sql);
                conexao.pst.setInt(1, Integer.parseInt(termo));
            } 
            // Se for texto, busca por título, autor ou gênero
            else {
                sql = "SELECT * FROM livro WHERE titulo LIKE ? OR autor LIKE ? OR genero LIKE ?";
                conexao.pst = conexao.conn.prepareStatement(sql);
                conexao.pst.setString(1, "%" + termo + "%");
                conexao.pst.setString(2, "%" + termo + "%");
                conexao.pst.setString(3, "%" + termo + "%");
            }

            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Livro livro = new Livro();
                livro.setId(conexao.rs.getInt("id"));
                livro.setTitulo(conexao.rs.getString("titulo"));
                livro.setAutor(conexao.rs.getString("autor"));
                livro.setGenero(conexao.rs.getString("genero"));
                livro.setAnoPublicacao(conexao.rs.getString("ano_publicacao"));
                livro.setDescricao(conexao.rs.getString("descricao"));
                livro.setStatusDisponibilidade(conexao.rs.getString("status_disponibilidade"));

                listaLivros.add(livro);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return listaLivros;
    }
    
    
    public Livro buscarPorId(int id) {
        conexao = new Conexao();
        Livro livro = null;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return null;
            }

            String sql = "SELECT * FROM livro WHERE id = ?";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setInt(1, id);

            conexao.rs = conexao.pst.executeQuery();

            if (conexao.rs.next()) {
                livro = new Livro();
                livro.setId(conexao.rs.getInt("id"));
                livro.setTitulo(conexao.rs.getString("titulo"));
                livro.setAutor(conexao.rs.getString("autor"));
                livro.setGenero(conexao.rs.getString("genero"));
                livro.setAnoPublicacao(conexao.rs.getString("ano_publicacao"));
                livro.setDescricao(conexao.rs.getString("descricao"));
                livro.setStatusDisponibilidade(conexao.rs.getString("status_disponibilidade"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro por ID: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return livro;
    }
    
    
    public int atualizarLivro(Livro livro) {
        conexao = new Conexao();
        int status = 0;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return 0;
            }

            String sql = "UPDATE livro SET titulo=?, autor=?, genero=?, ano_publicacao=?, descricao=?, status_disponibilidade=? WHERE id=?";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setString(1, livro.getTitulo());
            conexao.pst.setString(2, livro.getAutor());
            conexao.pst.setString(3, livro.getGenero());
            conexao.pst.setString(4, livro.getAnoPublicacao());
            conexao.pst.setString(5, livro.getDescricao());
            conexao.pst.setString(6, livro.getStatusDisponibilidade());
            conexao.pst.setInt(7, livro.getId());

            status = conexao.pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return status;
    }
}
