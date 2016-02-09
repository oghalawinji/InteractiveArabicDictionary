/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.SystemStateManager;
import BusinessLogicLayer.Util.BLUtil;
import DataAccessLayer.JPADAO.GenderJPADAO;
import PersistenceLayer.Gender;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class GenderManager 
{

    public static List<String> getAll ()
    {
        List<String> results = new ArrayList<String> ();
        GenderJPADAO dao = BLUtil.daoFactory.getGenderDAO ();
        List<Gender> genderObjs = dao.getAll ();
        for ( int i = 0 ; i < genderObjs.size () ; i ++ )
        {
            String gender = genderObjs.get ( i ).getGender ();
            results.add ( gender );
        }
        return results;
    }

    public String get ( Integer id , String mode ) throws RawNotFoundException
    {
        GenderJPADAO dao = BLUtil.daoFactory.getGenderDAO ();
        Gender gender = dao.getById ( id );
        return get ( gender , mode );
    }

    public String get ( Gender gender , String mode ) throws RawNotFoundException
    {
        String infoStatus = gender.getInfoStatus ();
        int checkStatus = gender.getChechStatus ();

        if (  ! SystemStateManager.available ( mode , infoStatus , checkStatus ) )
        {
            throw new RawNotFoundException ( "According to your privilige, The item does not found!!" );
        }

        String genderStr = gender.getGender ();
        return genderStr;
    }

    public boolean suggestDeleting ( Integer Id ) throws RawNotFoundException
    {
        GenderJPADAO dao = BLUtil.daoFactory.getGenderDAO ();
        Gender updatedGender = dao.getById ( Id );
        return suggestDeleting ( updatedGender );
    }

    public boolean suggestDeleting ( Gender gender ) throws RawNotFoundException
    {
        GenderJPADAO dao = BLUtil.daoFactory.getGenderDAO ();
        gender.setInfoStatus ( "D" );
        gender.setChechStatus ( 1 );
        dao.update ( gender );
        return true;
    }

    public boolean affirmDeleting ( Integer Id ) throws RawNotFoundException
    {
        try
        {
            GenderJPADAO dao = BLUtil.daoFactory.getGenderDAO ();
            dao.delete ( Id );
            return true;
        }
        catch ( RawNotFoundException ex )
        {
            throw new RawNotFoundException ( "Item does not found!!" );
        }
    }

    public boolean suggestUpdating ( Integer Id , String newObj ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException ( "Not supported yet." );
    }

    public boolean affirmUpdating ( Integer Id ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException ( "Not supported yet." );
    }

    public static int suggestAdding( String gender ) throws RawNotFoundException
    {
        Gender newGender = new Gender( gender );
        newGender.setChechStatus( BOManagerUtil.ADDING_STATUS.getCheckStatus() );
        newGender.setInfoStatus( BOManagerUtil.ADDING_STATUS.getInfoStatus() );
        newGender.setSuggestion( BOManagerUtil.GET_ADD_SUGGESTION() );
        return BLUtil.daoFactory.getGenderDAO().insertWithCheck( newGender , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions() );
    }

    public boolean affirmAddinging ( String bObj ) throws RawNotFoundException
    {
        throw new UnsupportedOperationException ( "Not supported yet." );
    }

    public static void main ( String[] args )
    {
        try
        {
            //String res = new GenderManager().get(1, "");
            //boolean res = new GenderManager().suggestDeleting(2);
            boolean res = new GenderManager ().affirmDeleting ( 2 );
            System.out.println ( "res=" + res );
        }
        catch ( RawNotFoundException ex )
        {
            System.out.println ( "error" );
        }
    }
}
