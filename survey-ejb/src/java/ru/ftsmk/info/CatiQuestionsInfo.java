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
public class CatiQuestionsInfo implements Serializable{
    
    private Integer id;
    private int idchamping;
    private int idparent;
    private String name;
    private String text;
    private String condition;
    private int selectmax;
    private String direction;
    private boolean isshowanswertext;
    private boolean israndom;
    private int level;
    private boolean isenable;
    private boolean israndomchild;

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
     * @return the idchamping
     */
    public int getIdchamping() {
        return idchamping;
    }

    /**
     * @param idchamping the idchamping to set
     */
    public void setIdchamping(int idchamping) {
        this.idchamping = idchamping;
    }

    /**
     * @return the idparent
     */
    public int getIdparent() {
        return idparent;
    }

    /**
     * @param idparent the idparent to set
     */
    public void setIdparent(int idparent) {
        this.idparent = idparent;
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
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the selectmax
     */
    public int getSelectmax() {
        return selectmax;
    }

    /**
     * @param selectmax the selectmax to set
     */
    public void setSelectmax(int selectmax) {
        this.selectmax = selectmax;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return the isshowanswertext
     */
    public boolean getIsshowanswertext() {
        return isshowanswertext;
    }

    /**
     * @param isshowanswertext the isshowanswertext to set
     */
    public void setIsshowanswertext(boolean isshowanswertext) {
        this.isshowanswertext = isshowanswertext;
    }

    /**
     * @return the israndom
     */
    public boolean getIsrandom() {
        return israndom;
    }

    /**
     * @param israndom the israndom to set
     */
    public void setIsrandom(boolean israndom) {
        this.israndom = israndom;
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
     * @return the israndomchild
     */
    public boolean getIsrandomchild() {
        return israndomchild;
    }

    /**
     * @param israndomchild the israndomchild to set
     */
    public void setIsrandomchild(boolean israndomchild) {
        this.israndomchild = israndomchild;
    }
}
