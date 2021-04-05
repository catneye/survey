/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author plintus
 */
@Entity
@Table(name = "catilog", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catilog.findAll", query = "SELECT c FROM Catilog c")
    , @NamedQuery(name = "Catilog.findById", query = "SELECT c FROM Catilog c WHERE c.id = :id")
    , @NamedQuery(name = "Catilog.findByIdchamping", query = "SELECT c FROM Catilog c WHERE c.idchamping = :idchamping")
    , @NamedQuery(name = "Catilog.findByIdcatiquestions", query = "SELECT c FROM Catilog c WHERE c.idcatiquestions = :idcatiquestions")
    , @NamedQuery(name = "Catilog.findByIdcatianswers", query = "SELECT c FROM Catilog c WHERE c.idcatianswers = :idcatianswers")
    , @NamedQuery(name = "Catilog.findByIdusers", query = "SELECT c FROM Catilog c WHERE c.idusers = :idusers")
    , @NamedQuery(name = "Catilog.findByIdbase", query = "SELECT c FROM Catilog c WHERE c.idbase = :idbase")
    , @NamedQuery(name = "Catilog.findByAdddate", query = "SELECT c FROM Catilog c WHERE c.adddate = :adddate")
    , @NamedQuery(name = "Catilog.findByIscheck", query = "SELECT c FROM Catilog c WHERE c.ischeck = :ischeck")
    , @NamedQuery(name = "Catilog.findByValue", query = "SELECT c FROM Catilog c WHERE c.value = :value")
    , @NamedQuery(name = "Catilog.findByIsfinal", query = "SELECT c FROM Catilog c WHERE c.isfinal = :isfinal")
    , @NamedQuery(name = "Catilog.findByQlevel", query = "SELECT c FROM Catilog c WHERE c.qlevel = :qlevel")
    , @NamedQuery(name = "Catilog.findByAlevel", query = "SELECT c FROM Catilog c WHERE c.alevel = :alevel")
    , @NamedQuery(name = "Catilog.findByIsqenable", query = "SELECT c FROM Catilog c WHERE c.isqenable = :isqenable")
    , @NamedQuery(name = "Catilog.findByIsaenable", query = "SELECT c FROM Catilog c WHERE c.isaenable = :isaenable")
    , @NamedQuery(name = "Catilog.findByIdrequest", query = "SELECT c FROM Catilog c WHERE c.idrequest = :idrequest")
    , @NamedQuery(name = "Catilog.findByIpv4", query = "SELECT c FROM Catilog c WHERE c.ipv4 = :ipv4")
    , @NamedQuery(name = "Catilog.findByIdrequestIdcatiquestions", 
            query = "SELECT c FROM Catilog c WHERE c.idrequest = :idrequest and c.idcatiquestions = :idcatiquestions")
    , @NamedQuery(name = "Catilog.findByIdrequestIdcatianswers", 
            query = "SELECT c FROM Catilog c WHERE c.idrequest = :idrequest and c.idcatianswers = :idcatianswers")
    , @NamedQuery(name = "Catilog.findByIdchampingDates", 
            query = "SELECT c FROM Catilog c WHERE c.idchamping = :idchamping and c.adddate <= :edate and c.adddate >= :bdate")
    , @NamedQuery(name = "Catilog.findByIdchampingIdrequestsDates", 
            query = "SELECT c FROM Catilog c WHERE c.idrequest = :idrequest and c.idchamping = :idchamping "
                    + "and c.adddate <= :edate and c.adddate >= :bdate")})
public class Catilog implements Serializable {

    @Size(max = 256)
    @Column(name = "ipv4", length = 256)
    private String ipv4;

    @Basic(optional = false)
    @NotNull
    @Column(name = "idchamping", nullable = false)
    private int idchamping;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcatiquestions", nullable = false)
    private int idcatiquestions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcatianswers", nullable = false)
    private int idcatianswers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idusers", nullable = false)
    private int idusers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idbase", nullable = false)
    private int idbase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ischeck", nullable = false)
    private boolean ischeck;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isfinal", nullable = false)
    private boolean isfinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idrequest", nullable = false)
    private int idrequest;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "adddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adddate;
    @Size(max = 256)
    @Column(name = "value", length = 256)
    private String value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qlevel", nullable = false)
    private int qlevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "alevel", nullable = false)
    private int alevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isqenable", nullable = false)
    private boolean isqenable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isaenable", nullable = false)
    private boolean isaenable;

    public Catilog() {
    }

    public Catilog(Integer id) {
        this.id = id;
    }

    public Catilog(Integer id, int qlevel, int alevel, boolean isqenable, boolean isaenable) {
        this.id = id;
        this.qlevel = qlevel;
        this.alevel = alevel;
        this.isqenable = isqenable;
        this.isaenable = isaenable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIsfinal() {
        return isfinal;
    }

    public void setIsfinal(Boolean isfinal) {
        this.isfinal = isfinal;
    }

    public int getQlevel() {
        return qlevel;
    }

    public void setQlevel(int qlevel) {
        this.qlevel = qlevel;
    }

    public int getAlevel() {
        return alevel;
    }

    public void setAlevel(int alevel) {
        this.alevel = alevel;
    }

    public boolean getIsqenable() {
        return isqenable;
    }

    public void setIsqenable(boolean isqenable) {
        this.isqenable = isqenable;
    }

    public boolean getIsaenable() {
        return isaenable;
    }

    public void setIsaenable(boolean isaenable) {
        this.isaenable = isaenable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catilog)) {
            return false;
        }
        Catilog other = (Catilog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.Catilog[ id=" + id + " ]";
    }

    public int getIdchamping() {
        return idchamping;
    }

    public void setIdchamping(int idchamping) {
        this.idchamping = idchamping;
    }

    public int getIdcatiquestions() {
        return idcatiquestions;
    }

    public void setIdcatiquestions(int idcatiquestions) {
        this.idcatiquestions = idcatiquestions;
    }

    public int getIdcatianswers() {
        return idcatianswers;
    }

    public void setIdcatianswers(int idcatianswers) {
        this.idcatianswers = idcatianswers;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public int getIdbase() {
        return idbase;
    }

    public void setIdbase(int idbase) {
        this.idbase = idbase;
    }

    public boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public int getIdrequest() {
        return idrequest;
    }

    public void setIdrequest(int idrequest) {
        this.idrequest = idrequest;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }
    
}
