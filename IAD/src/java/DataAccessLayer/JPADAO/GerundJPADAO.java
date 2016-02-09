/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Gerund;

/**
 *
 * @author Omar
 */
public class GerundJPADAO extends GenericJPADAO<Gerund> {

    public GerundJPADAO() {
        super(Gerund.class);
    }

}