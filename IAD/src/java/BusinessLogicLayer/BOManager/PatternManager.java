/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.CompareWordsBO;
import DataAccessLayer.JPADAO.PatternJPADAO;
import PersistenceLayer.Pattern;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class PatternManager {

    public static List<String> getAll() {
        List<String> results = new ArrayList<String>();
        PatternJPADAO dao = BLUtil.daoFactory.getPatternDAO();
        List<Pattern> patternObjs = dao.getAll();
        for (int i = 0; i < patternObjs.size(); i++) {
            String pattern = patternObjs.get(i).getPattern();
            results.add(pattern);
        }
        Collections.sort(results, new CompareWordsBO());
        return results;
    }

    public static List<String> getAllVerbPatterns() {
        List<String> patterns = BLUtil.daoFactory.getPatternDAO().getAllVerbPatterns();
        Collections.sort(patterns, new CompareWordsBO());
        return patterns;
    }

    public static void deletePattern(Integer patternId) {
        try {
            PatternJPADAO dao = BLUtil.daoFactory.getPatternDAO();
            dao.delete(patternId);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(PatternManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<String> getAllNounPatterns() {
        List<String> patterns = BLUtil.daoFactory.getPatternDAO().getAllNounPatterns();
        Collections.sort(patterns, new CompareWordsBO());
        return patterns;
    }

    public static int suggestAdding(String pattern) {
        try {
            Pattern newPattern = new Pattern(pattern);
            newPattern.setChechStatus(BOManagerUtil.ADDING_STATUS.getCheckStatus());
            newPattern.setInfoStatus(BOManagerUtil.ADDING_STATUS.getInfoStatus());
            newPattern.setSuggestion(BOManagerUtil.GET_ADD_SUGGESTION());
            return BLUtil.daoFactory.getPatternDAO().insertWithCheck(newPattern, BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES, BOManagerUtil.getAddRestrictions());
        } catch (RawNotFoundException ex) {
            Logger.getLogger(PatternManager.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
