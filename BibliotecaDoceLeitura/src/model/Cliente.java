    
package model;

public class Cliente {
    private int id;
    private String nome, email, telefone;
    private String statusAcesso = "Habilitado"; // "Habilitado" ou "Bloqueado"

    public int getId(){ 
        return id;
    } 
    public String getNome(){ 
        return nome;
    } 
    public String getEmail(){
        return email;
    } 
    public String getTelefone(){ 
        return telefone;
    } 
    public String getStatusAcesso(){ 
        return statusAcesso;
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
    public void setTelefone(String telefone){
        this.telefone = telefone;
    } 
    public void setStatusAcesso(String statusAcesso){
        this.statusAcesso = statusAcesso;
    } 
}
