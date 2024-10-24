package com.atoudeft.banque;

import com.atoudeft.banque.operations.OperationDepot;
import com.atoudeft.banque.operations.OperationRetrait;
import com.atoudeft.commun.Piles.PileChainee;

public class CompteCheque extends CompteBancaire {

    //Pour le moment, il est là parce que il y a une erreur sinon
    public CompteCheque(String numero, TypeCompte type) {
        super(numero, type);
    }

    /**
     * Ajoute un montant d'argent dans le compte bancaire.
     * @param montant le montant à ajouter
     * @return retourne true si le monant est positif (en ajoutant le montant au solde) sinon retourne false
     */
    @Override
    public boolean crediter(double montant) {
        if (montant > 0) {
            setSolde(this.getSolde() + montant);
            this.historique.ajouter(new OperationDepot(montant).toString());
            return true;
        }
        return false;
    }

    /**
     * Retire un momtant d'argent du compte bancaire si il est positif et plus petit ou égale au montant du solde du compte.
     * @param montant le montant à retirer
     * @return retourne true si le montant est positif et plus petit ou égale au montant du solde du compte sinon retourne false
     */
    @Override
    public boolean debiter(double montant) {
        if (montant > 0 && montant <= getSolde()) {
            setSolde(getSolde() - montant);
            this.historique.ajouter(new OperationRetrait(montant).toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description)
    {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire)
    {
        return false;
    }

    @Override
    public PileChainee getHistorique()
    {
        return this.historique;
    }


}
