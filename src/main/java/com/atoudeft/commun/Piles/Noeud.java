package com.atoudeft.commun.Piles;

import java.io.Serializable;
/**
 * Noeud qui est contenu dans une Pile Chainée
 * @param <T> type d'objet contenu dans le noeud
 * @Auteur Alexandre Gamache
 */
public class Noeud<T> implements Serializable {
    private Noeud<T> suivant;
    T contenu;

    /**
     *  Créer un nouveau noeud
     * @param contenu object associé au noeud
     */
    public Noeud(T contenu){
        this.contenu = contenu;
    }

    /**
     * Change le prochaine noeud
     * @param suivant noeud qui sera le suivant
     */
    public void setSuivant(Noeud<T> suivant) {
        this.suivant = suivant;
    }

    /**
     * Ajoute un nouveau noeud apres celui-ci
     * @param contenu contenu qui doit etre ajouté noeud
     */
    public void ajouterElement(T contenu){
        if (suivant != null){
            suivant.ajouterElement(contenu);
        }else {
            suivant = new Noeud<T>(contenu);
        }
    }


    /**
     * Obtiens le prochain noeud
     * @return le prochain noeud
     */
    public Noeud<T> getSuivant(){
        return suivant;
    }

    /**
     * Obtiens le contenu associé au noeud
     * @return le contenu
     */
    public T getContenu(){
        return contenu;
    }
}

