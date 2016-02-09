/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util.Stemmer;

import BusinessLogicLayer.Util.BLUtil;
import BusinessLogicLayer.Util.CompareWordsBO;
import BusinessLogicLayer.Util.Distance;
import DataAccessLayer.JPADAO.RawwordJPADAO;
import DataAccessLayer.JPADAO.RootJPADAO;
import PersistenceLayer.Rawword;
import PersistenceLayer.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author riad
 */
public class SpellChecking {

    public static int max = 10;

    public static List<String> findOptions(String word, boolean byRoot) {
        if( byRoot)
            return findRootOptions( word );
        else
            return findWordOptions( word );
    }

    private static List<String> findRootOptions(String word){
            RootJPADAO dao = BLUtil.daoFactory.getRootDAO();

            //options <=> %x%y%z%
            String expr1 = "%";
            for( int i=0; i<word.length(); i++)
                expr1 += word.charAt(i) + "%";
            List<Root> list = dao.getRootsLike( expr1 );
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i).getRoot();
                if( ! arr.contains(str))
                    arr.add( str );
            }
            //options <=> %x%y%
            for( int j=0; j<word.length(); j++){
                String exprj = "%";
                for( int i=0; i<word.length(); i++)
                    if( i != j)
                        exprj += word.charAt(i) + "%";
                List<Root> listj = dao.getRootsLike(exprj );
                for (int i = 0; i < listj.size(); i++) {
                    String str = listj.get(i).getRoot();
                    if( ! arr.contains(str))
                    arr.add( str );
                }
            }
            arr.remove(word);
            Distance dis = new Distance();
            dis.setBase(word);
            Collections.sort(arr, dis);
            if( arr.size() > max)
                arr = arr.subList(0, max);
            return arr;
    }

    private static List<String> findWordOptions(String word) {
            RawwordJPADAO dao = BLUtil.daoFactory.getRawwordDAO();

            //options <=> %x%y%z%
            String expr1 = "%";
            for( int i=0; i<word.length(); i++)
                expr1 += word.charAt(i) + "%";
            List<Rawword> list = dao.getWordsLike( expr1 );
            List<String> arr = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i).getRawWord();
                if( ! arr.contains(str))
                    arr.add( str );
            }
            //options <=> %x%y%
            for( int j=0; j<word.length(); j++){
                String exprj = "%";
                for( int i=0; i<word.length(); i++)
                    if( i != j)
                        exprj += word.charAt(i) + "%";
                List<Rawword> listj = dao.getWordsLike( exprj );
                for (int i = 0; i < listj.size(); i++) {
                    String str = listj.get(i).getRawWord();
                    if( ! arr.contains(str))
                    arr.add( str );
                }
            }
            arr.remove(word);
            Distance dis = new Distance();
            dis.setBase(word);
            Collections.sort(arr, dis);
            if( arr.size() > max)
                arr = arr.subList(0, max);
            return arr;
    }

}
