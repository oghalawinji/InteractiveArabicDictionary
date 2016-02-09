/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.DiminutiveBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.DiminutiveJPADAO;
import PersistenceLayer.Diminutive;
import PersistenceLayer.Semanticnoun;
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
public class DiminutiveManager
{

    private static DiminutiveJPADAO DIMINUTIVE_DAO = BLUtil.daoFactory.getDiminutiveDAO();

    public static List<DiminutiveBO> getDiminutiveForSemanticNoun( Semanticnoun semNoun ) throws RawNotFoundException
    {
        List<DiminutiveBO> diminutiveList = new ArrayList<DiminutiveBO>();
        Set<Diminutive> diminutiveSet = semNoun.getDiminutivesForNounId();
        for ( Iterator iter = diminutiveSet.iterator() ; iter.hasNext() ; )
        {
            Diminutive newDiminutive = ( Diminutive ) iter.next();
            Semanticnoun dimNoun = newDiminutive.getSemanticDiminutive();
            DiminutiveBO newDiminutiveBO = new DiminutiveBO();
            newDiminutiveBO.setDiminutiveMeaning( SemanticNounBOManager.getSemanticNounBO( dimNoun ) );
            newDiminutiveBO.setDiminutive( NounBOManager.getNounBO( dimNoun.getDerivednoun().getIdentity() , "" ) );
            newDiminutiveBO.setDiminutiveId( newDiminutive.getIdentity() );
            newDiminutiveBO.setStatus( newDiminutive.getInfoStatus() );
            diminutiveList.add( newDiminutiveBO );
        }
        return diminutiveList;
    }

    public static void deleteDiminutive( Integer semanticNounId , String diminutive )
    {
        try
        {
            Semanticnoun semNoun = BLUtil.daoFactory.getSemanticnounDAO().getById( semanticNounId );
            Set<Diminutive> diminutiveSet = semNoun.getDiminutivesForNounId();
            for ( Iterator iter = diminutiveSet.iterator() ; iter.hasNext() ; )
            {
                Diminutive diminutiveRelation = ( Diminutive ) iter.next();
                Semanticnoun dimNoun = diminutiveRelation.getSemanticDiminutive();
                String dim = dimNoun.getDerivednoun().getVocalizedNoun();
                if ( dim.equals( diminutive ) )
                {
                    DiminutiveJPADAO dao = BLUtil.daoFactory.getDiminutiveDAO();
                    dao.delete( diminutiveRelation.getIdentity() );
                    return;
                }
            }
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( DiminutiveManager.class.getName() ).log( Level.SEVERE , null , ex );
        }
    }

    public static void suggestDeleting( int diminutiveId ) throws RawNotFoundException
    {
        Diminutive newDiminutive = BLUtil.daoFactory.getDiminutiveDAO().getById( diminutiveId );

        newDiminutive.setChechStatus( BOManagerUtil.DELETING_STATUS.getCheckStatus() );
        newDiminutive.setInfoStatus( BOManagerUtil.DELETING_STATUS.getInfoStatus() );
        newDiminutive.setSuggestion( BOManagerUtil.GET_DELETE_SUGGESTION() );

        BLUtil.daoFactory.getDiminutiveDAO().update( newDiminutive );
    }

    static void affirmAdding( int diminutiveId ) throws RawNotFoundException
    {
        Diminutive newDIMINUTIVE = DIMINUTIVE_DAO.getById( diminutiveId );
        if ( newDIMINUTIVE.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newDIMINUTIVE.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newDIMINUTIVE.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newDIMINUTIVE.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newDIMINUTIVE.getSuggestion() );
            }
            DIMINUTIVE_DAO.update( newDIMINUTIVE );
        }
    }

    static void rejectAdding( int diminutiveId ) throws RawNotFoundException
    {
        Diminutive newDIMINUTIVE = DIMINUTIVE_DAO.getById( diminutiveId );
        if ( newDIMINUTIVE.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newDIMINUTIVE.setInfoStatus( WordStatus.REJECTED_INFO_STATUS );
            newDIMINUTIVE.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newDIMINUTIVE.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newDIMINUTIVE.getSuggestion() );
            }
            DIMINUTIVE_DAO.update( newDIMINUTIVE );
        }
    }

    static void affirmDeleting( int diminutiveId ) throws RawNotFoundException
    {
        Diminutive newDIMINUTIVE = DIMINUTIVE_DAO.getById( diminutiveId );
        if ( newDIMINUTIVE.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newDIMINUTIVE.setInfoStatus( WordStatus.NEED_DELETING );
            newDIMINUTIVE.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newDIMINUTIVE.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newDIMINUTIVE.getSuggestion() );
            }
            DIMINUTIVE_DAO.update( newDIMINUTIVE );
        }
    }

    static void rejectDeleting( int diminutiveId ) throws RawNotFoundException
    {
        Diminutive newDIMINUTIVE = DIMINUTIVE_DAO.getById( diminutiveId );
        if ( newDIMINUTIVE.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newDIMINUTIVE.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newDIMINUTIVE.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newDIMINUTIVE.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newDIMINUTIVE.getSuggestion() );
            }
            DIMINUTIVE_DAO.update( newDIMINUTIVE );
        }
    }
}
