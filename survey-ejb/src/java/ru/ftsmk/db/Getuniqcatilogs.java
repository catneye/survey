/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author plintus
 */
@Entity
@Table(name = "getuniqcatilogs", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Getuniqcatilogs.findAll", query = "SELECT g FROM Getuniqcatilogs g")
    , @NamedQuery(name = "Getuniqcatilogs.findById", query = "SELECT g FROM Getuniqcatilogs g WHERE g.id = :id")
    , @NamedQuery(name = "Getuniqcatilogs.findByIdchamping", query = "SELECT g FROM Getuniqcatilogs g WHERE g.idchamping = :idchamping")
    , @NamedQuery(name = "Getuniqcatilogs.findByIdrequest", query = "SELECT g FROM Getuniqcatilogs g WHERE g.idrequest = :idrequest")
    , @NamedQuery(name = "Getuniqcatilogs.findByAdddate", query = "SELECT g FROM Getuniqcatilogs g WHERE g.adddate = :adddate")
    , @NamedQuery(name = "Getuniqcatilogs.findByIdchampingIdrequestDates", 
            query = "SELECT g FROM Getuniqcatilogs g WHERE "
                    + "g.adddate >= :bdate and g.adddate <= :edate and g.idrequest = :idrequest and g.idchamping = :idchamping "
                    + "order by g.adddate")
    , @NamedQuery(name = "Getuniqcatilogs.findByIdchampingDates", 
            query = "SELECT g FROM Getuniqcatilogs g WHERE "
                    + "g.adddate >= :bdate and g.adddate <= :edate and g.idchamping = :idchamping "
                    + "order by g.adddate")
    , @NamedQuery(name = "Getuniqcatilogs.findByIdchampingDatesFinal", 
            query = "SELECT g FROM Getuniqcatilogs g WHERE "
                    + "g.adddate >= :bdate and g.adddate <= :edate and g.idchamping = :idchamping and g.isfinal=:isfinal "
                    + "order by g.adddate")})
public class Getuniqcatilogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "idchamping")
    private Integer idchamping;
    @Column(name = "idrequest")
    private Integer idrequest;
    @Column(name = "adddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adddate;
    @Column(name = "isfinal")
    private Boolean isfinal;

    public Getuniqcatilogs() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdchamping() {
        return idchamping;
    }

    public void setIdchamping(Integer idchamping) {
        this.idchamping = idchamping;
    }

    public Integer getIdrequest() {
        return idrequest;
    }

    public void setIdrequest(Integer idrequest) {
        this.idrequest = idrequest;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    /**
     * @return the isfinal
     */
    public Boolean getIsfinal() {
        return isfinal;
    }

    /**
     * @param isfinal the isfinal to set
     */
    public void setIsfinal(Boolean isfinal) {
        this.isfinal = isfinal;
    }
    
}
