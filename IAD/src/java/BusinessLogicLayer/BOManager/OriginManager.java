/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.OriginJPADAO;
import PersistenceLayer.Origin;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class OriginManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        OriginJPADAO dao = BLUtil.daoFactory.getOriginDAO();
        List<Origin> originObjs = dao.getAll();
        for (int i = 0; i < originObjs.size(); i++) {
            String origin = originObjs.get(i).getOrigin();
            results.add(origin);
        }
        return results;
    }

    public static void deleteOrigin(Integer originId) {
        try {
            OriginJPADAO dao = BLUtil.daoFactory.getOriginDAO();
            dao.delete(originId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(OriginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int suggestAdding(String origin) throws RawNotFoundException {
        Origin newOrigin = new Origin(origin);
        newOrigin.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newOrigin.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newOrigin.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        return BLUtil.daoFactory.getOriginDAO().insertWithCheck(newOrigin, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
    }
}
