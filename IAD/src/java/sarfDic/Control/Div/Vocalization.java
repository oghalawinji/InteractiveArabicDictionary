/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Div;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sarfDic.Ajax.AjaxRequest;

/**
 *
 * @author Gowzancha
 */
public class Vocalization {

    public static Vocalization getInstance() {
        return new Vocalization();
    }

    public String getVocalization(AjaxRequest ajaxReq) {
        String res = " ";
        String root = ajaxReq.getRoot();
        String conjugation = ajaxReq.getConjugation();

        List<String> ex1 = new ArrayList();
        ex1.add("جود");
        ex1.add("حوش");
        ex1.add("حول");
        ex1.add("خوص");
        ex1.add("خول");
        ex1.add("دوء");
        ex1.add("روح");
        ex1.add("رود");
        ex1.add("روض");
        ex1.add("سود");
        ex1.add("سوغ");
        ex1.add("طول");
        ex1.add("عور");
        ex1.add("لوث");
        ex1.add("نوء");

        ex1.add("بيض");
        ex1.add("حين");
        ex1.add("خيف");
        ex1.add("خيل");
        ex1.add("خيم");
        ex1.add("ريف");
        ex1.add("عير");
        ex1.add("عين");
        ex1.add("غيب");
        ex1.add("غيل");
        ex1.add("غيم");
        ex1.add("كيس");
        ex1.add("ليل");
        ex1.add("لين");
        ex1.add("نيء");
        ex1.add("هيج");

        List<String> ex2 = new ArrayList();
        ex2.add("جوب");
        ex2.add("جوف");
        ex2.add("خول");
        ex2.add("روح");
        ex2.add("روض");
        ex2.add("صوب");

        List<String> ex3 = new ArrayList();
        ex3.add("حول");
        ex3.add("روح");
        ex3.add("شور");

        if ((conjugation.equals("7") && ex1.contains(root)) || (conjugation.equals("15") && ex2.contains(root)) || (conjugation.equals("11") && ex3.contains(root))) {// First Forumla in Augmanted
            res = "<table class=main><tr>"
                    + "<td><button id='false' class=main onclick=setVocalization('false')>التصحيح</button></td>"
                    + "<td><button id='true' class=main onclick=setVocalization('true')>الإعلال</button></td>"
                    + "</tr></table>";
        }
        return res;

    }
}
