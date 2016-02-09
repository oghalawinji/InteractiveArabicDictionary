/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Specialization;

/**
 *
 * @author Omar
 */
public class SpecializationJPADAO extends GenericJPADAO<Specialization> {

    public SpecializationJPADAO() {
        super(Specialization.class);
    }

}