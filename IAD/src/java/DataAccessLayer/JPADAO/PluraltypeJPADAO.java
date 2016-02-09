/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Pluraltype;

/**
 *
 * @author Omar
 */
public class PluraltypeJPADAO extends GenericJPADAO<Pluraltype> {

    public PluraltypeJPADAO() {
        super(Pluraltype.class);
    }

}