/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Funcao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Joao
 */
public class Data {

    public static String arrumaData(String _data) {

        try {
            if (!_data.equals("")) {

                String data = _data.replace("/", "");

                String ano = data.substring(4, 8);
                String mes = data.substring(2, 4);
                String dia = data.substring(0, 2);

                SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                Date datas = formatador.parse(dia + "/" + mes + "/" + ano);
                // cria o formatador  
                SimpleDateFormat novoFormatador = new SimpleDateFormat("dd/MM/yyyy");
                // cria a string  
                String novoFormato = novoFormatador.format(datas);

                return novoFormato;

            } else {
                return "";
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static int getHora() {

        String data = "dd/MM/yyyy";
        String hora = "HH:mm";
        String data1, hora1;

        java.util.Date agora = new java.util.Date();;
        SimpleDateFormat formata = new SimpleDateFormat(data);
        formata = new SimpleDateFormat(hora);
        hora1 = formata.format(agora);

        return Integer.parseInt(hora1.replace(":", ""));

    }

    public static String getData() {

        String data = "dd/MM/yyyy";
        String data1, hora1;

        java.util.Date agora = new java.util.Date();;
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);

        return data1;
    }
    
    public static String getDataAnterior() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        
        return dataFormatada.format(calendar.getTime());
    }
}
