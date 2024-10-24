package com.atoudeft.banque.operations;

/**
 * Auteur : Alexandre Gamache
 */
public class OperationDepot extends Operation{
    double montant;
    /**
     * Permet de garder un historique des opérations de dépot
     *
     * @param montant du dépot
     */
    public OperationDepot(double montant) {
        super(TypeOperation.DEPOT);
        this.montant = montant;
    }

    /**
     * Genere la description complete d'une operation de type Depot
     * @return la string en format "Date TYPE montant"
     */
    @Override
    public String toString() {
        return super.toString() + " " + montant;
    }
}
