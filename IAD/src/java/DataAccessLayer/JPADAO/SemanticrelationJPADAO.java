/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import DataAccessLayer.GenericDAO;
import PersistenceLayer.PersistencyConfig;
import PersistenceLayer.Semanticrelation;
import PersistenceLayer.SemanticrelationPK;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.querybyexample.jpa.GenericRepository;
import org.querybyexample.jpa.SearchParameters;

/**
 *
 * @author Omar
 */
public class SemanticrelationJPADAO implements GenericDAO<Semanticrelation> {

    protected final EntityManager em;
    private final Class<Semanticrelation> type;

    public SemanticrelationJPADAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PersistencyConfig.PERSISTANCE_UNIT_NAME);
        em = emf.createEntityManager();
        type = Semanticrelation.class;
    }

    @Override
    public List<Semanticrelation> getByExample(Semanticrelation exampleInstance, String[] excludeProperty) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Semanticrelation> query = builder.createQuery(type);
        Root<Semanticrelation> relationRoot = query.from(type);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (exampleInstance.getSemanticscopByFirstSemanticScopId() != null) {
            predicates.add(builder.equal(relationRoot.get("semanticrelationPK").get("firstSemanticScopId"), exampleInstance.getSemanticscopByFirstSemanticScopId().getIdentity()));
        }
        if (exampleInstance.getSemanticscopBySecondSemanticScopId() != null) {
            predicates.add(builder.equal(relationRoot.get("semanticrelationPK").get("secondSemanticScopId"), exampleInstance.getSemanticscopBySecondSemanticScopId().getIdentity()));
        }
        if (exampleInstance.getSemanticrelationtype() != null) {
            predicates.add(builder.equal(relationRoot.get("semanticrelationPK").get("semanticRelationTypeId"), exampleInstance.getSemanticrelationtype().getIdentity()));
        }
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        query.select(relationRoot);
        TypedQuery<Semanticrelation> tq = em.createQuery(query);
        return tq.getResultList();
    }

    @Override
    public Semanticrelation getById(Integer id) throws RawNotFoundException {
        Semanticrelation result = (Semanticrelation) em.find(type, id);
        if (result == null) {
            throw new RawNotFoundException(type.getSimpleName() + " Id " + id + " is NOT found.");
        }
        return result;
    }

    @Override
    public List<Semanticrelation> getByExample(Semanticrelation exampleInstance, String[] excludeProperty, Map restrictions) {
        return this.getByExample(exampleInstance);
    }

    @Override
    public List<Semanticrelation> getByExample(Semanticrelation exampleInstance) {
        return getByExample(exampleInstance, new String[0]);
    }

    @Override
    public List<Semanticrelation> getConfirmedInstance(Semanticrelation exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Semanticrelation> getAddedInstance(Semanticrelation exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Semanticrelation> getDeletedInstance(Semanticrelation exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Semanticrelation> getTempInstance(Semanticrelation exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Semanticrelation> getUpdatedInstance(Semanticrelation exampleInstance, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer insert(Semanticrelation newInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(Semanticrelation newInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(Semanticrelation newInstance, String[] excludeProperties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(Semanticrelation newInstance, String[] excludeProperties, Map restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertWithCheck(Semanticrelation newInstance, String[] excludeProperties, Map restrictions, String message) throws EntryExistedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Semanticrelation transientObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Semanticrelation> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
