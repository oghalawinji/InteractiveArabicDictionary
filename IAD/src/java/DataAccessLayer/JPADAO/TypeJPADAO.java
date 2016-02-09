/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Type;

/**
 *
 * @author Omar
 */
public class TypeJPADAO extends GenericJPADAO<Type> {

    public TypeJPADAO() {
        super(Type.class);
    }

}