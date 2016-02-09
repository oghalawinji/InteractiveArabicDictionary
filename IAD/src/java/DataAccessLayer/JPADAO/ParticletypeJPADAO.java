/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Particletype;

/**
 *
 * @author Omar
 */
public class ParticletypeJPADAO extends GenericJPADAO<Particletype> {

    public ParticletypeJPADAO() {
        super(Particletype.class);
    }

}