package com.atoudeft.serveur;

import com.atoudeft.banque.Banque;
import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.CompteClient;
import com.atoudeft.banque.TypeCompte;
import com.atoudeft.banque.*;
import com.atoudeft.banque.Banque;
import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.CompteClient;
import com.atoudeft.banque.TypeCompte;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

import java.util.List;


/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        ServeurBanque serveurBanque = (ServeurBanque)serveur;
        Banque banque;
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip;
        String[] t;

        if (source instanceof Connexion) {
            cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            cnx.setTempsDerniereOperation(System.currentTimeMillis());
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveurBanque.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des numéros de comptes-clients connectés :
                    cnx.envoyer("LIST " + serveurBanque.list());
                    break;
                /******************* COMMANDES DE GESTION DE COMPTES *******************/
                case "NOUVEAU": //Crée un nouveau compte-client :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO deja connecte");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("NOUVEAU NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.ajouter(numCompteClient,nip)) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("NOUVEAU OK " + t[0] + " cree");
                            cnx.envoyer(" : " + banque.getNumeroCompteParDefaut(numCompteClient));

                        }
                        else
                            cnx.envoyer("NOUVEAU NO "+t[0]+" existe");
                    }
                    break;

                case "CONNECT":
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("CONNECT NO");
                    }
                    else {                                                                                              //TODO Manque la vérification si il est déja connecté
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.getCompteClient(numCompteClient) == null) {
                            cnx.envoyer("CONNECT NO");
                        }
                        else {
                            CompteClient compteClient = banque.getCompteClient(numCompteClient);
                            if (compteClient.verificationCompte(numCompteClient, nip)) {
                                cnx.setNumeroCompteClient(numCompteClient);
                                cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                                cnx.envoyer("CONNECT OK");
                            }
                            else {
                                cnx.envoyer("CONNECT NO");
                            }
                        }

                    }
                    break;
                case "EPARGNE":
                    banque = serveurBanque.getBanque();
                    if (cnx.getNumeroCompteClient() == null) {cnx.envoyer("EPARGNE NO (pas Connecté)");}
                    else {cnx.envoyer(banque.ajouterEpargne(cnx.getNumeroCompteClient()));}
                    break;



                case "SELECT":
                    banque = serveurBanque.getBanque();
                    argument = evenement.getArgument();
                    if (cnx.getNumeroCompteClient() == null) {
                        cnx.envoyer("SELECT NO (pas Connecté)");
                        break;
                    }
                    String numeroclient = cnx.getNumeroCompteClient();
                    CompteClient compteClient = banque.getCompteClient(numeroclient);
                    List<CompteBancaire> comptes = compteClient.getComptes();
                    CompteBancaire compteBancaire = null;

                    if (argument.equalsIgnoreCase("cheque")) {
                        for (int i = 0; i < comptes.size(); i++) {
                            if (comptes.get(i).getType() == TypeCompte.CHEQUE) {
                                compteBancaire = comptes.get(i);
                                break;
                            }
                        }
                        if (compteBancaire == null) {
                            cnx.envoyer("SELECT NO (Compte inexistant)");
                        } else {
                            cnx.setNumeroCompteActuel(compteBancaire.getNumero());
                            cnx.envoyer("SELECT OK");
                        }

                    }else if (argument.equalsIgnoreCase("epargne")) {
                        for (int i = 0; i < comptes.size(); i++) {
                            if (comptes.get(i).getType() == TypeCompte.EPARGNE) {
                                compteBancaire = comptes.get(i);
                                break;
                            }
                        }
                        if (compteBancaire == null) {
                            cnx.envoyer("SELECT NO (Compte inexistant)");
                        } else {
                            cnx.setNumeroCompteActuel(compteBancaire.getNumero());
                            cnx.envoyer("SELECT OK");
                        }

                    }else {
                        cnx.envoyer("SELECT NO (cheque ou epargne)");
                        break;
                    }
                    break;
                    //Auteur Alexandre Gamache
                case "DEPOT", "RETRAIT":
                    try{
                        double montantDepot = Double.parseDouble(evenement.getArgument());

                        banque = serveurBanque.getBanque();
                        compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        if (compteClient == null){
                            cnx.envoyer( typeEvenement + " " + evenement.getArgument() + " NO (Pas connecté a un compte client)");
                            break;
                        }
                        compteBancaire = compteClient.obtenirCompteBancaire(cnx.getNumeroCompteActuel());
                        if (compteBancaire == null){
                            cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (Compte Bancaire Inexistant)");
                            break;
                        }

                        //Détermine si c'est un dépot ou un retrait avant de faire l'action approprié
                        boolean resultat = false;
                        if (typeEvenement.equals("DEPOT")){
                           resultat = compteBancaire.crediter(montantDepot);
                        }else {
                            resultat = compteBancaire.debiter(montantDepot);
                        }

                        if(resultat){
                            cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " OK");
                            break;
                        }
                        cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (Montant invalide)");

                    }catch (NumberFormatException numberFormatException){
                        cnx.envoyer(typeEvenement + " "+ evenement.getArgument()+ " NO (Formmat de montant invalide)");
                    }
                    break;
                    //Auteur Alexandre Gamache
                case "FACTURE","TRANSFER":

                    banque = serveurBanque.getBanque();
                    compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                    if (compteClient == null){
                        cnx.envoyer( typeEvenement + " " + evenement.getArgument() + " NO (Pas connecté a un compte client)");
                        break;
                    }
                    compteBancaire = compteClient.obtenirCompteBancaire(cnx.getNumeroCompteActuel());
                    if (compteBancaire == null){
                        cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (Compte bancaire inexistant)");
                        break;
                    }

                    try {
                        String[] evenementSepare = evenement.getArgument().split(" ");

                        if (typeEvenement.equals("TRANSFER") && evenementSepare.length != 2){
                            System.out.println(evenementSepare.length);
                            cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (arguments invalides)");
                            break;
                        }
                        if (typeEvenement.equals("FACTURE") && evenementSepare.length <= 2){
                            cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (arguments invalides)");
                            break;
                        }

                        double montant = Double.parseDouble(evenementSepare[0]);

                        String numEvenement = evenementSepare[1];

                        if (typeEvenement.equals("FACTURE")){

                            String description = "";
                            for (int i = 2; i < evenementSepare.length; i++) {
                                description += " " + evenementSepare[i];
                            }
                            if (compteBancaire.debiter(montant)){
                                cnx.envoyer(typeEvenement + " " + montant + " " + numEvenement + " OK");
                                break;
                            }
                            cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (Montant invalide)");

                            //TODO Enregistrer la facture
                        }else {

                            CompteBancaire compteACrediter = banque.obtenirCompteBancaire(numEvenement);
                            if (compteACrediter == null){
                                cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (Compte bancaire invalide)");
                                break;
                            }
                            if (compteBancaire.debiter(montant) && compteACrediter.crediter(montant)){
                                cnx.envoyer(typeEvenement + " " + montant + " " + numEvenement + " OK");
                                break;
                            }
                            cnx.envoyer(typeEvenement + " " + evenement.getArgument() + " NO (Solde invalide)");
                            break;
                        }



                    }catch (NumberFormatException numberFormatException){
                        cnx.envoyer(typeEvenement + " "+ evenement.getArgument()+ " NO (Formmat de montant invalide)");
                    }
                    break;

                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}