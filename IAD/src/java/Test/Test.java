/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;


import DataAccessLayer.HibernateDAOFactory;
import PersistenceLayer.Gender;
import Util.RawNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author riad
 */
public class Test
{

    public static void main( String[] args ) throws UnsupportedEncodingException
    {

        /*UserManager newUserManager  = new UserManager();
        int[] result = newUserManager.getUserWorks( 1);
        for(int i : result)
        {
            System.out.print("["+ i+"]/n" );
        }

    /*        String s = "????";
        String s1 = new String( s.getBytes(), "windows-1256");
        JOptionPane.showMessageDialog( null , s1 );
        System.out.print( s1 );
/*
        try
        {
            Gender list = HibernateDAOFactory.getHibernateDAOFactory().getGenderDAO().getById( 1 );
            Object o = list.getGender();
            System.out.println( "s" );
        }
        catch ( RawNotFoundException ex )
        {
            Logger.getLogger( Test.class.getName() ).log( Level.SEVERE , null , ex );
        }*/

    }
}
