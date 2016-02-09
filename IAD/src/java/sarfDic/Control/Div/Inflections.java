/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Control.Div;

/**
 *
 * @author Gowzancha
 */
public class Inflections {

    public static Inflections getInstance(){
        return new Inflections();
    }

    public String getInflections(){
        return "<table class='main'><tr>" +
                "<td><button id='active' onclick=setInflection('active') class='main'>الأفعال المبنية للمعلوم</button></td>" +
                "<td><button id='passive' onclick=setInflection('passive') class='main'>الأفعال المبنية للمجهول</button></td>" +
                "<td><button id='nouns' onclick=setInflection('nouns') class='main'>الأسماء المشتقة</button></td>" +
                "<td><button id='gerunds' onclick=setInflection('gerunds') class='main'>المصادر</button></td>" +
                "</tr></table>";
    }
}
