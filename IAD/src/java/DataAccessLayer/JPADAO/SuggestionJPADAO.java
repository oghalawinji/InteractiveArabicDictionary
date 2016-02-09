/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Suggestion;

/**
 *
 * @author Omar
 */
public class SuggestionJPADAO extends GenericJPADAO<Suggestion> {

    public SuggestionJPADAO() {
        super(Suggestion.class);
    }

}