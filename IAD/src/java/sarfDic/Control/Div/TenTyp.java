/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Div;

import sarfDic.Ajax.AjaxRequest;
import sarfDic.Midleware.SarfDictionary;
import java.util.List;
import sarfDic.Midleware.Noun.trilateral.TrilateralUnaugmentedNouns;

/**
 *
 * @author Gowzancha
 */
public class TenTyp {

    public static TenTyp getInstance() {
        return new TenTyp();
    }

    public String getVerbTenses(String verbType) {
        if (verbType.equals("active")) {
            return "<table class=main><tr>"
                    + "<td><button disabled='true' id='past' class=main onclick=setVerbTense('past')>الماضي</button></td>"
                    + "<td><button id='nominativePresent' class=main onclick=setVerbTense('nominativePresent')>المضارع المرفوع</button></td>"
                    + "<td><button id='accusativePresent' class=main onclick=setVerbTense('accusativePresent')>المضارع المنصوب</button></td>"
                    + "<td><button id='jussivePresent' class=main onclick=setVerbTense('jussivePresent')>المضارع المجزوم</button></td>"
                    + "<td><button id='emphasizedPresent' class=main onclick=setVerbTense('emphasizedPresent')>المضارع المؤكد</button></td>"
                    + "<td><button id='normalImperative' class=main onclick=setVerbTense('normalImperative')>الأمر</button></td>"
                    + "<td><button id='emphasizedImperative' class=main onclick=setVerbTense('emphasizedImperative')>الأمر المؤكد</button></td>"
                    + "</tr></table>";
        } else {
            if (verbType.equals("passive")) {
                return "<table class=main><tr>"
                        + "<td><button disabled='true' id='past' class=main onclick=setVerbTense('past')> الماضي المجهول</button></td>"
                        + "<td><button id='nominativePresent' class=main onclick=setVerbTense('nominativePresent')>المضارع المرفوع المجهول</button></td>"
                        + "<td><button id='accusativePresent' class=main onclick=setVerbTense('accusativePresent')>المضارع المنصوب المجهول</button></td>"
                        + "<td><button id='jussivePresent' class=main onclick=setVerbTense('jussivePresent')>المضارع المجزوم المجهول</button></td>"
                        + "<td><button id='emphasizedPresent' class=main onclick=setVerbTense('emphasizedPresent')>المضارع المؤكد المجهول</button></td>"
                        + "</tr></table>";
            }
        }
        return "";
    }

    public String getNounTypes(AjaxRequest ajaxReq) {
        try {
            String rootStr = ajaxReq.getRoot();
            String conjugation = ajaxReq.getConjugation();

            String res = "<table class=main><tr>"
                    + "<td><button disabled='true' id='activeParticiple' class=main onclick=setNounType('activeParticiple')>اسم الفاعل</button></td>"
                    + "<td><button id='passiveParticiple' class=main onclick=setNounType('passiveParticiple')>اسم المفعول</button></td>";

            if (rootStr.length() == 3 && Integer.parseInt(conjugation) <= 6) { // Un Trilateral
                List list = SarfDictionary.getInstance().getUnaugmentedTrilateralRoots(rootStr);
                sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot root;
                for (int i = 0; i < list.size(); i++) {
                    root = (sarf.verb.trilateral.unaugmented.UnaugmentedTrilateralRoot) list.get(i);
                    if (root.getConjugation().equals(conjugation)) {
                        TrilateralUnaugmentedNouns nounsObject = new TrilateralUnaugmentedNouns(root);

                        if (nounsObject.getStandardExaggerations() != null) {
                            if (!nounsObject.getStandardExaggerations().isEmpty()) {
                                res += "<td><button id='exaggeration' class=main onclick=setNounType('exaggeration')>مبالغة اسم الفاعل</button></td>";
                            }
                        }

                        if (nounsObject.getStandardInstrumentals() != null) {
                            if (!nounsObject.getStandardInstrumentals().isEmpty()) {
                                res += "<td><button id='instrumental' class=main onclick=setNounType('instrumental')>اسم الآلة</button></td>";
                            }
                        }

                        if (nounsObject.getTimeAndPlaces() != null) {
                            if (!nounsObject.getTimeAndPlaces().isEmpty()) {
                                res += "<td><button id='timeAndPlace' class=main onclick=setNounType('timeAndPlace')>اسما الزمان والمكان</button></td>";
                            }
                        }

                        if (nounsObject.getElatives() != null) {
                            if (!nounsObject.getElatives().isEmpty()) {
                                res += "<td><button id='elativeNoun' class=main onclick=setNounType('elativeNoun')>اسم التفضيل</button></td>";
                            }
                        }

                        if (nounsObject.getAssimilates() != null) {
                            if (!nounsObject.getAssimilates().isEmpty()) {
                                res += "<td><button id='assimilateAdjective' class=main onclick=setNounType('assimilateAdjective')>الصفات المشبهة</button></td>";
                            }
                        }
                    }
                }
                res += "</tr></table>";
            } else { // Au Trilateral and Un and Au Quadrilateral
                res = "<table class=main><tr>"
                        + "<td><button disabled='true' id='activeParticiple' onclick=setNounType('activeParticiple') class=main>اسم الفاعل</button></td>"
                        + "<td><button id='passiveParticiple' onclick=setNounType('passiveParticiple') class=main>اسم المفعول</button></td>"
                        + "<td><button id='timeAndPlace' onclick=setNounType('timeAndPlace') class=main>اسم الزمان والمكان</button></td>"
                        + "</tr></table>";
            }
            return res;

        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public String getGerundTypes(AjaxRequest ajaxReq) {
        String rootStr = ajaxReq.getRoot();
        String conjugation = ajaxReq.getConjugation();
        String res = "<table class=main><tr>"
                + "<td><button disabled='true' id='standard' onclick=setNounType('standard') class=main>المصدر الأصلي</button></td>"
                + "<td><button id='nomen' onclick=setNounType('nomen') class=main>مصدر المرة</button></td>"
                + "<td><button id='meem' onclick=setNounType('meem') class=main>المصدر الميمي</button></td>";

        if (rootStr.length() == 3 && Integer.parseInt(conjugation) <= 6) { // Un Trilateral
            res += "<td><button id='quality' onclick=setNounType('quality') class=main>مصدر الهيئة</button></td>";
        }
        res += "</tr></table>";
        return res;
    }

    public static void main(String[] args) {
        AjaxRequest ajax = new AjaxRequest();
        ajax.setRoot("ءسف");
        ajax.setConjugation("4");

        TenTyp.getInstance().getNounTypes(ajax);
    }
}
