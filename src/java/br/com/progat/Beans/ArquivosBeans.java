/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Beans;

/**
 * @author Joao
 */

public class ArquivosBeans {
    
    private String nome;
    private String arquivo;
    private String download;
    private String tamanho;
    private String data;
    private long tmDownload;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTmDownload() {
        return tmDownload;
    }

    public void setTmDownload(long tmDownload) {
        this.tmDownload = tmDownload;
    }
    
    
}
