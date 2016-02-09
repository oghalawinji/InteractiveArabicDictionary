/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Sound;

/**
 *
 * @author Omar
 */
public class SoundJPADAO extends GenericJPADAO<Sound> {

    public SoundJPADAO() {
        super(Sound.class);
    }

}