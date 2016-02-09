/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Source;

/**
 *
 * @author Omar
 */
public class SourceJPADAO extends GenericJPADAO<Source> {

    public SourceJPADAO() {
        super(Source.class);
    }

}