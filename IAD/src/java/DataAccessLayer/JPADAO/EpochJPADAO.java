/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Epoch;

/**
 *
 * @author Omar
 */
public class EpochJPADAO extends GenericJPADAO<Epoch> {

    public EpochJPADAO() {
        super(Epoch.class);
    }

}