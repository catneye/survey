/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import ru.ftsmk.db.Getgroupca;
import ru.ftsmk.info.BaseInfo;
import ru.ftsmk.info.CatiAnswersInfo;
import ru.ftsmk.info.CatiLogInfo;
import ru.ftsmk.info.CatiQuestionsInfo;
import ru.ftsmk.info.ChampingInfo;
import ru.ftsmk.info.RequestInfo;
import ru.ftsmk.info.UniqCatilogInfo;

/**
 *
 * @author plintus
 */
@Remote
public interface ChampingBeanRemote {
    public List<ChampingInfo> getChampings();
    public ChampingInfo getChamping(Integer id);
    public List<ChampingInfo> getExternalChampings();
    public ChampingInfo setChamping(ChampingInfo info);
    public List<ChampingInfo> getChampingsByCompany(Integer idcompany);
    public ChampingInfo cloneChamping(ChampingInfo info);
    public String getChampingDescriptor();
    //CatiQuestions
    public List<CatiQuestionsInfo> getCatiQuestions();
    public List<CatiQuestionsInfo> getCatiQuestionsByChamping(Integer idchamping);
    public CatiQuestionsInfo getCatiQuestion(Integer id);
    public CatiQuestionsInfo setCatiQuestion(CatiQuestionsInfo info);
    public String getCatiQuestionDescriptor();
    //CatiAnswers
    public List<CatiAnswersInfo> getCatiAnswers();
    public List<CatiAnswersInfo> getCatiAnswersByCatiQuestion(Integer idcatiquestions);
    public List<CatiAnswersInfo> getCatiAnswersByChamping(Integer idchamping);
    public CatiAnswersInfo getCatiAnswer(Integer id);
    public CatiAnswersInfo setCatiAnswer(CatiAnswersInfo info);
    public String getCatiAnswerDescriptor();
    //Bases
    public List<BaseInfo> getBases();
    public BaseInfo getBase(Integer id);
    public List<BaseInfo> getBasesByChamping(Integer idchamping);
    public BaseInfo setBase(BaseInfo info);
    public BaseInfo getBaseByChampingDefault(Integer idchamping);
    //Request
    public List<RequestInfo> getRequests();
    public RequestInfo getRequest(Integer id);
    public List<RequestInfo> getRequestsByBase(Integer idbase);
    public RequestInfo setRequest(RequestInfo info);
    public RequestInfo getRequestByToken(String token);
    //Catilog
    public CatiLogInfo setCatiLog(CatiLogInfo info);
    public List<CatiLogInfo> getCatiLogs();
    public CatiLogInfo getCatiLog(Integer id);
    public List<CatiLogInfo> getCatiLogsByRequest(Integer idrequest);
    public CatiLogInfo getCatiLogByRequestAnswer(Integer idrequest, Integer idcatianswers);
    public List<CatiLogInfo> getCatiLogsByChamping(Integer idchamping);
    public List<CatiLogInfo> getCatiLogsByChampingDates(Integer idchamping, Date bdate, Date edate);
    public CatiLogInfo getCatiLogByRequestChampingDates(Integer idrequest, Integer idchamping, Date bdate, Date edate);
    public List<UniqCatilogInfo> getUniqCatilogs(Integer idrequest, Integer idchamping, Date bdate, Date edate);
    public List<UniqCatilogInfo> getUniqCatilogs(Integer idchamping, Date bdate, Date edate);
    public List<UniqCatilogInfo> getUniqCatilogs(Integer idchamping, Date bdate, Date edate, Boolean isfinal);
    public List<CatiLogInfo> getCatiLogsByQuestionRequest(Integer idcatiquestions, Integer request);
    
    public List<Getgroupca> getGrupsQA(Integer idchamping);
}
