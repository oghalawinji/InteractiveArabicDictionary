/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Difficultydegree;

/**
 *
 * @author Omar
 */
public class DifficultydegreeJPADAO extends GenericJPADAO<Difficultydegree> {

    public DifficultydegreeJPADAO() {
        super(Difficultydegree.class);
    }

}