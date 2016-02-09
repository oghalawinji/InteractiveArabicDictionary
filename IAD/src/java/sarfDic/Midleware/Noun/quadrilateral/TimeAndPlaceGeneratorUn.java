/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Midleware.Noun.quadrilateral;

/**
 *
 * @author Gowzancha
 */
import sarfDic.Midleware.Generator;
import java.util.ArrayList;
import java.util.List;


public class TimeAndPlaceGeneratorUn implements Generator {

    public List<String> inDefiniteNouns = new ArrayList<String>();
    public List<String> definiteNouns = new ArrayList<String>();
    public List<String> annexedNouns = new ArrayList<String>();

    public static TimeAndPlaceGeneratorUn getInstance() {
        return new TimeAndPlaceGeneratorUn();
    }

    public List<String> executeSimpleGenerator(String root) {
        List<String> result = new ArrayList<String>();
        getDefiniteNouns(root);
        getInDefiniteNouns(root);
        getAnnexedNouns(root);
        return result;
    }

    public List getDefiniteNouns(String rootStr) {
        List list = PassiveParticipleGeneratorUn.getInstance().getDefiniteNouns(rootStr);
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

    public List getInDefiniteNouns(String rootStr) {
        List list = PassiveParticipleGeneratorUn.getInstance().getInDefiniteNouns(rootStr);
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

    public List getAnnexedNouns(String rootStr) {
        List list = PassiveParticipleGeneratorUn.getInstance().getAnnexedNouns(rootStr);
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

    public static void main(String[] args) {
        TimeAndPlaceGeneratorUn.getInstance().executeSimpleGenerator("زحزح");
    }
}

