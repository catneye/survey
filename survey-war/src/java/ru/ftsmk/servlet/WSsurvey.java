/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
public class WSsurvey extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //@Inject
    //private WSHandler sessionHandler;
    Boolean dowait = true;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession httpSession = request.getSession(true);
        PrintWriter out = response.getWriter();
        UsersInfo userInfo = (UsersInfo) httpSession.getAttribute("UserInfo");
        //String queryString = URLDecoder.decode(request.getQueryString(), "UTF-8");
        String object = (String) request.getParameter("object");

        Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "object: {0} ", object);
        Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "request.getCharacterEncoding(): {0} ", request.getCharacterEncoding());

        try {
            if (object != null) {
                //ws://127.0.0.1:8080/survey-war/WSsurvey
                //ff console ws://survey.olegus.su:8080/WSsurvey"
                //ws://survey.olegus.su:8080/WSsurvey
                //ws://survey.olegus.su:80/WSsurvey
                String wsName = "WSsurvey";
                String uri = request.getScheme().equals("https") ? "wss" : "ws" + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                //
                //String uri = request.getScheme().equals("https") ? "wss" : "ws" + "://" + request.getServerName() + ":" + "8080" + request.getContextPath();
                //
                //String uri="ws://127.0.0.1:8080";
                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "uri + wsName: {0} ", uri + "/" + wsName);
                dowait = true;
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                ClientEndpointConfig config = Builder.create()
                        .configurator(new ClientEndpointConfig.Configurator() {
                            @Override
                            public void beforeRequest(Map<String, List<String>> headers) {
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "beforeRequest: {0} ", headers);
                                //super.beforeRequest(headers);
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "getHeaderNames: {0} ",
                                        Collections.list(request.getHeaderNames()));
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "Content-Type: {0} ",
                                        Collections.list(request.getHeaders("Content-Type")));
                                Enumeration<String> cookieList = request.getHeaders("Cookie");
                                List list = Collections.list(cookieList);
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "cookieList: {0} ", list);
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "end beforeRequest: {0} ", list);
                                headers.put("Cookie", list);
                            }

                            @Override
                            public void afterResponse(HandshakeResponse response) {
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "afterResponse: {0} ", response);
                            }
                        })
                        .build();
                        //.getUserProperties();.put("org.apache.tomcat.websocket.SSL_CONTEXT", context);
                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "ClientEndpointConfig: {0} ", config);

                Endpoint endPoint = new Endpoint() {
                    @Override
                    public void onOpen(Session session, EndpointConfig config) {
                        Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "Endpoint onOpen: {0} ", session);
                        session.addMessageHandler(new MessageHandler.Whole<String>() {
                            @Override
                            public void onMessage(String message) {
                                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "onMessage: {0} ", message);
                                out.println(message);
                                try {
                                    session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "onMessage : sessison close"));
                                } catch (IOException ex) {
                                    Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "IOException: {0} ", ex);
                                }
                                dowait = false;
                            }
                        });
                        session.getAsyncRemote().sendObject(object);
                    }
                };
                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "Endpoint: {0} ", endPoint);
                URI buri=URI.create(uri + "/" + wsName);
                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "buri: {0} ", buri);
                Session session = container.connectToServer(endPoint, config, buri);
                //Thread.sleep(5000);
                Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "connected: {0} ", session);
                while (dowait) {
                    Thread.sleep(2000);
                }
                dowait = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(WSsurvey.class.getName()).log(Level.INFO, "Exception: {0} ", ex);
        } finally {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
