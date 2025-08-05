package model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private Cliente cliente;
    private Livro livro;
    private LocalDate dataEmprestimo = LocalDate.now();
    private LocalDate dataPrevistaDevolucao;
    private String statusVigor = "Ativo"; // "Ativo" ou "Inativo"

    
    
    public int getId(){
        return id;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public Livro getLivro(){
        return livro; 
    }
    public LocalDate getDataEmprestimo(){
        return dataEmprestimo; 
    }
    public LocalDate getDataPrevistaDevolucao(){
        return dataPrevistaDevolucao; 
    }
    public String getStatusVigor(){
        return statusVigor; 
    }

    
    public void setId(int id){
        this.id = id; 
    }
    public void setCliente(Cliente cliente){ 
        this.cliente = cliente; 
    }
    public void setLivro(Livro livro){ 
        this.livro = livro; 
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo){
        this.dataEmprestimo = dataEmprestimo; 
    }
    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao){
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }
    public void setStatusVigor(String statusVigor){
        this.statusVigor = statusVigor;
    }

}
