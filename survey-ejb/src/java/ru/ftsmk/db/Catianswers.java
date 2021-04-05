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
@Table(name = "catianswers", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Catianswers.findAll", query = "SELECT c FROM Catianswers c")
    , @NamedQuery(name = "Catianswers.findById", query = "SELECT c FROM Catianswers c WHERE c.id = :id")
    , @NamedQuery(name = "Catianswers.findByIdcatiquestions", query = "SELECT c FROM Catianswers c WHERE c.idcatiquestions = :idcatiquestions")
    , @NamedQuery(name = "Catianswers.findByIdcatiquestionsOrdLevel", 
            query = "SELECT c FROM Catianswers c WHERE c.idcatiquestions = :idcatiquestions order by c.level")
    , @NamedQuery(name = "Catianswers.findByName", query = "SELECT c FROM Catianswers c WHERE c.name = :name")
    , @NamedQuery(name = "Catianswers.findByText", query = "SELECT c FROM Catianswers c WHERE c.text = :text")
    , @NamedQuery(name = "Catianswers.findByLevel", query = "SELECT c FROM Catianswers c WHERE c.level = :level")
    , @NamedQuery(name = "Catianswers.findByIdinclude", query = "SELECT c FROM Catianswers c WHERE c.idinclude = :idinclude")
    , @NamedQuery(name = "Catianswers.findByIseditable", query = "SELECT c FROM Catianswers c WHERE c.iseditable = :iseditable")
    , @NamedQuery(name = "Catianswers.findByIsfinal", query = "SELECT c FROM Catianswers c WHERE c.isfinal = :isfinal")
    , @NamedQuery(name = "Catianswers.findByIsenable", query = "SELECT c FROM Catianswers c WHERE c.isenable = :isenable")
    , @NamedQuery(name = "Catianswers.findByOptions", query = "SELECT c FROM Catianswers c WHERE c.options = :options")})
public class Catianswers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcatiquestions", nullable = false)
    private int idcatiquestions;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 64)
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 256)
    @Column(name = "text", nullable = false, length = 256)
    private String text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "level", nullable = false)
    private int level;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idinclude", nullable = false)
    private int idinclude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iseditable", nullable = false)
    private boolean iseditable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isfinal", nullable = false)
    private boolean isfinal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isenable", nullable = false)
    private boolean isenable;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 1024)
    @Column(name = "options", nullable = false, length = 1024)
    private String options;

    public Catianswers() {
    }

    public Catianswers(Integer id) {
        this.id = id;
    }

    public Catianswers(Integer id, int idcatiquestions, String name, String text, int level, int idinclude, boolean iseditable, boolean isfinal, boolean isenable, String options) {
        this.id = id;
        this.idcatiquestions = idcatiquestions;
        this.name = name;
        this.text = text;
        this.level = level;
        this.idinclude = idinclude;
        this.iseditable = iseditable;
        this.isfinal = isfinal;
        this.isenable = isenable;
        this.options = options;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdcatiquestions() {
        return idcatiquestions;
    }

    public void setIdcatiquestions(int idcatiquestions) {
        this.idcatiquestions = idcatiquestions;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIdinclude() {
        return idinclude;
    }

    public void setIdinclude(int idinclude) {
        this.idinclude = idinclude;
    }

    public boolean getIseditable() {
        return iseditable;
    }

    public void setIseditable(boolean iseditable) {
        this.iseditable = iseditable;
    }

    public boolean getIsfinal() {
        return isfinal;
    }

    public void setIsfinal(boolean isfinal) {
        this.isfinal = isfinal;
    }

    public boolean getIsenable() {
        return isenable;
    }

    public void setIsenable(boolean isenable) {
        this.isenable = isenable;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
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
        if (!(object instanceof Catianswers)) {
            return false;
        }
        Catianswers other = (Catianswers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.ftsmk.db.Catianswers[ id=" + id + " ]";
    }
    
}
