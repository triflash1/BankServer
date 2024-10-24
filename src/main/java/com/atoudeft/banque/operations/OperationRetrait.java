package com.atoudeft.banque.operations;
/**
 * Auteur : Alexandre Gamache
 */
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

    /**
     * Génere la description complete d'une operation de retrait
     * @return la string en format "Date TYPE montant"
     */
    @Override
    public String toString() {
        return super.toString() + " " + montant;
    }
}
