/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author plintus
 */
@Entity
@Table(name = "etc", catalog = "survey", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"key", "idcompany", "idusers"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etc.findAll", query = "SELECT e FROM Etc e")
    , @NamedQuery(name = "Etc.findById", query = "SELECT e FROM Etc e WHERE e.id = :id")
    , @NamedQuery(name = "Etc.findByKey", query = "SELECT e FROM Etc e WHERE e.key = :key")
    , @NamedQuery(name = "Etc.findByValue", query = "SELECT e FROM Etc e WHERE e.value = :value")
    , @NamedQuery(name = "Etc.findByIdcompany", query = "SELECT e FROM Etc e WHERE e.idcompany = :idcompany")
    , @NamedQuery(name = "Etc.findByIdusers", query = "SELECT e FROM Etc e WHERE e.idusers = :idusers")})
public class Etc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "key", nullable = false, length = 64)
    private String key;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "value", nullable = false, length = 256)
    private String value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcompany", nullable = false)
    private int idcompany;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idusers", nullable = false)
    private int idusers;

    public Etc() {
    }

    public Etc(Integer id) {
        this.id = id;
    }

    public Etc(Integer id, String key, String value, int idcompany, int idusers) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.idcompany = idcompany;
        this.idusers = idusers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(int idcompany) {
        this.idcompany = idcompany;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
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
        if (!(object instanceof Etc)) {
            return false;
        }
        Etc other = (Etc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.Etc[ id=" + id + " ]";
    }
    
}
