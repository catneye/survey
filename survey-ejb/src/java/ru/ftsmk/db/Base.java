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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author plintus
 */
@Entity
@Table(name = "base", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Base.findAll", query = "SELECT b FROM Base b")
    , @NamedQuery(name = "Base.findById", query = "SELECT b FROM Base b WHERE b.id = :id")
    , @NamedQuery(name = "Base.findByName", query = "SELECT b FROM Base b WHERE b.name = :name")
    , @NamedQuery(name = "Base.findByIdchamping", query = "SELECT b FROM Base b WHERE b.idchamping = :idchamping")
    , @NamedQuery(name = "Base.findByIdchampingName", query = "SELECT b FROM Base b WHERE b.idchamping = :idchamping and b.name = :name")})
public class Base implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 64)
    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "idchamping")
    private Integer idchamping;

    public Base() {
    }

    public Base(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdchamping() {
        return idchamping;
    }

    public void setIdchamping(Integer idchamping) {
        this.idchamping = idchamping;
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
        if (!(object instanceof Base)) {
            return false;
        }
        Base other = (Base) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.Base[ id=" + id + " ]";
    }
    
}
