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
public class MeemGerundGeneratorUn {

    public List<String> InDefiniteMeemGerunds = new ArrayList<String>();
    public List<String> DefiniteMeemGerunds = new ArrayList<String>();
    public List<String> AnnexedMeemGerunds = new ArrayList<String>();

    public static MeemGerundGeneratorUn getInstance() {
        return new MeemGerundGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        getInDefiniteMeemGerunds(root);
        getDefiniteMeemGerunds(root);
        getAnnexedMeemGerunds(root);
        return result;
    }

    public List<String> getDefiniteMeemGerunds(String rootStr) {
      List list= PassiveParticipleGeneratorUn.getInstance().getDefiniteNouns(rootStr);
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

    public List<String> getInDefiniteMeemGerunds(String rootStr) {
        List list = PassiveParticipleGeneratorUn.getInstance().getInDefiniteNouns(rootStr);
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

    public List<String> getAnnexedMeemGerunds(String rootStr) {
        List list= PassiveParticipleGeneratorUn.getInstance().getAnnexedNouns(rootStr);
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
        List<String> tests = MeemGerundGeneratorUn.getInstance().executeSimpleGenerator("زحزح");

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(i));
        }
    }
}
