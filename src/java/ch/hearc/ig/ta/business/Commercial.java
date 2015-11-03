/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.ta.business;

import java.io.Serializable;

/**
 *
 * @author JulmyS
 */
public class Commercial implements Serializable{
    private int Numero;
    private String nom;
    private String prenom;
    private String username;
    private String password;
    private int points;
    
    public Commercial(String nom, String prenom, String username) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int Numero) {
        this.Numero = Numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}
