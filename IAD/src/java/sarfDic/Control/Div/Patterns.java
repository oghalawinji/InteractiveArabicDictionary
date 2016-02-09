/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Div;

import java.util.List;

/**
 *
 * @author Gowzancha
 */
public class Patterns {

    public static Patterns getInstance() {
        return new Patterns();
    }

    public String getPatterns(List list) {
        try {
            String res = "<table class=main><tr>";
            for (int i = 0; i < list.size(); i++) {
                if(i==0)
                res += "<td><button disabled='true' id='"+i+"' onclick=setNounPattern(" + i + ","+list.size()+") class=main>" + list.get(i) + "</button></td>";
                else
                    res += "<td><button id='"+i+"' onclick=setNounPattern(" + i + ","+list.size()+") class=main>" + list.get(i) + "</button></td>";
            }
            res += "</tr></table>";
            return res;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
