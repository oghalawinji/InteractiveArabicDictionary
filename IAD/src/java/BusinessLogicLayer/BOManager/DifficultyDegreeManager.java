package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.DifficultydegreeJPADAO;
import PersistenceLayer.Difficultydegree;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class DifficultyDegreeManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        DifficultydegreeJPADAO dao = BLUtil.daoFactory.getDifficultydegreeDAO();
        List<Difficultydegree> transObjs = dao.getAll();
        for (int i = 0; i < transObjs.size(); i++) {
            String trans = transObjs.get(i).getDifficultyDegree();
            results.add(trans);
        }
        return results;
    }

    public static int suggestAdding(String difficultydegree) {
        try {
            DifficultydegreeJPADAO dao = BLUtil.daoFactory.getDifficultydegreeDAO();
            Difficultydegree newDifficultydegree = new Difficultydegree(difficultydegree);
            newDifficultydegree.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            newDifficultydegree.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            newDifficultydegree.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getDifficultydegreeDAO().insertWithCheck(newDifficultydegree, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(DifficultyDegreeManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    static int add(String difficultydegree) {
        try {
            DifficultydegreeJPADAO dao = BLUtil.daoFactory.getDifficultydegreeDAO();
            Difficultydegree newDifficultydegree = new Difficultydegree(difficultydegree);
            newDifficultydegree.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            newDifficultydegree.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newDifficultydegree.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getDifficultydegreeDAO().insertWithCheck(newDifficultydegree, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(DifficultyDegreeManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public String get(Integer id, String mode) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String get(Difficultydegree dataAccessObject, String mode) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestDeleting(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestDeleting(Difficultydegree dataAccessObject) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean affirmDeleting(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestUpdating(Integer Id, String newObj) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean affirmUpdating(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean affirmAddinging(String attribute) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
