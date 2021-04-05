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
@Table(name = "champing", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Champing.findAll", query = "SELECT c FROM Champing c")
    , @NamedQuery(name = "Champing.findById", query = "SELECT c FROM Champing c WHERE c.id = :id")
    , @NamedQuery(name = "Champing.findByIdcompany", query = "SELECT c FROM Champing c WHERE c.idcompany = :idcompany")
    , @NamedQuery(name = "Champing.findByName", query = "SELECT c FROM Champing c WHERE c.name = :name")
    , @NamedQuery(name = "Champing.findByChampingtype", query = "SELECT c FROM Champing c WHERE c.champingtype = :champingtype")
    , @NamedQuery(name = "Champing.findByAdddate", query = "SELECT c FROM Champing c WHERE c.adddate = :adddate")
    , @NamedQuery(name = "Champing.findByIsenable", query = "SELECT c FROM Champing c WHERE c.isenable = :isenable")
    , @NamedQuery(name = "Champing.findByIsforall", query = "SELECT c FROM Champing c WHERE c.isforall = :isforall")})
public class Champing implements Serializable {

    @Size(max = 2147483647)
    @Column(name = "description", length = 2147483647)
    private String description;

    @Basic(optional = false)
    @NotNull
    @Column(name = "isforall", nullable = false)
    private boolean isforall;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcompany", nullable = false)
    private int idcompany;
    @Size(max = 64)
    @Column(name = "name", length = 64)
    private String name;
    @Size(max = 64)
    @Column(name = "champingtype", length = 64)
    private String champingtype;
    @Column(name = "adddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adddate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isenable", nullable = false)
    private boolean isenable;

    public Champing() {
    }

    public Champing(Integer id) {
        this.id = id;
    }

    public Champing(Integer id, int idcompany, boolean isenable) {
        this.id = id;
        this.idcompany = idcompany;
        this.isenable = isenable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(int idcompany) {
        this.idcompany = idcompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChampingtype() {
        return champingtype;
    }

    public void setChampingtype(String champingtype) {
        this.champingtype = champingtype;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public boolean getIsenable() {
        return isenable;
    }

    public void setIsenable(boolean isenable) {
        this.isenable = isenable;
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
        if (!(object instanceof Champing)) {
            return false;
        }
        Champing other = (Champing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.Champing[ id=" + id + " ]";
    }

    public boolean getIsforall() {
        return isforall;
    }

    public void setIsforall(boolean isforall) {
        this.isforall = isforall;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
