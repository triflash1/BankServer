package com.atoudeft.commun.Piles;

import java.io.Serializable;

/**
 *Pile chainée qui permet de cc
 * @param <T> type d'objet contenu dans la pile
 * @Auteur Alexandre Gamache
 */
public class PileChainee<T> implements Serializable {
    Noeud<T> premierNoeud;


    public PileChainee(){}

    /**
     * Ajoute un objet a la fin de la pile
     * @param contenu a ajouter a la fin
     */
    public void ajouter(T contenu){
        if (premierNoeud != null){
            premierNoeud.ajouterElement(contenu);
        }else {
            premierNoeud = new Noeud<T>(contenu);
        }
    }

    /**
     *  Enleve le noeud a la position voulue
     * @param pos position ou le noeud va être effacé
     */
    public void enlever(int pos){
        if (pos !=0){
            getNoeud(pos-1).setSuivant(getNoeud(pos+1));
            return;
        }
        premierNoeud = premierNoeud.getSuivant();
    }

    /**
     *Trouve le noeud à une postion désirée
     * @param pos la position dont on désire obtenir le noeud
     * @return le noeud a la position voulue
     */
    private Noeud<T> getNoeud(int pos){
        Noeud<T> noeudFinal = premierNoeud;
        for (int i = 0; i < pos; i++) {

            noeudFinal = noeudFinal.getSuivant();
            if (noeudFinal == null) {
                return null;
            }
        }
        return noeudFinal;
    }

    /**
     * Permet d'obtenir le contnu à la position demandée
     * @param pos position ou on veut obtenir le contenu
     * @return le contenu a la position demandée
     */
    public T get(int pos){
        Noeud<T> noeud = getNoeud(pos);
        if (noeud == null){
            return null;
        }
        return noeud.getContenu();
    }

}

