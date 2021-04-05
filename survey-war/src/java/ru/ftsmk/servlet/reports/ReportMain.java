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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.ftsmk.bean.ChampingBeanRemote;
import ru.ftsmk.bean.UtilBeanRemote;
import ru.ftsmk.info.CatiAnswersInfo;
import ru.ftsmk.info.CatiLogInfo;
import ru.ftsmk.info.CatiQuestionsInfo;
import ru.ftsmk.info.ChampingInfo;
import ru.ftsmk.info.RequestInfo;
import ru.ftsmk.info.UniqCatilogInfo;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
public class ReportMain extends HttpServlet {

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

    protected class HeaderInfo {

        private String name;
        private String detail;
        private Integer idcatiquestion;
        private Boolean isvalue;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the idcatiquestion
         */
        public Integer getIdcatiquestion() {
            return idcatiquestion;
        }

        /**
         * @param idcatiquestion the idcatiquestion to set
         */
        public void setIdcatiquestion(Integer idcatiquestion) {
            this.idcatiquestion = idcatiquestion;
        }

        /**
         * @return the detail
         */
        public String getDetail() {
            return detail;
        }

        /**
         * @param detail the detail to set
         */
        public void setDetail(String detail) {
            this.detail = detail;
        }

        /**
         * @return the isvalue
         */
        public Boolean getIsvalue() {
            return isvalue;
        }

        /**
         * @param isvalue the isvalue to set
         */
        public void setIsvalue(Boolean isvalue) {
            this.isvalue = isvalue;
        }
    }

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
        String pisfinal = (String) request.getParameter("isfinal");
        String pisdetail = (String) request.getParameter("isdetail");
        String pidchamping = (String) request.getParameter("idchamping");
        try {
            if ((userinfo != null)
                    && (action != null)
                    && ((action.equals("reportmain")))
                    && (userinfo.getRole().equals("manager") || userinfo.getRole().equals("administrator"))
                    && (pbdate != null) && (pedate != null) && (pisfinal != null) && (pidchamping != null) && (pisdetail != null)) {
                String bdatef = utEjb.determineDate(pbdate);
                String edatef = utEjb.determineDate(pedate);
                Boolean isfinal = Boolean.parseBoolean(pisfinal);
                Boolean isdetail = Boolean.parseBoolean(pisdetail);
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
                    out.print("<div>Только конечные:" + (isfinal ? "Да" : "Нет") + "</div>");
                    out.println("<div>Отображать детали:" + (isdetail ? "Да" : "Нет") + "</div>");
                    //header from catiquest.selectmax and catianswers.name or text(for detail)
                    List<HeaderInfo> headers = new ArrayList();
                    List<CatiQuestionsInfo> cqis = chEjb.getCatiQuestionsByChamping(chi.getId());
                    for (CatiQuestionsInfo cqi : cqis) {
                        if (cqi.getIsenable()) {
                            for (Integer i = 0; i < cqi.getSelectmax(); i++) {
                                HeaderInfo hi = new HeaderInfo();
                                hi.setName(cqi.getName() + "." + (i + 1));
                                hi.setIdcatiquestion(cqi.getId());
                                hi.setDetail(cqi.getText());
                                hi.setIsvalue(false);
                                headers.add(hi);
                            }
                            List<CatiAnswersInfo> cqas = chEjb.getCatiAnswersByCatiQuestion(cqi.getId());
                            Integer i = 0;
                            for (CatiAnswersInfo cqa : cqas) {
                                if (cqa.getIseditable()) {
                                    HeaderInfo hi = new HeaderInfo();
                                    hi.setName(cqi.getName() + ".value." + (i + 1));
                                    hi.setIdcatiquestion(cqi.getId());
                                    hi.setDetail(cqi.getText());
                                    hi.setIsvalue(true);
                                    headers.add(hi);
                                    i++;
                                }
                            }
                        }
                    }
                    out.print("<tr class='defaulttable'>"
                            + "<th class='defaulttable' stile='width: 12em;'>#</th>"
                            + "<th class='defaulttable' stile='width: 12em;'>Дата</th>"
                            + "<th class='defaulttable' stile='width: 12em;'>Заявка</th>");
                    for (HeaderInfo header : headers) {
                        out.print("<th class='defaulttable'>");
                        out.print(header.getName());
                        if (isdetail) {
                            out.print((header.getDetail().length() > 15) ? (header.getDetail().substring(0, 15) + "...") : header.getDetail());
                        }
                        out.print("</th>");
                    }
                    out.print("</tr>");

                    Integer r = 0;
                    List<UniqCatilogInfo> uclis = isfinal
                            ? chEjb.getUniqCatilogs(chi.getId(), bdate, edate)
                            : chEjb.getUniqCatilogs(chi.getId(), bdate, edate, true);
                    out.print("<tr class='defaulttable'>");
                    for (UniqCatilogInfo ucli : uclis) {
                        RequestInfo ri = chEjb.getRequest(ucli.getIdrequest());
                        r++;
                        out.print("<td class='defaulttable'>" + r + "</td>");
                        out.print("<td class='defaulttable'>" + sdf.format(ucli.getAdddate()) + "</td>");
                        out.print("<td class='defaulttable' title='" + ri.getToken() + "'>"
                                + ((ri.getToken().length() > 15) ? (ri.getToken().substring(0, 15) + "...") : ri.getToken())
                                + "</td>");
                        //only checked
                        List<Integer> clichkids = new ArrayList();
                        List<Integer> clivalids = new ArrayList();
                        for (HeaderInfo header : headers) {
                            Boolean finded = false;
                            //Logger.getLogger(ReportMain.class.getName()).log(Level.INFO, "header {0}",
                            //        header.getIdcatiquestion() + ":" + header.getIsvalue());
                            List<CatiLogInfo> clis = chEjb.getCatiLogsByQuestionRequest(header.getIdcatiquestion(), ri.getId());
                            for (CatiLogInfo cli : clis) {
                                if (cli.getIscheck()) {
                                    if (header.getIsvalue()) {
                                        if (!clivalids.contains(cli.getId())) {
                                            clivalids.add(cli.getId());
                                            finded = true;
                                            break;
                                        }
                                    } else {
                                        if (!clichkids.contains(cli.getId())) {
                                            clichkids.add(cli.getId());
                                            finded = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!finded) {
                                if (header.getIsvalue()) {
                                    clivalids.add(0);
                                } else {
                                    clichkids.add(0);
                                }
                            }
                        }
                        Logger.getLogger(ReportMain.class.getName()).log(Level.INFO, "clichkids {0}", clichkids);
                        Logger.getLogger(ReportMain.class.getName()).log(Level.INFO, "clivalids {0}", clivalids);
                        Integer i = 0;
                        Integer j = 0;
                        for (HeaderInfo header : headers) {
                            try {
                                if (header.getIsvalue()) {
                                    if (clivalids.get(j) != 0) {
                                        CatiLogInfo cli = chEjb.getCatiLog(clivalids.get(j));
                                        out.print("<td class='defaulttable'>");
                                        out.print(cli.getValue());
                                        out.print("</td>");
                                    } else {
                                        out.print("<td class='defaulttable'>");
                                        out.print("</td>");
                                    }
                                    j++;
                                } else {
                                    if (clichkids.get(i) != 0) {
                                        CatiLogInfo cli = chEjb.getCatiLog(clichkids.get(i));
                                        CatiAnswersInfo cai = chEjb.getCatiAnswer(cli.getIdcatianswers());
                                        out.print("<td class='defaulttable'>");
                                        out.print(cai.getName());
                                        out.print("</td>");
                                    } else {
                                        out.print("<td class='defaulttable'>");
                                        out.print("</td>");
                                    }
                                    i++;
                                }
                            } catch (IndexOutOfBoundsException ex) {
                                Logger.getLogger(ReportMain.class.getName()).log(Level.INFO, "IndexOutOfBoundsException {0}", ex);
                                out.print("<td class='defaulttable'>");
                                out.print("</td>");
                            }
                        }
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
