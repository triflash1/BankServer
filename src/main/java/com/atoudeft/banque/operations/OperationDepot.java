package com.atoudeft.banque.operations;

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
}
