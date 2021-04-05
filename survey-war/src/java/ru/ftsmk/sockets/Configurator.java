/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.sockets;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author plintus
 */
public class Configurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
            HandshakeRequest request,
            HandshakeResponse response) {
        Logger.getLogger(Configurator.class.getName()).log(Level.INFO, 
                "config.getUserProperties {0}", config.getUserProperties().get("httpSession"));
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //String token=request.getQueryString()
        Logger.getLogger(Configurator.class.getName()).log(Level.INFO, "request.getHttpSession() {0}", httpSession);
        if (httpSession != null) {
            Logger.getLogger(Configurator.class.getName()).log(Level.INFO, "request.getHttpSession().getId() {0}", httpSession.getId());
            config.getUserProperties().put("httpSession", httpSession);
        }
    }
}
