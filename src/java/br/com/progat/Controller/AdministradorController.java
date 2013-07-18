/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Controller;

import br.com.progat.Beans.ClienteBeans;
import br.com.progat.DAO.ClienteDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Joao
 */
@ManagedBean(name = "admin")
@SessionScoped
public class AdministradorController implements Serializable {

    private ClienteBeans cliente;
    private List<ClienteBeans> lista;
    
    public AdministradorController() {
        limpaCampos();
        listaCliente();
    }

    public void limpaCampos() {

        this.cliente = new ClienteBeans();
    }

    public void listaCliente() {

        ClienteDAO dao = new ClienteDAO();

        lista = dao.getClientes();
    }

    public boolean verificaCadstros() {
         FacesContext context = FacesContext.getCurrentInstance();
        
        ClienteDAO dao = new ClienteDAO();
        
        if (dao.verificaEmpresaCadastrada(cliente) == true) {
            context.addMessage(null, new FacesMessage("Cadastro", "Empresa cadastrada....."));
        }else if (dao.verificaPastaCadastrada(cliente) == true) {
            context.addMessage(null, new FacesMessage("Cadastro", "Pasta cadastrada...."));
        }else if (dao.verificaUsuarioCadastrada(cliente) == true) {
            context.addMessage(null, new FacesMessage("Cadastro", "Usuario cadastrado....."));
        }else{
            return false;
        }
        
        return true;
    }

    public void crud() {
        FacesContext context = FacesContext.getCurrentInstance();
        ClienteDAO dao = new ClienteDAO();

        
        if (verificaCadstros() == false && dao.inserir(cliente) == true) {

            listaCliente();
            limpaCampos();
            context.addMessage(null, new FacesMessage("Cadastro", "Cliente Cadastrado com sucesso...."));
        
        }else{

            context.addMessage(null, new FacesMessage("Cadastro", "Erro ao cadastrar o cliente....."));
        }
    }

    public ClienteBeans getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBeans cliente) {
        this.cliente = cliente;
    }

    public List<ClienteBeans> getLista() {
        return lista;
    }

    public void setLista(List<ClienteBeans> lista) {
        this.lista = lista;
    }
}
