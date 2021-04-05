/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ru.ftsmk.db.Base;
import ru.ftsmk.db.Catianswers;
import ru.ftsmk.db.Catilog;
import ru.ftsmk.db.Catiquestions;
import ru.ftsmk.db.Champing;
import ru.ftsmk.db.Getgroupca;
import ru.ftsmk.db.Getuniqcatilogs;
import ru.ftsmk.db.Request;
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
@Stateless
public class ChampingBean implements ChampingBeanRemote {

    @PersistenceContext(unitName = "survey-ejbPU")
    private EntityManager em;

    @Override
    public List<ChampingInfo> getChampings() {
        List<ChampingInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Champing.findAll");
        List<Champing> all = query.getResultList();
        for (Champing one : all) {
            ChampingInfo n = new ChampingInfo();
            n.setId(one.getId());
            n.setIdcompany(one.getIdcompany());
            n.setChampingtype(one.getChampingtype());
            n.setIsenable(one.getIsenable());
            n.setIsforall(one.getIsforall());
            n.setName(one.getName());
            n.setAdddate(one.getAdddate());
            n.setDescription(one.getDescription());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<ChampingInfo> getExternalChampings() {
        List<ChampingInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Champing.findByIsforall");
        query.setParameter("isforall", true);
        List<Champing> all = query.getResultList();
        for (Champing one : all) {
            ChampingInfo n = new ChampingInfo();
            n.setId(one.getId());
            n.setIdcompany(one.getIdcompany());
            n.setChampingtype(one.getChampingtype());
            n.setIsenable(one.getIsenable());
            n.setIsforall(one.getIsforall());
            n.setName(one.getName());
            n.setAdddate(one.getAdddate());
            n.setDescription(one.getDescription());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<ChampingInfo> getChampingsByCompany(Integer idcompany) {
        List<ChampingInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Champing.findByIdcompany");
        query.setParameter("idcompany", idcompany);
        List<Champing> all = query.getResultList();
        for (Champing one : all) {
            ChampingInfo n = new ChampingInfo();
            n.setId(one.getId());
            n.setIdcompany(one.getIdcompany());
            n.setChampingtype(one.getChampingtype());
            n.setIsenable(one.getIsenable());
            n.setIsforall(one.getIsforall());
            n.setName(one.getName());
            n.setAdddate(one.getAdddate());
            n.setDescription(one.getDescription());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public ChampingInfo getChamping(Integer id) {
        ChampingInfo ret = null;
        Query query = em.createNamedQuery("Champing.findById");
        query.setParameter("id", id);
        try {
            Champing one = (Champing) query.getSingleResult();
            ret = new ChampingInfo();
            ret.setId(one.getId());
            ret.setIdcompany(one.getIdcompany());
            ret.setChampingtype(one.getChampingtype());
            ret.setIsenable(one.getIsenable());
            ret.setName(one.getName());
            ret.setIsforall(one.getIsforall());
            ret.setAdddate(one.getAdddate());
            ret.setDescription(one.getDescription());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public ChampingInfo setChamping(ChampingInfo info) {
        ChampingInfo ret = null;
        Champing one = null;
        if (info.getId() != null) {
            Query query = em.createNamedQuery("Champing.findById");
            query.setParameter("id", info.getId());
            try {
                one = (Champing) query.getSingleResult();
                one.setIdcompany(info.getIdcompany());
                one.setChampingtype(info.getChampingtype());
                one.setIsenable(info.getIsenable());
                one.setName(info.getName());
                one.setAdddate(info.getAdddate());
                one.setIsforall(info.getIsforall());
                one.setDescription(info.getDescription());

                em.merge(one);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (one == null) {
            one = new Champing();
            one.setIdcompany(info.getIdcompany());
            one.setChampingtype(info.getChampingtype());
            one.setIsenable(info.getIsenable());
            one.setName(info.getName());
            one.setAdddate(info.getAdddate());
            one.setIsforall(info.getIsforall());
            one.setDescription(info.getDescription());
            em.persist(one);
            em.flush();
            //create base
            Base b = new Base();
            b.setName("default");
            b.setIdchamping(one.getId());
            em.persist(b);
            em.flush();
        }
        ret = new ChampingInfo();
        ret.setId(one.getId());
        ret.setIdcompany(one.getIdcompany());
        ret.setChampingtype(one.getChampingtype());
        ret.setIsenable(one.getIsenable());
        ret.setName(one.getName());
        ret.setAdddate(one.getAdddate());
        ret.setIsforall(one.getIsforall());
        ret.setDescription(one.getDescription());
        return ret;
    }

    @Override
    public ChampingInfo cloneChamping(ChampingInfo info) {

        ChampingInfo ret = null;
        Integer cloningid = info.getId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();
        info.setId(null);
        info.setName(sdf.format(dt));
        info.setAdddate(dt);
        ret = setChamping(info);
        Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping new champing {0}", ret.getId());

        HashMap<Integer, Integer> cqisids = new HashMap();
        HashMap<Integer, Integer> cqisparents = new HashMap();
        cqisids.put(0, 0);
        cqisparents.put(0, 0);

        //clone questions
        List<CatiQuestionsInfo> cqis = getCatiQuestionsByChamping(cloningid);
        for (CatiQuestionsInfo cqi : cqis) {
            Integer cqiid = cqi.getId();
            //store old parrents
            cqisparents.put(cqiid, cqi.getIdparent());
            cqi.setId(null);
            cqi.setIdparent(0);
            cqi.setIdchamping(ret.getId());
            CatiQuestionsInfo ncqi = setCatiQuestion(cqi);
            //store old-new ids
            cqisids.put(cqiid, ncqi.getId());
        }
        Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping cqisids {0}", cqisids);
        Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping cqisparents {0}", cqisparents);
        //restore parents
        for (Integer oldid : cqisparents.keySet()) {
            Integer oldparent = cqisparents.get(oldid);
            Integer newid = cqisids.get(oldid);
            Integer newparent = cqisids.get(oldparent);
            Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping oldid {0}", oldid);
            Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping oldparent {0}", oldparent);
            Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping newid {0}", newid);
            Logger.getLogger(ChampingBean.class.getName()).log(Level.INFO, "cloneChamping newparent {0}", newparent);
            if ((oldid!=0) && (newparent != null)) {
                CatiQuestionsInfo cqi = getCatiQuestion(newid);
                cqi.setIdparent(newparent);
                setCatiQuestion(cqi);
            }
        }
        //clone answers
        List<CatiAnswersInfo> cais = getCatiAnswersByChamping(cloningid);
        for (CatiAnswersInfo cai : cais) {
            Integer newid = cqisids.get(cai.getIdcatiquestions());
            Integer newinclude = cqisids.get(cai.getIdinclude());
            cai.setId(null);
            cai.setIdcatiquestions(newid);
            cai.setIdinclude(newinclude != null ? newinclude : 0);
            setCatiAnswer(cai);
        }
        return ret;
    }

    @Override
    public List<CatiQuestionsInfo> getCatiQuestions() {
        List<CatiQuestionsInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catiquestions.findAll");
        List<Catiquestions> all = query.getResultList();
        for (Catiquestions one : all) {
            CatiQuestionsInfo n = new CatiQuestionsInfo();
            n.setId(one.getId());
            n.setCondition(one.getCondition());
            n.setDirection(one.getDirection());
            n.setIdchamping(one.getIdchamping());
            n.setIdparent(one.getIdparent());
            n.setIsenable(one.getIsenable());
            n.setIsrandom(one.getIsrandom());
            n.setIsshowanswertext(one.getIsshowanswertext());
            n.setLevel(one.getLevel());
            n.setName(one.getName());
            n.setSelectmax(one.getSelectmax());
            n.setText(one.getText());
            n.setIsrandomchild(one.getIsrandomchild());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<CatiQuestionsInfo> getCatiQuestionsByChamping(Integer idchamping) {
        List<CatiQuestionsInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catiquestions.findByIdchampingOrdLevel");
        query.setParameter("idchamping", idchamping);
        List<Catiquestions> all = query.getResultList();
        for (Catiquestions one : all) {
            CatiQuestionsInfo n = new CatiQuestionsInfo();
            n.setId(one.getId());
            n.setCondition(one.getCondition());
            n.setDirection(one.getDirection());
            n.setIdchamping(one.getIdchamping());
            n.setIdparent(one.getIdparent());
            n.setIsenable(one.getIsenable());
            n.setIsrandom(one.getIsrandom());
            n.setIsshowanswertext(one.getIsshowanswertext());
            n.setLevel(one.getLevel());
            n.setName(one.getName());
            n.setSelectmax(one.getSelectmax());
            n.setText(one.getText());
            n.setIsrandomchild(one.getIsrandomchild());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public CatiQuestionsInfo getCatiQuestion(Integer id) {
        CatiQuestionsInfo ret = null;
        Query query = em.createNamedQuery("Catiquestions.findById");
        query.setParameter("id", id);
        try {
            Catiquestions one = (Catiquestions) query.getSingleResult();
            ret = new CatiQuestionsInfo();
            ret.setId(one.getId());
            ret.setCondition(one.getCondition());
            ret.setDirection(one.getDirection());
            ret.setIdchamping(one.getIdchamping());
            ret.setIdparent(one.getIdparent());
            ret.setIsenable(one.getIsenable());
            ret.setIsrandom(one.getIsrandom());
            ret.setIsshowanswertext(one.getIsshowanswertext());
            ret.setLevel(one.getLevel());
            ret.setName(one.getName());
            ret.setSelectmax(one.getSelectmax());
            ret.setText(one.getText());
            ret.setIsrandomchild(one.getIsrandomchild());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public CatiQuestionsInfo setCatiQuestion(CatiQuestionsInfo info) {
        CatiQuestionsInfo ret = null;
        Catiquestions one = null;
        if (info.getId() != null) {
            Query query = em.createNamedQuery("Catiquestions.findById");
            query.setParameter("id", info.getId());
            try {
                one = (Catiquestions) query.getSingleResult();
                one.setCondition(info.getCondition());
                one.setDirection(info.getDirection());
                one.setIdchamping(info.getIdchamping());
                one.setIdparent(info.getIdparent());
                one.setIsenable(info.getIsenable());
                one.setIsrandom(info.getIsrandom());
                one.setIsshowanswertext(info.getIsshowanswertext());
                one.setLevel(info.getLevel());
                one.setName(info.getName());
                one.setSelectmax(info.getSelectmax());
                one.setText(info.getText());
                one.setIsrandomchild(info.getIsrandomchild());

                em.merge(one);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (one == null) {
            one = new Catiquestions();
            one.setCondition(info.getCondition());
            one.setDirection(info.getDirection());
            one.setIdchamping(info.getIdchamping());
            one.setIdparent(info.getIdparent());
            one.setIsenable(info.getIsenable());
            one.setIsrandom(info.getIsrandom());
            one.setIsshowanswertext(info.getIsshowanswertext());
            one.setLevel(info.getLevel());
            one.setName(info.getName());
            one.setSelectmax(info.getSelectmax());
            one.setText(info.getText());
            one.setIsrandomchild(info.getIsrandomchild());
            em.persist(one);
            em.flush();
        }
        ret = new CatiQuestionsInfo();
        ret.setId(one.getId());
        ret.setCondition(one.getCondition());
        ret.setDirection(one.getDirection());
        ret.setIdchamping(one.getIdchamping());
        ret.setIdparent(one.getIdparent());
        ret.setIsenable(one.getIsenable());
        ret.setIsrandom(one.getIsrandom());
        ret.setIsshowanswertext(one.getIsshowanswertext());
        ret.setLevel(one.getLevel());
        ret.setName(one.getName());
        ret.setSelectmax(one.getSelectmax());
        ret.setText(one.getText());
        ret.setIsrandomchild(one.getIsrandomchild());
        return ret;
    }

    @Override
    public List<CatiAnswersInfo> getCatiAnswers() {
        List<CatiAnswersInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catianswers.findAll");
        List<Catianswers> all = query.getResultList();
        for (Catianswers one : all) {
            CatiAnswersInfo n = new CatiAnswersInfo();
            n.setId(one.getId());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdinclude(one.getIdinclude());
            n.setIseditable(one.getIseditable());
            n.setIsenable(one.getIsenable());
            n.setIsfinal(one.getIsfinal());
            n.setLevel(one.getLevel());
            n.setName(one.getName());
            n.setOptions(one.getOptions());
            n.setText(one.getText());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<CatiAnswersInfo> getCatiAnswersByCatiQuestion(Integer idcatiquestions) {
        List<CatiAnswersInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catianswers.findByIdcatiquestionsOrdLevel");
        query.setParameter("idcatiquestions", idcatiquestions);
        List<Catianswers> all = query.getResultList();
        for (Catianswers one : all) {
            CatiAnswersInfo n = new CatiAnswersInfo();
            n.setId(one.getId());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdinclude(one.getIdinclude());
            n.setIseditable(one.getIseditable());
            n.setIsenable(one.getIsenable());
            n.setIsfinal(one.getIsfinal());
            n.setLevel(one.getLevel());
            n.setName(one.getName());
            n.setOptions(one.getOptions());
            n.setText(one.getText());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<CatiAnswersInfo> getCatiAnswersByChamping(Integer idchamping) {
        List<CatiAnswersInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catiquestions.findByIdchamping");
        query.setParameter("idchamping", idchamping);
        List<Catiquestions> allqs = query.getResultList();
        for (Catiquestions allq : allqs) {
            Query aquery = em.createNamedQuery("Catianswers.findByIdcatiquestions");
            aquery.setParameter("idcatiquestions", allq.getId());
            List<Catianswers> allas = aquery.getResultList();
            for (Catianswers one : allas) {
                CatiAnswersInfo n = new CatiAnswersInfo();
                n.setId(one.getId());
                n.setIdcatiquestions(one.getIdcatiquestions());
                n.setIdinclude(one.getIdinclude());
                n.setIseditable(one.getIseditable());
                n.setIsenable(one.getIsenable());
                n.setIsfinal(one.getIsfinal());
                n.setLevel(one.getLevel());
                n.setName(one.getName());
                n.setOptions(one.getOptions());
                n.setText(one.getText());
                ret.add(n);
            }

        }
        return ret;
    }

    @Override
    public CatiAnswersInfo getCatiAnswer(Integer id) {
        CatiAnswersInfo ret = null;
        Query query = em.createNamedQuery("Catianswers.findById");
        query.setParameter("id", id);
        try {
            Catianswers one = (Catianswers) query.getSingleResult();
            ret = new CatiAnswersInfo();
            ret.setId(one.getId());
            ret.setIdcatiquestions(one.getIdcatiquestions());
            ret.setIdinclude(one.getIdinclude());
            ret.setIseditable(one.getIseditable());
            ret.setIsenable(one.getIsenable());
            ret.setIsfinal(one.getIsfinal());
            ret.setLevel(one.getLevel());
            ret.setName(one.getName());
            ret.setOptions(one.getOptions());
            ret.setText(one.getText());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public CatiAnswersInfo setCatiAnswer(CatiAnswersInfo info) {
        CatiAnswersInfo ret = null;
        Catianswers one = null;
        if (info.getId() != null) {
            Query query = em.createNamedQuery("Catianswers.findById");
            query.setParameter("id", info.getId());
            try {
                one = (Catianswers) query.getSingleResult();
                one.setIdcatiquestions(info.getIdcatiquestions());
                one.setIdinclude(info.getIdinclude());
                one.setIseditable(info.getIseditable());
                one.setIsenable(info.getIsenable());
                one.setIsfinal(info.getIsfinal());
                one.setLevel(info.getLevel());
                one.setName(info.getName());
                one.setOptions(info.getOptions());
                one.setText(info.getText());

                em.merge(one);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (one == null) {
            one = new Catianswers();
            one.setIdcatiquestions(info.getIdcatiquestions());
            one.setIdinclude(info.getIdinclude());
            one.setIseditable(info.getIseditable());
            one.setIsenable(info.getIsenable());
            one.setIsfinal(info.getIsfinal());
            one.setLevel(info.getLevel());
            one.setName(info.getName());
            one.setOptions(info.getOptions());
            one.setText(info.getText());
            em.persist(one);
            em.flush();
        }
        ret = new CatiAnswersInfo();
        ret.setId(one.getId());
        ret.setIdcatiquestions(one.getIdcatiquestions());
        ret.setIdinclude(one.getIdinclude());
        ret.setIseditable(one.getIseditable());
        ret.setIsenable(one.getIsenable());
        ret.setIsfinal(one.getIsfinal());
        ret.setLevel(one.getLevel());
        ret.setName(one.getName());
        ret.setOptions(one.getOptions());
        ret.setText(one.getText());
        return ret;
    }

    @Override
    public List<BaseInfo> getBases() {
        List<BaseInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Base.findAll");
        List<Base> all = query.getResultList();
        for (Base one : all) {
            BaseInfo n = new BaseInfo();
            n.setId(one.getId());
            n.setIdchamping(one.getIdchamping());
            n.setName(one.getName());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public BaseInfo getBase(Integer id) {
        BaseInfo ret = null;
        Query query = em.createNamedQuery("Base.findById");
        query.setParameter("id", id);
        try {
            Base one = (Base) query.getSingleResult();
            ret = new BaseInfo();
            ret.setId(one.getId());
            ret.setIdchamping(one.getIdchamping());
            ret.setName(one.getName());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public BaseInfo getBaseByChampingDefault(Integer idchamping) {
        BaseInfo ret = null;
        Query query = em.createNamedQuery("Base.findByIdchampingName");
        query.setParameter("idchamping", idchamping);
        query.setParameter("name", "default");
        try {
            Base one = (Base) query.getSingleResult();
            ret = new BaseInfo();
            ret.setId(one.getId());
            ret.setIdchamping(one.getIdchamping());
            ret.setName(one.getName());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public List<BaseInfo> getBasesByChamping(Integer idchamping) {
        List<BaseInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Base.findByIdchamping");
        query.setParameter("idchamping", idchamping);
        List<Base> all = query.getResultList();
        for (Base one : all) {
            BaseInfo n = new BaseInfo();
            n.setId(one.getId());
            n.setIdchamping(one.getIdchamping());
            n.setName(one.getName());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public BaseInfo setBase(BaseInfo info) {
        BaseInfo ret = null;
        Base one = null;
        if (info.getId() != null) {
            Query query = em.createNamedQuery("Base.findById");
            query.setParameter("id", info.getId());
            try {
                one = (Base) query.getSingleResult();
                one.setIdchamping(info.getIdchamping());
                one.setName(info.getName());

                em.merge(one);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (one == null) {
            one = new Base();
            one.setIdchamping(info.getIdchamping());
            one.setName(info.getName());
            em.persist(one);
            em.flush();
        }
        ret = new BaseInfo();
        ret.setId(one.getId());
        ret.setIdchamping(one.getIdchamping());
        ret.setName(one.getName());
        return ret;
    }

    @Override
    public List<RequestInfo> getRequests() {
        List<RequestInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Request.findAll");
        List<Request> all = query.getResultList();
        for (Request one : all) {
            RequestInfo n = new RequestInfo();
            n.setId(one.getId());
            n.setIdbase(one.getIdbase());
            n.setToken(one.getToken());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public RequestInfo getRequest(Integer id) {
        RequestInfo ret = null;
        Query query = em.createNamedQuery("Request.findById");
        query.setParameter("id", id);
        try {
            Request one = (Request) query.getSingleResult();
            ret = new RequestInfo();
            ret.setId(one.getId());
            ret.setIdbase(one.getIdbase());
            ret.setToken(one.getToken());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public RequestInfo getRequestByToken(String token) {
        RequestInfo ret = null;
        Query query = em.createNamedQuery("Request.findByToken");
        query.setParameter("token", token);
        try {
            Request one = (Request) query.getSingleResult();
            ret = new RequestInfo();
            ret.setId(one.getId());
            ret.setIdbase(one.getIdbase());
            ret.setToken(one.getToken());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public List<RequestInfo> getRequestsByBase(Integer idbase) {
        List<RequestInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Request.findByIdbase");
        query.setParameter("idbase", idbase);
        List<Request> all = query.getResultList();
        for (Request one : all) {
            RequestInfo n = new RequestInfo();
            n.setId(one.getId());
            n.setIdbase(one.getIdbase());
            n.setToken(one.getToken());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public RequestInfo setRequest(RequestInfo info) {
        RequestInfo ret = null;
        Request one = null;
        if (info.getId() != null) {
            Query query = em.createNamedQuery("Request.findById");
            query.setParameter("id", info.getId());
            try {
                one = (Request) query.getSingleResult();
                one.setIdbase(info.getIdbase());
                one.setToken(info.getToken());

                em.merge(one);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (one == null) {
            one = new Request();
            one.setIdbase(info.getIdbase());
            one.setToken(info.getToken());
            em.persist(one);
            em.flush();
        }
        ret = new RequestInfo();
        ret.setId(one.getId());
        ret.setIdbase(one.getIdbase());
        ret.setToken(one.getToken());
        return ret;
    }

    @Override
    public List<CatiLogInfo> getCatiLogs() {
        List<CatiLogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catilog.findAll");
        List<Catilog> all = query.getResultList();
        for (Catilog one : all) {
            CatiLogInfo n = new CatiLogInfo();
            n.setId(one.getId());
            n.setAdddate(one.getAdddate());
            n.setAlevel(one.getAlevel());
            n.setIdbase(one.getIdbase());
            n.setIdcatianswers(one.getIdcatianswers());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setIdusers(one.getIdusers());
            n.setIsaenable(one.getIsaenable());
            n.setIsfinal(one.getIsfinal());
            n.setIscheck(one.getIscheck());
            n.setIsqenable(one.getIsqenable());
            n.setQlevel(one.getQlevel());
            n.setValue(one.getValue());
            n.setIpv4(one.getIpv4());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public CatiLogInfo getCatiLog(Integer id) {
        CatiLogInfo ret = null;
        Query query = em.createNamedQuery("Catilog.findById");
        query.setParameter("id", id);
        try {
            Catilog one = (Catilog) query.getSingleResult();
            ret = new CatiLogInfo();
            ret.setId(one.getId());
            ret.setAdddate(one.getAdddate());
            ret.setAlevel(one.getAlevel());
            ret.setIdbase(one.getIdbase());
            ret.setIdcatianswers(one.getIdcatianswers());
            ret.setIdcatiquestions(one.getIdcatiquestions());
            ret.setIdchamping(one.getIdchamping());
            ret.setIdrequest(one.getIdrequest());
            ret.setIdusers(one.getIdusers());
            ret.setIsaenable(one.getIsaenable());
            ret.setIsfinal(one.getIsfinal());
            ret.setIscheck(one.getIscheck());
            ret.setIsqenable(one.getIsqenable());
            ret.setQlevel(one.getQlevel());
            ret.setValue(one.getValue());
            ret.setIpv4(one.getIpv4());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public List<CatiLogInfo> getCatiLogsByRequest(Integer idrequest) {
        List<CatiLogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catilog.findByIdrequest");
        query.setParameter("idrequest", idrequest);
        List<Catilog> all = query.getResultList();
        for (Catilog one : all) {
            CatiLogInfo n = new CatiLogInfo();
            n.setId(one.getId());
            n.setAdddate(one.getAdddate());
            n.setAlevel(one.getAlevel());
            n.setIdbase(one.getIdbase());
            n.setIdcatianswers(one.getIdcatianswers());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setIdusers(one.getIdusers());
            n.setIsaenable(one.getIsaenable());
            n.setIsfinal(one.getIsfinal());
            n.setIscheck(one.getIscheck());
            n.setIsqenable(one.getIsqenable());
            n.setQlevel(one.getQlevel());
            n.setValue(one.getValue());
            n.setIpv4(one.getIpv4());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<CatiLogInfo> getCatiLogsByChamping(Integer idchamping) {
        List<CatiLogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catilog.findByIdchamping");
        query.setParameter("idchamping", idchamping);
        List<Catilog> all = query.getResultList();
        for (Catilog one : all) {
            CatiLogInfo n = new CatiLogInfo();
            n.setId(one.getId());
            n.setAdddate(one.getAdddate());
            n.setAlevel(one.getAlevel());
            n.setIdbase(one.getIdbase());
            n.setIdcatianswers(one.getIdcatianswers());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setIdusers(one.getIdusers());
            n.setIsaenable(one.getIsaenable());
            n.setIsfinal(one.getIsfinal());
            n.setIscheck(one.getIscheck());
            n.setIsqenable(one.getIsqenable());
            n.setQlevel(one.getQlevel());
            n.setValue(one.getValue());
            n.setIpv4(one.getIpv4());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<CatiLogInfo> getCatiLogsByChampingDates(Integer idchamping, Date bdate, Date edate) {
        List<CatiLogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catilog.findByIdchampingDates");
        query.setParameter("idchamping", idchamping);
        query.setParameter("bdate", bdate);
        query.setParameter("edate", edate);
        List<Catilog> all = query.getResultList();
        for (Catilog one : all) {
            CatiLogInfo n = new CatiLogInfo();
            n.setId(one.getId());
            n.setAdddate(one.getAdddate());
            n.setAlevel(one.getAlevel());
            n.setIdbase(one.getIdbase());
            n.setIdcatianswers(one.getIdcatianswers());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setIdusers(one.getIdusers());
            n.setIsaenable(one.getIsaenable());
            n.setIsfinal(one.getIsfinal());
            n.setIscheck(one.getIscheck());
            n.setIsqenable(one.getIsqenable());
            n.setQlevel(one.getQlevel());
            n.setValue(one.getValue());
            n.setIpv4(one.getIpv4());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<CatiLogInfo> getCatiLogsByQuestionRequest(Integer idcatiquestions, Integer idrequest) {
        List<CatiLogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Catilog.findByIdrequestIdcatiquestions");
        query.setParameter("idcatiquestions", idcatiquestions);
        query.setParameter("idrequest", idrequest);
        List<Catilog> all = query.getResultList();
        for (Catilog one : all) {
            CatiLogInfo n = new CatiLogInfo();
            n.setId(one.getId());
            n.setAdddate(one.getAdddate());
            n.setAlevel(one.getAlevel());
            n.setIdbase(one.getIdbase());
            n.setIdcatianswers(one.getIdcatianswers());
            n.setIdcatiquestions(one.getIdcatiquestions());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setIdusers(one.getIdusers());
            n.setIsaenable(one.getIsaenable());
            n.setIsfinal(one.getIsfinal());
            n.setIscheck(one.getIscheck());
            n.setIsqenable(one.getIsqenable());
            n.setQlevel(one.getQlevel());
            n.setValue(one.getValue());
            n.setIpv4(one.getIpv4());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public CatiLogInfo getCatiLogByRequestAnswer(Integer idrequest, Integer idcatianswers) {
        CatiLogInfo ret = null;
        Query query = em.createNamedQuery("Catilog.findByIdrequestIdcatianswers");
        query.setParameter("idrequest", idrequest);
        query.setParameter("idcatianswers", idcatianswers);
        try {
            Catilog one = (Catilog) query.getSingleResult();
            ret = new CatiLogInfo();
            ret.setId(one.getId());
            ret.setAdddate(one.getAdddate());
            ret.setAlevel(one.getAlevel());
            ret.setIdbase(one.getIdbase());
            ret.setIdcatianswers(one.getIdcatianswers());
            ret.setIdcatiquestions(one.getIdcatiquestions());
            ret.setIdchamping(one.getIdchamping());
            ret.setIdrequest(one.getIdrequest());
            ret.setIdusers(one.getIdusers());
            ret.setIsaenable(one.getIsaenable());
            ret.setIsfinal(one.getIsfinal());
            ret.setIscheck(one.getIscheck());
            ret.setIsqenable(one.getIsqenable());
            ret.setQlevel(one.getQlevel());
            ret.setValue(one.getValue());
            ret.setIpv4(one.getIpv4());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public CatiLogInfo getCatiLogByRequestChampingDates(Integer idrequest, Integer idchamping, Date bdate, Date edate) {
        CatiLogInfo ret = null;
        Query query = em.createNamedQuery("Catilog.findByIdchampingIdrequestsDates");
        query.setParameter("idrequest", idrequest);
        query.setParameter("idchamping", idchamping);
        query.setParameter("bdate", bdate);
        query.setParameter("edate", edate);
        try {
            Catilog one = (Catilog) query.getSingleResult();
            ret = new CatiLogInfo();
            ret.setId(one.getId());
            ret.setAdddate(one.getAdddate());
            ret.setAlevel(one.getAlevel());
            ret.setIdbase(one.getIdbase());
            ret.setIdcatianswers(one.getIdcatianswers());
            ret.setIdcatiquestions(one.getIdcatiquestions());
            ret.setIdchamping(one.getIdchamping());
            ret.setIdrequest(one.getIdrequest());
            ret.setIdusers(one.getIdusers());
            ret.setIsaenable(one.getIsaenable());
            ret.setIsfinal(one.getIsfinal());
            ret.setIscheck(one.getIscheck());
            ret.setIsqenable(one.getIsqenable());
            ret.setQlevel(one.getQlevel());
            ret.setValue(one.getValue());
            ret.setIpv4(one.getIpv4());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public CatiLogInfo setCatiLog(CatiLogInfo info) {
        CatiLogInfo ret = null;
        Catilog one = null;
        if (info.getId() != null) {
            Query query = em.createNamedQuery("Catilog.findById");
            query.setParameter("id", info.getId());
            try {
                one = (Catilog) query.getSingleResult();
                one.setAdddate(info.getAdddate());
                one.setAlevel(info.getAlevel());
                one.setIdbase(info.getIdbase());
                one.setIdcatianswers(info.getIdcatianswers());
                one.setIdcatiquestions(info.getIdcatiquestions());
                one.setIdchamping(info.getIdchamping());
                one.setIdrequest(info.getIdrequest());
                one.setIdusers(info.getIdusers());
                one.setIsaenable(info.getIsaenable());
                one.setIsfinal(info.getIsfinal());
                one.setIscheck(info.getIscheck());
                one.setIsqenable(info.getIsqenable());
                one.setQlevel(info.getQlevel());
                one.setValue(info.getValue());
                one.setIpv4(info.getIpv4());

                em.merge(one);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (one == null) {
            one = new Catilog();
            one.setAdddate(info.getAdddate());
            one.setAlevel(info.getAlevel());
            one.setIdbase(info.getIdbase());
            one.setIdcatianswers(info.getIdcatianswers());
            one.setIdcatiquestions(info.getIdcatiquestions());
            one.setIdchamping(info.getIdchamping());
            one.setIdrequest(info.getIdrequest());
            one.setIdusers(info.getIdusers());
            one.setIsaenable(info.getIsaenable());
            one.setIsfinal(info.getIsfinal());
            one.setIscheck(info.getIscheck());
            one.setIsqenable(info.getIsqenable());
            one.setQlevel(info.getQlevel());
            one.setValue(info.getValue());
            one.setIpv4(info.getIpv4());
            em.persist(one);
            em.flush();
        }
        ret = new CatiLogInfo();
        ret.setId(one.getId());
        ret.setAdddate(one.getAdddate());
        ret.setAlevel(one.getAlevel());
        ret.setIdbase(one.getIdbase());
        ret.setIdcatianswers(one.getIdcatianswers());
        ret.setIdcatiquestions(one.getIdcatiquestions());
        ret.setIdchamping(one.getIdchamping());
        ret.setIdrequest(one.getIdrequest());
        ret.setIdusers(one.getIdusers());
        ret.setIsaenable(one.getIsaenable());
        ret.setIsfinal(one.getIsfinal());
        ret.setIscheck(one.getIscheck());
        ret.setIsqenable(one.getIsqenable());
        ret.setQlevel(one.getQlevel());
        ret.setValue(one.getValue());
        ret.setIpv4(one.getIpv4());
        return ret;
    }

    @Override
    public List<UniqCatilogInfo> getUniqCatilogs(Integer idrequest, Integer idchamping, Date bdate, Date edate) {
        List<UniqCatilogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Getuniqcatilogs.findByIdchampingIdrequestDates");
        query.setParameter("idrequest", idrequest);
        query.setParameter("idchamping", idchamping);
        query.setParameter("bdate", bdate);
        query.setParameter("edate", edate);
        List<Getuniqcatilogs> all = query.getResultList();
        for (Getuniqcatilogs one : all) {
            UniqCatilogInfo n = new UniqCatilogInfo();
            n.setId(one.getId());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setAdddate(one.getAdddate());
            ret.add(n);
        }
        return ret;
    }

    @Override
    public List<UniqCatilogInfo> getUniqCatilogs(Integer idchamping, Date bdate, Date edate) {
        List<UniqCatilogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Getuniqcatilogs.findByIdchampingDates");
        query.setParameter("idchamping", idchamping);
        query.setParameter("bdate", bdate);
        query.setParameter("edate", edate);
        List<Getuniqcatilogs> all = query.getResultList();
        for (Getuniqcatilogs one : all) {
            UniqCatilogInfo n = new UniqCatilogInfo();
            n.setId(one.getId());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setAdddate(one.getAdddate());
            ret.add(n);
        }
        return ret;
    }
    
    @Override
    public List<UniqCatilogInfo> getUniqCatilogs(Integer idchamping, Date bdate, Date edate, Boolean isfinal) {
        List<UniqCatilogInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Getuniqcatilogs.findByIdchampingDatesFinal");
        query.setParameter("idchamping", idchamping);
        query.setParameter("bdate", bdate);
        query.setParameter("edate", edate);
        query.setParameter("isfinal", isfinal);
        List<Getuniqcatilogs> all = query.getResultList();
        for (Getuniqcatilogs one : all) {
            UniqCatilogInfo n = new UniqCatilogInfo();
            n.setId(one.getId());
            n.setIdchamping(one.getIdchamping());
            n.setIdrequest(one.getIdrequest());
            n.setAdddate(one.getAdddate());
            ret.add(n);
        }
        return ret;
    }
    @Override
    public List<Getgroupca> getGrupsQA(Integer idchamping) {
        List<Getgroupca> ret = new ArrayList();
        Query query = em.createNamedQuery("Getgroupca.findByIdchamping");
        query.setParameter("idchamping", idchamping);
        List<Getgroupca> all = query.getResultList();
        return all;
    }

    @Override
    public String getChampingDescriptor() {
        //JsonArrayBuilder jabdsc = Json.createArrayBuilder();
        JsonObjectBuilder jobdsc = Json.createObjectBuilder();
        jobdsc.add("id", Json.createObjectBuilder()
                .add("name", "id")
                .add("title", "ID")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("name", Json.createObjectBuilder()
                .add("name", "name")
                .add("title", "Наименование")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("type", Json.createObjectBuilder()
                .add("name", "type")
                .add("title", "Тип")
                .add("type", "string")
                .add("iseditable", false));
        jobdsc.add("idcompany", Json.createObjectBuilder()
                .add("name", "idcompany")
                .add("title", "Компания")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("isenable", Json.createObjectBuilder()
                .add("name", "isenable")
                .add("title", "Включена")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("isforall", Json.createObjectBuilder()
                .add("name", "isforall")
                .add("title", "Для всех")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("adddate", Json.createObjectBuilder()
                .add("name", "adddate")
                .add("title", "Дата создания")
                .add("type", "date")
                .add("iseditable", false));
        jobdsc.add("description", Json.createObjectBuilder()
                .add("name", "description")
                .add("title", "Описание")
                .add("type", "text")
                .add("iseditable", true));
        return jobdsc.build().toString();
    }

    @Override
    public String getCatiQuestionDescriptor() {
        JsonObjectBuilder jobdsc = Json.createObjectBuilder();
        jobdsc.add("id", Json.createObjectBuilder()
                .add("name", "id")
                .add("title", "ID")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("idchamping", Json.createObjectBuilder()
                .add("name", "idchamping")
                .add("title", "Кампания")
                .add("type", "integer")
                .add("iseditable", true));
        jobdsc.add("idparent", Json.createObjectBuilder()
                .add("name", "idparent")
                .add("title", "Родитель")
                .add("type", "integer")
                .add("iseditable", true));
        jobdsc.add("name", Json.createObjectBuilder()
                .add("name", "name")
                .add("title", "Наименование")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("text", Json.createObjectBuilder()
                .add("name", "text")
                .add("title", "Техт")
                .add("type", "text")
                .add("iseditable", true));
        jobdsc.add("condition", Json.createObjectBuilder()
                .add("name", "condition")
                .add("title", "Условия")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("selectmax", Json.createObjectBuilder()
                .add("name", "selectmax")
                .add("title", "Количество для выбора")
                .add("type", "integer")
                .add("iseditable", true));
        jobdsc.add("direction", Json.createObjectBuilder()
                .add("name", "direction")
                .add("title", "Направление")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("isshowanswertext", Json.createObjectBuilder()
                .add("name", "isshowanswertext")
                .add("title", "Показывать имя ответов")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("israndom", Json.createObjectBuilder()
                .add("name", "israndom")
                .add("title", "Случайный порядок ответов")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("israndomchild", Json.createObjectBuilder()
                .add("name", "israndomchild")
                .add("title", "Случайный порядок подвопросов")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("level", Json.createObjectBuilder()
                .add("name", "level")
                .add("title", "Уровень")
                .add("type", "integer")
                .add("iseditable", true));
        jobdsc.add("isenable", Json.createObjectBuilder()
                .add("name", "isenable")
                .add("title", "Включен")
                .add("type", "boolean")
                .add("iseditable", true));
        return jobdsc.build().toString();
    }

    @Override
    public String getCatiAnswerDescriptor() {
        JsonObjectBuilder jobdsc = Json.createObjectBuilder();
        jobdsc.add("id", Json.createObjectBuilder()
                .add("name", "id")
                .add("title", "ID")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("idcatiquestions", Json.createObjectBuilder()
                .add("name", "idcatiquestions")
                .add("title", "Вопрос")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("name", Json.createObjectBuilder()
                .add("name", "name")
                .add("title", "Наименование")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("text", Json.createObjectBuilder()
                .add("name", "text")
                .add("title", "Текст")
                .add("type", "text")
                .add("iseditable", true));
        jobdsc.add("level", Json.createObjectBuilder()
                .add("name", "level")
                .add("title", "Уровень")
                .add("type", "integer")
                .add("iseditable", true));
        jobdsc.add("idinclude", Json.createObjectBuilder()
                .add("name", "idinclude")
                .add("title", "Включает вопрос")
                .add("type", "integer")
                .add("iseditable", true));
        jobdsc.add("iseditable", Json.createObjectBuilder()
                .add("name", "iseditable")
                .add("title", "Редактируемый")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("isfinal", Json.createObjectBuilder()
                .add("name", "isfinal")
                .add("title", "Конечный")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("isenable", Json.createObjectBuilder()
                .add("name", "isenable")
                .add("title", "Включен")
                .add("type", "boolean")
                .add("iseditable", true));
        jobdsc.add("options", Json.createObjectBuilder()
                .add("name", "options")
                .add("title", "Опции")
                .add("type", "string")
                .add("iseditable", true));
        return jobdsc.build().toString();
    }
}
