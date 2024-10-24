package com.atoudeft.banque.operations;

import java.io.Serializable;
import java.util.Date;

public abstract class Operation implements Serializable {
    //TODO mettre date et typeOperation final
    private TypeOperation typeOperation;
    private Date date;

    /**
     * Permet de garder un historique des op�rations
     * @param type type de l'op�ration effectu�
     */
    public Operation(TypeOperation type){
        this.typeOperation = type;
        date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

}
