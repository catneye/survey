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
@Table(name = "news", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "News.findAll", query = "SELECT n FROM News n")
    , @NamedQuery(name = "News.findAllDesc", query = "SELECT n FROM News n order by n.adddate DESC")
    , @NamedQuery(name = "News.findById", query = "SELECT n FROM News n WHERE n.id = :id")
    , @NamedQuery(name = "News.findByName", query = "SELECT n FROM News n WHERE n.name = :name")
    , @NamedQuery(name = "News.findByDescription", query = "SELECT n FROM News n WHERE n.description = :description")
    , @NamedQuery(name = "News.findByAdddate", query = "SELECT n FROM News n WHERE n.adddate = :adddate")
    , @NamedQuery(name = "News.findByStartdate", query = "SELECT n FROM News n WHERE n.startdate = :startdate")
    , @NamedQuery(name = "News.findByStopdate", query = "SELECT n FROM News n WHERE n.stopdate = :stopdate")
    , @NamedQuery(name = "News.findByDatesOrd", query = "SELECT n FROM News n WHERE "
            + "n.stopdate <= :actualdate and n.startdate >= :actualdate order by n.startdate")})
public class News implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "description", nullable = false, length = 2048)
    private String description;
    @Column(name = "adddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adddate;
    @Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Column(name = "stopdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopdate;

    public News() {
    }

    public News(Integer id) {
        this.id = id;
    }

    public News(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getStopdate() {
        return stopdate;
    }

    public void setStopdate(Date stopdate) {
        this.stopdate = stopdate;
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
        if (!(object instanceof News)) {
            return false;
        }
        News other = (News) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.News[ id=" + id + " ]";
    }
    
}
