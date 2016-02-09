/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Exaggeration;

/**
 *
 * @author Omar
 */
public class ExaggerationJPADAO extends GenericJPADAO<Exaggeration> {

    public ExaggerationJPADAO() {
        super(Exaggeration.class);
    }

}