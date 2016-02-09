/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.JPADAO;

import PersistenceLayer.Entrycommonmistake;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Omar
 */
public class EntrycommonmistakeJPADAO extends GenericJPADAO<Entrycommonmistake> {

    public EntrycommonmistakeJPADAO() {
        super(Entrycommonmistake.class);
    }

    public List<Entrycommonmistake> getNotDeletedInstance(Entrycommonmistake newEntrycommonmistake, Map restrictions3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}