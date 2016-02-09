/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.AssimilateAdjectiveBO;
import BusinessLogicLayer.BusinessObjects.NounBO;
import BusinessLogicLayer.BusinessObjects.SemanticNounBO;
import BusinessLogicLayer.SearchProperties;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.AssimilateadjectiveJPADAO;
import PersistenceLayer.Assimilateadjective;
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
public class AssimilateAdjectiveManager
{

    private static AssimilateadjectiveJPADAO ASADJ_DAO = BLUtil.daoFactory.getAssimilateadjectiveDAO();

    static List<AssimilateAdjectiveBO> getAssimilateAdjective( Semanticverb semVerb )
    {
        List<AssimilateAdjectiveBO> assimilateAdjectiveList = new ArrayList<AssimilateAdjectiveBO>();
        Set<Assimilateadjective> assimilateAdjectiveSet = semVerb.getAssimilateadjectives();
        for ( Iterator iter = assimilateAdjectiveSet.iterator() ; iter.hasNext() ; )
        {
            Assimilateadjective newAssimilateadjective = ( Assimilateadjective ) iter.next();
            SemanticNounBO newSemanticNounBO = SemanticNounBOManager.getSemanticNounBO( newAssimilateadjective.getSemanticnoun().getIdentity() , SearchProperties.simpleSearchOptions );
            NounBO newNounBO = NounBOManager.getNounBO( newAssimilateadjective.getSemanticnoun().getDerivednoun().getIdentity() , new SearchProperties() , "" );
            AssimilateAdjectiveBO newAssimilateAdjectiveBO = new AssimilateAdjectiveBO();
            newAssimilateAdjectiveBO.setAssimilateAdjective( newNounBO );
            newAssimilateAdjectiveBO.setAssimilateAdjectiveMeaning( newSemanticNounBO );
            newAssimilateAdjectiveBO.setAssimilateAdjectiveId( newAssimilateadjective.getIdentity() );
            newAssimilateAdjectiveBO.setStatus( newAssimilateadjective.getInfoStatus() );
            assimilateAdjectiveList.add( newAssimilateAdjectiveBO );
        }

        return assimilateAdjectiveList;
    }

    public static void deleteAssimilateAdjective( Integer semanticVerbId , String adjective )
    {
        try
        {
            Semanticverb semVerb = BLUtil.daoFactory.getSemanticverbDAO().getById( semanticVerbId );
            Set<Assimilateadjective> assimilateAdjectiveSet = semVerb.getAssimilateadjectives();
            for ( Iterator iter = assimilateAdjectiveSet.iterator() ; iter.hasNext() ; )
            {
                Assimilateadjective assimilateAdjectiveRelation = ( Assimilateadjective ) iter.next();
                Semanticnoun adjNoun = assimilateAdjectiveRelation.getSemanticnoun();
                String adj = adjNoun.getDerivednoun().getVocalizedNoun();
                if ( adj.equals( adjective ) )
                {
                    AssimilateadjectiveJPADAO dao = BLUtil.daoFactory.getAssimilateadjectiveDAO();
                    dao.delete( assimilateAdjectiveRelation.getIdentity() );
                    return;
                }
            }
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( AssimilateAdjectiveManager.class.getName() ).log( Level.SEVERE , null , ex );
        }
    }

    public static void suggestDeleting( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        Assimilateadjective newAssimilateadjective = BLUtil.daoFactory.getAssimilateadjectiveDAO().getById( assimilateAdjectiveId );

        newAssimilateadjective.setChechStatus( BOManagerUtil.DELETING_STATUS.getCheckStatus() );
        newAssimilateadjective.setInfoStatus( BOManagerUtil.DELETING_STATUS.getInfoStatus() );
        newAssimilateadjective.setSuggestion( BOManagerUtil.GET_DELETE_SUGGESTION() );

        BLUtil.daoFactory.getAssimilateadjectiveDAO().update( newAssimilateadjective );
    }

    static void affirmAdding( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        Assimilateadjective newAsAdj = ASADJ_DAO.getById( assimilateAdjectiveId );
        if ( newAsAdj.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newAsAdj.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newAsAdj.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newAsAdj.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newAsAdj.getSuggestion() );
            }
            ASADJ_DAO.update( newAsAdj );
        }
    }

    static void rejectAdding( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        Assimilateadjective newAsAdj = ASADJ_DAO.getById( assimilateAdjectiveId );
        if ( newAsAdj.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newAsAdj.setInfoStatus( WordStatus.REJECTED_INFO_STATUS );
            newAsAdj.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newAsAdj.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newAsAdj.getSuggestion() );
            }
            ASADJ_DAO.update( newAsAdj );
        }
    }

    static void affirmDeleting( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        Assimilateadjective newAsAdj = ASADJ_DAO.getById( assimilateAdjectiveId );
        if ( newAsAdj.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newAsAdj.setInfoStatus( WordStatus.NEED_DELETING );
            newAsAdj.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newAsAdj.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newAsAdj.getSuggestion() );
            }
            ASADJ_DAO.update( newAsAdj );
        }
    }

    static void rejectDeleting( int assimilateAdjectiveId ) throws RawNotFoundException
    {
        Assimilateadjective newAsAdj = ASADJ_DAO.getById( assimilateAdjectiveId );
        if ( newAsAdj.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newAsAdj.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newAsAdj.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newAsAdj.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newAsAdj.getSuggestion() );
            }
            ASADJ_DAO.update( newAsAdj );
        }
    }
}
