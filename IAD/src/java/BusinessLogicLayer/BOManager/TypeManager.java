/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
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
public class TypeManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        TypeJPADAO dao = BLUtil.daoFactory.getTypeDAO();
        List<Type> typeObjs = dao.getAll();
        for (int i = 0; i < typeObjs.size(); i++) {
            String type = typeObjs.get(i).getType();
            results.add(type);
        }
        return results;
    }

    public static void deleteType(Integer typeId) {
        try {
            TypeJPADAO dao = BLUtil.daoFactory.getTypeDAO();
            dao.delete(typeId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(TypeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int suggestAdding(String type) throws RawNotFoundException {
        Type newType = new Type(type);
        newType.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newType.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newType.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        return BLUtil.daoFactory.getTypeDAO().insertWithCheck(newType, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
    }
}
