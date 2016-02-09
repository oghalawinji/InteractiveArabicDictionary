/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Annexednoun;
import PersistenceLayer.Semanticrelation;
import PersistenceLayer.SemanticrelationPK;
import PersistenceLayer.Semanticscop;
import Util.RawNotFoundException;
import java.lang.reflect.Field;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author Omar
 */
public class Tester {

    public static void main(String[] args) throws RawNotFoundException {
        Semanticscop scope = new SemanticscopJPADAO().getById(62);
        Semanticrelation rel = new Semanticrelation();
        rel.setSemanticscopByFirstSemanticScopId(scope);
        SemanticrelationJPADAO dao = new SemanticrelationJPADAO();
        System.out.println(dao.getByExample(rel));
    }

}
