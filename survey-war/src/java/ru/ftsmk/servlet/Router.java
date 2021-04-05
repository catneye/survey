/*
 * Copyright (C) 2017 plintus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.ftsmk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
public class Router extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        //PrintWriter out = response.getWriter();
        String action = (String) request.getParameter("action");
        UsersInfo userinfo = (UsersInfo) session.getAttribute("UserInfo");

        Logger.getLogger(Router.class.getName()).log(Level.INFO, "getContextPath {0}", request.getContextPath());
        Logger.getLogger(Router.class.getName()).log(Level.INFO, "getRequestURI {0}", request.getRequestURI());
        Logger.getLogger(Router.class.getName()).log(Level.INFO, "getRequestURL {0}", request.getRequestURL());
        Logger.getLogger(Router.class.getName()).log(Level.INFO, "token: {0} ", session.getAttribute("token"));
        String include = "/jspf/pmain.jspf";
        try {
            if (action != null) {
                if (userinfo != null) {
                    //administrator
                    if (userinfo.getRole().equals("administrator")) {
                        switch (action) {
                            case "companys": {
                                include = "/jspf/pcompanys.jspf";
                                break;
                            }
                            case "users": {
                                include = "/jspf/pusers.jspf";
                                break;
                            }
                            case "settings": {
                                include = "/jspf/psettings.jspf";
                                break;
                            }
                        }
                    }
                    //manager
                    if (userinfo.getRole().equals("manager")
                            || userinfo.getRole().equals("administrator")) {
                        switch (action) {
                            case "champings": {
                                include = "/jspf/pchampings.jspf";
                                break;
                            }
                            case "bases": {
                                include = "/jspf/pbases.jspf";
                                break;
                            }
                            case "dispatches": {
                                include = "/jspf/pdispatches.jspf";
                                break;
                            }
                            case "reports": {
                                include = "/jspf/preports.jspf";
                                break;
                            }
                        }
                    }
                    //user
                    if (userinfo.getRole().equals("user")
                            || userinfo.getRole().equals("manager")
                            || userinfo.getRole().equals("administrator")) {
                        switch (action) {
                            case "authsurveys": {
                                include = "/jspf/authsurveys.jspf";
                                break;
                            }
                        }
                    }
                    //all auth
                    switch (action) {
                        case "dashboard": {
                            //include = "/jspfforms/pdashboard.jspf";
                            break;
                        }
                    }
                } else {
                    //only unauth
                }
                //all
                switch (action) {
                    case "unauthsurveys": {
                        include = "/jspf/unauthsurveys.jspf";
                        break;
                    }
                }
            }
            //RequestDispatcher dispatcher = request.getRequestDispatcher("/jspf/router.jspf");
            //dispatcher.include(request, response);
            //dispatcher.forward(request, response);
            //response.sendRedirect("/jspf/router.jspf");
            request.getRequestDispatcher(include).include(request, response);
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
