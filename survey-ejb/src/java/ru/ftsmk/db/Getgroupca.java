/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.db;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author plintus
 */
@Entity
@Table(name = "getgroupca", catalog = "survey", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Getgroupca.findAll", query = "SELECT g FROM Getgroupca g")
    , @NamedQuery(name = "Getgroupca.findById", query = "SELECT g FROM Getgroupca g WHERE g.id = :id")
    , @NamedQuery(name = "Getgroupca.findByIdchamping", query = "SELECT g FROM Getgroupca g WHERE g.idchamping = :idchamping")
    , @NamedQuery(name = "Getgroupca.findByIdcatiquestions", query = "SELECT g FROM Getgroupca g WHERE g.idcatiquestions = :idcatiquestions")
    , @NamedQuery(name = "Getgroupca.findByIdcatianswers", query = "SELECT g FROM Getgroupca g WHERE g.idcatianswers = :idcatianswers")
    , @NamedQuery(name = "Getgroupca.findByCount", query = "SELECT g FROM Getgroupca g WHERE g.count = :count")})
public class Getgroupca implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "id", precision = 17, scale = 17)
    private Double id;
    @Column(name = "idchamping")
    private Integer idchamping;
    @Column(name = "idcatiquestions")
    private Integer idcatiquestions;
    @Column(name = "idcatianswers")
    private Integer idcatianswers;
    @Column(name = "count")
    private BigInteger count;

    public Getgroupca() {
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Integer getIdchamping() {
        return idchamping;
    }

    public void setIdchamping(Integer idchamping) {
        this.idchamping = idchamping;
    }

    public Integer getIdcatiquestions() {
        return idcatiquestions;
    }

    public void setIdcatiquestions(Integer idcatiquestions) {
        this.idcatiquestions = idcatiquestions;
    }

    public Integer getIdcatianswers() {
        return idcatianswers;
    }

    public void setIdcatianswers(Integer idcatianswers) {
        this.idcatianswers = idcatianswers;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        this.count = count;
    }
    
}
