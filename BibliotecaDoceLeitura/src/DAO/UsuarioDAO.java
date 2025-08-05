package DAO;

import model.Usuario;
import java.sql.SQLException;

public class UsuarioDAO {
    private Conexao conexao;
    
    
    public Usuario validarLogin(String email, String senha) {
        try {
            conexao = new Conexao();
            
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return null;
            }

            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setString(1, email);
            conexao.pst.setString(2, senha);

            conexao.rs = conexao.pst.executeQuery();

            if (conexao.rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(conexao.rs.getInt("id"));
                usuario.setNome(conexao.rs.getString("nome"));
                usuario.setEmail(conexao.rs.getString("email"));
                usuario.setSenha(conexao.rs.getString("senha"));
                usuario.setTipoUsuario(conexao.rs.getString("tipoUsuario"));

                return usuario;
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao validar login: " + ex.getMessage());
        } 
        finally {
            conexao.desconectar();
        }

        return null;
    }
}
