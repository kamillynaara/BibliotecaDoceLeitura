package model;

public class Usuario {
    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String tipoUsuario; // "Gerente" ou "Atendente"
    
    
    public int getId(){
        return id; 
    }
    public String getNome(){
        return nome;
    }
    public String getEmail(){
        return email; 
    }
    public String getSenha(){
        return senha; 
    }
    public String getTipoUsuario(){ 
        return tipoUsuario; 
    }

    
    public void setId(int id){
        this.id = id;
    }
    public void setNome(String nome){
        this.nome = nome; 
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void setTipoUsuario(String tipoUsuario){
        this.tipoUsuario = tipoUsuario; 
    }

}
