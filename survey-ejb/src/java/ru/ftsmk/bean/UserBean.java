/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.bean;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import ru.ftsmk.db.Users;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
@Stateless
public class UserBean implements UserBeanRemote {

    @PersistenceContext(unitName = "survey-ejbPU")
    private EntityManager em;

    @Override
    public List<UsersInfo> getUsers() {
        List<UsersInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Users.findAll");
        List<Users> usrs = query.getResultList();
        for (Users usr : usrs) {
            UsersInfo ui = new UsersInfo();
            ui.setId(usr.getId());
            ui.setEmail(usr.getEmail());
            ui.setLogin(usr.getLogin());
            ui.setName(usr.getName());
            ui.setPhone(usr.getPhone());
            ui.setRole(usr.getRole());
            ui.setSecret(usr.getSecret());
            ui.setIdcompany(usr.getIdcompany());
            ret.add(ui);
        }
        return ret;
    }
    @Override
    public List<UsersInfo> getUsers(Integer idcompany) {
        List<UsersInfo> ret = new ArrayList();
        Query query = em.createNamedQuery("Users.findByIdCompany");
        query.setParameter("idcompany", idcompany);
        List<Users> usrs = query.getResultList();
        for (Users usr : usrs) {
            UsersInfo ui = new UsersInfo();
            ui.setId(usr.getId());
            ui.setEmail(usr.getEmail());
            ui.setLogin(usr.getLogin());
            ui.setName(usr.getName());
            ui.setPhone(usr.getPhone());
            ui.setRole(usr.getRole());
            ui.setSecret(usr.getSecret());
            ui.setIdcompany(usr.getIdcompany());
            ret.add(ui);
        }
        return ret;
    }

    @Override
    public UsersInfo getUser(Integer id) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findById");
        query.setParameter("id", id);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public UsersInfo getUserByLogin(String login) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", login);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }
    @Override
    public UsersInfo getUser(Integer iduser, Integer idcompany) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findByIdIdCompany");
        query.setParameter("id", iduser);
        query.setParameter("idcompany", idcompany);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }
    @Override
    public UsersInfo getUser(String login, Integer idcompany) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findByLoginIdCompany");
        query.setParameter("login", login);
        query.setParameter("idcompany", idcompany);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public UsersInfo getUserByName(String name) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findByName");
        query.setParameter("name", name);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public UsersInfo getUserByEmail(String email) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findByEmail");
        query.setParameter("email", email);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public UsersInfo getUserByPhone(String phone) {
        UsersInfo ret = null;
        Query query = em.createNamedQuery("Users.findByPhone");
        query.setParameter("phone", phone);
        try {
            Users usr = (Users) query.getSingleResult();
            ret = new UsersInfo();
            ret.setId(usr.getId());
            ret.setEmail(usr.getEmail());
            ret.setLogin(usr.getLogin());
            ret.setName(usr.getName());
            ret.setPhone(usr.getPhone());
            ret.setRole(usr.getRole());
            ret.setSecret(usr.getSecret());
            ret.setIdcompany(usr.getIdcompany());
        } catch (NoResultException ex) {
        }
        return ret;
    }

    @Override
    public UsersInfo addUser(UsersInfo userinfo) {
        UsersInfo ret = null;
        Users usr = new Users();
        usr.setEmail(userinfo.getEmail());
        usr.setLogin(userinfo.getLogin());
        usr.setName(userinfo.getName());
        usr.setPhone(userinfo.getPhone());
        usr.setRole(userinfo.getRole());
        if (userinfo.getNewsecret() != null) {
            usr.setSecret(DigestUtils.md5Hex(userinfo.getNewsecret()));
        } else {
            String newsecret = RandomStringUtils.random(8, "QWERTYUIPLKJHGFDSAZXCVBNMmnbvcxzasdfghjklpiuytrewq123456789");
            usr.setSecret(DigestUtils.md5Hex(newsecret));
        }
        usr.setEmail(userinfo.getEmail());
        usr.setIdcompany(userinfo.getIdcompany());

        em.persist(usr);
        em.flush();

        ret = new UsersInfo();
        ret.setId(usr.getId());
        ret.setEmail(usr.getEmail());
        ret.setLogin(usr.getLogin());
        ret.setName(usr.getName());
        ret.setPhone(usr.getPhone());
        ret.setRole(usr.getRole());
        ret.setSecret(usr.getSecret());
        ret.setIdcompany(usr.getIdcompany());
        return ret;
    }

    @Override
    public UsersInfo setUser(UsersInfo userinfo) {
        UsersInfo ret = null;
        Users usr = null;
        if (userinfo.getId() != null) {
            Query query = em.createNamedQuery("Users.findById");
            query.setParameter("id", userinfo.getId());
            try {
                usr = (Users) query.getSingleResult();
                usr.setEmail(userinfo.getEmail());
                usr.setLogin(userinfo.getLogin());
                usr.setName(userinfo.getName());
                usr.setPhone(userinfo.getPhone());
                if (userinfo.getNewsecret() != null) {
                    usr.setSecret(DigestUtils.md5Hex(userinfo.getNewsecret()));
                }
                usr.setEmail(userinfo.getEmail());
                usr.setIdcompany(userinfo.getIdcompany());
                em.merge(usr);
                em.flush();

            } catch (NoResultException ex) {
            }
        }
        if (usr == null) {
            usr = new Users();
            usr.setEmail(userinfo.getEmail());
            usr.setLogin(userinfo.getLogin());
            usr.setName(userinfo.getName());
            usr.setPhone(userinfo.getPhone());
            if (userinfo.getNewsecret() != null) {
                usr.setSecret(DigestUtils.md5Hex(userinfo.getNewsecret()));
            } else {
                String newsecret = RandomStringUtils.random(8, "QWERTYUIPLKJHGFDSAZXCVBNMmnbvcxzasdfghjklpiuytrewq123456789");
                usr.setSecret(DigestUtils.md5Hex(newsecret));
            }
            usr.setEmail(userinfo.getEmail());
            usr.setIdcompany(userinfo.getIdcompany());
            em.persist(usr);
            em.flush();
        }
        ret = new UsersInfo();
        ret.setId(usr.getId());
        ret.setEmail(usr.getEmail());
        ret.setLogin(usr.getLogin());
        ret.setName(usr.getName());
        ret.setPhone(usr.getPhone());
        ret.setRole(usr.getRole());
        ret.setSecret(usr.getSecret());
        ret.setIdcompany(usr.getIdcompany());
        return ret;
    }

}
