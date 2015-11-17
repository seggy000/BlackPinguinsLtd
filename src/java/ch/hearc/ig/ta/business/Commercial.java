package ch.hearc.ig.ta.business;

import java.io.Serializable;

/**
 *
 * @author JulmyS
 */
public class Commercial implements Serializable {

    private int numero;
    private String nom;
    private String prenom;
    private String username;
    private String password;
    private int points;

    public Commercial(final String nom, final String prenom, final String username, final int points) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.points = points;
    }

    public int getLevel() {
        return (int) Math.floor(points);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
