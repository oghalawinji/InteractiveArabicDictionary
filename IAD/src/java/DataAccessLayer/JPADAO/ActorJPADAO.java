/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Actor;

/**
 *
 * @author Omar
 */
public class ActorJPADAO extends GenericJPADAO<Actor> {

    public ActorJPADAO() {
        super(Actor.class);
    }

}