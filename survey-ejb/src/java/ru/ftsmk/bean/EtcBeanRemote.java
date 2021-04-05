/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import ru.ftsmk.info.EtcInfo;
import ru.ftsmk.info.NewsInfo;

/**
 *
 * @author plintus
 */
@Remote
public interface EtcBeanRemote {
    public List<EtcInfo> getEtcs();
    public EtcInfo getEtc(Integer id);
    public List<EtcInfo> getEtcs(String key);
    public EtcInfo setEtc(EtcInfo etc);
    public EtcInfo getEtcByKey(String key, Integer idcompany, Integer idusers);
    ///News
    
    public List<NewsInfo> getNews();
    public NewsInfo getNew(Integer id);
    public NewsInfo setNew(NewsInfo etcinfo);
    public List<NewsInfo> getActualNews(Date actualdate);
    //descriptors    
    public String getEtcDescriptor();
}
