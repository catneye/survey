/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.info;

import java.io.Serializable;

/**
 *
 * @author plintus
 */
public class CatiAnswersInfo implements Serializable{
    
    private Integer id;
    private int idcatiquestions;
    private String name;
    private String text;
    private int level;
    private int idinclude;
    private boolean iseditable;
    private boolean isfinal;
    private boolean isenable;
    private String options;

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
     * @return the idcatiquestions
     */
    public int getIdcatiquestions() {
        return idcatiquestions;
    }

    /**
     * @param idcatiquestions the idcatiquestions to set
     */
    public void setIdcatiquestions(int idcatiquestions) {
        this.idcatiquestions = idcatiquestions;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the idinclude
     */
    public int getIdinclude() {
        return idinclude;
    }

    /**
     * @param idinclude the idinclude to set
     */
    public void setIdinclude(int idinclude) {
        this.idinclude = idinclude;
    }

    /**
     * @return the iseditable
     */
    public boolean getIseditable() {
        return iseditable;
    }

    /**
     * @param iseditable the iseditable to set
     */
    public void setIseditable(boolean iseditable) {
        this.iseditable = iseditable;
    }

    /**
     * @return the isfinal
     */
    public boolean getIsfinal() {
        return isfinal;
    }

    /**
     * @param isfinal the isfinal to set
     */
    public void setIsfinal(boolean isfinal) {
        this.isfinal = isfinal;
    }

    /**
     * @return the isenable
     */
    public boolean getIsenable() {
        return isenable;
    }

    /**
     * @param isenable the isenable to set
     */
    public void setIsenable(boolean isenable) {
        this.isenable = isenable;
    }

    /**
     * @return the options
     */
    public String getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(String options) {
        this.options = options;
    }
}
