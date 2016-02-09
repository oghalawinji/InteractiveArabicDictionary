/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Quadrilateral;

import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.Noun.quadrilateral.*;
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
        nounType = ajaxReq.getNounType();
        nounPattern = ajaxReq.getNounPattern();
        nounState = ajaxReq.getNounState();

        List list = new ArrayList<String>();

        if (nounType.equals("activeParticiple")) { // اسم الفاعل
            if (Integer.parseInt(conjugation) == 0) {
                if (nounState.equals("definite")) {
                    list = ActiveParticipleGeneratorUn.getInstance().getDefiniteNouns(root);
                }
                if (nounState.equals("indefinite")) {
                    list = ActiveParticipleGeneratorUn.getInstance().getInDefiniteNouns(root);
                }
                if (nounState.equals("annexed")) {
                    list = ActiveParticipleGeneratorUn.getInstance().getAnnexedNouns(root);
                }
            } else {
                if (nounState.equals("definite")) {
                    list = ActiveParticipleGeneratorAu.getInstance().generateDefinite(root, Integer.parseInt(conjugation));
                }
                if (nounState.equals("indefinite")) {
                    list = ActiveParticipleGeneratorAu.getInstance().generateInDefinite(root, Integer.parseInt(conjugation));
                }
                if (nounState.equals("annexed")) {
                    list = ActiveParticipleGeneratorAu.getInstance().generateAnnexed(root, Integer.parseInt(conjugation));
                }
            }
        } else {
            if (nounType.equals("passiveParticiple")) { //اسم المفعول
                if (Integer.parseInt(conjugation) == 0) {
                    if (nounState.equals("definite")) {
                        list = PassiveParticipleGeneratorUn.getInstance().getDefiniteNouns(root);
                    }
                    if (nounState.equals("indefinite")) {
                        list = PassiveParticipleGeneratorUn.getInstance().getInDefiniteNouns(root);
                    }
                    if (nounState.equals("annexed")) {
                        list = PassiveParticipleGeneratorUn.getInstance().getAnnexedNouns(root);
                    }
                } else {
                    if (nounState.equals("definite")) {
                        list = PassiveParticipleGeneratorAu.getInstance().generateDefinite(root, Integer.parseInt(conjugation));
                    }
                    if (nounState.equals("indefinite")) {
                        list = PassiveParticipleGeneratorAu.getInstance().generateInDefinite(root, Integer.parseInt(conjugation));
                    }
                    if (nounState.equals("annexed")) {
                        list = PassiveParticipleGeneratorAu.getInstance().generateAnnexed(root, Integer.parseInt(conjugation));
                    }
                }
            } else {

                if (nounType.equals("timeAndPlace")) { // اسما المكان والزمان
                    if (Integer.parseInt(conjugation) ==0) {
                        if (nounState.equals("definite")) {
                            list = TimeAndPlaceGeneratorUn.getInstance().getDefiniteNouns(root);
                        }
                        if (nounState.equals("indefinite")) {
                            list = TimeAndPlaceGeneratorUn.getInstance().getInDefiniteNouns(root);
                        }
                        if (nounState.equals("annexed")) {
                            list = TimeAndPlaceGeneratorUn.getInstance().getAnnexedNouns(root);
                        }
                    } else {
                        if (nounState.equals("definite")) {
                            list = TimeAndPlaceGeneratorAu.getInstance().getDefiniteNouns(root, Integer.parseInt(conjugation));
                        }
                        if (nounState.equals("indefinite")) {
                            list = TimeAndPlaceGeneratorAu.getInstance().getInDefiniteNouns(root, Integer.parseInt(conjugation));
                        }
                        if (nounState.equals("annexed")) {
                            list = TimeAndPlaceGeneratorAu.getInstance().getAnnexedNouns(root, Integer.parseInt(conjugation));
                        }
                    }
                }
            }
        }
        return list;
    }

    
    
}
