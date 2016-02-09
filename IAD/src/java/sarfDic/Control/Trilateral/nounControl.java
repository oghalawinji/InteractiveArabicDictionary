/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Trilateral;

import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.Noun.trilateral.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gowzancha
 */
public class nounControl {

    public static nounControl getInstance() {
        return new nounControl();
    }
    public String root = "";
    public String conjugation = "";
    public String nounType = "";
    public String nounPattern = "";
    public String nounState = "";

    public List nounController(AjaxRequest ajaxReq) {
        root = ajaxReq.getRoot();
        conjugation = ajaxReq.getConjugation();
        //String inflection = ajaxReq.getInflection();
        boolean vocalization=Boolean.parseBoolean(ajaxReq.getVocalization());
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList<String>();

        if (nounType.equals("activeParticiple")) { // اسم الفاعل
            if (Integer.parseInt(conjugation) <= 6) {
                if (nounState.equals("definite")) {
                    list = ActiveParticipleGeneratorUn.getInstance().getDefiniteNouns(root, conjugation);
                }
                if (nounState.equals("indefinite")) {
                    list = ActiveParticipleGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation);
                }
                if (nounState.equals("annexed")) {
                    list = ActiveParticipleGeneratorUn.getInstance().getAnnexedNouns(root, conjugation);
                }
            } else {
                if (nounState.equals("definite")) {
                    list = ActiveParticipleGeneratorAu.getInstance().generateDefinite(root, Integer.parseInt(conjugation) - 6,vocalization);
                }
                if (nounState.equals("indefinite")) {
                    list = ActiveParticipleGeneratorAu.getInstance().generateInDefinite(root, Integer.parseInt(conjugation) - 6,vocalization);
                }
                if (nounState.equals("annexed")) {
                    list = ActiveParticipleGeneratorAu.getInstance().generateAnnexed(root, Integer.parseInt(conjugation) - 6,vocalization);
                }
            }
        } else {
            if (nounType.equals("exaggeration")) { //مبالغة اسم الفاعل
                if (nounState.equals("definite")) {
                    list = ExaggerationGeneratorUn.getInstance().getDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                }
                if (nounState.equals("indefinite")) {
                    list = ExaggerationGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                }
                if (nounState.equals("annexed")) {
                    list = ExaggerationGeneratorUn.getInstance().getAnnexedNouns(root, conjugation, Integer.parseInt(nounPattern));
                }
            } else {
                if (nounType.equals("passiveParticiple")) { //اسم المفعول
                    if (Integer.parseInt(conjugation) <= 6) {
                        if (nounState.equals("definite")) {
                            list = PassiveParticipleGeneratorUn.getInstance().getDefiniteNouns(root, conjugation);
                        }
                        if (nounState.equals("indefinite")) {
                            list = PassiveParticipleGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation);
                        }
                        if (nounState.equals("annexed")) {
                            list = PassiveParticipleGeneratorUn.getInstance().getAnnexedNouns(root, conjugation);
                        }
                    } else {
                        if (nounState.equals("definite")) {
                            list = PassiveParticipleGeneratorAu.getInstance().generateDefinite(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                        if (nounState.equals("indefinite")) {
                            list = PassiveParticipleGeneratorAu.getInstance().generateInDefinite(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                        if (nounState.equals("annexed")) {
                            list = PassiveParticipleGeneratorAu.getInstance().generateAnnexed(root, Integer.parseInt(conjugation) - 6,vocalization);
                        }
                    }
                } else {
                    if (nounType.equals("assimilateAdjective")) { //الصفات المشبهة
                        if (nounState.equals("definite")) {
                            list = AssimilateAdjectiveGeneratorUn.getInstance().getDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                        }
                        if (nounState.equals("indefinite")) {
                            list = AssimilateAdjectiveGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                        }
                        if (nounState.equals("annexed")) {
                            list = AssimilateAdjectiveGeneratorUn.getInstance().getAnnexedNouns(root, conjugation, Integer.parseInt(nounPattern));
                        }
                    } else {
                        if (nounType.equals("instrumental")) { // اسم الآلة
                            if (nounState.equals("definite")) {
                                list = InstrumentalGeneratorUn.getInstance().getDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                            }
                            if (nounState.equals("indefinite")) {
                                list = InstrumentalGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                            }
                            if (nounState.equals("annexed")) {
                                list = InstrumentalGeneratorUn.getInstance().getAnnexedNouns(root, conjugation, Integer.parseInt(nounPattern));
                            }
                        } else {
                            if (nounType.equals("timeAndPlace")) { // اسما المكان والزمان
                                if (Integer.parseInt(conjugation) <= 6) {
                                    if (nounState.equals("definite")) {
                                        list = TimeAndPlaceGeneratorUn.getInstance().getDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                                    }
                                    if (nounState.equals("indefinite")) {
                                        list = TimeAndPlaceGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation, Integer.parseInt(nounPattern));
                                    }
                                    if (nounState.equals("annexed")) {
                                        list = TimeAndPlaceGeneratorUn.getInstance().getAnnexedNouns(root, conjugation, Integer.parseInt(nounPattern));
                                    }
                                } else {
                                    if (nounState.equals("definite")) {
                                        list = TimeAndPlaceGeneratorAu.getInstance().getDefiniteNouns(root, Integer.parseInt(conjugation) - 6,vocalization);
                                    }
                                    if (nounState.equals("indefinite")) {
                                        list = TimeAndPlaceGeneratorAu.getInstance().getInDefiniteNouns(root, Integer.parseInt(conjugation) - 6,vocalization);
                                    }
                                    if (nounState.equals("annexed")) {
                                        list = TimeAndPlaceGeneratorAu.getInstance().getAnnexedNouns(root, Integer.parseInt(conjugation) - 6,vocalization);
                                    }
                                }
                            } else {
                                if (nounType.equals("elativeNoun")) {// اسم التفضيل
                                    if (nounState.equals("definite")) {
                                        list = ElativeNounGeneratorUn.getInstance().getDefiniteNouns(root, conjugation);
                                    }
                                    if (nounState.equals("indefinite")) {
                                        list = ElativeNounGeneratorUn.getInstance().getInDefiniteNouns(root, conjugation);
                                    }
                                    if (nounState.equals("annexed")) {
                                        list = ElativeNounGeneratorUn.getInstance().getAnnexedNouns(root, conjugation);
                                    }
                                    if (nounState.equals("notannexed")) {
                                        list = ElativeNounGeneratorUn.getInstance().getNotAnnexedNouns(root, conjugation);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return list;
    }

    public List getNounPatternsList(AjaxRequest ajaxReq) {
        root = ajaxReq.getRoot();
        conjugation = ajaxReq.getConjugation();
        //String inflection = ajaxReq.getInflection();
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList();
        if (Integer.parseInt(conjugation) <= 6) {
            if (nounType.equals("activeParticiple")) {
                list.add("فَاعِل");
            }

            if (nounType.equals("passiveParticiple")) {
                list.add("مَفْعُول");
            }

            if (nounType.equals("exaggeration")) {
                list = ExaggerationGeneratorUn.getInstance().getFormulas(root, conjugation);
            }

            if (nounType.equals("assimilateAdjective")) {
                list = AssimilateAdjectiveGeneratorUn.getInstance().getFormulas(root, conjugation);
            }

            if (nounType.equals("instrumental")) {
                list = InstrumentalGeneratorUn.getInstance().getFormulas(root, conjugation);
            }

            if (nounType.equals("timeAndPlace")) {
                list = TimeAndPlaceGeneratorUn.getInstance().getFormulas(root, conjugation);
            }

            if (nounType.equals("elativeNoun")) {
                list.add("أفْعَل");
            }

        }
        return list;
    }
}
