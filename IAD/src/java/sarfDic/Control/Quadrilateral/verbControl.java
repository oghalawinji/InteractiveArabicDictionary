/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Quadrilateral;

import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.Verbs.quadrilateral.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gowzancha
 */
public class verbControl {

    public static verbControl getInstance() {
        return new verbControl();
    }

    public List verbController(AjaxRequest ajaxReq) {
        String root = ajaxReq.getRoot();
        String conjugation = ajaxReq.getConjugation();
        String inflection = ajaxReq.getInflection();
        String verbTense = ajaxReq.getVerbTense();

        List list = new ArrayList<String>();

        if (inflection.equals("active")) {
            if (verbTense.equals("normalImperative")) {
                if (Integer.parseInt(conjugation) == 0) {
                    list = ActiveImperativeGeneratorUn.getInstance().generateNormalImperative(root);
                } else {
                    list = ActiveImperativeGeneratorAu.getInstance().getNormal(root, Integer.parseInt(conjugation));// Integer.parseInt(conjugation)-6 coz formulaNo 1->12 while conjugation 1->18
                }
            } else {
                if (verbTense.equals("emphasizedImperative")) {
                    if (Integer.parseInt(conjugation) == 0) {
                        list = ActiveImperativeGeneratorUn.getInstance().generateEmphasized(root);
                    } else {
                        list = ActiveImperativeGeneratorAu.getInstance().getEmphsized(root, Integer.parseInt(conjugation));
                    }
                } else {
                    if (verbTense.equals("past")) {
                        if (Integer.parseInt(conjugation) == 0) {
                            list = ActivePastGeneratorUn.getInstance().executeSimpleGenerator(root);
                        } else {
                            list = ActivePastGeneratorAu.getInstance().executeSimpleGenerator(root, Integer.parseInt(conjugation));
                        }
                    } else {
                        if (verbTense.equals("nominativePresent")) {
                            if (Integer.parseInt(conjugation) == 0) {
                                list = ActivePresentGeneratorUn.getInstance().generateNominative(root);
                            } else {
                                list = ActivePresentGeneratorAu.getInstance().getNominative(root, Integer.parseInt(conjugation));
                            }
                        } else {
                            if (verbTense.equals("emphasizedPresent")) {
                                if (Integer.parseInt(conjugation) == 0) {
                                    list = ActivePresentGeneratorUn.getInstance().generateEmphasized(root);
                                } else {
                                    list = ActivePresentGeneratorAu.getInstance().getEmphasized(root, Integer.parseInt(conjugation));
                                }
                            } else {
                                if (verbTense.equals("jussivePresent")) {
                                    if (Integer.parseInt(conjugation) == 0) {
                                        list = ActivePresentGeneratorUn.getInstance().generateJussive(root);
                                    } else {
                                        list = ActivePresentGeneratorAu.getInstance().getJussive(root, Integer.parseInt(conjugation));
                                    }
                                } else {
                                    if (verbTense.equals("accusativePresent")) {
                                        if (Integer.parseInt(conjugation) == 0) {
                                            list = ActivePresentGeneratorUn.getInstance().generateAccusative(root);
                                        } else {
                                            list = ActivePresentGeneratorAu.getInstance().getAccusative(root, Integer.parseInt(conjugation));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (inflection.equals("passive")) {
            if (verbTense.equals("past")) {
                if (Integer.parseInt(conjugation) == 0) {
                    list = PassivePastGeneratorUn.getInstance().executeSimpleGenerator(root);
                } else {
                    list = PassivePastGeneratorAu.getInstance().executeSimpleGenerator(root, Integer.parseInt(conjugation));
                }
            } else {
                if (verbTense.equals("nominativePresent")) {
                    if (Integer.parseInt(conjugation) == 0) {
                        list = PassivePresentGeneratorUn.getInstance().generateNominative(root);
                    } else {
                        list = PassivePresentGeneratorAu.getInstance().getNominative(root, Integer.parseInt(conjugation));
                    }
                } else {
                    if (verbTense.equals("emphasizedPresent")) {
                        if (Integer.parseInt(conjugation) == 0) {
                            list = PassivePresentGeneratorUn.getInstance().generateEmphasized(root);
                        } else {
                            list = PassivePresentGeneratorAu.getInstance().getEmphasized(root, Integer.parseInt(conjugation));
                        }
                    } else {
                        if (verbTense.equals("jussivePresent")) {
                            if (Integer.parseInt(conjugation)== 0) {
                                list = PassivePresentGeneratorUn.getInstance().generateJussive(root);
                            } else {
                                list = PassivePresentGeneratorAu.getInstance().getJussive(root, Integer.parseInt(conjugation));
                            }
                        } else {
                            if (verbTense.equals("accusativePresent")) {
                                if (Integer.parseInt(conjugation) == 0) {
                                    list = PassivePresentGeneratorUn.getInstance().generateAccusative(root);
                                } else {
                                    list = PassivePresentGeneratorAu.getInstance().getAccusative(root, Integer.parseInt(conjugation));
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}
