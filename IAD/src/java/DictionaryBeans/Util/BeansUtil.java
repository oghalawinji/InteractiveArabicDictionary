package DictionaryBeans.Util;

import BusinessLogicLayer.Util.WordStatus;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fadel
 */
public class BeansUtil
{

    public static WordStatus insertStatus = new WordStatus( WordStatus.INSERT_INFO_STATUS , WordStatus.NEED_CHECK_STATUS );

    public static String getFormatedText( String text ) throws BadWordException
    {
        String formatedText = "";
       /* try
        {
            byte[] byts1 = text.getBytes();
            String s = "";
            for ( int ii = 0 ; ii < byts1.length ; ii ++ )
            {
                s += byts1[ii] + " || ";
            }
            formatedText = new String( text.getBytes() , "windows-1256" );
            byte[] byts2 = formatedText.getBytes();

            String s1 = "";
            for ( int ii = 0 ; ii < byts2.length ; ii ++ )
            {
                s1 += byts2[ii] + " || ";
            }

            s += "";
            //this.currentVerb.settext ( text );
        }
        catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger( BeansUtil.class.getName() ).log( Level.SEVERE , null , ex );
        }
        //formatedText = text;*/
        checkText( text );
        return text;
    }

    private static void checkText( String text ) throws BadWordException
    {
        String safeText = "";
        String message = "";
        String warning = "لا يسمح باستخدام الكلمات أو المحارف التالية كدخل: ";

        String[] unSafeWords = new String[]
        {
            "'" , "\"" , ";" , "&" , "%" , "|" , "--" , "/" , "\\" , "or" , "and" , "select" , "union" , "<" , ">"
        };
        for ( String s : unSafeWords )
        {
            if ( text.contains( s ) )
            {
                message += s + " ";
            }
        }
        if ( message != "" )
        {
            throw new BadWordException( "arDic_Ex" + warning + "[ " + message + " ]" + "arDic_Ex" );
        }

    }
}
