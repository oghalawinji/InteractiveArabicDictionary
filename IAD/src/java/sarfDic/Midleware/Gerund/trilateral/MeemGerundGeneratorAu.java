/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.trilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.Noun.trilateral.*;
/**
 *
 * @author Gowzancha
 */
public class MeemGerundGeneratorAu {

    public List<String> InDefiniteMeemGerunds = new ArrayList<String>();
    public List<String> DefiniteMeemGerunds = new ArrayList<String>();
    public List<String> AnnexedMeemGerunds = new ArrayList<String>();

    public static MeemGerundGeneratorAu getInstance() {
        return new MeemGerundGeneratorAu();
    }

    public List<String> executeSimpleGenerator(String root, int formulaNo,boolean vocal) {
        List<String> result = new ArrayList<String>();
        getInDefiniteMeemGerunds(root, formulaNo,vocal);
        getDefiniteMeemGerunds(root, formulaNo,vocal);
        getAnnexedMeemGerunds(root, formulaNo,vocal);
        return result;
    }

    public List<String> getDefiniteMeemGerunds(String rootStr, int formulaNo,boolean vocal) {
        List list= PassiveParticipleGeneratorAu.getInstance().generateDefinite(rootStr, formulaNo,vocal);
        list.set(1, "");
        list.set(2, "");
        list.set(3, "");
        list.set(5, "");
        list.set(7, "");
        list.set(8, "");
        list.set(9, "");
        list.set(11, "");
        list.set(13, "");
        list.set(14, "");
        list.set(15, "");
        list.set(17, "");

        list.set(4, "");
        list.set(10, "");
        list.set(16, "");

        return list;
    }

    public List<String> getInDefiniteMeemGerunds(String rootStr,int formulaNo,boolean vocal) {
        List list= PassiveParticipleGeneratorAu.getInstance().generateInDefinite(rootStr, formulaNo,vocal);
        list.set(1, "");
        list.set(2, "");
        list.set(3, "");
        list.set(5, "");
        list.set(7, "");
        list.set(8, "");
        list.set(9, "");
        list.set(11, "");
        list.set(13, "");
        list.set(14, "");
        list.set(15, "");
        list.set(17, "");

        list.set(4, "");
        list.set(10, "");
        list.set(16, "");

        return list;
    }

    public List<String> getAnnexedMeemGerunds(String rootStr, int formulaNo,boolean vocal) {
        List list= PassiveParticipleGeneratorAu.getInstance().generateAnnexed(rootStr, formulaNo,vocal);
        list.set(1, "");
        list.set(2, "");
        list.set(3, "");
        list.set(5, "");
        list.set(7, "");
        list.set(8, "");
        list.set(9, "");
        list.set(11, "");
        list.set(13, "");
        list.set(14, "");
        list.set(15, "");
        list.set(17, "");

        list.set(4, "");
        list.set(10, "");
        list.set(16, "");

        return list;
    }

    public static void main(String[] args) {
        List<String> tests = MeemGerundGeneratorAu.getInstance().executeSimpleGenerator("جوب", 9,false);

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
