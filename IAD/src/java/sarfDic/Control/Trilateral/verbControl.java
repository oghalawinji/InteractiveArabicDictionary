/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Trilateral;

import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.Verbs.trilateral.*;
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
        boolean vocalization=Boolean.parseBoolean(ajaxReq.getVocalization());
        String verbTense = ajaxReq.getVerbTense();

        List list = new ArrayList<String>();

        if (inflection.equals("active")) {
            if (verbTense.equals("normalImperative")) {
                if (Integer.parseInt(conjugation) <= 6) {
                    list = ActiveImperativeGeneratorUn.getInstance().generateNormalImperative(root, conjugation);
                } else {
                    list = ActiveImperativeGeneratorAu.getInstance().getNormal(root, Integer.parseInt(conjugation) - 6,vocalization);// Integer.parseInt(conjugation)-6 coz formulaNo 1->12 while conjugation 1->18
                }
            } else {
                if (verbTense.equals("emphasizedImperative")) {
                    if (Integer.parseInt(conjugation) <= 6) {
                        list = ActiveImperativeGeneratorUn.getInstance().generateEmphasized(root, conjugation);
                    } else {
                        list = ActiveImperativeGeneratorAu.getInstance().getEmphsized(root, Integer.parseInt(conjugation) - 6,vocalization);
                    }
                } else {
                    if (verbTense.equals("past")) {
                        if (Integer.parseInt(conjugation) <= 6) {
                            list = ActivePastGeneratorUn.getInstance().executeSimpleGenerator(root, conjugation);
                        } else {
                            list = ActivePastGeneratorAu.getInstance().executeSimpleGenerator(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                    } else {
                        if (verbTense.equals("nominativePresent")) {
                            if (Integer.parseInt(conjugation) <= 6) {
                                list = ActivePresentGeneratorUn.getInstance().generateNominative(root, conjugation);
                            } else {
                                list = ActivePresentGeneratorAu.getInstance().getNominative(root, Integer.parseInt(conjugation) - 6,vocalization);
                            }
                        } else {
                            if (verbTense.equals("emphasizedPresent")) {
                                if (Integer.parseInt(conjugation) <= 6) {
                                    list = ActivePresentGeneratorUn.getInstance().generateEmphasized(root, conjugation);
                                } else {
                                    list = ActivePresentGeneratorAu.getInstance().getEmphasized(root, Integer.parseInt(conjugation) - 6,vocalization);
                                }
                            } else {
                                if (verbTense.equals("jussivePresent")) {
                                    if (Integer.parseInt(conjugation) <= 6) {
                                        list = ActivePresentGeneratorUn.getInstance().generateJussive(root, conjugation);
                                    } else {
                                        list = ActivePresentGeneratorAu.getInstance().getJussive(root, Integer.parseInt(conjugation) - 6,vocalization);
                                    }
                                } else {
                                    if (verbTense.equals("accusativePresent")) {
                                        if (Integer.parseInt(conjugation) <= 6) {
                                            list = ActivePresentGeneratorUn.getInstance().generateAccusative(root, conjugation);
                                        } else {
                                            list = ActivePresentGeneratorAu.getInstance().getAccusative(root, Integer.parseInt(conjugation) - 6,vocalization);
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
                if (Integer.parseInt(conjugation) <= 6) {
                    list = PassivePastGeneratorUn.getInstance().executeSimpleGenerator(root, conjugation);
                } else {
                    list = PassivePastGeneratorAu.getInstance().executeSimpleGenerator(root, Integer.parseInt(conjugation) - 6,vocalization);
                }
            } else {
                if (verbTense.equals("nominativePresent")) {
                    if (Integer.parseInt(conjugation) <= 6) {
                        list = PassivePresentGeneratorUn.getInstance().generateNominative(root, conjugation);
                    } else {
                        list = PassivePresentGeneratorAu.getInstance().getNominative(root, Integer.parseInt(conjugation) - 6,vocalization);
                    }
                } else {
                    if (verbTense.equals("emphasizedPresent")) {
                        if (Integer.parseInt(conjugation) <= 6) {
                            list = PassivePresentGeneratorUn.getInstance().generateEmphasized(root, conjugation);
                        } else {
                            list = PassivePresentGeneratorAu.getInstance().getEmphasized(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                    } else {
                        if (verbTense.equals("jussivePresent")) {
                            if (Integer.parseInt(conjugation) <= 6) {
                                list = PassivePresentGeneratorUn.getInstance().generateJussive(root, conjugation);
                            } else {
                                list = PassivePresentGeneratorAu.getInstance().getJussive(root, Integer.parseInt(conjugation) - 6,vocalization);
                            }
                        } else {
                            if (verbTense.equals("accusativePresent")) {
                                if (Integer.parseInt(conjugation) <= 6) {
                                    list = PassivePresentGeneratorUn.getInstance().generateAccusative(root, conjugation);
                                } else {
                                    list = PassivePresentGeneratorAu.getInstance().getAccusative(root, Integer.parseInt(conjugation) - 6,vocalization);
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
