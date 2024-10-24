package com.atoudeft.banque.operations;

public class OperationRetrait extends Operation{
    double montant;
    /**
     * Permet de garder un historique des opérations de retrait
     *
     * @param montant du retrait
     */
    public OperationRetrait(double montant) {
        super(TypeOperation.RETRAIT);
        this.montant = montant;
    }
}
