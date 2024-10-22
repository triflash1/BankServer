package com.atoudeft.banque;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Banque implements Serializable
{
    private String nom;
    private List<CompteClient> comptes;

    public Banque(String nom) {
        this.nom = nom;
        this.comptes = new ArrayList<>();
    }




    /**
     * Recherche un compte-client à partir de son numéro.
     *
     * @param numeroCompteClient le numéro du compte-client
     * @return le compte-client s'il a été trouvé. Sinon, retourne null
     */
    public CompteClient getCompteClient(String numeroCompteClient) {
        for (int i = 0; i < comptes.size(); i++) {
            if (comptes.get(i).getNumero().equalsIgnoreCase(numeroCompteClient)) {
                return comptes.get(i);
            }
        }
        return null;
    }

    /**
     * Génère un numéro de compte bancaire aléatoirement avec le format CCC00C, où C est un caractère alphabétique
     * majuscule et 0 est un chiffre entre 0 et 9.
     * @return
     */
    public String genereNouveauNumero() {
        boolean alreadyExists = false;
        char[] t = new char[6];
        do
        {

        for (int i=0;i<3;i++) {
            t[i] = (char)((int)(Math.random()*26)+'A');
        }
        for (int i=3;i<5;i++) {
            t[i] = (char)((int)(Math.random()*10)+'0');
        }
        t[5] = (char)((int)(Math.random()*26)+'A');

            for (CompteClient compte : comptes)
            {
                for (int j = 0; j < compte.getComptes().size(); j++)
                {
                    alreadyExists = !compte.getComptes().get(j).getNumero().equals(new String(t));
                }
            }

        }while (alreadyExists);
        return new String(t);
    }



    /**
     * Vérifier qu'un compte-bancaire appartient bien au compte-client.
     *
     * @param numeroCompteBancaire numéro du compte-bancaire
     * @param numeroCompteClient    numéro du compte-client
     * @return  true si le compte-bancaire appartient au compte-client
     */
    public boolean appartientA(String numeroCompteBancaire, String numeroCompteClient) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un dépot d'argent dans un compte-bancaire
     *
     * @param montant montant à déposer
     * @param numeroCompte numéro du compte
     * @return true si le dépot s'est effectué correctement
     */
    public boolean deposer(double montant, String numeroCompte) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un retrait d'argent d'un compte-bancaire
     *
     * @param montant montant retiré
     * @param numeroCompte numéro du compte
     * @return true si le retrait s'est effectué correctement
     */
    public boolean retirer(double montant, String numeroCompte) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un transfert d'argent d'un compte à un autre de la même banque
     * @param montant montant à transférer
     * @param numeroCompteInitial   numéro du compte d'où sera prélevé l'argent
     * @param numeroCompteFinal numéro du compte où sera déposé l'argent
     * @return true si l'opération s'est déroulée correctement
     */
    public boolean transferer(double montant, String numeroCompteInitial, String numeroCompteFinal) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un paiement de facture.
     * @param montant montant de la facture
     * @param numeroCompte numéro du compte bancaire d'où va se faire le paiement
     * @param numeroFacture numéro de la facture
     * @param description texte descriptif de la facture
     * @return true si le paiement s'est bien effectuée
     */
    public boolean payerFacture(double montant, String numeroCompte, String numeroFacture, String description) {
        throw new NotImplementedException();
    }

    /**
     * Crée un nouveau compte-client avec un numéro et un nip et l'ajoute à la liste des comptes.
     *
     * @param numCompteClient numéro du compte-client à créer
     * @param nip nip du compte-client à créer
     * @return true si le compte a été créé correctement
     */
    public boolean ajouter(String numCompteClient, String nip) {

        //Vérification du numéro de compte-client
        if (numCompteClient.length() < 6 || numCompteClient.length() > 8){
            return false;
        }
        for (int i = 0; i < numCompteClient.length(); i++) {
            char caractere = numCompteClient.charAt(i);     //Vérifie que chaque caractère est un nombre ou une lettre majuscule
            if (!(caractere >= 48 && caractere <= 57) && !(caractere >=65 && caractere<= 89  )  ) {
                return false;
            }
        }

        //Vérification du nip
        if (nip.length() < 4 || nip.length() > 5) {     //Vérifie la longueur du nip
            return false;
        }
        for (int i = 0; i < nip.length(); i++) {
            char caractere = nip.charAt(i);
            if (!(caractere >= 48 && caractere <= 57)) {    //Vérifie si chaque caractère est un nombre
                return false;
            }
        }

        //Vérification de l'existance du numéro du compte client
        for (int i = 0; i < comptes.size(); i++) {
            if (comptes.get(i).getNumero().equalsIgnoreCase(numCompteClient)) {
                return false;
            }
        }

        //Création du compte-client
        CompteClient compteClient = new CompteClient(numCompteClient,nip);
        String nouveauNumero = genereNouveauNumero();
        CompteCheque compteCheque = new CompteCheque(nouveauNumero,TypeCompte.CHEQUE);
        compteClient.ajouter(compteCheque);
        this.comptes.add(compteClient);
        return true;
    }

    /**
     * Retourne le numéro du compte-chèque d'un client à partir de son numéro de compte-client.
     *
     * @param numCompteClient numéro de compte-client
     * @return numéro du compte-chèque du client ayant le numéro de compte-client
     */
    public String getNumeroCompteParDefaut(String numCompteClient) {
        CompteClient compteClient = getCompteClient(numCompteClient);
        List <CompteBancaire> compteBancaires = compteClient.getComptes();
        for (int i = 0; i < compteBancaires.size(); i++) {
            if (compteBancaires.get(i).getType().equals(TypeCompte.CHEQUE)) {
                return compteBancaires.get(i).getNumero();
            }
        }
        return "ERREUR PAS DE COMPTE ÉPARGNE";
    }

    /**
     * Ajoute un compte Épargne si un n'existes pas déjà dans le compte client actuel.
     *
     * @param numCompteClient de compte-client
     * @return true si le compteClient n'a pas de compte Épargne.
     * Sinon false
     */
    public String ajouterEpargne(String numCompteClient)
    {
        CompteClient compteClient = getCompteClient(numCompteClient);
        List <CompteBancaire> compteBancaires = compteClient.getComptes();
        for (int i = 0; i < compteBancaires.size(); i++) {
            if (compteBancaires.get(i).getType().equals(TypeCompte.EPARGNE))
            {
                return "EPARGNE NO";
            }
        }
        String nouveauNumero = genereNouveauNumero();
        CompteEpargne compteEpargne = new CompteEpargne(5, nouveauNumero, TypeCompte.EPARGNE);
        compteClient.ajouter(compteEpargne);
        return nouveauNumero;
    }
}