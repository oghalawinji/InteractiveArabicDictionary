/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceLayer;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.querybyexample.jpa.GenericRepository;

/**
 *
 * @author Omar
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory(PersistencyConfig.PERSISTANCE_UNIT_NAME).createEntityManager();
        Semanticrelation rel = em.find(Semanticrelation.class, new SemanticrelationPK(12, 62, 1));
        System.out.println(rel);
    }

}
