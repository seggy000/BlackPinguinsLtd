package ch.hearc.ig.ta.business;

import java.util.Date;

/**
 *
 * @author JulmyS
 */
public class Achievement {

    private int numero;
    private String libelle;
    private String description;
    private Date obtentionDate;
    private boolean achieved;

    public Achievement(final String libelle, final String description) {
        this(libelle, description, null);
    }

    public Achievement(final String libelle, final String description, final Date obtentionDate) {
        this.libelle = libelle;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
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
