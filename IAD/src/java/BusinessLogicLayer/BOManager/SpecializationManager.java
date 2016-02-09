package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SpecializationJPADAO;
import PersistenceLayer.Specialization;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class SpecializationManager
{

    public static List<String> getAll()
    {
        List<String> results = new ArrayList<String>();
        SpecializationJPADAO dao = BLUtil.daoFactory.getSpecializationDAO();
        List<Specialization> transObjs = dao.getAll();
        for ( int i = 0 ; i < transObjs.size() ; i ++ )
        {
            String trans = transObjs.get( i ).getSpecialization();
            results.add( trans );
        }
        return results;
    }

    public String get( Integer id , String mode ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public String get( Specialization dataAccessObject , String mode ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestDeleting( Integer Id ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean suggestDeleting( Specialization dataAccessObject ) throws RawNotFoundException
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

    public static int suggestAdding( String specialization )
    {
        try
        {
            SpecializationJPADAO dao = BLUtil.daoFactory.getSpecializationDAO();
            Specialization newSpecialization = new Specialization( specialization );
            newSpecialization.setChechStatus( BOManagerUtil.ADDING_STATUS.getCheckStatus() );
            newSpecialization.setInfoStatus( BOManagerUtil.ADDING_STATUS.getInfoStatus() );
            newSpecialization.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
            return BLUtil.daoFactory.getSpecializationDAO().insertWithCheck( newSpecialization , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( SpecializationManager.class.getName() ).log( Level.SEVERE , null , ex );
            return -1;
        }
    }

    static int add( String specialization )
    {
        try
        {
            SpecializationJPADAO dao = BLUtil.daoFactory.getSpecializationDAO();
            Specialization newSpecialization = new Specialization( specialization );
            newSpecialization.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            newSpecialization.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newSpecialization.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
            return BLUtil.daoFactory.getSpecializationDAO().insertWithCheck( newSpecialization , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( SpecializationManager.class.getName() ).log( Level.SEVERE , null , ex );
            return -1;
        }
    }

    public boolean affirmAddinging( String attribute ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
