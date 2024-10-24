package com.atoudeft.banque.operations;

public class OperationTransfer extends Operation{
    double montant;
    String numDest;
    /**
     * Permet de garder un historique des opérations de transfer
     *
     * @param montant du transfer
     * @param numDest numero du destinataire du transfer
     */
    public OperationTransfer(double montant,String numDest) {
        super(TypeOperation.TRANSFER);
        this.montant =montant;
        this.numDest = numDest;
    }
}
