/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Factory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Joao
 */
public class ConnectionFactory implements Serializable{
   
    protected static String ip = "localhost";
    protected static String usuario = "sac";
    protected static String senha = "prosac2011";
    protected static String banco = "painelbkup";
    protected static String url = "jdbc:mysql://" + ip + "/" + banco;
    protected static String driverName = "com.mysql.jdbc.Driver";

    public static Connection getConection() {

        try {
            
            Class.forName(driverName);

            return DriverManager.getConnection(url, usuario, senha);
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
