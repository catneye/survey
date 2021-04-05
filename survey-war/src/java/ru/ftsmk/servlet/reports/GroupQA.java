/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.servlet.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.ftsmk.bean.ChampingBeanRemote;
import ru.ftsmk.bean.UtilBeanRemote;
import ru.ftsmk.db.Getgroupca;
import ru.ftsmk.info.CatiAnswersInfo;
import ru.ftsmk.info.CatiQuestionsInfo;
import ru.ftsmk.info.ChampingInfo;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
public class GroupQA extends HttpServlet {

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
    @EJB
    UtilBeanRemote utEjb;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        String action = (String) request.getParameter("action");
        String spage = (String) request.getParameter("page");
        UsersInfo userinfo = (UsersInfo) session.getAttribute("UserInfo");
        String pbdate = (String) request.getParameter("bdate");
        String pedate = (String) request.getParameter("edate");
        String pidchamping = (String) request.getParameter("idchamping");

        try {
            if ((userinfo != null)
                    && (action != null)
                    && ((action.equals("reportqa")))
                    && (userinfo.getRole().equals("manager") || userinfo.getRole().equals("administrator"))
                    && (pbdate != null) && (pedate != null)) {
                String bdatef = utEjb.determineDate(pbdate);
                String edatef = utEjb.determineDate(pedate);
                ChampingInfo chi = chEjb.getChamping(Integer.parseInt(pidchamping));
                if ((bdatef != null) && (edatef != null) && (chi != null)) {
                    //TODO check champing and user!!!
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdfbdate = new SimpleDateFormat(bdatef);
                    SimpleDateFormat sdfedate = new SimpleDateFormat(edatef);
                    Date bdate = sdfbdate.parse(pbdate);
                    Date edate = sdfedate.parse(pedate);
                    out.print("<div>Начальная дата:" + sdf.format(bdate) + "</div>");
                    out.print("<div>Конечная дата:" + sdf.format(edate) + "</div>");
                    out.print("<div>Кампания:" + chi.getName() + "</div>");

                    out.print("<tr class='defaulttable'>"
                            + "<th class='defaulttable' stile='width: 12em;'>#</th>"
                            + "<th class='defaulttable' stile='width: 12em;'>Вопрос</th>"
                            + "<th class='defaulttable' stile='width: 12em;'>Ответ</th>"
                            + "<th class='defaulttable' stile='width: 12em;'>Количество</th>");
                    out.print("</tr>");
                    Integer n = 0;
                    List<Getgroupca> gcas = chEjb.getGrupsQA(chi.getId());
                    for (Getgroupca gca : gcas) {
                        n++;
                        CatiQuestionsInfo cqi = chEjb.getCatiQuestion(gca.getIdcatiquestions());
                        CatiAnswersInfo cqa = chEjb.getCatiAnswer(gca.getIdcatianswers());
                        out.print("<tr class='defaulttable'>"
                                + "<td class='defaulttable'>" + n + "</td>"
                                + "<td class='defaulttable'>" + cqi.getName() + " : "
                                + (cqi.getText().length() > 35 ? cqi.getText().substring(0, 35) : cqi.getText())
                                + "</td>"
                                + "<td class='defaulttable'>" + cqa.getName() + " : "
                                + (cqa.getText().length() > 35 ? cqa.getText().substring(0, 35) : cqa.getText())
                                + "</td>"
                                + "<td class='defaulttable'>" + gca.getCount() + "</td>");
                        out.print("</tr>");
                    }
                }
            }
        } catch (ParseException ex) {
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
