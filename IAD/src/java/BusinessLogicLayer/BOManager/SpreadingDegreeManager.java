package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SpreadingdegreeJPADAO;
import PersistenceLayer.Spreadingdegree;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class SpreadingDegreeManager
{

    public static List<String> getAll()
    {
        List<String> results = new ArrayList<String>();
        SpreadingdegreeJPADAO dao = BLUtil.daoFactory.getSpreadingdegreeDAO();
        List<Spreadingdegree> transObjs = dao.getAll();
        for ( int i = 0 ; i < transObjs.size() ; i ++ )
        {
            String trans = transObjs.get( i ).getSpreadingDegree();
            results.add( trans );
        }
        return results;
    }

    public String get( Integer id , String mode ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public String get( Spreadingdegree dataAccessObject , String mode ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestDeleting( Integer Id ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestDeleting( Spreadingdegree dataAccessObject ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean affirmDeleting( Integer Id ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestUpdating( Integer Id , String newObj ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean affirmUpdating( Integer Id ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public static int suggestAdding( String spreadingDegree )
    {
        try
        {
            SpreadingdegreeJPADAO dao = BLUtil.daoFactory.getSpreadingdegreeDAO();
            Spreadingdegree newSpreadingdegree = new Spreadingdegree( spreadingDegree );
            newSpreadingdegree.setChechStatus( BOManagerUtil.ADDING_STATUS.getCheckStatus() );
            newSpreadingdegree.setInfoStatus( BOManagerUtil.ADDING_STATUS.getInfoStatus() );
            newSpreadingdegree.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
            return BLUtil.daoFactory.getSpreadingdegreeDAO().insertWithCheck( newSpreadingdegree , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( SpreadingDegreeManager.class.getName() ).log( Level.SEVERE , null , ex );
            return -1;
        }
    }

    static int add( String spreadingDegree )
    {
        try
        {
            SpreadingdegreeJPADAO dao = BLUtil.daoFactory.getSpreadingdegreeDAO();
            Spreadingdegree newSpreadingdegree = new Spreadingdegree( spreadingDegree );
            newSpreadingdegree.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            newSpreadingdegree.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newSpreadingdegree.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
            return BLUtil.daoFactory.getSpreadingdegreeDAO().insertWithCheck( newSpreadingdegree , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( SpreadingDegreeManager.class.getName() ).log( Level.SEVERE , null , ex );
            return -1;
        }
    }

    public boolean affirmAddinging( String attribute ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
