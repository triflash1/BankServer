package com.atoudeft.banque.operations;

public class OperationFacture extends Operation{
    double montant;
    String numFact;
    String desc;
    /**
     * Permet de garder un historique des op�rations de d�pot
     *
     * @param montant de la facture
     * @param numFact numero de la facture
     * @param desc description donn� a la facture
     */
    public OperationFacture(double montant,String numFact,String desc) {
        super(TypeOperation.DEPOT);
        this.montant = montant;
        this.numFact = numFact;
        this.desc = desc;
    }
}