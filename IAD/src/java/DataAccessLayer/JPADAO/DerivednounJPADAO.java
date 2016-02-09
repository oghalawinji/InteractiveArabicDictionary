/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Derivednoun;
import java.util.List;

/**
 *
 * @author Omar
 */
public class DerivednounJPADAO extends GenericJPADAO<Derivednoun> {

    public DerivednounJPADAO() {
        super(Derivednoun.class);
    }

    public List<Derivednoun> getNeedCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}