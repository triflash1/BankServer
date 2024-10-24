package com.atoudeft.commun.Piles;

import java.io.Serializable;

public class Noeud<T> implements Serializable {
    private Noeud<T> suivant;
    T contenu;

    public Noeud(T contenu){
        this.contenu = contenu;
    }

    public void setSuivant(Noeud<T> suivant) {
        this.suivant = suivant;
    }
    public void ajouterElement(T contenu){
        if (suivant != null){
            suivant.ajouterElement(contenu);
        }else {
            suivant = new Noeud<T>(contenu);
        }
    }


    public Noeud<T> getSuivant(){
        return suivant;
    }

    public T getContenu(){
        return contenu;
    }
}

