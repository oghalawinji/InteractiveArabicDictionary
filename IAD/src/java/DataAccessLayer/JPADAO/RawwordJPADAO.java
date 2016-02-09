/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Rawword;
import PersistenceLayer.Rawword_;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.kohsuke.rngom.digested.Main;

/**
 *
 * @author Omar
 */
public class RawwordJPADAO extends GenericJPADAO<Rawword> {

    public RawwordJPADAO() {
        super(Rawword.class);
    }

    public List<Rawword> getWordsLike(String filteredNoun) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Rawword> criteria = builder.createQuery(Rawword.class);
        Root<Rawword> rawRoot = criteria.from(Rawword.class);
        criteria.where(builder.like(rawRoot.get(Rawword_.rawWord), filteredNoun));
        TypedQuery<Rawword> query = em.createQuery(criteria);
        return query.getResultList();
    }

    public static void main(String[] args) {
        RawwordJPADAO dao = new RawwordJPADAO();
        System.out.println(dao.getWordsLike("لعب"));
    }

}
