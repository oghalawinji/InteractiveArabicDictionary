/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Entrylinguisticbenefit;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Omar
 */
public class EntrylinguisticbenefitJPADAO extends GenericJPADAO<Entrylinguisticbenefit> {

    public EntrylinguisticbenefitJPADAO() {
        super(Entrylinguisticbenefit.class);
    }

    public List<Entrylinguisticbenefit> getNotDeletedInstance(Entrylinguisticbenefit newEntrylinguisticbenefit, Map restrictions3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}