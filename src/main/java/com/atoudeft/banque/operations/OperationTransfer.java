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
    /**
     * Genere la description complete d'une operation de type Transfer
     * @return la string en format "Date TYPE montant numeroDestinataire"
     */
    @Override
    public String toString() {
        return super.toString() + " " + montant + " " + numDest;
    }
}
