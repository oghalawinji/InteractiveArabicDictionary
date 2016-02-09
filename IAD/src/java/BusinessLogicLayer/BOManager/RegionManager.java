package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.RegionJPADAO;
import PersistenceLayer.Region;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class RegionManager
{

    public static List<String> getAll()
    {
        List<String> results = new ArrayList<String>();
        RegionJPADAO dao = BLUtil.daoFactory.getRegionDAO();
        List<Region> transObjs = dao.getAll();
        for ( int i = 0 ; i < transObjs.size() ; i ++ )
        {
            String trans = transObjs.get( i ).getRegion();
            results.add( trans );
        }
        return results;
    }

    public String get( Integer id , String mode ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public String get( Region seed , String mode ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestDeleting( Integer Id ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestDeleting( Region root ) throws RawNotFoundException
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

    public static int suggestAdding( String region )
    {
        try
        {
            Region newRegion = new Region( region );
            newRegion.setChechStatus( BOManagerUtil.ADDING_STATUS.getCheckStatus() );
            newRegion.setInfoStatus( BOManagerUtil.ADDING_STATUS.getInfoStatus() );
            newRegion.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
            return BLUtil.daoFactory.getRegionDAO().insertWithCheck( newRegion , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( RegionManager.class.getName() ).log( Level.SEVERE , null , ex );
            return -1;
        }

    }

    static int add( String region )
    {
        try
        {
            Region newRegion = new Region( region );
            newRegion.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            newRegion.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newRegion.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
            return BLUtil.daoFactory.getRegionDAO().insertWithCheck( newRegion , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( RegionManager.class.getName() ).log( Level.SEVERE , null , ex );
            return -1;
        }
    }

    public boolean affirmAddinging( String bObj ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
