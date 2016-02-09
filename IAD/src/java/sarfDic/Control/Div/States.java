/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Div;

import sarfDic.Ajax.AjaxRequest;

/**
 *
 * @author Gowzancha
 */
public class States {

    public static States getInstance() {
        return new States();
    }

    public String getStates(AjaxRequest ajaxReq) {
        String res = "";
        if (ajaxReq.getNounType().equals("elativeNoun")) {
            res = "<table class=main><tr>"
                    + "<td><button disabled='true' id='indefinite' class=main onclick=setNounState('indefinite') >المضاف إلى نكرة</button></td>"
                    + "<td><button id='annexed' class=main onclick=setNounState('annexed')>المضاف إلى معرفة</button></td>"
                    + "<td><button id='definite' class=main onclick=setNounState('definite') >المعرف بأل</button></td>"
                    + "<td><button id='notannexed' class=main onclick=setNounState('notannexed')>غير المضاف</button></td>"
                    + "</tr></table>";
        } else {
            res = "<table class=main><tr>"
                    + "<td><button disabled='true' id='indefinite' class=main onclick=setNounState('indefinite')>في حالة النكرة</button></td>"
                    + "<td><button id='annexed' class=main onclick=setNounState('annexed') >في حالة الإضافة</button></td>"
                    + "<td><button id='definite' class=main onclick=setNounState('definite') >في حالة المعرفة</button></td>"
                    + "</tr></table>";
        }
        return res;
    }
}
