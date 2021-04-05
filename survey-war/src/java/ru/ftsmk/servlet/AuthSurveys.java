/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.ftsmk.bean.ChampingBeanRemote;
import ru.ftsmk.info.ChampingInfo;

/**
 *
 * @author plintus
 */
public class AuthSurveys extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    ChampingBeanRemote chEjb;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        String action = (String) request.getParameter("action");
        String usertoken = (String) session.getAttribute("token");
        try {
            if ((action != null) && (action.equals("authsurveys"))) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //header
                out.print("<tr class='defaulttable defaulttableheader'>");
                out.print("<th class='defaulttableheader' style='width:60%'>Имя</th>");
                out.print("<th class='defaulttableheader' style='width:20%'>Дата создания</th>");
                out.print("</tr>");
                List<ChampingInfo> chis = chEjb.getChampings();
                for (ChampingInfo chi : chis) {
                    if (chi.getIsenable()) {
                        out.print("<tr class='defaulttable' onclick='touchrow(" + chi.getId() + ", this);'>");
                        out.print("<td class='defaulttable'>" + chi.getName() + "</th>");
                        out.print("<td class='defaulttable'>" + sdf.format(chi.getAdddate()) + "</th>");
                        out.print("</tr>");
                    }
                }
            }
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
