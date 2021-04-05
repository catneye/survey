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
public class UniqCatilogInfo implements Serializable{
    
    private Integer id;
    private Integer idchamping;
    private Integer idrequest;
    private Date adddate;

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

    /**
     * @return the idrequest
     */
    public Integer getIdrequest() {
        return idrequest;
    }

    /**
     * @param idrequest the idrequest to set
     */
    public void setIdrequest(Integer idrequest) {
        this.idrequest = idrequest;
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
}
