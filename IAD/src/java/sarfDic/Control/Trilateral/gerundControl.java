/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Trilateral;

import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.Gerund.trilateral.*;
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
        boolean vocalization=Boolean.parseBoolean(ajaxReq.getVocalization());
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList<String>();

        if (nounType.equals("standard")) { //المصدر الأصلي
            if (Integer.parseInt(conjugation) <= 6) {
                if (nounState.equals("definite")) {
                    list = StandardGerundGeneratorUn.getInstance().getDefiniteGerunds(root, conjugation, Integer.parseInt(nounPattern));
                }
                if (nounState.equals("indefinite")) {
                    list = StandardGerundGeneratorUn.getInstance().getInDefiniteGerunds(root, conjugation, Integer.parseInt(nounPattern));
                }
                if (nounState.equals("annexed")) {
                    list = StandardGerundGeneratorUn.getInstance().getAnnexedGerunds(root, conjugation, Integer.parseInt(nounPattern));
                }
            } else {
                if (nounState.equals("definite")) {
                    list = StandardGerundGeneratorAu.getInstance().getDefiniteGerunds(root, Integer.parseInt(conjugation) - 6, Integer.parseInt(nounPattern) + 1,vocalization);// +1 coz nounPattern start from 0 while patternNo in the classes start from 1
                }
                if (nounState.equals("indefinite")) {
                    list = StandardGerundGeneratorAu.getInstance().getInDefiniteGerunds(root, Integer.parseInt(conjugation) - 6, Integer.parseInt(nounPattern) + 1,vocalization);
                }
                if (nounState.equals("annexed")) {
                    list = StandardGerundGeneratorAu.getInstance().getAnnexedGerunds(root, Integer.parseInt(conjugation) - 6, Integer.parseInt(nounPattern) + 1,vocalization);
                }
            }
        } else {

            if (nounType.equals("meem")) {// المصدر الميمي
                try {
                    if (Integer.parseInt(conjugation) <= 6) {
                        if (nounState.equals("definite")) {
                            list = MeemGerundGeneratorUn.getInstance().getDefiniteMeemGerunds(root, conjugation);
                        }
                        if (nounState.equals("indefinite")) {
                            list = MeemGerundGeneratorUn.getInstance().getInDefiniteMeemGerunds(root, conjugation);
                        }
                        if (nounState.equals("annexed")) {
                            list = MeemGerundGeneratorUn.getInstance().getAnnexedMeemGerunds(root, conjugation);
                        }
                    } else {
                        if (nounState.equals("definite")) {
                            list = MeemGerundGeneratorAu.getInstance().getDefiniteMeemGerunds(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                        if (nounState.equals("indefinite")) {
                            list = MeemGerundGeneratorAu.getInstance().getInDefiniteMeemGerunds(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                        if (nounState.equals("annexed")) {
                            list = MeemGerundGeneratorAu.getInstance().getAnnexedMeemGerunds(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                    }

                } catch (Exception ex) {
                    list.add(ex.toString());
                }
            } else {
                if (nounType.equals("nomen")) {//مصدر المرة
                    if (Integer.parseInt(conjugation) <= 6) {
                        if (nounState.equals("definite")) {
                            list = NomenGerundGeneratorUn.getInstance().getDefiniteNomenGerunds(root, conjugation);
                        }
                        if (nounState.equals("indefinite")) {
                            list = NomenGerundGeneratorUn.getInstance().getInDefiniteNomenGerunds(root, conjugation);
                        }
                        if (nounState.equals("annexed")) {
                            list = NomenGerundGeneratorUn.getInstance().getAnnexedNomenGerunds(root, conjugation);
                        }
                    } else {
                        if (nounState.equals("definite")) {
                            list = NomenGerundGeneratorAu.getInstance().getDefiniteNomenGerunds(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                        if (nounState.equals("indefinite")) {
                            list = NomenGerundGeneratorAu.getInstance().getInDefiniteNomenGerunds(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                        if (nounState.equals("annexed")) {
                            list = NomenGerundGeneratorAu.getInstance().getAnnexedNomenGerunds(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                    }
                } else {
                    if (nounType.equals("quality")) {//مصدر الهيئة
                        if (Integer.parseInt(conjugation) <= 6) {
                            if (nounState.equals("definite")) {
                                list = QualityGerundGeneratorUn.getInstance().getDefiniteQualityGerunds(root, conjugation);
                            }
                            if (nounState.equals("indefinite")) {
                                list = QualityGerundGeneratorUn.getInstance().getInDefiniteQualityGerunds(root, conjugation);
                            }
                            if (nounState.equals("annexed")) {
                                list = QualityGerundGeneratorUn.getInstance().getAnnexedQualityGerunds(root, conjugation);
                            }
                        }
                    }
                }
            }
        }

        return list;


    }

    public List getGerundPatternsList(AjaxRequest ajaxReq) {// NEED WORKING
        root = ajaxReq.getRoot();
        conjugation = ajaxReq.getConjugation();
        //String inflection = ajaxReq.getInflection();
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList();
        
        if (Integer.parseInt(conjugation) <= 6) {
            if (nounType.equals("standard")) {
                list = StandardGerundGeneratorUn.getInstance().getFormulas(root, conjugation);
            }
           /*if (nounType.equals("meem")) {// there snother one
                list.add("مَفْعَل");
            }*/
        } else {
            if (nounType.equals("standard")) {
                list = StandardGerundGeneratorAu.getInstance().getPatterns(root, Integer.parseInt(conjugation) - 6);
            }
        }
        return list;

    }
}
