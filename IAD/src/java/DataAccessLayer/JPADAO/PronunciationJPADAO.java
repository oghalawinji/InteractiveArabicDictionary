/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Pronunciation;

/**
 *
 * @author Omar
 */
public class PronunciationJPADAO extends GenericJPADAO<Pronunciation> {

    public PronunciationJPADAO() {
        super(Pronunciation.class);
    }

}