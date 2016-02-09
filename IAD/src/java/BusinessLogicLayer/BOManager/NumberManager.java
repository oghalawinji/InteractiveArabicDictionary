/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.NumberJPADAO;
import PersistenceLayer.Number;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class NumberManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        NumberJPADAO dao = BLUtil.daoFactory.getNumberDAO();
        List<Number> transObjs = dao.getAll();
        for (int i = 0; i < transObjs.size(); i++) {
            String trans = transObjs.get(i).getNumber();
            results.add(trans);
        }
        return results;

    }

    public List<String> getAllNumbers() {
        List<String> results = new ArrayList<String>();
        NumberJPADAO dao = BLUtil.daoFactory.getNumberDAO();
        List<Number> numberObjs = dao.getAll();
        for (int i = 0; i < numberObjs.size(); i++) {
            String number = numberObjs.get(i).getNumber();
            results.add(number);
        }
        return results;
    }

    public static void deleteNumber(Integer numberId) {
        try {
            NumberJPADAO dao = BLUtil.daoFactory.getNumberDAO();
            dao.delete(numberId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(NumberManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int suggestAdding(String number) throws RawNotFoundException {
        Number newNumber = new Number(number);
        newNumber.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newNumber.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        newNumber.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
        return BLUtil.daoFactory.getNumberDAO().insertWithCheck(newNumber, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
    }
}
