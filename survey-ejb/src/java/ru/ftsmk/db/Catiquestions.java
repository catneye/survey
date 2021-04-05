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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author plintus
 */
@Entity
@Table(name = "catiquestions", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catiquestions.findAll", query = "SELECT c FROM Catiquestions c")
    , @NamedQuery(name = "Catiquestions.findById", query = "SELECT c FROM Catiquestions c WHERE c.id = :id")
    , @NamedQuery(name = "Catiquestions.findByIdchamping", query = "SELECT c FROM Catiquestions c WHERE c.idchamping = :idchamping")
    , @NamedQuery(name = "Catiquestions.findByIdchampingOrdLevel", 
            query = "SELECT c FROM Catiquestions c WHERE c.idchamping = :idchamping order by c.level")
    , @NamedQuery(name = "Catiquestions.findByIdparent", query = "SELECT c FROM Catiquestions c WHERE c.idparent = :idparent")
    , @NamedQuery(name = "Catiquestions.findByName", query = "SELECT c FROM Catiquestions c WHERE c.name = :name")
    , @NamedQuery(name = "Catiquestions.findByText", query = "SELECT c FROM Catiquestions c WHERE c.text = :text")
    , @NamedQuery(name = "Catiquestions.findByCondition", query = "SELECT c FROM Catiquestions c WHERE c.condition = :condition")
    , @NamedQuery(name = "Catiquestions.findBySelectmax", query = "SELECT c FROM Catiquestions c WHERE c.selectmax = :selectmax")
    , @NamedQuery(name = "Catiquestions.findByDirection", query = "SELECT c FROM Catiquestions c WHERE c.direction = :direction")
    , @NamedQuery(name = "Catiquestions.findByIsshowanswertext", query = "SELECT c FROM Catiquestions c WHERE c.isshowanswertext = :isshowanswertext")
    , @NamedQuery(name = "Catiquestions.findByIsrandom", query = "SELECT c FROM Catiquestions c WHERE c.israndom = :israndom")
    , @NamedQuery(name = "Catiquestions.findByLevel", query = "SELECT c FROM Catiquestions c WHERE c.level = :level")
    , @NamedQuery(name = "Catiquestions.findByIsenable", query = "SELECT c FROM Catiquestions c WHERE c.isenable = :isenable")})
public class Catiquestions implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "israndomchild", nullable = false)
    private boolean israndomchild;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idchamping", nullable = false)
    private int idchamping;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idparent", nullable = false)
    private int idparent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 64)
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 2147483647)
    @Column(name = "text", nullable = false, length = 2147483647)
    private String text;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 256)
    @Column(name = "condition", nullable = false, length = 256)
    private String condition;
    @Basic(optional = false)
    @NotNull
    @Column(name = "selectmax", nullable = false)
    private int selectmax;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "direction", nullable = false, length = 64)
    private String direction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isshowanswertext", nullable = false)
    private boolean isshowanswertext;
    @Basic(optional = false)
    @NotNull
    @Column(name = "israndom", nullable = false)
    private boolean israndom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "level", nullable = false)
    private int level;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isenable", nullable = false)
    private boolean isenable;

    public Catiquestions() {
    }

    public Catiquestions(Integer id) {
        this.id = id;
    }

    public Catiquestions(Integer id, int idchamping, int idparent, String name, String text, String condition, int selectmax, String direction, boolean isshowanswertext, boolean israndom, int level, boolean isenable) {
        this.id = id;
        this.idchamping = idchamping;
        this.idparent = idparent;
        this.name = name;
        this.text = text;
        this.condition = condition;
        this.selectmax = selectmax;
        this.direction = direction;
        this.isshowanswertext = isshowanswertext;
        this.israndom = israndom;
        this.level = level;
        this.isenable = isenable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdchamping() {
        return idchamping;
    }

    public void setIdchamping(int idchamping) {
        this.idchamping = idchamping;
    }

    public int getIdparent() {
        return idparent;
    }

    public void setIdparent(int idparent) {
        this.idparent = idparent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getSelectmax() {
        return selectmax;
    }

    public void setSelectmax(int selectmax) {
        this.selectmax = selectmax;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean getIsshowanswertext() {
        return isshowanswertext;
    }

    public void setIsshowanswertext(boolean isshowanswertext) {
        this.isshowanswertext = isshowanswertext;
    }

    public boolean getIsrandom() {
        return israndom;
    }

    public void setIsrandom(boolean israndom) {
        this.israndom = israndom;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        if (!(object instanceof Catiquestions)) {
            return false;
        }
        Catiquestions other = (Catiquestions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.Catiquestions[ id=" + id + " ]";
    }

    public boolean getIsrandomchild() {
        return israndomchild;
    }

    public void setIsrandomchild(boolean israndomchild) {
        this.israndomchild = israndomchild;
    }
    
}
