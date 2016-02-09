/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Gerund.quadrilateral;

import java.util.ArrayList;
import java.util.List;
import sarfDic.Midleware.Noun.quadrilateral.*;
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

    public List<String> executeSimpleGenerator(String root, int formulaNo) {
        List<String> result = new ArrayList<String>();
        generateDefiniteMeemGerunds(root, formulaNo);
        generateInDefiniteMeemGerunds(root, formulaNo);
        generateAnnexedMeemGerunds(root, formulaNo);
        return result;
    }

    public List<String> generateDefiniteMeemGerunds(String rootStr, int formulaNo) {
        List list= PassiveParticipleGeneratorAu.getInstance().generateDefinite(rootStr,formulaNo);
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

    public List<String> generateInDefiniteMeemGerunds(String rootStr, int formulaNo) {
        List list= PassiveParticipleGeneratorAu.getInstance().generateInDefinite(rootStr,formulaNo);
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

    public List<String> generateAnnexedMeemGerunds(String rootStr, int formulaNo) {
        List list= PassiveParticipleGeneratorAu.getInstance().generateAnnexed(rootStr,formulaNo);
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
        List<String> tests = MeemGerundGeneratorAu.getInstance().executeSimpleGenerator("زحزح",1);

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
