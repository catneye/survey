/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import java.util.List;
import javax.ejb.Remote;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
@Remote
public interface UserBeanRemote {
    public List<UsersInfo> getUsers();
    public UsersInfo getUser(Integer id);
    public UsersInfo getUser(Integer iduser, Integer idcompany);
    public UsersInfo getUser(String login, Integer idcompany);
    public UsersInfo getUserByLogin(String login);
    public UsersInfo getUserByName(String name);
    public UsersInfo getUserByEmail(String email);
    public UsersInfo getUserByPhone(String phone);
    public UsersInfo addUser(UsersInfo userinfo);
    public UsersInfo setUser(UsersInfo userinfo);
    public List<UsersInfo> getUsers(Integer idcompany);
}
