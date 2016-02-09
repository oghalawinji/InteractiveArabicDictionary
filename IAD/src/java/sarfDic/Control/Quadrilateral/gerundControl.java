/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Quadrilateral;

/**
 *
 * @author Gowzancha
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.Gerund.quadrilateral.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gowzancha
 */
public class gerundControl {

    public static gerundControl getInstance() {
        return new gerundControl();
    }
    public String root = "";
    public String conjugation = "";
    public String nounType = "";
    public String nounPattern = "";
    public String nounState = "";

    public List gerundController(AjaxRequest ajaxReq) {
        root = ajaxReq.getRoot();
        conjugation = ajaxReq.getConjugation();
        //String inflection = ajaxReq.getInflection();
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList<String>();

        if (nounType.equals("standard")) { //المصدر الأصلي
            if (Integer.parseInt(conjugation) == 0) {
                if (nounState.equals("definite")) {
                    list = StandardGerundGeneratorUn.getInstance().getDefiniteGerunds(root, Integer.parseInt(nounPattern)+1);// +1 coz nounPattern start from 0 while patternNo in teh classes start from 1
                }
                if (nounState.equals("indefinite")) {
                    list = StandardGerundGeneratorUn.getInstance().getInDefiniteGerunds(root, Integer.parseInt(nounPattern)+1);
                }
                if (nounState.equals("annexed")) {
                    list = StandardGerundGeneratorUn.getInstance().getAnnexedGerunds(root, Integer.parseInt(nounPattern)+1);
                }
            } else {
                if (nounState.equals("definite")) {
                    list = StandardGerundGeneratorAu.getInstance().getDefiniteGerunds(root, Integer.parseInt(conjugation));
                }
                if (nounState.equals("indefinite")) {
                    list = StandardGerundGeneratorAu.getInstance().getInDefiniteGerunds(root, Integer.parseInt(conjugation));
                }
                if (nounState.equals("annexed")) {
                    list = StandardGerundGeneratorAu.getInstance().getAnnexedGerunds(root, Integer.parseInt(conjugation));
                }
            }
        } else {

            if (nounType.equals("meem")) {// المصدر الميمي
                try {
                    if (Integer.parseInt(conjugation) == 0) {
                        if (nounState.equals("definite")) {
                            list = MeemGerundGeneratorUn.getInstance().getDefiniteMeemGerunds(root);
                        }
                        if (nounState.equals("indefinite")) {
                            list = MeemGerundGeneratorUn.getInstance().getInDefiniteMeemGerunds(root);
                        }
                        if (nounState.equals("annexed")) {
                            list = MeemGerundGeneratorUn.getInstance().getAnnexedMeemGerunds(root);
                        }
                    } else {
                        if (nounState.equals("definite")) {
                            list = MeemGerundGeneratorAu.getInstance().generateDefiniteMeemGerunds(root, Integer.parseInt(conjugation));
                        }
                        if (nounState.equals("indefinite")) {
                            list = MeemGerundGeneratorAu.getInstance().generateInDefiniteMeemGerunds(root, Integer.parseInt(conjugation));
                        }
                        if (nounState.equals("annexed")) {
                            list = MeemGerundGeneratorAu.getInstance().generateAnnexedMeemGerunds(root, Integer.parseInt(conjugation));
                        }
                    }

                } catch (Exception ex) {
                    list.add(ex.toString());
                }
            } else {
                if (nounType.equals("nomen")) {//مصدر المرة
                    if (Integer.parseInt(conjugation) == 0) {
                        if (nounState.equals("definite")) {
                            list = NomenGerundGeneratorUn.getInstance().getDefiniteNomenGerunds(root);
                        }
                        if (nounState.equals("indefinite")) {
                            list = NomenGerundGeneratorUn.getInstance().getInDefiniteNomenGerunds(root);
                        }
                        if (nounState.equals("annexed")) {
                            list = NomenGerundGeneratorUn.getInstance().getAnnexedNomenGerunds(root);
                        }
                    } else {
                        if (nounState.equals("definite")) {
                            list = NomenGerundGeneratorAu.getInstance().getDefiniteNomenGerunds(root, Integer.parseInt(conjugation));
                        }
                        if (nounState.equals("indefinite")) {
                            list = NomenGerundGeneratorAu.getInstance().getInDefiniteNomenGerunds(root, Integer.parseInt(conjugation));
                        }
                        if (nounState.equals("annexed")) {
                            list = NomenGerundGeneratorAu.getInstance().getAnnexedNomenGerunds(root, Integer.parseInt(conjugation));
                        }
                    }
                }
            }
        }

        return list;
    }


    public List getGerundPatternsList(AjaxRequest ajaxReq) {
        root = ajaxReq.getRoot();
        conjugation = ajaxReq.getConjugation();
        //String inflection = ajaxReq.getInflection();
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList();

        if (Integer.parseInt(conjugation) == 0) {
            if (nounType.equals("standard")) {
                list = StandardGerundGeneratorUn.getInstance().getPatterns(root);
            }
        }
        return list;
    }
}

