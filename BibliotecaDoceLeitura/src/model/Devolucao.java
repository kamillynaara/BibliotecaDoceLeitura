package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Devolucao {
    private int id;
    private Emprestimo emprestimo;
    private LocalDate dataDevolucao;
    private int qtdeDiasAtraso;
    private double valorMulta;
    

    public int getId(){ 
        return id;
    } 
    public Emprestimo getEmprestimo(){ 
        return emprestimo;
    } 
    public LocalDate getDataDevolucao(){
        return dataDevolucao;
    } 
    public int getQtdeDiasAtraso(){ 
        return qtdeDiasAtraso;
    } 
    public double getValorMulta(){ 
        return valorMulta;
    } 
     

    public void setId(int id){
        this.id = id;
    } 
    public void setEmprestimo(Emprestimo emprestimo){
        this.emprestimo = emprestimo;
    } 
    public void setDataDevolucao(LocalDate dataDevolucao){
        this.dataDevolucao = dataDevolucao;
    } 
    public void setQtdeDiasAtraso(int qtdeDiasAtraso){
        this.qtdeDiasAtraso = qtdeDiasAtraso;
    } 
    public void setValorMulta(double valorMulta){
        this.valorMulta = valorMulta;
    } 
    
    
    private void calcularMulta() {
        long atraso = ChronoUnit.DAYS.between(
                emprestimo.getDataPrevistaDevolucao(), dataDevolucao
        );

        if (atraso > 0) {
            this.qtdeDiasAtraso = (int) atraso;
            this.valorMulta = qtdeDiasAtraso * 1.0; 
        } else {
            this.qtdeDiasAtraso = 0;
            this.valorMulta = 0.0;
        }
    }
}