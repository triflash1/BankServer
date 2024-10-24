package com.atoudeft.banque.operations;

import java.io.Serializable;
import java.util.Date;
/**
 * Auteur : Alexandre Gamache
 */
public abstract class Operation implements Serializable {

    private final TypeOperation typeOperation;
    private final Date date;

    /**
     * Permet de garder un historique des opérations
     * @param type type de l'opération effectué
     */
    public Operation(TypeOperation type){
        this.typeOperation = type;
        date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }


    /**
     * Génère le début d'une string qui decris une operation
     * @return les informations de l'operation avec le format "Date  TYPE"
     */
    @Override
    public String toString() {
        return date.toString() +" " + typeOperation.name();
    }
}
