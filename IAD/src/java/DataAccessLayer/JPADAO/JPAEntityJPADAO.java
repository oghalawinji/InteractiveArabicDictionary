/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.JPAEntity;

/**
 *
 * @author Omar
 */
public class JPAEntityJPADAO extends GenericJPADAO<JPAEntity> {

    public JPAEntityJPADAO() {
        super(JPAEntity.class);
    }

}