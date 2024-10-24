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
     * Permet de garder un historique des op�rations
     * @param type type de l'op�ration effectu�
     */
    public Operation(TypeOperation type){
        this.typeOperation = type;
        date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }


    /**
     * G�n�re le d�but d'une string qui decris une operation
     * @return les informations de l'operation avec le format "Date  TYPE"
     */
    @Override
    public String toString() {
        return date.toString() +" " + typeOperation.name();
    }
}
