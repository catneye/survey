/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import ru.ftsmk.bean.ChampingBeanRemote;
import ru.ftsmk.bean.EtcBeanRemote;
import ru.ftsmk.bean.UserBeanRemote;
import ru.ftsmk.bean.UtilBeanRemote;
import ru.ftsmk.info.BaseInfo;
import ru.ftsmk.info.CatiAnswersInfo;
import ru.ftsmk.info.CatiLogInfo;
import ru.ftsmk.info.CatiQuestionsInfo;
import ru.ftsmk.info.ChampingInfo;
import ru.ftsmk.info.EtcInfo;
import ru.ftsmk.info.RequestInfo;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
@ApplicationScoped
@ServerEndpoint(value = "/WSsurvey", configurator = Configurator.class, encoders = JsonObjectEncoder.class, decoders = JsonObjectDecoder.class)
@Stateful
//@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@AccessTimeout(value = 30000)
public class WSSocket implements Serializable {

    private EndpointConfig config;
    @EJB
    private EtcBeanRemote etcEjb;
    @EJB
    private UserBeanRemote userEjb;
    @EJB
    private UtilBeanRemote utilEjb;
    @EJB
    private ChampingBeanRemote chEjb;

    @Inject
    private WSHandler sessionHandler;

    @OnOpen
    public void onConnectionOpen(Session session, EndpointConfig config) {
        this.config = config;
        HttpSession httpSession = (HttpSession) this.config.getUserProperties().get("httpSession");
        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "socket open wsSession: {0} httpSession: {1}",
                new Object[]{session.getId(), httpSession});
        //sessions.put(session, httpSession);
        sessionHandler.addSession(session, httpSession);
    }

    @OnClose
    public void onConnectionClose(Session session) {
        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "socket close session: {0}", session.getId());
        //sessions.remove(session);
        sessionHandler.removeSession(session);
    }

    @OnMessage
    public JSONResponse onMessage(JSONObject message, Session session) throws IOException, EncodeException {
        HttpSession httpSession = sessionHandler.getSession(session);
        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "onMessage session: {0} message: {1} httpSession: {2}",
                new Object[]{session.getId(), message, httpSession});
        JSONResponse response = new JSONResponse();
        String type = message.getType();
        response.setResponse(type);
        response.setResult("false");
        if (httpSession != null) {
            UsersInfo userInfo = (UsersInfo) httpSession.getAttribute("UserInfo");
            Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "onMessage type: {0} ", type);
            Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "onMessage token: {0} ", httpSession.getAttribute("token"));
            try {
                if (userInfo != null) {
                    //for only users 
                    switch (type) {
                        case "checklogin": {
                            //allready auth
                            response.setResult("already");
                            break;
                        }
                        case "logout": {
                            userInfo = null;
                            httpSession.setAttribute("UserInfo", userInfo);
                            response.setResult("ok");
                            break;
                        }
                        case "aegetetcs": {
                            if (userInfo.getRole().equals("administrator")) {
                                List<EtcInfo> etcs = etcEjb.getEtcs();
                                JsonArrayBuilder jar = Json.createArrayBuilder();
                                for (EtcInfo etc : etcs) {
                                    jar.add(Json.createObjectBuilder()
                                            .add("id", etc.getId())
                                            .add("title", etc.getKey()));
                                }
                                JsonObjectBuilder job = Json.createObjectBuilder();
                                job.add("items", jar);
                                response.setResult(job.build().toString());
                            }
                            break;
                        }
                        case "aegetetc": {
                            if (userInfo.getRole().equals("administrator")) {
                                Integer id = (Integer) message.getObject();
                                EtcInfo etc;
                                if ((id != null) && (id != 0)) {
                                    etc = etcEjb.getEtc(id);
                                } else {
                                    etc = new EtcInfo();
                                    etc.setId(0);
                                    etc.setKey("key");
                                    etc.setValue("value");
                                }
                                JsonObjectBuilder job = Json.createObjectBuilder();
                                job.add("id", etc.getId())
                                        .add("key", etc.getKey())
                                        .add("value", etc.getValue());
                                response.setResult(job.build().toString());
                            }
                            break;
                        }
                        case "aesetetc": {
                            if (userInfo.getRole().equals("administrator")) {
                                EtcInfo etc = (EtcInfo) message.getObject();
                                if (etcEjb.setEtc(etc) != null) {
                                    response.setResult("ok");
                                }
                            }
                            break;
                        }
                        case "aegetusers": {
                            if (userInfo.getRole().equals("administrator")) {
                                List<UsersInfo> users = userEjb.getUsers();
                                JsonArrayBuilder jar = Json.createArrayBuilder();
                                for (UsersInfo user : users) {
                                    jar.add(Json.createObjectBuilder()
                                            .add("id", user.getId())
                                            .add("title", user.getLogin() + " - " + user.getName()));
                                }
                                JsonObjectBuilder job = Json.createObjectBuilder();
                                job.add("items", jar);
                                response.setResult(job.build().toString());
                            }
                            break;
                        }
                        case "aegetuser": {
                            if (userInfo.getRole().equals("administrator")) {
                                Integer id = (Integer) message.getObject();
                                UsersInfo usr;
                                if ((id != null) && (id != 0)) {
                                    usr = userEjb.getUser(id);
                                } else {
                                    usr = new UsersInfo();
                                    usr.setId(0);
                                    usr.setName("name");
                                    usr.setLogin("login");
                                    usr.setPhone("phone");
                                    usr.setRole("user");
                                    usr.setEmail("email");
                                    usr.setSecret("secret");
                                    usr.setIdcompany(0);
                                }
                                JsonObjectBuilder job = Json.createObjectBuilder();
                                job.add("id", usr.getId())
                                        .add("login", usr.getLogin())
                                        .add("name", usr.getName())
                                        .add("phone", usr.getPhone())
                                        .add("role", usr.getRole())
                                        .add("secret", usr.getSecret())
                                        .add("email", usr.getEmail());
                                //idcompany
                                response.setResult(job.build().toString());
                            }
                            break;
                        }
                        case "aesetuser": {
                            if (userInfo.getRole().equals("administrator")) {
                                UsersInfo usr = (UsersInfo) message.getObject();
                                if (userEjb.setUser(usr) != null) {
                                    response.setResult("ok");
                                }
                            }
                            break;
                        }
                        case "mggetchamping": {
                            if (userInfo.getRole().equals("administrator")
                                    || userInfo.getRole().equals("manager")) {
                                Integer id = (Integer) message.getObject();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date dt = new Date();
                                ChampingInfo nchi = chEjb.getChamping(id);
                                if (nchi != null) {
                                    JsonObjectBuilder job = Json.createObjectBuilder();

                                    JsonObjectBuilder jobi = Json.createObjectBuilder()
                                            .add("id", nchi.getId())
                                            .add("name", nchi.getName())
                                            .add("type", nchi.getChampingtype())
                                            .add("idcompany", nchi.getIdcompany())
                                            .add("isenable", nchi.getIsenable())
                                            .add("isforall", nchi.getIsforall())
                                            .add("adddate", sdf.format(nchi.getAdddate()))
                                            .add("description", nchi.getDescription());

                                    job.add("item", jobi);
                                    job.add("descriptor", Json.createObjectBuilder()
                                            .add("item", Json.createReader(new StringReader(chEjb.getChampingDescriptor())).readObject()));
                                    JsonObjectBuilder jobsels = Json.createObjectBuilder();
                                    JsonArrayBuilder jarcht = Json.createArrayBuilder();
                                    jarcht.add(Json.createObjectBuilder().add("id", "internal")
                                            .add("name", "internal"));
                                    jarcht.add(Json.createObjectBuilder().add("id", "external")
                                            .add("name", "external"));
                                    jobsels.add("type", jarcht);
                                    job.add("selects", jobsels);
                                    response.setResult(job.build().toString());
                                }
                            }
                            break;
                        }
                        case "mgsavechamping": {
                            if (userInfo.getRole().equals("administrator")
                                    || userInfo.getRole().equals("manager")) {
                                ChampingInfo chi = (ChampingInfo) message.getObject();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date dt = new Date();
                                //default values
                                chi.setIdcompany(userInfo.getIdcompany());
                                chi.setChampingtype("internal");
                                if (chi.getId() == 0) {
                                    chi.setIsenable(true);
                                    chi.setIsforall(false);
                                    chi.setName(sdf.format(dt));
                                    chi.setAdddate(dt);
                                    chi.setDescription("");
                                }
                                ChampingInfo nchi = chEjb.setChamping(chi);
                                //TODO New base!!!
                                if (nchi != null) {
                                    JsonObjectBuilder job = Json.createObjectBuilder();

                                    JsonObjectBuilder jobi = Json.createObjectBuilder()
                                            .add("id", nchi.getId())
                                            .add("name", nchi.getName())
                                            .add("type", nchi.getChampingtype())
                                            .add("idcompany", nchi.getIdcompany())
                                            .add("isenable", nchi.getIsenable())
                                            .add("isforall", nchi.getIsforall())
                                            .add("adddate", sdf.format(nchi.getAdddate()))
                                            .add("description", nchi.getDescription());

                                    job.add("item", jobi);
                                    response.setResult(job.build().toString());
                                }
                            }
                            break;
                        }
                        case "mgclonechamping": {
                            if (userInfo.getRole().equals("administrator")
                                    || userInfo.getRole().equals("manager")) {
                                Integer id = (Integer) message.getObject();
                                ChampingInfo chi = chEjb.getChamping(id);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date dt = new Date();
                                chi.setIdcompany(userInfo.getIdcompany());
                                ChampingInfo nchi = chEjb.cloneChamping(chi);
                                //TODO New base!!!
                                if (nchi != null) {
                                    JsonObjectBuilder job = Json.createObjectBuilder();

                                    JsonObjectBuilder jobi = Json.createObjectBuilder()
                                            .add("id", nchi.getId())
                                            .add("name", nchi.getName())
                                            .add("type", nchi.getChampingtype())
                                            .add("idcompany", nchi.getIdcompany())
                                            .add("isenable", nchi.getIsenable())
                                            .add("isforall", nchi.getIsforall())
                                            .add("adddate", sdf.format(nchi.getAdddate()))
                                            .add("description", nchi.getDescription());

                                    job.add("item", jobi);
                                    response.setResult(job.build().toString());
                                }
                            }
                            break;
                        }
                        case "mggetscenario": {
                            if (userInfo.getRole().equals("administrator")
                                    || userInfo.getRole().equals("manager")) {
                                Integer id = (Integer) message.getObject();
                                ChampingInfo nchi = chEjb.getChamping(id);
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "mggetscenario ChampingInfo {0}", nchi);
                                if (nchi != null) {
                                    JsonObjectBuilder job = Json.createObjectBuilder();
                                    job.add("idchamping", nchi.getId());
                                    JsonArrayBuilder jarcq = Json.createArrayBuilder();
                                    List<CatiQuestionsInfo> cqis = chEjb.getCatiQuestionsByChamping(nchi.getId());
                                    Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "mggetscenario CatiQuestionsInfo {0}", cqis.size());
                                    for (CatiQuestionsInfo cqi : cqis) {
                                        JsonObjectBuilder jobcqi = Json.createObjectBuilder();
                                        jobcqi.add("id", cqi.getId())
                                                .add("idchamping", cqi.getIdchamping())
                                                .add("name", cqi.getName())
                                                .add("condition", cqi.getCondition())
                                                .add("direction", cqi.getDirection())
                                                .add("idparent", cqi.getIdparent())
                                                .add("isenable", cqi.getIsenable())
                                                .add("israndom", cqi.getIsrandom())
                                                .add("israndomchild", cqi.getIsrandomchild())
                                                .add("isshowanswertext", cqi.getIsshowanswertext())
                                                .add("level", cqi.getLevel())
                                                .add("selectmax", cqi.getSelectmax())
                                                .add("text", cqi.getText());
                                        List<CatiAnswersInfo> cais = chEjb.getCatiAnswersByCatiQuestion(cqi.getId());
                                        JsonArrayBuilder jarca = Json.createArrayBuilder();
                                        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "mggetscenario CatiAnswersInfo {0}", cais.size());
                                        for (CatiAnswersInfo cai : cais) {
                                            JsonObjectBuilder jobcai = Json.createObjectBuilder();
                                            jobcai.add("id", cai.getId())
                                                    .add("name", cai.getName())
                                                    .add("idcatiquestions", cai.getIdcatiquestions())
                                                    .add("idinclude", cai.getIdinclude())
                                                    .add("iseditable", cai.getIseditable())
                                                    .add("isenable", cai.getIsenable())
                                                    .add("isfinal", cai.getIsfinal())
                                                    .add("level", cai.getLevel())
                                                    .add("options", cai.getOptions())
                                                    .add("text", cai.getText());
                                            jarca.add(jobcai);
                                        }
                                        jobcqi.add("answers", jarca);
                                        jarcq.add(jobcqi);
                                    }
                                    job.add("descriptor", Json.createObjectBuilder()
                                            .add("questions", Json.createReader(new StringReader(chEjb.getCatiQuestionDescriptor())).readObject())
                                            .add("answers", Json.createReader(new StringReader(chEjb.getCatiAnswerDescriptor())).readObject()));
                                    //selects
                                    JsonObjectBuilder jobsels = Json.createObjectBuilder();
                                    //questions selects
                                    JsonObjectBuilder jobselquest = Json.createObjectBuilder();
                                    JsonArrayBuilder jardirection = Json.createArrayBuilder();
                                    jardirection.add(Json.createObjectBuilder().add("id", "vertical")
                                            .add("name", "vertical"));
                                    jardirection.add(Json.createObjectBuilder().add("id", "horizontal")
                                            .add("name", "horizontal"));
                                    jobselquest.add("direction", jardirection);
                                    JsonArrayBuilder jaridparent = Json.createArrayBuilder();
                                    jaridparent.add(Json.createObjectBuilder().add("id", 0)
                                            .add("name", "Нет"));
                                    for (CatiQuestionsInfo cqi : cqis) {
                                        jaridparent.add(Json.createObjectBuilder().add("id", cqi.getId())
                                                .add("name", cqi.getName()));
                                    }
                                    jobselquest.add("idparent", jaridparent);
                                    JsonArrayBuilder jaridchamping = Json.createArrayBuilder();
                                    jaridchamping.add(Json.createObjectBuilder().add("id", nchi.getId())
                                            .add("name", nchi.getName()));
                                    jobselquest.add("idchamping", jaridchamping);
                                    //answers selects
                                    JsonObjectBuilder jobselansw = Json.createObjectBuilder();
                                    JsonArrayBuilder jaridinclude = Json.createArrayBuilder();
                                    jaridinclude.add(Json.createObjectBuilder().add("id", 0)
                                            .add("name", "Нет"));
                                    for (CatiQuestionsInfo cqi : cqis) {
                                        jaridinclude.add(Json.createObjectBuilder().add("id", cqi.getId())
                                                .add("name", cqi.getName()));
                                    }
                                    jobselansw.add("idinclude", jaridinclude);

                                    jobsels.add("questions", jobselquest);
                                    jobsels.add("answers", jobselansw);

                                    job.add("selects", jobsels);
                                    //selects
                                    job.add("questions", jarcq);
                                    response.setResult(job.build().toString());
                                }
                            }
                            break;
                        }
                        case "mgsavequestion": {
                            if (userInfo.getRole().equals("administrator")
                                    || userInfo.getRole().equals("manager")) {
                                CatiQuestionsInfo cqi = (CatiQuestionsInfo) message.getObject();
                                //TODO check champing for company!!!
                                //default values
                                if (cqi.getIdchamping() != 0) {
                                    ChampingInfo nchi = chEjb.getChamping(cqi.getIdchamping());
                                    if (cqi.getId() == 0) {
                                        cqi.setIdparent(0);
                                        cqi.setCondition("");
                                        cqi.setDirection("vertical");
                                        cqi.setIsenable(true);
                                        cqi.setIsrandom(false);
                                        cqi.setIsrandomchild(false);
                                        cqi.setIsshowanswertext(true);
                                        cqi.setLevel(1);
                                        cqi.setSelectmax(1);
                                        cqi.setName("Новый вопрос");
                                        cqi.setText("Новый вопрос");
                                    }
                                    CatiQuestionsInfo ncqi = chEjb.setCatiQuestion(cqi);
                                    JsonObjectBuilder job = Json.createObjectBuilder();
                                    job.add("id", ncqi.getId())
                                            .add("idchamping", nchi.getId())
                                            .add("name", ncqi.getName())
                                            .add("condition", ncqi.getCondition())
                                            .add("direction", ncqi.getDirection())
                                            .add("idparent", ncqi.getIdparent())
                                            .add("isenable", ncqi.getIsenable())
                                            .add("israndom", ncqi.getIsrandom())
                                            .add("israndomchild", ncqi.getIsrandomchild())
                                            .add("isshowanswertext", ncqi.getIsshowanswertext())
                                            .add("level", ncqi.getLevel())
                                            .add("selectmax", ncqi.getSelectmax())
                                            .add("text", ncqi.getText());
                                    List<CatiAnswersInfo> cais = chEjb.getCatiAnswersByCatiQuestion(ncqi.getId());
                                    JsonArrayBuilder jarca = Json.createArrayBuilder();
                                    for (CatiAnswersInfo cai : cais) {
                                        JsonObjectBuilder jobcai = Json.createObjectBuilder();
                                        jobcai.add("id", cai.getId())
                                                .add("name", cai.getName())
                                                .add("idcatiquestions", cai.getIdcatiquestions())
                                                .add("idinclude", cai.getIdinclude())
                                                .add("iseditable", cai.getIseditable())
                                                .add("isenable", cai.getIsenable())
                                                .add("isfinal", cai.getIsfinal())
                                                .add("level", cai.getLevel())
                                                .add("options", cai.getOptions())
                                                .add("text", cai.getText());
                                        jarca.add(jobcai);
                                    }
                                    //questions selects
                                    JsonObjectBuilder jobsels = Json.createObjectBuilder();
                                    JsonObjectBuilder jobselquest = Json.createObjectBuilder();
                                    JsonArrayBuilder jardirection = Json.createArrayBuilder();
                                    jardirection.add(Json.createObjectBuilder().add("id", "vertical")
                                            .add("name", "vertical"));
                                    jardirection.add(Json.createObjectBuilder().add("id", "horizontal")
                                            .add("name", "horizontal"));
                                    jobselquest.add("direction", jardirection);
                                    JsonArrayBuilder jaridparent = Json.createArrayBuilder();
                                    jaridparent.add(Json.createObjectBuilder().add("id", 0)
                                            .add("name", "Нет"));
                                    List<CatiQuestionsInfo> cqis = chEjb.getCatiQuestionsByChamping(nchi.getId());
                                    for (CatiQuestionsInfo lcqi : cqis) {
                                        jaridparent.add(Json.createObjectBuilder().add("id", lcqi.getId())
                                                .add("name", lcqi.getName()));
                                    }
                                    jobselquest.add("idparent", jaridparent);
                                    JsonArrayBuilder jaridchamping = Json.createArrayBuilder();
                                    jaridchamping.add(Json.createObjectBuilder().add("id", nchi.getId())
                                            .add("name", nchi.getName()));
                                    jobselquest.add("idchamping", jaridchamping);
                                    //answers selects
                                    JsonObjectBuilder jobselansw = Json.createObjectBuilder();
                                    JsonArrayBuilder jaridinclude = Json.createArrayBuilder();
                                    jaridinclude.add(Json.createObjectBuilder().add("id", 0)
                                            .add("name", "Нет"));
                                    for (CatiQuestionsInfo lcqi : cqis) {
                                        jaridinclude.add(Json.createObjectBuilder().add("id", lcqi.getId())
                                                .add("name", lcqi.getName()));
                                    }
                                    jobselansw.add("idinclude", jaridinclude);

                                    jobsels.add("questions", jobselquest);
                                    jobsels.add("answers", jobselansw);
                                    job.add("selects", jobsels);
                                    //selects

                                    job.add("answers", jarca);
                                    response.setResult(job.build().toString());
                                }
                            }
                            break;
                        }
                        case "mgsaveanswer": {
                            if (userInfo.getRole().equals("administrator")
                                    || userInfo.getRole().equals("manager")) {
                                CatiAnswersInfo cai = (CatiAnswersInfo) message.getObject();
                                if (cai.getIdcatiquestions() != 0) {
                                    if (cai.getId() == 0) {
                                        cai.setIdinclude(0);
                                        cai.setIseditable(false);
                                        cai.setIsenable(true);
                                        cai.setIsfinal(false);
                                        cai.setLevel(0);
                                        cai.setName("Новый ответ");
                                        cai.setOptions("");
                                        cai.setText("Новый ответ");
                                    }
                                    CatiAnswersInfo ncai = chEjb.setCatiAnswer(cai);
                                    JsonObjectBuilder jobcai = Json.createObjectBuilder();
                                    jobcai.add("id", ncai.getId())
                                            .add("name", ncai.getName())
                                            .add("idcatiquestions", ncai.getIdcatiquestions())
                                            .add("idinclude", ncai.getIdinclude())
                                            .add("iseditable", ncai.getIseditable())
                                            .add("isenable", ncai.getIsenable())
                                            .add("isfinal", ncai.getIsfinal())
                                            .add("level", ncai.getLevel())
                                            .add("options", ncai.getOptions())
                                            .add("text", ncai.getText());
                                    response.setResult(jobcai.build().toString());
                                }
                            }
                            break;
                        }
                        case "usergetscenario": {
                            Integer id = (Integer) message.getObject();
                            String token = (String) httpSession.getAttribute("token");
                            Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "usergetscenario token {0}", token);
                            RequestInfo rqi = chEjb.getRequestByToken(token);
                            ChampingInfo nchi = chEjb.getChamping(id);
                            if ((nchi != null) && (!nchi.getIsforall())) {
                                JsonObjectBuilder job = Json.createObjectBuilder();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                JsonObjectBuilder jobch = Json.createObjectBuilder()
                                        .add("id", nchi.getId())
                                        .add("name", nchi.getName())
                                        .add("type", nchi.getChampingtype())
                                        .add("idcompany", nchi.getIdcompany())
                                        .add("isenable", nchi.getIsenable())
                                        .add("isforall", nchi.getIsforall())
                                        .add("adddate", sdf.format(nchi.getAdddate()))
                                        .add("description", nchi.getDescription());
                                job.add("champing", jobch);
                                JsonArrayBuilder jarcq = Json.createArrayBuilder();
                                List<CatiQuestionsInfo> cqis = chEjb.getCatiQuestionsByChamping(nchi.getId());
                                for (CatiQuestionsInfo cqi : cqis) {
                                    if (cqi.getIsenable()) {
                                        JsonObjectBuilder jobcqi = Json.createObjectBuilder();
                                        jobcqi.add("id", cqi.getId())
                                                .add("idchamping", cqi.getIdchamping())
                                                .add("name", cqi.getName())
                                                .add("condition", cqi.getCondition())
                                                .add("direction", cqi.getDirection())
                                                .add("idparent", cqi.getIdparent())
                                                .add("isenable", cqi.getIsenable())
                                                .add("israndom", cqi.getIsrandom())
                                                .add("israndomchild", cqi.getIsrandomchild())
                                                .add("isshowanswertext", cqi.getIsshowanswertext())
                                                .add("level", cqi.getLevel())
                                                .add("selectmax", cqi.getSelectmax())
                                                .add("text", cqi.getText());
                                        List<CatiAnswersInfo> cais = chEjb.getCatiAnswersByCatiQuestion(cqi.getId());
                                        JsonArrayBuilder jarca = Json.createArrayBuilder();
                                        for (CatiAnswersInfo cai : cais) {
                                            if (cai.getIsenable()) {
                                                Boolean checked = false;
                                                String value = "";
                                                if (rqi != null) {
                                                    CatiLogInfo cli = chEjb.getCatiLogByRequestAnswer(rqi.getId(), cai.getId());
                                                    if (cli != null) {
                                                        checked = cli.getIscheck();
                                                        value = cli.getValue();
                                                    }
                                                }
                                                JsonObjectBuilder jobcai = Json.createObjectBuilder();
                                                jobcai.add("id", cai.getId())
                                                        .add("name", cai.getName())
                                                        .add("idcatiquestions", cai.getIdcatiquestions())
                                                        .add("idinclude", cai.getIdinclude())
                                                        .add("iseditable", cai.getIseditable())
                                                        .add("isenable", cai.getIsenable())
                                                        .add("isfinal", cai.getIsfinal())
                                                        .add("level", cai.getLevel())
                                                        .add("options", cai.getOptions())
                                                        .add("text", cai.getText())
                                                        .add("checked", checked)
                                                        .add("value", value);
                                                jarca.add(jobcai);
                                            }
                                        }
                                        jobcqi.add("answers", jarca);
                                        jarcq.add(jobcqi);
                                    }
                                }
                                job.add("descriptor", Json.createObjectBuilder()
                                        .add("questions", Json.createReader(new StringReader(chEjb.getCatiQuestionDescriptor())).readObject())
                                        .add("answers", Json.createReader(new StringReader(chEjb.getCatiAnswerDescriptor())).readObject()));
                                job.add("questions", jarcq);
                                response.setResult(job.build().toString());
                            }
                            break;
                        }
                    }
                } else {
                    //for only guests 
                    switch (type) {
                        case "checklogin": {
                            UsersInfo obj = (UsersInfo) message.getObject();
                            UsersInfo ui = userEjb.getUserByLogin(obj.getLogin());
                            if ((ui != null) && (ui.getSecret().equals(obj.getSecret()))) {
                                UsersInfo nui = new UsersInfo();
                                nui.setEmail(ui.getEmail());
                                nui.setId(ui.getId());
                                nui.setLogin(ui.getLogin());
                                nui.setName(ui.getName());
                                nui.setPhone(ui.getPhone());
                                nui.setRole(ui.getRole());
                                nui.setSecret(ui.getSecret());
                                nui.setIdcompany(ui.getIdcompany());
                                httpSession.setAttribute("UserInfo", nui);
                                response.setResult("ok");
                            } else {
                                response.setResult("error");
                            }
                            break;
                        }
                        case "checkregister": {
                            //check login unique
                            //check name unique
                            //check secret
                            //check email unique
                            //check phone unique
                            //check captha
                            UsersInfo obj = (UsersInfo) message.getObject();
                            String rettext = "";
                            if (obj != null) {
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "login {0}", obj.getLogin());
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "email {0}", obj.getEmail());
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "name {0}", obj.getName());
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "phone {0}", obj.getPhone());
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "captha {0}", obj.getCaptcha());
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "secret {0}", obj.getSecret());

                                {
                                    UsersInfo ui = userEjb.getUserByLogin(obj.getLogin());
                                    if (ui != null) {
                                        rettext += "Такой логин уже зарегистрирован<br />";
                                    } else if (!utilEjb.isValidLoginName(obj.getLogin())) {
                                        rettext += "Введен неверный Логин<br />";
                                    }
                                }
                                {
                                    UsersInfo ui = userEjb.getUserByEmail(obj.getEmail());
                                    if (ui != null) {
                                        rettext += "Такой Email уже зарегистрирован<br />";
                                    } else if (!utilEjb.isValidEmailAddress(obj.getEmail())) {
                                        rettext += "Введен неверный Email<br />";
                                    }
                                }
                                {
                                    UsersInfo ui = userEjb.getUserByName(obj.getName());
                                    if (ui != null) {
                                        rettext += "Такое Имя уже зарегистрировано<br />";
                                    }
                                }
                                {
                                    UsersInfo ui = userEjb.getUserByPhone(obj.getPhone());
                                    if (ui != null) {
                                        rettext += "Такой Телефон уже зарегистрировано<br />";
                                    } else if (!utilEjb.isValidPhoneName(obj.getPhone())) {
                                        rettext += "Введен неверный Номер телефона<br />";
                                    }
                                }
                                {
                                    if (!httpSession.getAttribute("captcha").equals(obj.getCaptcha().toUpperCase())) {
                                        rettext += "Неверный проверочный код<br />";
                                    }
                                }
                                {
                                    if (obj.getNewsecret().length() < 6) {
                                        rettext += "Короткий пароль<br />";
                                    } else if (!utilEjb.isValidLoginName(obj.getNewsecret())) {
                                        rettext += "Введен некорректный Пароль<br />";
                                    }
                                }
                                if (rettext.isEmpty()) {
                                    rettext += "ok";
                                    //register to db
                                    Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "add user {0}", obj.getLogin());
                                    obj.setRole("user");
                                    UsersInfo newuser = userEjb.addUser(obj);
                                    if (newuser != null) {
                                        userInfo = new UsersInfo();
                                        userInfo.setId(newuser.getId());
                                        userInfo.setEmail(newuser.getEmail());
                                        userInfo.setLogin(newuser.getLogin());
                                        userInfo.setName(newuser.getName());
                                        userInfo.setPhone(newuser.getPhone());
                                        userInfo.setRole(newuser.getRole());
                                        userInfo.setSecret(newuser.getSecret());
                                        //idcompany
                                        httpSession.setAttribute("UserInfo", userInfo);
                                    }
                                }
                                response.setResult(rettext);
                            } else {
                                rettext += "Ошибка передачи данных <br />";
                            }
                            break;
                        }
                    }
                }
                //for all users
                switch (type) {
                    case "allgetscenario": {
                        Integer id = (Integer) message.getObject();
                        String token = (String) httpSession.getAttribute("token");
                        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allgetscenario token {0}", token);
                        RequestInfo rqi = chEjb.getRequestByToken(token);
                        ChampingInfo nchi = chEjb.getChamping(id);
                        if ((nchi != null) && (nchi.getIsforall())) {
                            JsonObjectBuilder job = Json.createObjectBuilder();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            JsonObjectBuilder jobch = Json.createObjectBuilder()
                                    .add("id", nchi.getId())
                                    .add("name", nchi.getName())
                                    .add("type", nchi.getChampingtype())
                                    .add("idcompany", nchi.getIdcompany())
                                    .add("isenable", nchi.getIsenable())
                                    .add("isforall", nchi.getIsforall())
                                    .add("adddate", sdf.format(nchi.getAdddate()))
                                    .add("description", nchi.getDescription());
                            job.add("champing", jobch);
                            JsonArrayBuilder jarcq = Json.createArrayBuilder();
                            List<CatiQuestionsInfo> cqis = chEjb.getCatiQuestionsByChamping(nchi.getId());
                            for (CatiQuestionsInfo cqi : cqis) {
                                if (cqi.getIsenable()) {
                                    JsonObjectBuilder jobcqi = Json.createObjectBuilder();
                                    jobcqi.add("id", cqi.getId())
                                            .add("idchamping", cqi.getIdchamping())
                                            .add("name", cqi.getName())
                                            .add("condition", cqi.getCondition())
                                            .add("direction", cqi.getDirection())
                                            .add("idparent", cqi.getIdparent())
                                            .add("isenable", cqi.getIsenable())
                                            .add("israndom", cqi.getIsrandom())
                                            .add("israndomchild", cqi.getIsrandomchild())
                                            .add("isshowanswertext", cqi.getIsshowanswertext())
                                            .add("level", cqi.getLevel())
                                            .add("selectmax", cqi.getSelectmax())
                                            .add("text", cqi.getText());
                                    List<CatiAnswersInfo> cais = chEjb.getCatiAnswersByCatiQuestion(cqi.getId());
                                    JsonArrayBuilder jarca = Json.createArrayBuilder();
                                    for (CatiAnswersInfo cai : cais) {
                                        if (cai.getIsenable()) {
                                            Boolean checked = false;
                                            String value = "";
                                            if (rqi != null) {
                                                CatiLogInfo cli = chEjb.getCatiLogByRequestAnswer(rqi.getId(), cai.getId());
                                                if (cli != null) {
                                                    checked = cli.getIscheck();
                                                    value = cli.getValue();
                                                }
                                            }
                                            JsonObjectBuilder jobcai = Json.createObjectBuilder();
                                            jobcai.add("id", cai.getId())
                                                    .add("name", cai.getName())
                                                    .add("idcatiquestions", cai.getIdcatiquestions())
                                                    .add("idinclude", cai.getIdinclude())
                                                    .add("iseditable", cai.getIseditable())
                                                    .add("isenable", cai.getIsenable())
                                                    .add("isfinal", cai.getIsfinal())
                                                    .add("level", cai.getLevel())
                                                    .add("options", cai.getOptions())
                                                    .add("text", cai.getText())
                                                    .add("checked", checked)
                                                    .add("value", value);
                                            jarca.add(jobcai);
                                        }
                                    }
                                    jobcqi.add("answers", jarca);
                                    jarcq.add(jobcqi);
                                }
                            }
                            job.add("descriptor", Json.createObjectBuilder()
                                    .add("questions", Json.createReader(new StringReader(chEjb.getCatiQuestionDescriptor())).readObject())
                                    .add("answers", Json.createReader(new StringReader(chEjb.getCatiAnswerDescriptor())).readObject()));
                            job.add("questions", jarcq);
                            response.setResult(job.build().toString());
                        }
                        break;
                    }
                    case "allchecktoken": {
                        String obj = (String) message.getObject();
                        RequestInfo rqi = chEjb.getRequestByToken(obj);
                        if (rqi != null) {
                            BaseInfo bi = chEjb.getBase(rqi.getIdbase());
                            if (bi != null) {
                                ChampingInfo nchi = chEjb.getChamping(bi.getIdchamping());
                                if (nchi != null) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    JsonObjectBuilder job = Json.createObjectBuilder();
                                    JsonObjectBuilder jobi = Json.createObjectBuilder()
                                            .add("id", nchi.getId())
                                            .add("name", nchi.getName())
                                            .add("type", nchi.getChampingtype())
                                            .add("idcompany", nchi.getIdcompany())
                                            .add("isenable", nchi.getIsenable())
                                            .add("isforall", nchi.getIsforall())
                                            .add("adddate", sdf.format(nchi.getAdddate()));

                                    job.add("item", jobi);
                                    httpSession.setAttribute("token", rqi.getToken());
                                    response.setResult(job.build().toString());
                                }
                            }
                        }
                        break;
                    }
                    case "allgetnewtoken": {
                        Integer id = (Integer) message.getObject();
                        UUID uid1 = UUID.randomUUID();
                        UUID uid2 = UUID.randomUUID();
                        String suid = uid1.toString().replaceAll("-", "") + uid2.toString().replaceAll("-", "");
                        BaseInfo bi = chEjb.getBaseByChampingDefault(id);
                        if (bi != null) {
                            RequestInfo ri = new RequestInfo();
                            ri.setIdbase(bi.getId());
                            ri.setToken(suid);
                            RequestInfo nri = chEjb.setRequest(ri);
                            if (nri != null) {
                                httpSession.setAttribute("token", nri.getToken());
                                response.setResult(nri.getToken());
                                String xrealip = (String) httpSession.getAttribute("xrealip");
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allgetnewtoken xrealip {0}", xrealip);
                                Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allgetnewtoken token {0}", nri.getToken());
                            }
                        }
                        break;
                    }
                    case "allresettoken": {
                        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allresettoken reset token");
                        httpSession.setAttribute("token", null);
                        response.setResult("true");
                        break;
                    }
                    case "allsavescenario": {
                        List<CatiLogInfo> clis = (ArrayList) message.getObject();
                        //Idcatiquestions
                        //Idcatianswers
                        //Idchamping
                        //Ischeck
                        //Value
                        String xrealip = (String) httpSession.getAttribute("xrealip");
                        String token = (String) httpSession.getAttribute("token");
                        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario xrealip {0}", xrealip);
                        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario token {0}", token);
                        String ret = token;
                        if (token != null) {
                            Date adddate = new Date();
                            for (CatiLogInfo cli : clis) {
                                CatiQuestionsInfo cqi = chEjb.getCatiQuestion(cli.getIdcatiquestions());
                                CatiAnswersInfo cai = chEjb.getCatiAnswer(cli.getIdcatianswers());
                                RequestInfo rqi = chEjb.getRequestByToken(token);
                                //Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cqi.getId() {0}", cqi);
                                //Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cai.getId() {0}", cai);
                                //Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cli.getIdcatiquestions() {0}", cli.getIdcatiquestions());
                                //Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cli.getIdcatianswers() {0}", cli.getIdcatianswers());
                                if ((cqi != null) && (cai != null) && (rqi != null)) {
                                    BaseInfo bi = chEjb.getBase(rqi.getIdbase());
                                    if ((bi != null)) {
                                        ChampingInfo chi = chEjb.getChamping(bi.getIdchamping());
                                        if (chi != null) {
                                            CatiLogInfo clie = chEjb.getCatiLogByRequestAnswer(rqi.getId(), cai.getId());
                                            if (clie == null) {
                                                clie = cli;
                                            } else {
                                                clie.setIscheck(cli.getIscheck());
                                                clie.setValue(cli.getValue());
                                            }
                                            //Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cli.getIscheck() {0}", cli.getIscheck());
                                            //Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cli.getValue() {0}", cli.getValue());
                                            clie.setAdddate(adddate);
                                            clie.setAlevel(cai.getLevel());
                                            clie.setIdbase(bi.getId());
                                            clie.setIdchamping(chi.getId());
                                            clie.setIdrequest(rqi.getId());
                                            clie.setIdusers((userInfo != null) ? userInfo.getId() : 0);
                                            clie.setIsaenable(cai.getIsenable());
                                            clie.setIsfinal(cai.getIsfinal());
                                            clie.setIsqenable(cqi.getIsenable());
                                            clie.setQlevel(cqi.getLevel());
                                            clie.setIpv4(xrealip != null ? xrealip : "");
                                            chEjb.setCatiLog(clie);
                                        } else {
                                            Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario chi null");
                                            ret = "error";
                                        }
                                    } else {
                                        Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario bi null");
                                        ret = "error";
                                    }
                                } else {
                                    Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario cqi cai rqi null");
                                    ret = "error";
                                }
                            }
                            Logger.getLogger(WSSocket.class.getName()).log(Level.INFO, "allsavescenario reset token");
                            httpSession.setAttribute("token", null);
                        }
                        response.setResult(ret);
                        break;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(WSSocket.class.getName()).log(Level.SEVERE, "Exception {0}", ex);
            }
        }
        return response;
    }

    private String sendGet(String url, String useragent) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", useragent);
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();

    }

    // HTTP POST request
    private String sendPost(String url, String useragent) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", useragent);
        //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5"); 
        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        //wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
