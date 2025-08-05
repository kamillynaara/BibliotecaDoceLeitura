
package DAO;

import model.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Conexao conexao;

    public int cadastrar(Cliente cliente) {
        conexao = new Conexao();
        int status = 0;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return 0;
            }

            String sql = "INSERT INTO cliente (nome, email, telefone) VALUES (?, ?, ?)";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setString(1, cliente.getNome());
            conexao.pst.setString(2, cliente.getEmail());
            conexao.pst.setString(3, cliente.getTelefone());

            status = conexao.pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar cliente: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return status;
    }
    
    public List<Cliente> getLista() {
        List<Cliente> lista = new ArrayList<>();
        conexao = new Conexao();

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return lista;
            }

            String sql = "SELECT * FROM cliente";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(conexao.rs.getInt("id"));
                cliente.setNome(conexao.rs.getString("nome"));
                cliente.setEmail(conexao.rs.getString("email"));
                cliente.setTelefone(conexao.rs.getString("telefone"));
                cliente.setStatusAcesso(conexao.rs.getString("status_acesso"));

                lista.add(cliente);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao listar clientes: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        return lista;
    }
    
    
    public List<Cliente> buscarClientes(String termo) {
        conexao = new Conexao();
        List<Cliente> listaClientes = new ArrayList<>();
        String sql;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return listaClientes;
            }

            if (termo.matches("\\d+")) { // busca por ID se for número
                sql = "SELECT * FROM cliente WHERE id = ?";
                conexao.pst = conexao.conn.prepareStatement(sql);
                conexao.pst.setInt(1, Integer.parseInt(termo));
            } else { // busca por nome se for texto
                sql = "SELECT * FROM cliente WHERE nome LIKE ?";
                conexao.pst = conexao.conn.prepareStatement(sql);
                conexao.pst.setString(1, "%" + termo + "%");
            }

            conexao.rs = conexao.pst.executeQuery();

            while (conexao.rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(conexao.rs.getInt("id"));
                cliente.setNome(conexao.rs.getString("nome"));
                cliente.setEmail(conexao.rs.getString("email"));
                cliente.setTelefone(conexao.rs.getString("telefone"));
                cliente.setStatusAcesso(conexao.rs.getString("status_acesso"));

                listaClientes.add(cliente);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return listaClientes;
    }
    
    
    public Cliente buscarPorId(int id) {
        conexao = new Conexao();
        Cliente cliente = null;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return null;
            }

            String sql = "SELECT * FROM cliente WHERE id = ?";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setInt(1, id);

            conexao.rs = conexao.pst.executeQuery();

            if (conexao.rs.next()) {
                cliente = new Cliente();
                cliente.setId(conexao.rs.getInt("id"));
                cliente.setNome(conexao.rs.getString("nome"));
                cliente.setEmail(conexao.rs.getString("email"));
                cliente.setTelefone(conexao.rs.getString("telefone"));
                cliente.setStatusAcesso(conexao.rs.getString("status_acesso"));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return cliente;
    }
    
    
    public int atualizarCliente(Cliente cliente) {
        conexao = new Conexao();
        int status = 0;

        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
                return 0;
            }

            String sql = "UPDATE cliente SET nome=?, email=?, telefone=?, status_acesso=? WHERE id=?";
            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.setString(1, cliente.getNome());
            conexao.pst.setString(2, cliente.getEmail());
            conexao.pst.setString(3, cliente.getTelefone());
            conexao.pst.setString(4, cliente.getStatusAcesso());
            conexao.pst.setInt(5, cliente.getId());

            status = conexao.pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return status;
    }
    
    
    public void bloquearClientesAtrasados(){
        conexao = new Conexao();
        
        try {
            if (!conexao.conectar()) {
                System.out.println("Não foi possível conectar ao banco.");
            }

            String sql = """
            UPDATE cliente c
            INNER JOIN emprestimo e ON c.id = e.cliente_id
            SET c.status_acesso = 'Bloqueado'
            WHERE e.status_vigor = 'Ativo' AND CURDATE() > DATE_ADD(e.dt_prevista_devolucao, INTERVAL 10 DAY)""";

            conexao.pst = conexao.conn.prepareStatement(sql);
            conexao.pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao bloquear cliente: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }    
    }
}
