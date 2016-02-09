/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Idiom;
import PersistenceLayer.Idiom_;
import PersistenceLayer.Rawword;
import PersistenceLayer.Rawword_;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Omar
 */
public class IdiomJPADAO extends GenericJPADAO<Idiom> {

    public IdiomJPADAO() {
        super(Idiom.class);
    }

    public List<Idiom> getIdiomsLike(String idiom) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Idiom> criteria = builder.createQuery(Idiom.class);
        Root<Idiom> idiomRoot = criteria.from(Idiom.class);
        criteria.where(builder.like(idiomRoot.get(Idiom_.idiom), idiom));
        TypedQuery<Idiom> query = em.createQuery(criteria);
        return query.getResultList();
    }

}
