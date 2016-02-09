/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Entrysound;

/**
 *
 * @author Omar
 */
public class EntrysoundJPADAO extends GenericJPADAO<Entrysound> {

    public EntrysoundJPADAO() {
        super(Entrysound.class);
    }

}