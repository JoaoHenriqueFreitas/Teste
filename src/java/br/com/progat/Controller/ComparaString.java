/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Controller;

import br.com.progat.Beans.ArquivosBeans;
import java.util.Comparator;

/**
 *
 * @author Joao
 */
public class ComparaString implements Comparator<ArquivosBeans>{

    @Override
    public int compare(ArquivosBeans o1, ArquivosBeans o2) {
       
        
        return o1.getArquivo().compareTo(o2.getArquivo());
        
    }
    
}
