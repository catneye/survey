/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import javax.ejb.Remote;

/**
 *
 * @author plintus
 */
@Remote
public interface UtilBeanRemote {
    public boolean isValidEmailAddress(String email);
    public boolean isValidLoginName(String login);
    public boolean isValidPhoneName(String phone);
    public String determineDate(String dateString);
}
