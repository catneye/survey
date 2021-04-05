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
public class BaseInfo implements Serializable{
    private Integer id;
    private String name;
    private Integer idchamping;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the idchamping
     */
    public Integer getIdchamping() {
        return idchamping;
    }

    /**
     * @param idchamping the idchamping to set
     */
    public void setIdchamping(Integer idchamping) {
        this.idchamping = idchamping;
    }
    
}
