package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.EpochJPADAO;
import PersistenceLayer.Epoch;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class EpochManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        EpochJPADAO dao = BLUtil.daoFactory.getEpochDAO();
        List<Epoch> transObjs = dao.getAll();
        for (int i = 0; i < transObjs.size(); i++) {
            String trans = transObjs.get(i).getEpoch();
            results.add(trans);
        }
        return results;
    }

    public String get(Integer id, String mode) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String get(Epoch seed, String mode) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestDeleting(Integer Id) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean suggestDeleting(Epoch root) throws RawNotFoundException {
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

    public static int suggestAdding(String epoch) {
        try {
            EpochJPADAO dao = BLUtil.daoFactory.getEpochDAO();
            Epoch newEpoch = new Epoch(epoch);
            newEpoch.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            newEpoch.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            newEpoch.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getEpochDAO().insertWithCheck(newEpoch, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(EpochManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    static int add(String epoch) {
        try {
            EpochJPADAO dao = BLUtil.daoFactory.getEpochDAO();
            Epoch newEpoch = new Epoch(epoch);
            newEpoch.setChechStatus(WordStatus.NOT_NEED_CHECK_STATUS);
            newEpoch.setInfoStatus(WordStatus.CONFIRMED_INFO_STATUS);
            newEpoch.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getEpochDAO().insertWithCheck(newEpoch, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(EpochManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public boolean affirmAddinging(String bObj) throws RawNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
