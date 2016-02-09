/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Root;
import PersistenceLayer.Root_;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Omar
 */
public class RootJPADAO extends GenericJPADAO<Root> {

    public RootJPADAO() {
        super(Root.class);
    }

    public List<Root> getRootsLike(String root) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Root> criteria = builder.createQuery(Root.class);
        javax.persistence.criteria.Root<Root> rootRoot = criteria.from(Root.class);
        criteria.where(builder.like(rootRoot.get(Root_.root), root));
        TypedQuery<Root> query = em.createQuery(criteria);
        return query.getResultList();
    }

}
