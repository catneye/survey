/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.info;

import java.util.Date;

/**
 *
 * @author plintus
 */
public class NewsInfo {
    
    private Integer id;
    private String name;
    private String description;
    private Date adddate;
    private Date startdate;
    private Date stopdate;

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
     * @return the startdate
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * @return the stopdate
     */
    public Date getStopdate() {
        return stopdate;
    }

    /**
     * @param stopdate the stopdate to set
     */
    public void setStopdate(Date stopdate) {
        this.stopdate = stopdate;
    }
}
