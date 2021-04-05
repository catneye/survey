/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author plintus
 */
public class HeadersRouter extends HttpServlet {

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
        HttpSession httpSession = request.getSession(true);
        PrintWriter out = response.getWriter();
        String depricated = (String) request.getParameter("depricated");
        Logger.getLogger(HeadersRouter.class.getName()).log(Level.INFO, "depricated: {0} ", depricated);
        try {
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Система опроса</title>");
            out.println("<meta name='keywords' content='Система опроса' />");
            out.println("<meta name='description' content='Система опроса' />");
            out.println("<script type='text/javascript' src='./js/libs/prototype/prototype.js'></script>");
            out.println("<script type='text/javascript' src='./js/libs/scriptaculous/scriptaculous.js'></script>");
            out.println("<script type='text/javascript' src='./js/libs/md5/md5.js'></script>");
            out.println("<script type='text/javascript' src='./js/libs/nicedit/nicEdit.js'></script>");
            out.println("<link href='./js/libs/calendar/calendarview.css' rel='stylesheet' type='text/css'>");
            out.println("<script type='text/javascript' src='./js/libs/calendar/calendarview.js' ></script>");
            out.println("<script type='text/javascript' src='./js/funcs.js'></script>");

            if (depricated != null) {
                out.println("<script type='text/javascript' src='./js/ajax.js'></script>");
            } else {
                out.println("<script type='text/javascript' src='./js/sockets.js'></script>");
            }
            out.println("<link rel='icon' href='./images/favicon.ico' type='image/x-icon'>");
            out.println("<link href='./css/site.css' rel='stylesheet' type='text/css'>");
            out.println("<link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400i,700,700i&subset=cyrillic' rel='stylesheet'> ");
            out.println("<meta name='viewport' content='width=1200, initial-scale=2.0'>");
            //out.println("<meta http-equiv='X-UA-Compatible' content='IE=EmulateIE8' />");
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
