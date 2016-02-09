/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Semanticentry;

/**
 *
 * @author Omar
 */
public class SemanticentryJPADAO extends GenericJPADAO<Semanticentry> {

    public SemanticentryJPADAO() {
        super(Semanticentry.class);
    }

}