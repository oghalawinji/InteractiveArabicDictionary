/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.TypeJPADAO;
import PersistenceLayer.Type;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class NounTypeManager {

    public List<String> getAllNounTypes() {
        List<String> results = new ArrayList<String>();
        TypeJPADAO dao = BLUtil.daoFactory.getNountypeDAO();
        List<Type> nounTypeObjs = dao.getAll();
        for (int i = 0; i < nounTypeObjs.size(); i++) {
            String type = nounTypeObjs.get(i).getType();
            results.add(type);
        }
        return results;
    }

    public static void deleteNounType(Integer typeId) {
        try {
            TypeJPADAO dao = BLUtil.daoFactory.getNountypeDAO();
            dao.delete(typeId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(NounTypeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
