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
public class CatiLogInfo implements Serializable{
    
    private Integer id;
    private Integer idchamping;
    private Integer idcatiquestions;
    private Integer idcatianswers;
    private Integer idusers;
    private Integer idbase;
    private Date adddate;
    private Boolean ischeck;
    private String value;
    private Boolean isfinal;
    private int qlevel;
    private int alevel;
    private boolean isqenable;
    private boolean isaenable;
    private int idrequest;
    private String ipv4;

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
     * @return the idcatiquestions
     */
    public Integer getIdcatiquestions() {
        return idcatiquestions;
    }

    /**
     * @param idcatiquestions the idcatiquestions to set
     */
    public void setIdcatiquestions(Integer idcatiquestions) {
        this.idcatiquestions = idcatiquestions;
    }

    /**
     * @return the idcatianswers
     */
    public Integer getIdcatianswers() {
        return idcatianswers;
    }

    /**
     * @param idcatianswers the idcatianswers to set
     */
    public void setIdcatianswers(Integer idcatianswers) {
        this.idcatianswers = idcatianswers;
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

    /**
     * @return the idbase
     */
    public Integer getIdbase() {
        return idbase;
    }

    /**
     * @param idbase the idbase to set
     */
    public void setIdbase(Integer idbase) {
        this.idbase = idbase;
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
     * @return the ischeck
     */
    public Boolean getIscheck() {
        return ischeck;
    }

    /**
     * @param ischeck the ischeck to set
     */
    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
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
     * @return the isafinal
     */
    public Boolean getIsfinal() {
        return isfinal;
    }

    /**
     * @param isfinal the isafinal to set
     */
    public void setIsfinal(Boolean isfinal) {
        this.isfinal = isfinal;
    }

    /**
     * @return the qlevel
     */
    public int getQlevel() {
        return qlevel;
    }

    /**
     * @param qlevel the qlevel to set
     */
    public void setQlevel(int qlevel) {
        this.qlevel = qlevel;
    }

    /**
     * @return the alevel
     */
    public int getAlevel() {
        return alevel;
    }

    /**
     * @param alevel the alevel to set
     */
    public void setAlevel(int alevel) {
        this.alevel = alevel;
    }

    /**
     * @return the isqenable
     */
    public boolean getIsqenable() {
        return isqenable;
    }

    /**
     * @param isqenable the isqenable to set
     */
    public void setIsqenable(boolean isqenable) {
        this.isqenable = isqenable;
    }

    /**
     * @return the isaenable
     */
    public boolean getIsaenable() {
        return isaenable;
    }

    /**
     * @param isaenable the isaenable to set
     */
    public void setIsaenable(boolean isaenable) {
        this.isaenable = isaenable;
    }

    /**
     * @return the idrequest
     */
    public int getIdrequest() {
        return idrequest;
    }

    /**
     * @param idrequest the idrequest to set
     */
    public void setIdrequest(int idrequest) {
        this.idrequest = idrequest;
    }

    /**
     * @return the ipv4
     */
    public String getIpv4() {
        return ipv4;
    }

    /**
     * @param ipv4 the ipv4 to set
     */
    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }
}
