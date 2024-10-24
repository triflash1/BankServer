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
        return this.debiter(montant) && compteDestinataire.crediter(montant);
    }

    @Override
    public PileChainee getHistorique()
    {
        return this.historique;
    }


}
