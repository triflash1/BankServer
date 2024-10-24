package com.atoudeft.banque;

import com.atoudeft.banque.operations.OperationDepot;
import com.atoudeft.banque.operations.OperationRetrait;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.Piles.PileChainee;

public class CompteEpargne extends CompteBancaire
{
    private final int LIMITE_COMPTE_AVANT_FRAIS = 1000;
    private final int FRAIS = 2;
    private double tauxInteret;

    public CompteEpargne(double tauxInteret, String numero, TypeCompte type)
    {
        super(numero, type);
        this.tauxInteret = tauxInteret;
    }

    /**
     * Méthode de manipulation du Solde.
     * Cette méthode incrémente le solde par un taux fixé au constructeur.
     */
    public void ajouterInterets()
    {
        double montantInterets = tauxInteret * this.getSolde();
        this.historique.ajouter("Interêts ajoutés : " + montantInterets);
        this.setSolde(this.getSolde() + montantInterets);
    }

    @Override
    public boolean crediter(double montant)
    {
        if(this.getSolde() >= 0)
        {
            this.setSolde(this.getSolde() + montant);
            this.historique.ajouter(new OperationDepot(montant).toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant)
    {
        this.taxeApplicable = this.getSolde() <= LIMITE_COMPTE_AVANT_FRAIS;
        if(this.getSolde() > 0 && this.getSolde() >= montant)
        {
            if (taxeApplicable && montant + FRAIS <= this.getSolde())
            {
                this.setSolde(this.getSolde() - (FRAIS + montant));
                this.historique.ajouter(new OperationRetrait(montant + FRAIS).toString());
                return true;
            }
            else if(taxeApplicable)
            {
                return false;

            } else //Ici le Solde est plus de LIMITE_COMPTE_AVANT_FRAIS
            {
                this.setSolde(this.getSolde() - montant);
                this.historique.ajouter(new OperationRetrait(montant).toString());
                return true;
            }
        }
        return false;
    }
    public boolean getTaxeApplicable()
    {
        return this.taxeApplicable;
    }
    /**
     * Permet de payer une facture
     *
     * @param numeroFacture numéro associé a la facture
     * @param montant montant qui doit être débité
     * @param description description de la facture
     * @return true si l'opération à été effectué correctement
     * @Auteur Alexandre Gamache
     */
    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description)
    {
        return debiter(montant);
    }

    /**
     * Transfer de largent dun compte a lautre
     * @param montant montant qui doit etre transféré
     * @param compteDestinataire compte de celui qui doit recevoir le montant
     * @return true si l'operation a ete effectué cprrectement
     * @Auteur Alexandre Gamache
     */
    @Override
    public boolean transferer(double montant, CompteBancaire compteDestinataire)
    {
        if (this.debiter(montant) && compteDestinataire.crediter(montant)){
            return true;
        }
        return false;
    }

    @Override
    public PileChainee getHistorique()
    {
        return this.historique;
    }
}
