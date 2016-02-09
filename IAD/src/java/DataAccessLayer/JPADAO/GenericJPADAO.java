/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import DataAccessLayer.GenericDAO;
import PersistenceLayer.JPAEntity;
import PersistenceLayer.PersistencyConfig;
import Util.RawNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.querybyexample.jpa.GenericRepository;

/**
 *
 * @author Omar
 * @param <T>
 */
public class GenericJPADAO<T extends JPAEntity> implements GenericDAO<T> {

    protected final EntityManager em;

    private final Class<T> type;

    public GenericJPADAO(Class<T> type) {
        this.type = type;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PersistencyConfig.PERSISTANCE_UNIT_NAME);
        em = emf.createEntityManager();
    }

    @Override
    public T getById(Integer id) throws RawNotFoundException {
        T result = (T) em.find(type, id);
        if (result == null) {
            throw new RawNotFoundException(type.getSimpleName() + " Id " + id + " is NOT found.");
        }
        return result;

    }

    @Override
    public List<T> getByExample(T exampleInstance, String[] excludeProperty) {
        exampleInstance.execludeGeneralProperties();
        GenericRepository<T, Integer> gr = new GenericRepository<T, Integer>(type) {
            @Override
            public T getNew() {
                try {
                    return type.newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(GenericJPADAO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GenericJPADAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        };
        return gr.find(exampleInstance);
    }

    @Override
    public List<T> getByExample(T exampleInstance, String[] excludeProperty, Map restrictions) {
        return this.getByExample(exampleInstance);
    }

    @Override
    public List<T> getByExample(T exampleInstance) {
        return getByExample(exampleInstance, new String[0]);
    }

    @Override
    public List<T> getConfirmedInstance(T exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> getAddedInstance(T exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> getDeletedInstance(T exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> getTempInstance(T exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> getUpdatedInstance(T exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // <editor-fold defaultstate="collapsed" desc=" INSERT/UPDATE/DELETE ">
    @Override
    public Integer insert(T newInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(T newInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(T newInstance, String[] excludeProperties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(T newInstance, String[] excludeProperties, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(T newInstance, String[] excludeProperties, Map restrictions, String message) throws EntryExistedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(T transientObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

// </editor-fold>
    @Override
    public List<T> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public void clearSession() {
        throw new UnsupportedOperationException("Clear Session is Hibernate-Dependant");
    }

    public List<T> getNeedCheck() {
        throw new UnsupportedOperationException("getNeedCheck()");
    }

}
