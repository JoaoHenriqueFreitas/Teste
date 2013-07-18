/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.DAO;

import br.com.progat.Beans.ClienteBeans;
import br.com.progat.Factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class ClienteDAO {

    public Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    public ClienteBeans getLogin(ClienteBeans beans) {

        try {

            ClienteBeans cli = new ClienteBeans();
            conn = ConnectionFactory.getConection();

            stm = conn.prepareStatement("select * from tb_cliente where usuario = ? and senha = md5(?)");

            stm.setString(1, beans.getUsuario());
            stm.setString(2, beans.getSenha());

            rs = stm.executeQuery();

            if (rs != null && rs.next()) {

                cli.setId(rs.getString("id_cliente"));
                cli.setNome(rs.getString("empresa"));
                cli.setPasta(rs.getString("pasta"));
                cli.setUsuario(rs.getString("usuario"));
                cli.setSenha(rs.getString("senha"));

                conn.close();

                return cli;
            }

            conn.close();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClienteBeans> getClientes() {

        try {
            
            List<ClienteBeans> dados = new ArrayList<ClienteBeans>();

            conn = new ConnectionFactory().getConection();

            stm = conn.prepareStatement("select * from tb_cliente");

            rs = stm.executeQuery();

            while (rs.next()) {

                if (!rs.getString("empresa").equals("Progat")) {
                    ClienteBeans cli = new ClienteBeans();
                    cli.setId(rs.getString("id_cliente"));
                    cli.setNome(rs.getString("empresa"));
                    cli.setPasta(rs.getString("pasta"));
                    cli.setUsuario(rs.getString("usuario"));
                    cli.setSenha(rs.getString("senha"));

                    dados.add(cli);
                }
            }

            conn.close();

            return dados;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean inserir(ClienteBeans cli) {

        try {

            conn = ConnectionFactory.getConection();

            stm = conn.prepareStatement("insert into tb_cliente (empresa,pasta,usuario,senha) values (?,?,?,md5(?))");

            stm.setString(1, cli.getNome());
            stm.setString(2, cli.getPasta());
            stm.setString(3, cli.getUsuario());
            stm.setString(4, cli.getSenha());

            stm.execute();
            stm.close();

            conn.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verificaPastaCadastrada(ClienteBeans cli){
        
        try {
            conn = ConnectionFactory.getConection();

            stm = conn.prepareStatement("Select * from tb_cliente where pasta = ?");
            stm.setString(1, cli.getPasta());
            
            rs = stm.executeQuery();
            
            if(rs != null && rs.next()){ conn.close(); return true;}
            else{ conn.close(); return false; }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verificaUsuarioCadastrada(ClienteBeans cli){
        
        try {
            conn = ConnectionFactory.getConection();

            stm = conn.prepareStatement("Select * from tb_cliente where usuario = ?");
            stm.setString(1, cli.getUsuario());
            
            rs = stm.executeQuery();
            
            if(rs != null && rs.next()){ conn.close(); return true;}
            else{ conn.close(); return false; }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verificaEmpresaCadastrada(ClienteBeans cli){
        
        try {
            conn = ConnectionFactory.getConection();

            stm = conn.prepareStatement("Select * from tb_cliente where empresa = ?");
            stm.setString(1, cli.getNome());
            
            rs = stm.executeQuery();
            
            if(rs != null && rs.next()){ conn.close(); return true;}
            else{ conn.close(); return false; }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
