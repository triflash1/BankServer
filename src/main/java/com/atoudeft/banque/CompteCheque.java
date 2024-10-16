package com.atoudeft.banque;

public class CompteCheque extends CompteBancaire {

    //Pour le moment, il est là parce que il y a une erreur sinon
    public CompteCheque(String numero, TypeCompte type) {
        super(numero, type);
    }

    /**
     * Ajoute un montant d'argent dans le compte bancaire.
     * @param montant le montant à ajouter
     * @return retourne true si le monant est positif (en ajoutant le montant au solde) sinon retourne false
     */
    @Override
    public boolean crediter(double montant) {
        if (montant > 0) {
            setSolde(this.getSolde() + montant);
            return true;
        }
        return false;
    }

    /**
     * Retire un momtant d'argent du compte bancaire si il est positif et plus petit ou égale au montant du solde du compte.
     * @param montant le montant à retirer
     * @return retourne true si le montant est positif et plus petit ou égale au montant du solde du compte sinon retourne false
     */
    @Override
    public boolean debiter(double montant) {
        if (montant > 0 && montant <= getSolde()) {
            setSolde(getSolde() - montant);
            return true;
        }
        return false;
    }

    }
}
