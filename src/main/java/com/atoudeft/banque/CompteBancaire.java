package com.atoudeft.banque;

import com.atoudeft.commun.Piles.PileChainee;

import java.io.Serializable;

public abstract class CompteBancaire implements Serializable {
    private String numero;
    private TypeCompte type;
    private double solde;
    protected boolean taxeApplicable = false;
    protected PileChainee historique = new PileChainee();

    /**
     * Crée un compte bancaire.
     * @param numero numéro du compte
     * @param type type du compte
     */
    public CompteBancaire(String numero, TypeCompte type) {
        this.numero = numero;
        this.type = type;
        this.solde = 0;
    }
    public String getNumero() {
        return numero;
    }
    public TypeCompte getType() {
        return type;
    }
    public double getSolde() {
        return solde;
    }
    protected void setSolde(double solde) {
        this.solde = solde;
    }
    public abstract boolean crediter(double montant);
    public abstract boolean debiter(double montant);
    public abstract boolean payerFacture(String numeroFacture, double montant, String description);
    public abstract boolean transferer(double montant, String numeroCompteDestinataire);
    public abstract PileChainee getHistorique();
    public boolean isTaxeApplicable() {return taxeApplicable;}
}