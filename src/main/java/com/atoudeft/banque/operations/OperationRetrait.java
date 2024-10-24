package com.atoudeft.banque.operations;

public class OperationRetrait extends Operation{
    double montant;
    /**
     * Permet de garder un historique des op�rations de retrait
     *
     * @param montant du retrait
     */
    public OperationRetrait(double montant) {
        super(TypeOperation.RETRAIT);
        this.montant = montant;
    }

    /**
     * G�nere la description complete d'une operation de retrait
     * @return la string en format "Date TYPE montant"
     */
    @Override
    public String toString() {
        return super.toString() + " " + montant;
    }
}
