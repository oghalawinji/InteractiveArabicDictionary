/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.ExaggerationBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.ExaggerationJPADAO;
import PersistenceLayer.Exaggeration;
import PersistenceLayer.Semanticnoun;
import PersistenceLayer.Semanticverb;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class ExaggerationManager
{
    private static ExaggerationJPADAO EXAGGERATION_DAO = BLUtil.daoFactory.getExaggerationDAO();

    public static List<ExaggerationBO> getExaggerations( Semanticverb semVerb )
    {

        List<ExaggerationBO> exaggerationList = new ArrayList<ExaggerationBO>();
        Set<Exaggeration> exaggerationeSet = semVerb.getExaggerations();
        for ( Iterator iter = exaggerationeSet.iterator() ; iter.hasNext() ; )
        {
            Exaggeration exaggeration = ( Exaggeration ) iter.next();
            SemanticNounBO newSemanticNounBO = SemanticNounBOManager.getSemanticNounBO( exaggeration.getSemanticnoun().getIdentity() , SearchProperties.simpleSearchOptions );
            NounBO newNounBO = NounBOManager.getNounBO( exaggeration.getSemanticnoun().getDerivednoun().getIdentity() , new SearchProperties() , "" );
            ExaggerationBO newExaggerationBO = new ExaggerationBO();
            newExaggerationBO.setExaggeration( newNounBO );
            newExaggerationBO.setExaggerationMeaning( newSemanticNounBO );
            newExaggerationBO.setExaggerationId( exaggeration.getIdentity() );
            newExaggerationBO.setStatus( exaggeration.getInfoStatus() );
            exaggerationList.add( newExaggerationBO );
        }

        return exaggerationList;
    }

    public static void deleteExaggeration( Integer semanticVerbId , String exaggeration )
    {
        try
        {
            Semanticverb semVerb = BLUtil.daoFactory.getSemanticverbDAO().getById( semanticVerbId );
            Set<Exaggeration> exaggerationeSet = semVerb.getExaggerations();
            for ( Iterator iter = exaggerationeSet.iterator() ; iter.hasNext() ; )
            {
                Exaggeration exaggerationRelation = ( Exaggeration ) iter.next();
                Semanticnoun adjNoun = exaggerationRelation.getSemanticnoun();
                String adj = adjNoun.getDerivednoun().getVocalizedNoun();
                if ( adj.equals( exaggeration ) )
                {
                    ExaggerationJPADAO dao = BLUtil.daoFactory.getExaggerationDAO();
                    dao.delete( exaggerationRelation.getIdentity() );
                    return;
                }
            }
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( ExaggerationManager.class.getName() ).log( Level.SEVERE , null , ex );
        }
    }

    public static void suggestDeleting( int exaggerationId ) throws RawNotFoundException
    {
        Exaggeration newExaggeration = BLUtil.daoFactory.getExaggerationDAO().getById( exaggerationId );

        newExaggeration.setChechStatus( BOManagerUtil.DELETING_STATUS.getCheckStatus() );
        newExaggeration.setInfoStatus( BOManagerUtil.DELETING_STATUS.getInfoStatus() );
        newExaggeration.setSuggestion( BOManagerUtil.GET_DELETE_SUGGESTION() );

        BLUtil.daoFactory.getExaggerationDAO().update( newExaggeration );
    }

    static void affirmAdding( int exaggerationId ) throws RawNotFoundException
    {
        Exaggeration newEXAGGERATION = EXAGGERATION_DAO.getById( exaggerationId );
        if ( newEXAGGERATION.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newEXAGGERATION.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newEXAGGERATION.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newEXAGGERATION.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newEXAGGERATION.getSuggestion() );
            }
            EXAGGERATION_DAO.update( newEXAGGERATION );
        }
    }

    static void rejectAdding( int exaggerationId ) throws RawNotFoundException
    {
        Exaggeration newEXAGGERATION = EXAGGERATION_DAO.getById( exaggerationId );
        if ( newEXAGGERATION.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newEXAGGERATION.setInfoStatus( WordStatus.REJECTED_INFO_STATUS );
            newEXAGGERATION.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newEXAGGERATION.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newEXAGGERATION.getSuggestion() );
            }
            EXAGGERATION_DAO.update( newEXAGGERATION );
        }
    }

    static void affirmDeleting( int exaggerationId ) throws RawNotFoundException
    {
        Exaggeration newEXAGGERATION = EXAGGERATION_DAO.getById( exaggerationId );
        if ( newEXAGGERATION.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newEXAGGERATION.setInfoStatus( WordStatus.NEED_DELETING );
            newEXAGGERATION.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newEXAGGERATION.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newEXAGGERATION.getSuggestion() );
            }
            EXAGGERATION_DAO.update( newEXAGGERATION );
        }
    }

    static void rejectDeleting( int exaggerationId ) throws RawNotFoundException
    {
        Exaggeration newEXAGGERATION = EXAGGERATION_DAO.getById( exaggerationId );
        if ( newEXAGGERATION.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newEXAGGERATION.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newEXAGGERATION.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newEXAGGERATION.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newEXAGGERATION.getSuggestion() );
            }
            EXAGGERATION_DAO.update( newEXAGGERATION );
        }
    }
}
