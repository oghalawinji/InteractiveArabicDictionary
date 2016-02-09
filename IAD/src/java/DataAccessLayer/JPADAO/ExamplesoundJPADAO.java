/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Examplesound;

/**
 *
 * @author Omar
 */
public class ExamplesoundJPADAO extends GenericJPADAO<Examplesound> {

    public ExamplesoundJPADAO() {
        super(Examplesound.class);
    }

}