/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Pattern;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author Omar
 */
public class PatternJPADAO extends GenericJPADAO<Pattern> {

    public PatternJPADAO() {
        super(Pattern.class);
    }

    public List<String> getAllVerbPatterns() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Query q = em.createQuery("SELECT distinct P.pattern FROM Derivedverb AS D inner join D.pattern as P");
        return q.getResultList();
    }

    public List<String> getAllNounPatterns() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        Query q = em.createQuery("SELECT distinct P.pattern FROM Derivednoun AS D inner join D.pattern as P");
        return q.getResultList();
    }

}
