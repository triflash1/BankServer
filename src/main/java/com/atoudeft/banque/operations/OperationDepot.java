package com.atoudeft.banque.operations;

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
}
