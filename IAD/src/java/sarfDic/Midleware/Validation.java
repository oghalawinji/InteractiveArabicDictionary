/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Midleware;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class Validation {
    public Validation (){

    }
public boolean checkValidatio(String root){
    if(root.equals("")){
        return false;
    }
     List list =sarfDic.Midleware.SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(root);
     if(list.isEmpty()){
         return false;
     }
    return true;
}
}
