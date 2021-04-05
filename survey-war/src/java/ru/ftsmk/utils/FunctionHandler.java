/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.utils;

import javax.ejb.EJB;
import javax.servlet.http.HttpSession;
import ru.ftsmk.bean.ChampingBeanRemote;
import ru.ftsmk.bean.EtcBeanRemote;
import ru.ftsmk.bean.UserBeanRemote;
import ru.ftsmk.bean.UtilBeanRemote;
import ru.ftsmk.info.UsersInfo;
import ru.ftsmk.sockets.JSONResponse;

/**
 *
 * @author plintus
 */
public class FunctionHandler {

    @EJB
    private EtcBeanRemote etcEjb;
    @EJB
    private UserBeanRemote userEjb;
    @EJB
    private UtilBeanRemote utilEjb;
    @EJB
    private ChampingBeanRemote chEjb;

    public String getlogin(UsersInfo obj, HttpSession httpSession) {
        String response = "false";
        UsersInfo ui = userEjb.getUserByLogin(obj.getLogin());
        if ((ui != null) && (ui.getSecret().equals(obj.getSecret()))) {
            UsersInfo nui = new UsersInfo();
            nui.setEmail(ui.getEmail());
            nui.setId(ui.getId());
            nui.setLogin(ui.getLogin());
            nui.setName(ui.getName());
            nui.setPhone(ui.getPhone());
            nui.setRole(ui.getRole());
            nui.setSecret(ui.getSecret());
            nui.setIdcompany(ui.getIdcompany());
            httpSession.setAttribute("UserInfo", nui);
            response="ok";
        } else {
            response="error";
        }
        return response;
    }

}
