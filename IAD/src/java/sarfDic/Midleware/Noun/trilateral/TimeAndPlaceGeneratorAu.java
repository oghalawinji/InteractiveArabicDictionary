/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Midleware.Noun.trilateral;

import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;

//import sarf.noun.trilateral.unaugmented.timeandplace.TimeAndPlaceConjugator;
/**
 *
 * @author riad
 */
public class TimeAndPlaceGeneratorAu implements Generator {

    public List<String> inDefiniteNouns = new ArrayList<String>();
    public List<String> definiteNouns = new ArrayList<String>();
    public List<String> annexedNouns = new ArrayList<String>();

    public static TimeAndPlaceGeneratorAu getInstance() {
        return new TimeAndPlaceGeneratorAu();
    }

    public List<String> executeSimpleGenerator(String root, int formulaNo, boolean vocal) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root, formulaNo,  vocal);
        getInDefiniteNouns(root, formulaNo,  vocal);
        getAnnexedNouns(root, formulaNo,  vocal);
        return result;
    }

    public List getDefiniteNouns(String rootStr, int formulaNo, boolean vocal) {
        List list = PassiveParticipleGeneratorAu.getInstance().generateDefinite(rootStr, formulaNo,vocal);
        list.set(1, "");
        list.set(3, "");
        list.set(5, "");
        list.set(7, "");
        list.set(9, "");
        list.set(11, "");
        list.set(13, "");
        list.set(15, "");
        list.set(17, "");

        list.set(4, "");
        list.set(10, "");
        list.set(16, "");

        return list;
    }

    public List getInDefiniteNouns(String rootStr, int formulaNo, boolean vocal) {
        List list = PassiveParticipleGeneratorAu.getInstance().generateInDefinite(rootStr, formulaNo,vocal);
        list.set(1, "");
        list.set(3, "");
        list.set(5, "");
        list.set(7, "");
        list.set(9, "");
        list.set(11, "");
        list.set(13, "");
        list.set(15, "");
        list.set(17, "");

        list.set(4, "");
        list.set(10, "");
        list.set(16, "");

        return list;
    }

    public List getAnnexedNouns(String rootStr, int formulaNo, boolean vocal) {
        List list = PassiveParticipleGeneratorAu.getInstance().generateAnnexed(rootStr, formulaNo,vocal);
        list.set(1, "");
        list.set(3, "");
        list.set(5, "");
        list.set(7, "");
        list.set(9, "");
        list.set(11, "");
        list.set(13, "");
        list.set(15, "");
        list.set(17, "");

        list.set(4, "");
        list.set(10, "");
        list.set(16, "");

        return list;
    }

    public List<String> executeDetailedGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> executeSimpleGenerator(String root) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        TimeAndPlaceGeneratorAu.getInstance().executeSimpleGenerator("جوب", 9,true);
    }
}
