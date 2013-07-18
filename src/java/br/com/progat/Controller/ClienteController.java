/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Controller;

import br.com.progat.Beans.ArquivosBeans;
import br.com.progat.Funcao.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author JOAO HENRIQUE FREITAS
 */
@ViewScoped
@ManagedBean(name = "cli")
public class ClienteController implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArquivosBeans arquivo;
    private List<ArquivosBeans> listaBkp;
    public String pasta;
    protected File dir;
    private StreamedContent file;
    public String data;
    public String dataPesquisa = "";
    public String nome;
    public String nomePasta = "";
    private Logger logger;

    public String getNomePasta() {
        return nomePasta;
    }

    public void setNomePasta(String nomePasta) {
        this.nomePasta = nomePasta;
    }

    public String getPasta() {
        return pasta;
    }

    public void setPasta(String pasta) {
        this.pasta = pasta;
    }

    public ClienteController() {

        logger = Logger.getLogger(ClienteController.class);
        
        LoginController log = (LoginController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("login");
        if (log != null) {
            nomePasta = log.getLoginB().getPasta();
            logger.info(log.getLoginB().getNome()+"Acessando o sistema de bkup....");
        }
        //pasta = "d:/backup/" + nomePasta + "/";
        pasta = "/var/www/" + nomePasta + "/";

        dir = new File(pasta);
        dataPesquisa = Data.getDataAnterior();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("login");
        
        logger.info(pasta+"Acessando o sistema de bkup....");
    }

    public ArquivosBeans getArquivo() {
        return arquivo;
    }

    public void setArquivo(ArquivosBeans arquivo) {
        this.arquivo = arquivo;
    }

    /*
     * CONVERTE O TAMANHO DOS ARQUIVOS DE BYTE PARA KB E MB
     */
    public String convertMb(long bty) {

        double tamanho = bty / 1024;

        if (tamanho > 1000.0) {

            return String.valueOf(bty / (1024 * 1024)) + " MB";

        } else {

            return String.valueOf(tamanho) + " KB";
        }
    }

    public boolean verificaTamanhoLoja(long bty) {

        double tm = bty / (1024 * 1024);

        if (tm >= 10.0) {

            return true;

        } else {

            return false;
        }
    }

    public boolean verificaTamanho(long bty) {

        double tm = bty / (1024 * 1024);

        if (tm >= 80.0) {

            return true;

        } else {

            return false;
        }
    }

    /*
     * FAZ A SEPARACAO DAS INFORMAÇÕES DO ARQUIVO
     */
    public void separaInformacao(String nome) {

        String[] array = nome.split("\\.");

        if (nome.length() >= 22) {

            this.nome = array[0].toUpperCase() + " loja: " + array[1] + " Data: " + Data.arrumaData(array[2]);
            data = Data.arrumaData(array[2]);

        } else {

            this.nome = array[0].toUpperCase() + " Data: " + Data.arrumaData(array[1]);
            data = Data.arrumaData(array[1]);
        }
    }

    /*
     * VERIFICA NA PASTA DO CLIENTE TEM OS ARQUIVOS DOS BKUPS
     * CASO ELE TENHA E PASSADO AS INFORMACÕES DOS ARQUIVOS PARA
     * QUE APARECA NA TELA
     */
    public List<ArquivosBeans> getListaBkup() {
        try {

            listaBkp = new ArrayList<ArquivosBeans>();
            int j = 0;
            if (dir.isDirectory()) {
                String[] filhos = dir.list();
                for (int i = 0; i < filhos.length; i++) {
                    File nome = new File(dir, filhos[i]);
                    if (nome.isFile()) {

                        separaInformacao(nome.getName());//FAZ A SEPARACAO DAS INFORMACOES DOS ARQUIVOS

                        if (verificaTamanhoLoja(nome.length())) {

                            if (data.equals(dataPesquisa) || dataPesquisa.equals("")) {

                                ArquivosBeans arq = new ArquivosBeans();

                                arq.setArquivo(nome.getName());
                                arq.setDownload(pasta + nome.getName());
                                arq.setTamanho(convertMb(nome.length()));
                                arq.setData(data);;
                                arq.setNome(this.nome);
                                arq.setTmDownload(nome.length());

                                listaBkp.add(arq);
                            }
                        }
                    }
                }
            }

            Collections.sort(listaBkp, new ComparaString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaBkp;
    }

    public List<ArquivosBeans> getListaBkp() {
        return listaBkp;
    }

    public void setListaBkp(List<ArquivosBeans> listaBkp) {
        this.listaBkp = listaBkp;
    }

    public void listas() {
        getListaBkp();
    }

    /*
     * FAZ O DOWNLOAD DO ARQUIVO ESQPECIFICADO PELO CLIENTE
     * 
     * FAZER A VERIFICACAO PARA LIBERACAO DO DOWNLOAD EM DETERMINADO HORARIO
     */
    public StreamedContent downloadArquivo() {
        try {
           
            logger.info(pasta+" Inciando o download......");
             
            FacesContext context = FacesContext.getCurrentInstance();

            InputStream stream;
            if (verificaTamanho(arquivo.getTmDownload()) == true) {
                if ((Data.getHora() < 1200 || Data.getHora() > 1400 && Data.getHora() < 1800)) {
                    context.addMessage(null, new FacesMessage("Informação", "Arquivo maior que 80 MB realize o download entre"
                            + " 12:00 e 14:00 e depois das 18:00....."+Data.getHora()));
                } else {

                    stream = new FileInputStream(arquivo.getDownload());
                    file = new DefaultStreamedContent(stream, "Imagem/jpg", arquivo.getArquivo());
                }
            } else {
                stream = new FileInputStream(arquivo.getDownload());
                file = new DefaultStreamedContent(stream, "Imagem/jpg", arquivo.getArquivo());
            }



            return file;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * GETTER E SETTERS
     */
    public StreamedContent getFile() {
        return file;
    }

    public String getDataPesquisa() {
        return dataPesquisa;
    }

    public void setDataPesquisa(String dataPesquisa) {
        this.dataPesquisa = dataPesquisa;
    }
}
