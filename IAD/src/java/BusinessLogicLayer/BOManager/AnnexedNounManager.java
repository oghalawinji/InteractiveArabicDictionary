/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.AnnexedNounBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.AnnexednounJPADAO;
import PersistenceLayer.Annexednoun;
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
public class AnnexedNounManager
{
    private static AnnexednounJPADAO ANNEXED_DAO = BLUtil.daoFactory.getAnnexednounDAO();

    public static List<AnnexedNounBO> getAnnexedNounForSemanticNoun( Semanticnoun semNoun ) throws RawNotFoundException
    {
        List<AnnexedNounBO> annexedNounList = new ArrayList<AnnexedNounBO>();
        Set<Annexednoun> annexedNounSet = semNoun.getAnnexednounsForNounId();
        for ( Iterator iter = annexedNounSet.iterator() ; iter.hasNext() ; )
        {
            Annexednoun newAnnexedNoun = ( Annexednoun ) iter.next();
            Semanticnoun anNoun = newAnnexedNoun.getSemanticAnnexedNoun();
            AnnexedNounBO newAnnexedNounBO = new AnnexedNounBO();
            newAnnexedNounBO.setAnnexedNounMeaning( SemanticNounBOManager.getSemanticNounBO( anNoun ) );
            newAnnexedNounBO.setAnnexedNoun( NounBOManager.getNounBO( anNoun.getDerivednoun().getIdentity() , "" ) );
            newAnnexedNounBO.setAnnexedNounId( newAnnexedNoun.getIdentity() );
            newAnnexedNounBO.setStatus( newAnnexedNoun.getInfoStatus() );
            annexedNounList.add( newAnnexedNounBO );
        }
        return annexedNounList;
    }

    public static void deleteAnnexedNoun( Integer semanticNounId , String annexedNoun )
    {
        try
        {
            Semanticnoun semNoun = BLUtil.daoFactory.getSemanticnounDAO().getById( semanticNounId );
            Set<Annexednoun> annexedNounSet = semNoun.getAnnexednounsForNounId();
            for ( Iterator iter = annexedNounSet.iterator() ; iter.hasNext() ; )
            {
                Annexednoun annexedNounRelation = ( Annexednoun ) iter.next();
                Semanticnoun annexedNounObj = annexedNounRelation.getSemanticAnnexedNoun();
                String annexed = annexedNounObj.getDerivednoun().getVocalizedNoun();
                if ( annexed.equals( annexedNoun ) )
                {
                    AnnexednounJPADAO dao = BLUtil.daoFactory.getAnnexednounDAO();
                    dao.delete( annexedNounRelation.getIdentity() );
                    return;
                }
            }
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( AnnexedNounManager.class.getName() ).log( Level.SEVERE , null , ex );
        }
    }

    public static void suggestDeleting( int annexedNounId ) throws RawNotFoundException
    {
        Annexednoun newAnnexednoun = BLUtil.daoFactory.getAnnexednounDAO().getById( annexedNounId );

        newAnnexednoun.setChechStatus( BOManagerUtil.DELETING_STATUS.getCheckStatus() );
        newAnnexednoun.setInfoStatus( BOManagerUtil.DELETING_STATUS.getInfoStatus() );
        newAnnexednoun.setSuggestion( BOManagerUtil.GET_DELETE_SUGGESTION() );

        BLUtil.daoFactory.getAnnexednounDAO().update( newAnnexednoun );
    }

        static void affirmAdding( int annexedNounId ) throws RawNotFoundException
    {
        Annexednoun newANNEXED = ANNEXED_DAO.getById( annexedNounId );
        if ( newANNEXED.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newANNEXED.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newANNEXED.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newANNEXED.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newANNEXED.getSuggestion() );
            }
            ANNEXED_DAO.update( newANNEXED );
        }
    }

    static void rejectAdding( int annexedNounId ) throws RawNotFoundException
    {
        Annexednoun newANNEXED = ANNEXED_DAO.getById( annexedNounId );
        if ( newANNEXED.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newANNEXED.setInfoStatus( WordStatus.REJECTED_INFO_STATUS );
            newANNEXED.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newANNEXED.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newANNEXED.getSuggestion() );
            }
            ANNEXED_DAO.update( newANNEXED );
        }
    }

    static void affirmDeleting( int annexedNounId ) throws RawNotFoundException
    {
        Annexednoun newANNEXED = ANNEXED_DAO.getById( annexedNounId );
        if ( newANNEXED.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newANNEXED.setInfoStatus( WordStatus.NEED_DELETING );
            newANNEXED.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newANNEXED.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newANNEXED.getSuggestion() );
            }
            ANNEXED_DAO.update( newANNEXED );
        }
    }

    static void rejectDeleting( int annexedNounId ) throws RawNotFoundException
    {
        Annexednoun newANNEXED = ANNEXED_DAO.getById( annexedNounId );
        if ( newANNEXED.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newANNEXED.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newANNEXED.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newANNEXED.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newANNEXED.getSuggestion() );
            }
            ANNEXED_DAO.update( newANNEXED );
        }
    }

}
