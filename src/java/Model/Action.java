/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.Serializable;

/**
 *
 * @author termine
 */
public class Action implements Serializable {
    public final static int CREATE = 1;
    public final static int UPDATE = 2;
    public final static int DELETE = 3;
    public final static int RESEARCH = 4;
    
    private int action;
    private String content;
    private Long idUser;
    private String username;


    public Action (int a, String c){
    action=a;content=c;
    }

    public Action (int a, String c, String usern){
    action=a;content=c;username=usern;
    }
    public Action (int a, String c, String usern, long idu){
    action=a;content=c;username=usern;this.idUser=idu;
    }


    public  String  toString(){
        String a="";
        if (getAction()==1)a="CREATE";
         if (getAction()==2)a="UPDATE";
         if (getAction()==3)a="DELETE";
         if (getAction()==4)a="RESEARCH";


        return "Action: "+a +" Content: "+getContent()+" Username: "+ getUsername();
    }

    /**
     * @return the action
     */
    public int getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(int action) {
        this.action = action;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the idUser
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
