/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Role;

/**
 *
 * @author Omar
 */
public class RoleJPADAO extends GenericJPADAO<Role> {

    public RoleJPADAO() {
        super(Role.class);
    }

}