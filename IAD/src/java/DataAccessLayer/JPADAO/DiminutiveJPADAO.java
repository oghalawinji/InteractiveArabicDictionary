/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Diminutive;

/**
 *
 * @author Omar
 */
public class DiminutiveJPADAO extends GenericJPADAO<Diminutive> {

    public DiminutiveJPADAO() {
        super(Diminutive.class);
    }

}