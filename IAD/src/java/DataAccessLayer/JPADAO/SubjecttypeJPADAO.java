/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Subjecttype;

/**
 *
 * @author Omar
 */
public class SubjecttypeJPADAO extends GenericJPADAO<Subjecttype> {

    public SubjecttypeJPADAO() {
        super(Subjecttype.class);
    }

}