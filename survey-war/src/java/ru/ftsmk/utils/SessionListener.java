/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author plintus
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        String sessid = (String) se.getSession().getAttribute("SESSIONID");
        se.getSession().setAttribute("SESSIONID", se.getSession().getId());
        Logger.getLogger(SessionListener.class.getName()).log(Level.INFO, "session created: {0}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().removeAttribute("SESSIONID");
        Logger.getLogger(SessionListener.class.getName()).log(Level.INFO, "session removed: {0}", se.getSession().getId());
    }
}
