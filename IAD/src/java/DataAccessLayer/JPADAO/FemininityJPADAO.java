/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Femininity;

/**
 *
 * @author Omar
 */
public class FemininityJPADAO extends GenericJPADAO<Femininity> {

    public FemininityJPADAO() {
        super(Femininity.class);
    }

}