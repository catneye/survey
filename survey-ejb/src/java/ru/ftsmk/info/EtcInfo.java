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
public class EtcInfo implements Serializable{
    
    private String key;
    private String value;
    private Integer id;
    private Integer idcompany;
    private Integer idusers;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

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
     * @return the idcompany
     */
    public Integer getIdcompany() {
        return idcompany;
    }

    /**
     * @param idcompany the idcompany to set
     */
    public void setIdcompany(Integer idcompany) {
        this.idcompany = idcompany;
    }

    /**
     * @return the idusers
     */
    public Integer getIdusers() {
        return idusers;
    }

    /**
     * @param idusers the idusers to set
     */
    public void setIdusers(Integer idusers) {
        this.idusers = idusers;
    }
}
