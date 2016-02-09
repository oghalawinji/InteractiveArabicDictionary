/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Meaning;

/**
 *
 * @author Omar
 */
public class MeaningJPADAO extends GenericJPADAO<Meaning> {

    public MeaningJPADAO() {
        super(Meaning.class);
    }

}