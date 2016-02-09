/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Div;

/**
 *
 * @author Gowzancha
 */
public class Properties {

    public static Properties getInstance() {
        return new Properties();
    }

    public String getProperties(String kovDesc, String transitive, String verb) {
        if (!kovDesc.equals("")) {
            if (transitive.equals("") && verb.equals("")) {
                return "<table class=main>"
                        + "<tr>"
                        + "<td class=main></td>"
                        + "<td class=banner>نوع الفعل المجرد</td>"
                        + "<td>" + kovDesc + "</td>"
                        + "<td class=banner>التعدية واللزوم</td>"
                        + "<td class=main></td>"
                        + "</tr>"
                        + "</table>";
            } else {
                if (transitive.equals("ك")) {
                    transitive = "لازم ومتعد";
                } else {
                    if (transitive.equals("م")) {
                        transitive = "متعد";
                    } else {
                        if (transitive.equals("ل")) {
                            transitive = "لازم";
                        }
                    }
                }

                return "<table class=main>"
                        + "<tr>"
                        + "<td>" + verb + "</td>"
                        + "<td class=banner>نوع الفعل المجرد</td>"
                        + "<td>" + kovDesc + "</td>"
                        + "<td class=banner>التعدية واللزوم</td>"
                        + "<td>" + transitive + "</td>"
                        + "</tr>"
                        + "</table>";

            }
        } else {
            return " ";
        }
    }
}
