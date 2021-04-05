/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.info;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author plintus
 */
public class ChampingInfo implements Serializable{
    
    private Integer id;
    private int idcompany;
    private String name;
    private String champingtype;
    private Date adddate;
    private boolean isenable;
    private boolean isforall;
    private String description;

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
    public int getIdcompany() {
        return idcompany;
    }

    /**
     * @param idcompany the idcompany to set
     */
    public void setIdcompany(int idcompany) {
        this.idcompany = idcompany;
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
     * @return the champingtype
     */
    public String getChampingtype() {
        return champingtype;
    }

    /**
     * @param champingtype the champingtype to set
     */
    public void setChampingtype(String champingtype) {
        this.champingtype = champingtype;
    }

    /**
     * @return the adddate
     */
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate the adddate to set
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return the isenable
     */
    public boolean getIsenable() {
        return isenable;
    }

    /**
     * @param isenable the isenable to set
     */
    public void setIsenable(boolean isenable) {
        this.isenable = isenable;
    }

    /**
     * @return the isforall
     */
    public boolean getIsforall() {
        return isforall;
    }

    /**
     * @param isforall the isforall to set
     */
    public void setIsforall(boolean isforall) {
        this.isforall = isforall;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
