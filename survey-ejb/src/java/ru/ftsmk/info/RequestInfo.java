/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.info;

import java.io.Serializable;

/**
 *
 * @author plintus
 */
public class RequestInfo implements Serializable  {
    
    private Integer id;
    private int idbase;
    private String token;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the idbase
     */
    public int getIdbase() {
        return idbase;
    }

    /**
     * @param idbase the idbase to set
     */
    public void setIdbase(int idbase) {
        this.idbase = idbase;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
