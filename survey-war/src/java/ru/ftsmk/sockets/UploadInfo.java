/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.sockets;

import java.io.Serializable;

/**
 *
 * @author plintus
 */
public class UploadInfo implements Serializable{
    private String originalname;
    private String filename;

    /**
     * @return the originalname
     */
    public String getOriginalname() {
        return originalname;
    }

    /**
     * @param originalname the originalname to set
     */
    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
