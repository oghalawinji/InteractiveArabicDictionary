package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.TransitivitycaseJPADAO;
import PersistenceLayer.Transitivitycase;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class TransitivityCasesManager
{

    public static List<String> getAll()
    {
        List<String> results = new ArrayList<String>();
        TransitivitycaseJPADAO dao = BLUtil.daoFactory.getTransitivitycaseDAO();
        List<Transitivitycase> transObjs = dao.getAll();
        for ( int i = 0 ; i < transObjs.size() ; i ++ )
        {
            String trans = transObjs.get( i ).getTransitivityCase();
            results.add( trans );
        }
        return results;
    }

    public static int suggestAdding( String transitivityCase ) throws RawNotFoundException
    {
        Transitivitycase transitivitycaseObj = new Transitivitycase();
        transitivitycaseObj.setTransitivityCase( transitivityCase );
        transitivitycaseObj.setChechStatus( BOManagerUtil.ADDING_STATUS.getCheckStatus() );
        transitivitycaseObj.setInfoStatus( BOManagerUtil.ADDING_STATUS.getInfoStatus() );
        transitivitycaseObj.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
        return BLUtil.daoFactory.getTransitivitycaseDAO().insertWithCheck( transitivitycaseObj , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
    }

    static void affirmDeleting( int transitivityCaseId ) throws RawNotFoundException
    {
        Transitivitycase newTransitivitycase = BLUtil.daoFactory.getTransitivitycaseDAO().getById( transitivityCaseId );
        if ( newTransitivitycase.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newTransitivitycase.setInfoStatus( WordStatus.NEED_DELETING );
            newTransitivitycase.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newTransitivitycase.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newTransitivitycase.getSuggestion() );
            }
            BLUtil.daoFactory.getTransitivitycaseDAO().update( newTransitivitycase );
        }
    }

    static void rejectDeleting( int transtivityCaseId ) throws RawNotFoundException
    {
        Transitivitycase newTransitivitycase = BLUtil.daoFactory.getTransitivitycaseDAO().getById( transtivityCaseId );
        if ( newTransitivitycase.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newTransitivitycase.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newTransitivitycase.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newTransitivitycase.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newTransitivitycase.getSuggestion() );
            }
            BLUtil.daoFactory.getTransitivitycaseDAO().update(newTransitivitycase );
        }
    }
}
