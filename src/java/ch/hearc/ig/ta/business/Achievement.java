package ch.hearc.ig.ta.business;

import java.util.Date;

/**
 *
 * @author JulmyS
 */
public class Achievement {

    private int numero;
    private String libelle;
    private Date obtentionDate;
    private boolean achieved;

    public Achievement(final String libelle) {
        this(libelle, null);
    }

    public Achievement(final String libelle, final Date obtentionDate) {
        this.libelle = libelle;
        this.obtentionDate = obtentionDate;
        if (obtentionDate == null) {
            this.achieved = false;
        } else {
            this.achieved = true;
        }
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(final int numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(final String libelle) {
        this.libelle = libelle;
    }

    public Date getObtentionDate() {
        return obtentionDate;
    }

    public void setObtentionDate(final Date obtentionDate) {
        this.obtentionDate = obtentionDate;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(final boolean achieved) {
        this.achieved = achieved;
    }

}
