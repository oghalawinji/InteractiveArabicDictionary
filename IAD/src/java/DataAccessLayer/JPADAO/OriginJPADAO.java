/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Origin;

/**
 *
 * @author Omar
 */
public class OriginJPADAO extends GenericJPADAO<Origin> {

    public OriginJPADAO() {
        super(Origin.class);
    }

}