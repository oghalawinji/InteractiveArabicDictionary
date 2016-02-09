/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util;

/**
 *
 * @author riad
 */
public class FilterDiacritics {

    public static String execute(String vocVerb) {
        String raw = "";
        for( int i=0; i<vocVerb.length(); i++){
            char ch = vocVerb.charAt(i);
            if( isDiacritics( ch ) == false )
                raw += ch;
        }
        return raw;
    }

    public static boolean isDiacritics(char ch) {
        String diacritics = "ًٌٍَُِّْ";
        return ( diacritics.indexOf(ch) != -1);
    }

}
