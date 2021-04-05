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
public class TopMenu extends HttpServlet {

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
        //String action = (String) request.getParameter("action");
        UsersInfo userinfo = (UsersInfo) session.getAttribute("UserInfo");
        try {
            if (userinfo != null) {
                //all auth
                request.getRequestDispatcher("/jspf/topmenuauth.jspf").include(request, response);
                request.getRequestDispatcher("/jspf/topmenuuser.jspf").include(request, response);

                if (userinfo.getRole().equals("administrator")) {
                    //administrator
                    request.getRequestDispatcher("/jspf/topmenumanager.jspf").include(request, response);
                    request.getRequestDispatcher("/jspf/topmenuadmin.jspf").include(request, response);
                } else if (userinfo.getRole().equals("manager")) {
                    //manager
                    request.getRequestDispatcher("/jspf/topmenumanager.jspf").include(request, response);
                } else if (userinfo.getRole().equals("user")) {
                    //user
                }
            } else {
                //RequestDispatcher dispatcher = request.getRequestDispatcher("/jspf/unathtopmenu.jspf");
                //dispatcher.include(request, response);

                //unauth
                request.getRequestDispatcher("/jspf/topmenuunauth.jspf").include(request, response);
            }
            //request.getRequestDispatcher("/jspf/topmenu.jspf").include(request, response);
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
