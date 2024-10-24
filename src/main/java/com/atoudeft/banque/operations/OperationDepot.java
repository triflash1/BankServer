package com.atoudeft.banque.operations;

/**
 * Auteur : Alexandre Gamache
 */
public class OperationDepot extends Operation{
    double montant;
    /**
     * Permet de garder un historique des op�rations de d�pot
     *
     * @param montant du d�pot
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
