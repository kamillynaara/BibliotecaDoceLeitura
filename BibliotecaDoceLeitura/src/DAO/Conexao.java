package DAO;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    public String url = "jdbc:mysql://localhost:3306/bibliotecadoceleitura"; 
    public String user = "root";
    public String password = "";
    
    /**
    *Realiza a conexão do código com o banco de dados
    *@return boolean true se conectado, ou false se houver erro
    */
    public boolean conectar(){   
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return true;
        } 
        catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }       
    }
    
    /**
    *Realiza a desconexão do código com o banco de dados
    */
    public void desconectar(){
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao desconectar: " + ex.getMessage());
        }
    }
}
