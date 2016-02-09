/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.BusinessObjects.FemininityBO;
import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.WordStatus;
import DataAccessLayer.JPADAO.FemininityJPADAO;
import PersistenceLayer.Femininity;
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
public class FemininityManager
{

    private static FemininityJPADAO FEMININITY_DAO = BLUtil.daoFactory.getFemininityDAO();

    public static List<FemininityBO> getFemininitiyForSemanticNoun( Semanticnoun semNoun ) throws RawNotFoundException
    {
        List<FemininityBO> femininityList = new ArrayList<FemininityBO>();
        Set<Femininity> femininitySet = semNoun.getFemininitiesForNounId();
        for ( Iterator iter = femininitySet.iterator() ; iter.hasNext() ; )
        {
            Femininity newFemininity = ( Femininity ) iter.next();
            Semanticnoun fimNoun = newFemininity.getSemanticFemininity();
            FemininityBO newFemininityBO = new FemininityBO();
            newFemininityBO.setFemininityMeaning( SemanticNounBOManager.getSemanticNounBO( fimNoun ) );
            newFemininityBO.setFemininity( NounBOManager.getNounBO( fimNoun.getDerivednoun().getIdentity() , "" ) );
            newFemininityBO.setFemininityId( newFemininity.getIdentity() );
            newFemininityBO.setStatus( newFemininity.getInfoStatus() );
            femininityList.add( newFemininityBO );
        }
        return femininityList;
    }

    public static void deleteFemininityRelation( Integer semanticNounId , String femininity )
    {
        try
        {
            Semanticnoun semNoun = BLUtil.daoFactory.getSemanticnounDAO().getById( semanticNounId );
            Set<Femininity> femininitySet = semNoun.getFemininitiesForNounId();
            for ( Iterator iter = femininitySet.iterator() ; iter.hasNext() ; )
            {
                Femininity femRelation = ( Femininity ) iter.next();
                Semanticnoun femininityNoun = ( ( Femininity ) iter.next() ).getSemanticFemininity();
                String fem = femininityNoun.getDerivednoun().getVocalizedNoun();
                if ( femininity.equals( fem ) )
                {
                    FemininityJPADAO dao = BLUtil.daoFactory.getFemininityDAO();
                    dao.delete( femRelation.getIdentity() );
                    return;
                }
            }
        }
        catch (RawNotFoundException ex)
        {
            Logger.getLogger( FemininityManager.class.getName() ).log( Level.SEVERE , null , ex );
        }

    }

    public static void suggestDeleting( int femininityId ) throws RawNotFoundException
    {
        Femininity newFemininity = BLUtil.daoFactory.getFemininityDAO().getById( femininityId );

        newFemininity.setChechStatus( BOManagerUtil.DELETING_STATUS.getCheckStatus() );
        newFemininity.setInfoStatus( BOManagerUtil.DELETING_STATUS.getInfoStatus() );
        newFemininity.setSuggestion( BOManagerUtil.GET_DELETE_SUGGESTION() );

        BLUtil.daoFactory.getFemininityDAO().update( newFemininity );
    }

    static void affirmAdding( int femininityId ) throws RawNotFoundException
    {
        Femininity newFEMININITY = FEMININITY_DAO.getById( femininityId );
        if ( newFEMININITY.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newFEMININITY.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newFEMININITY.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newFEMININITY.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newFEMININITY.getSuggestion() );
            }
            FEMININITY_DAO.update( newFEMININITY );
        }
    }

    static void rejectAdding( int femininityId ) throws RawNotFoundException
    {
        Femininity newFEMININITY = FEMININITY_DAO.getById( femininityId );
        if ( newFEMININITY.getInfoStatus().equals( WordStatus.INSERT_INFO_STATUS ) )
        {
            newFEMININITY.setInfoStatus( WordStatus.REJECTED_INFO_STATUS );
            newFEMININITY.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newFEMININITY.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newFEMININITY.getSuggestion() );
            }
            FEMININITY_DAO.update( newFEMININITY );
        }
    }

    static void affirmDeleting( int femininityId ) throws RawNotFoundException
    {
        Femininity newFEMININITY = FEMININITY_DAO.getById( femininityId );
        if ( newFEMININITY.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newFEMININITY.setInfoStatus( WordStatus.NEED_DELETING );
            newFEMININITY.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newFEMININITY.getSuggestion() != null )
            {
                BOManagerUtil.AFFIRM_SUGGESTION( newFEMININITY.getSuggestion() );
            }
            FEMININITY_DAO.update( newFEMININITY );
        }
    }

    static void rejectDeleting( int femininityId ) throws RawNotFoundException
    {
        Femininity newFEMININITY = FEMININITY_DAO.getById( femininityId );
        if ( newFEMININITY.getInfoStatus().equals( WordStatus.DELETE_INFO_STATUS ) )
        {
            newFEMININITY.setInfoStatus( WordStatus.CONFIRMED_INFO_STATUS );
            newFEMININITY.setChechStatus( WordStatus.NOT_NEED_CHECK_STATUS );
            if ( newFEMININITY.getSuggestion() != null )
            {
                BOManagerUtil.REJECT_SUGGESTION( newFEMININITY.getSuggestion() );
            }
            FEMININITY_DAO.update( newFEMININITY );
        }
    }
}
