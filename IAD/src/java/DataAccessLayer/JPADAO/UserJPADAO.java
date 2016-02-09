/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.User;

/**
 *
 * @author Omar
 */
public class UserJPADAO extends GenericJPADAO<User> {

    public UserJPADAO() {
        super(User.class);
    }

}