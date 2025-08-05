
package model;

public class Livro {
    private int id;
    private String titulo, autor, genero, anoPublicacao, descricao;
    private String statusDisponibilidade = "Disponível"; // "Disponível" ou "Emprestado"


    public int getId(){ 
        return id;
    } 
    public String getTitulo(){ 
        return titulo;
    } 
    public String getAutor(){
        return autor;
    } 
    public String getGenero(){ 
        return genero;
    } 
    public String getAnoPublicacao(){ 
        return anoPublicacao;
    } 
    public String getDescricao(){
        return descricao;
    } 
    public String getStatusDisponibilidade(){ 
        return statusDisponibilidade;
    } 


    public void setId(int id){
        this.id = id;
    } 
    public void setTitulo(String titulo){
        this.titulo = titulo;
    } 
    public void setAutor(String autor){
        this.autor = autor;
    } 
    public void setGenero(String genero){
        this.genero = genero;
    } 
    public void setAnoPublicacao(String anoPublicacao){
        this.anoPublicacao = anoPublicacao;
    } 
    public void setDescricao(String descricao){
        this.descricao = descricao;
    } 
    public void setStatusDisponibilidade(String statusDisponibilidade){
        this.statusDisponibilidade = statusDisponibilidade;
    } 
}
