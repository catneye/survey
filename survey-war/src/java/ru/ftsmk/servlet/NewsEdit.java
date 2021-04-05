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
import ru.ftsmk.bean.EtcBeanRemote;
import ru.ftsmk.info.ChampingInfo;
import ru.ftsmk.info.NewsInfo;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
public class NewsEdit extends HttpServlet {

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
    EtcBeanRemote etcEjb;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        String action = (String) request.getParameter("action");
        String spage = (String) request.getParameter("page");
        UsersInfo userinfo = (UsersInfo) session.getAttribute("UserInfo");
        try {
            if ((userinfo != null)
                    && (action != null)
                    && ((action.equals("newsedit")))
                    && (userinfo.getRole().equals("manager") || userinfo.getRole().equals("administrator"))) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //header
                out.print("<tr class='defaulttable defaulttableheader'>");
                out.print("<th class='defaulttableheader' style='width:70%'>Имя</th>");
                out.print("<th class='defaulttableheader' style='width:10%'>Дата старт</th>");
                out.print("<th class='defaulttableheader' style='width:10%'>Дата стоп</th>");
                out.print("<th class='defaulttableheader' style='width:10%'>Дата создания</th>");
                out.print("</tr>");
                List<NewsInfo> nis = etcEjb.getNews();
                for (NewsInfo ni : nis) {
                    out.print("<tr class='defaulttable' onclick='touchrow("+ni.getId()+", this);'>");
                    out.print("<td class='defaulttable'>" + ni.getName() + "</th>");
                    out.print("<td class='defaulttable'>" + sdf.format(ni.getStartdate()) + "</th>");
                    out.print("<td class='defaulttable'>" + sdf.format(ni.getStopdate()) + "</th>");
                    out.print("<td class='defaulttable'>" + sdf.format(ni.getAdddate()) + "</th>");
                    out.print("</tr>");
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
