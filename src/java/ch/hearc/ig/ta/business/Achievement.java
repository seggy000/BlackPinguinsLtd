package ch.hearc.ig.ta.business;

/**
 *
 * @author JulmyS
 */
public class Achievement {

    private int numero;
    private String libelle;

    public Achievement(String libelle) {
        this.libelle = libelle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
