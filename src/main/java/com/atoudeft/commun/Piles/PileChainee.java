package com.atoudeft.commun.Piles;

import java.io.Serializable;

/**
 *
 * @param <T>
 * @Autho
 */
public class PileChainee<T> implements Serializable {
    Noeud<T> premierNoeud;
    int taille;

    public PileChainee(){
        taille = 0;
    }
    public void ajouter(T contenu){
        if (premierNoeud != null){
            premierNoeud.ajouterElement(contenu);
        }else {
            premierNoeud = new Noeud<T>(contenu);
        }
        taille++;
    }

    /**
     *
     * @param pos
     */
    public void enlever(int pos){
        taille--;
        if (pos !=0){
            getNoeud(pos-1).setSuivant(getNoeud(pos+1));
            return;
        }
        premierNoeud = premierNoeud.getSuivant();
    }
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

    public T get(int pos){
        Noeud<T> noeud = getNoeud(pos);
        if (noeud == null){
            return null;
        }
        return noeud.getContenu();
    }

    /**
     * Donne la longueur de la chaine
     * @return nombres delements dans la chaine
     */
    public int getTaille(){
        return taille;
    }


}

