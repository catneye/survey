/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ru.ftsmk.db.Etc;
import ru.ftsmk.db.News;
import ru.ftsmk.info.EtcInfo;
import ru.ftsmk.info.NewsInfo;

/**
 *
 * @author plintus
 */
@Stateless
public class EtcBean implements EtcBeanRemote {

    @PersistenceContext(unitName = "survey-ejbPU")
    private EntityManager em;

    @Override
    public List<EtcInfo> getEtcs() {
        List<EtcInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Etc.findAll");
        List<Etc> etcs = query.getResultList();
        for (Etc etc : etcs) {
            EtcInfo etci = new EtcInfo();
            etci.setId(etc.getId());
            etci.setKey(etc.getKey());
            etci.setValue(etc.getValue());
            etci.setIdcompany(etc.getIdcompany());
            etci.setIdusers(etc.getIdusers());
            ret.add(etci);
        }
        return ret;
    }

    @Override
    public EtcInfo getEtc(Integer id) {
        EtcInfo ret = null;
        Query query = em.createNamedQuery("Etc.findById");
        query.setParameter("id", id);
        try {
            Etc etc = (Etc) query.getSingleResult();
            ret = new EtcInfo();
            ret.setId(etc.getId());
            ret.setKey(etc.getKey());
            ret.setValue(etc.getValue());
            ret.setIdcompany(etc.getIdcompany());
            ret.setIdusers(etc.getIdusers());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public List<EtcInfo> getEtcs(String key) {
        List<EtcInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Etc.findByKey");
        query.setParameter("key", key);
        List<Etc> etcs = query.getResultList();
        for (Etc etc : etcs) {
            EtcInfo etci = new EtcInfo();
            etci.setId(etc.getId());
            etci.setKey(etc.getKey());
            etci.setValue(etc.getValue());
            etci.setIdcompany(etc.getIdcompany());
            etci.setIdusers(etc.getIdusers());
            ret.add(etci);
        }
        return ret;
    }

    @Override
    public EtcInfo getEtcByKey(String key, Integer idcompany, Integer idusers) {
        EtcInfo ret = null;
        Query query = em.createNamedQuery("Etc.findByKeyIdcompanyIdusers");
        query.setParameter("key", key);
        query.setParameter("idusers", idusers);
        query.setParameter("idcompany", idcompany);
        try {
            Etc etc = (Etc) query.getSingleResult();
            ret = new EtcInfo();
            ret.setId(etc.getId());
            ret.setKey(etc.getKey());
            ret.setValue(etc.getValue());
            ret.setIdcompany(etc.getIdcompany());
            ret.setIdusers(etc.getIdusers());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public EtcInfo setEtc(EtcInfo etcinfo) {
        EtcInfo ret = null;
        Etc etc = null;
        if (etcinfo.getId() != null) {
            Query query = em.createNamedQuery("Etc.findById");
            query.setParameter("id", etcinfo.getId());
            try {
                etc = (Etc) query.getSingleResult();
                etc.setKey(etcinfo.getKey());
                etc.setValue(etcinfo.getValue());
                etc.setIdcompany(etcinfo.getIdcompany());
                etc.setIdusers(etcinfo.getIdusers());

                em.merge(etc);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (etc == null) {
            etc = new Etc();
            etc.setKey(etcinfo.getKey());
            etc.setValue(etcinfo.getValue());
            etc.setIdcompany(etcinfo.getIdcompany());
            etc.setIdusers(etcinfo.getIdusers());
            em.persist(etc);
            em.flush();
        }
        ret = new EtcInfo();
        ret.setId(etc.getId());
        ret.setKey(etc.getKey());
        ret.setValue(etc.getValue());
        ret.setIdcompany(etc.getIdcompany());
        ret.setIdusers(etc.getIdusers());
        return ret;
    }

    @Override
    public List<NewsInfo> getNews() {
        List<NewsInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("News.findAll");
        List<News> etcs = query.getResultList();
        for (News etc : etcs) {
            NewsInfo etci = new NewsInfo();
            etci.setId(etc.getId());
            etci.setAdddate(etc.getAdddate());
            etci.setStartdate(etc.getStartdate());
            etci.setStopdate(etc.getStopdate());
            etci.setName(etc.getName());
            etci.setDescription(etc.getDescription());
            ret.add(etci);
        }
        return ret;
    }

    @Override
    public NewsInfo getNew(Integer id) {
        NewsInfo ret = null;
        Query query = em.createNamedQuery("News.findById");
        query.setParameter("id", id);
        try {
            News etc = (News) query.getSingleResult();
            ret = new NewsInfo();
            ret.setId(etc.getId());
            ret.setAdddate(etc.getAdddate());
            ret.setStartdate(etc.getStartdate());
            ret.setStopdate(etc.getStopdate());
            ret.setName(etc.getName());
            ret.setDescription(etc.getDescription());
        } catch (NoResultException ex) {
        }
        return ret;
    }
    
    @Override
    public List<NewsInfo> getActualNews(Date actualdate) {
        List<NewsInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("News.findByDatesOrd");
        query.setParameter("actualdate", actualdate);
        List<News> etcs = query.getResultList();
        for (News etc : etcs) {
            NewsInfo etci = new NewsInfo();
            etci.setId(etc.getId());
            etci.setAdddate(etc.getAdddate());
            etci.setStartdate(etc.getStartdate());
            etci.setStopdate(etc.getStopdate());
            etci.setName(etc.getName());
            etci.setDescription(etc.getDescription());
            ret.add(etci);
        }
        return ret;
    }

    @Override
    public NewsInfo setNew(NewsInfo etcinfo) {
        NewsInfo ret = null;
        News etc = null;
        if (etcinfo.getId() != null) {
            Query query = em.createNamedQuery("News.findById");
            query.setParameter("id", etcinfo.getId());
            try {
                etc = (News) query.getSingleResult();
                etc.setAdddate(etcinfo.getAdddate());
                etc.setStartdate(etcinfo.getStartdate());
                etc.setStopdate(etcinfo.getStopdate());
                etc.setName(etcinfo.getName());
                etc.setDescription(etcinfo.getDescription());

                em.merge(etc);
                em.flush();
            } catch (NoResultException ex) {
            }
        }
        if (etc == null) {
            etc = new News();
            etc.setAdddate(etcinfo.getAdddate());
            etc.setStartdate(etcinfo.getStartdate());
            etc.setStopdate(etcinfo.getStopdate());
            etc.setName(etcinfo.getName());
            etc.setDescription(etcinfo.getDescription());
            em.persist(etc);
            em.flush();
        }
        ret = new NewsInfo();
        ret.setId(etc.getId());
        ret.setAdddate(etc.getAdddate());
        ret.setStartdate(etc.getStartdate());
        ret.setStopdate(etc.getStopdate());
        ret.setName(etc.getName());
        ret.setDescription(etc.getDescription());
        return ret;
    }
    @Override
    public String getEtcDescriptor() {
        //JsonArrayBuilder jabdsc = Json.createArrayBuilder();
        JsonObjectBuilder jobdsc = Json.createObjectBuilder();
        jobdsc.add("id", Json.createObjectBuilder()
                .add("name", "id")
                .add("title", "ID")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("key", Json.createObjectBuilder()
                .add("name", "key")
                .add("title", "Ключ")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("value", Json.createObjectBuilder()
                .add("name", "value")
                .add("title", "Значение")
                .add("type", "string")
                .add("iseditable", true));
        jobdsc.add("idcompany", Json.createObjectBuilder()
                .add("name", "idcompany")
                .add("title", "Компания")
                .add("type", "integer")
                .add("iseditable", false));
        jobdsc.add("iduser", Json.createObjectBuilder()
                .add("name", "iduser")
                .add("title", "Пользователь")
                .add("type", "integer")
                .add("iseditable", true));
        return jobdsc.build().toString();
    }
}
