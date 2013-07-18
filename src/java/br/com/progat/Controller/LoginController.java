/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.progat.Controller;

import br.com.progat.Beans.ClienteBeans;
import br.com.progat.DAO.ClienteDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author JoãoHenrique
 */
@SessionScoped
@ManagedBean(name = "login")
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;
    private ClienteBeans usuario;
    private List<ClienteBeans> lista;
    public String nomePasta = "";
    protected FacesContext context;
    protected boolean login;
    public ClienteBeans loginB;

    
    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public LoginController() {
        this.usuario = new ClienteBeans();
    }

    public void verificaLogin() {
        context = FacesContext.getCurrentInstance();

        try {

            ClienteDAO dao = new ClienteDAO();

            loginB = dao.getLogin(usuario);

            if (loginB != null) {

                usuario.setPasta(loginB.getPasta());

                if (loginB.getNome().equals("Progat")) {

                    context.getExternalContext().redirect(""
                            + "/PortalBkup/faces/Administrador/Administrador.xhtml");
                } else {

                    if (usuario.getSenha().equals("123456")) {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login", "Senha 123456 "));

                    } else {
                        this.login = true;

                        context.getExternalContext().redirect(""
                                + "/PortalBkup/faces/Cliente/Cliente.xhtml");
                    }
                }
            } else {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login", "Usario ou senha errados.... "));
            }

            this.usuario = new ClienteBeans();

        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login", e.getMessage()));
        }
    }

    public ClienteBeans getUsuario() {
        return usuario;
    }

    public void setUsuario(ClienteBeans usuario) {
        this.usuario = usuario;
    }

    public List<ClienteBeans> getLista() {
        return lista;
    }

    public void setLista(List<ClienteBeans> lista) {
        this.lista = lista;
    }

    public String getNomePasta() {
        return nomePasta;
    }

    public void setNomePasta(String nomePasta) {
        this.nomePasta = nomePasta;
    }

    public ClienteBeans getLoginB() {
        return loginB;
    }

    public void setLoginB(ClienteBeans loginB) {
        this.loginB = loginB;
    }
}
