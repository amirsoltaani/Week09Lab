package database;

import models.User;
import java.util.List;
import javax.persistence.*;

public class UserDB {

    public int insert(User user) throws NotesDBException {
       
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        int result = 0;
        
        try {
            trans.begin();
            em.persist(user);
            result = 1;
            trans.commit();
            
        } catch(Exception e){
            result = 0;
            trans.rollback();
        }finally {
            em.close();
            return result;
        }
        
    }

    public User update(User user) throws NotesDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        User result = null;
        
        try {
            trans.begin();
            result = em.merge(user);
            trans.commit();
            
        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
            
        }
        return result;
    }

    public List<User> getAll() throws NotesDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<User> users = em.createNamedQuery("Users.findAll",User.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }


    public User getUser(String username) throws NotesDBException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
           User user = em.find(User.class, username);
           return user;
        }  finally {
            em.close();
        }
    }

    public int delete(User user) throws Exception {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        int result = 0;

        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
            result = 1;
        } catch (Exception e) {
            trans.rollback();
            result = 0;
        } finally {
           em.close();
        }
        return result;
    }
}
