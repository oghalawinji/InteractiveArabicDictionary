/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Suffix;

/**
 *
 * @author Omar
 */
public class SuffixJPADAO extends GenericJPADAO<Suffix> {

    public SuffixJPADAO() {
        super(Suffix.class);
    }

}