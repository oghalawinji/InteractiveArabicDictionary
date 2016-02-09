/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.morphgen;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riad
 */
public class MainGenerator {

    public static String get(int pattern, String rootStr) {
        if (rootStr.length() == 3) {
            if (pattern >= 1 && pattern <= 6) {
                return TriUnaugmentedCase.get(pattern, rootStr);
            }
            if (pattern >= 7 && pattern <= 18) {
                return TriAugmentedCase.get(pattern - 6, rootStr);
            }
        }
        if (rootStr.length() == 4) {
            if (pattern == 0) {
                return QuadUnaugmentedCase.get(pattern , rootStr);
            }
            if (pattern >= 1) {
                return QuadAugmentedCase.get(pattern , rootStr);
            }
        }
        return "";
    }

    public static String getKovDesc(String rootStr) {
        String kovDesc="";
        if (rootStr.length() == 3) {
            kovDesc= TriUnaugmentedCase.getKovDesc(rootStr);
            if(kovDesc.equals(""))
                kovDesc= TriAugmentedCase.getKovDesc(rootStr);
        }
        if (rootStr.length() == 4) {
             kovDesc= QuadUnaugmentedCase.getKovDesc(rootStr);
            if(kovDesc.equals(""))
                kovDesc= QuadAugmentedCase.getKovDesc(rootStr);
        }
        return kovDesc;
    }

    public static String getTransitive(String rootStr, String patternStr) {
        int pattern=Integer.parseInt(patternStr);
        String transitive="";
        if (rootStr.length() == 3) {
            if (pattern >= 1 && pattern <= 6) {
                transitive= TriUnaugmentedCase.getTransitive(pattern, rootStr);
            }
            if (pattern >= 7 && pattern <= 18) {
                transitive= TriAugmentedCase.getTransitive(pattern - 6, rootStr);
            }
        }
        if (rootStr.length() == 4) {
            if (pattern == 0) {
                transitive= QuadUnaugmentedCase.getTransitive(pattern, rootStr);
            }
            if (pattern >= 1) {
                transitive= QuadAugmentedCase.getTransitive(pattern, rootStr);
            }
        }
       return transitive;
    }

    public static List getList(String rootStr) {
        List<String> list = new ArrayList<String>();
        if (rootStr.length() == 3) {
            for (int i = 1; i <= 18; i++) {
                list.add(get(i, rootStr));
            }
        }
        if (rootStr.length() == 4) {
            for (int j = 0; j <= 3; j++) {// 0 Unaug, 1,2,3 Aug
                list.add(get(j, rootStr));
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String root = "قذل";
        List res = MainGenerator.getList(root);
    }
}
