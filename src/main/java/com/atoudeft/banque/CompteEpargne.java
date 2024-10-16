package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire
{
    private final int LIMITE_COMPTE_AVANT_FRAIS = 1000;
    private final int FRAIS = 2;
    private double tauxInteret;

    public CompteEpargne(double tauxInteret, String numero, TypeCompte type)
    {
        super(numero, type);
        this.tauxInteret = tauxInteret;
    }

    /**
     * Méthode de manipulation du Solde.
     * Cette méthode incrémente le solde par un taux fixé au constructeur.
     */
    public void ajouterInterets()
    {
        double montantInterets = tauxInteret * this.getSolde();
        this.setSolde(this.getSolde() + montantInterets);
    }

    @Override
    public boolean crediter(double montant)
    {
        if(this.getSolde() > 0)
        {
            this.setSolde(this.getSolde() + montant);
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant)
    {
        boolean taxeApplicable = this.getSolde() < LIMITE_COMPTE_AVANT_FRAIS;
        if(this.getSolde() > 0 && this.getSolde() >= montant)
        {
            if (taxeApplicable && montant + FRAIS <= this.getSolde())
            {
                this.setSolde(this.getSolde() - (FRAIS + montant));
                return true;
            }
            else if(taxeApplicable)
            {
                //TODO Demander au prof ce qui arrive quand la taxe s'applique mais que
                //TODO ton débit est plus grand que SOLDE - 2 (It would output a negative SOLDE)
                return false;
            } else //Ici le Solde est plus de LIMITE_COMPTE_AVANT_FRAIS
            {
                this.setSolde(this.getSolde() - montant);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description)
    {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire)
    {
        return false;
    }
}
