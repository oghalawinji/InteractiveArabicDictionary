package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.SourceJPADAO;
import PersistenceLayer.Source;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SourceBOManager
{
    private static SourceJPADAO SOURCE_DAO = BLUtil.daoFactory.getSourceDAO();
    public static List<String> getAll()
    {
        List<String> results = new ArrayList<String>();
        List<Source> transObjs = SOURCE_DAO.getAll();
        for ( int i = 0; i < transObjs.size(); i++ )
        {
            String trans = transObjs.get( i ).getSource();
            results.add( trans );
        }
        return results;
    }

    public static int suggestAdding( String source ) throws RawNotFoundException
    {
        source = source.trim();
        Source newSource = new Source( source );
        newSource.setChechStatus( BOManagerUtil.ADDING_STATUS.getCheckStatus() );
        newSource.setInfoStatus( BOManagerUtil.ADDING_STATUS.getInfoStatus() );
        newSource.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );

        return SOURCE_DAO.insertWithCheck( newSource , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
    }

    static void affirmAdding( int sourceId ) throws RawNotFoundException
    {
        Source newSource = SOURCE_DAO.getById( sourceId );
        if(newSource.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS))
        {
            newSource.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS);
            newSource.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS);
            if(newSource.getSuggestion() != null)
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newSource.getSuggestion());
            }
            SOURCE_DAO.update( newSource );
        }
    }
}
