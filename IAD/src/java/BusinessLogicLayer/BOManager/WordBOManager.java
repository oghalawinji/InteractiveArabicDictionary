package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BOManager.Util.EntryExistedException;
import BusinessLogicLayer.BusinessObjects.WordBO;
import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.RawwordJPADAO;
import PersistenceLayer.Rawword;
import Util.RawNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class WordBOManager {

    public static int suggestAdding(WordBO wordBO) {
        RawwordJPADAO dao = BLUtil.daoFactory.getRawwordDAO();
        Rawword newRawWord = new Rawword(wordBO.getRawWord());
        newRawWord.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
        newRawWord.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
        List<Rawword> existed = (dao.getByExample(newRawWord));
        if (existed.size() == 0) {
            return dao.insert(newRawWord);
        } else {
            return existed.get(0).getIdentity();
        }

    }

    public static int suggestAdding(String word) {
        try {
            RawwordJPADAO dao = BLUtil.daoFactory.getRawwordDAO();
            Rawword newRawWord = new Rawword(word);
            newRawWord.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            newRawWord.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            newRawWord.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getRawwordDAO().insertWithCheck(newRawWord, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(WordBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public static int suggestAdding(Rawword newRawWord) {
        try {
            newRawWord.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            newRawWord.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            newRawWord.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getRawwordDAO().insertWithCheck(newRawWord, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(WordBOManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
