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
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;

/**
 *
 * @author plintus
 */
public class CheckBrowser extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String depricated = (String) request.getParameter("depricated");

        if (depricated == null) {
            String userAgent = request.getHeader("user-agent");
            UserAgent ua = UserAgent.parseUserAgentString(userAgent);
            Version browserVersion = ua.getBrowserVersion();
            Boolean supported = false;
            if (browserVersion != null) {
                Logger.getLogger(CheckBrowser.class.getName()).log(Level.INFO, "ua.getBrowser() :{0}", ua.getBrowser());
                Logger.getLogger(CheckBrowser.class.getName()).log(Level.INFO, "browserVersion.getMajorVersion() :{0}", browserVersion.getMajorVersion());
                supported = true;
                switch (ua.getBrowser()) {
                    case FIREFOX:
                        if (Integer.parseInt(browserVersion.getMajorVersion()) < 11) {
                            supported = false;
                        }
                        break;
                    case CHROME:
                        if (Integer.parseInt(browserVersion.getMajorVersion()) < 16) {
                            supported = false;
                        }
                        break;
                    case IE:
                        if (Integer.parseInt(browserVersion.getMajorVersion()) < 10) {
                            supported = false;
                        }
                        break;
                    case SAFARI:
                        if (Integer.parseInt(browserVersion.getMajorVersion()) < 6) {
                            supported = false;
                        }
                        break;
                    case OPERA:
                        if (Integer.parseInt(browserVersion.getMajorVersion()) < 12) {
                            supported = false;
                        }
                        break;
                    default:
                        break;
                }
            }
            if (!supported) {
                String scheme = request.getScheme();
                Integer rport = 0;
                if (((scheme.equals("http")) && (request.getServerPort() != 80))
                        || ((scheme.equals("https")) && (request.getServerPort() != 443))) {
                    rport = request.getServerPort();
                }
                String uri = request.getScheme() + "://" + request.getServerName() + (rport != 0 ? ":" + rport : "") + request.getContextPath();
                out.println("<div id='unsupportedbrowser' style='width:100%;text-align: center;'>"
                        + "<div style='color:red'> Похоже, что ваш браузер устарел и не поддерживается. "
                        + "Пожалуйста, используйте версию для устаревших браузеров:</br>"
                        + "<a href='" + uri + "/index.jsp?depricated=yes'>" + uri + "/index.jsp?depricated=yes</a></div>"
                        + "</div>");
                Logger.getLogger(CheckBrowser.class.getName()).log(Level.INFO, "don't supported");
            }
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
