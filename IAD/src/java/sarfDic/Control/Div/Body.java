/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sarfDic.Control.Div;

import Controller.SearchController;
import java.util.ArrayList;
import java.util.List;
import sarfDic.Ajax.AjaxRequest;


/*
import BusinessLogicLayer.BusinessObjects.*;
import Controller.*;
import BusinessLogicLayer.SearchService.SemanticSearch.*;
 */
/**
 *
 * @author Gowzancha
 */
public class Body {

    public static Body getInstance() {
        return new Body();
    }

    public String getMainTrilateral(List list) {
        if (list != null) {
            String res = "<table class='main'><tr><td>"
                    + "<div class='banner'>الأفعال الثلاثية المجردة</div>"
                    + "<table class=mainUn id='triUn'>"
                    + "<tr>"
                    + "<td>فعَل يفعُل</td>"
                    + "<td>فعَل يفعِل</td>"
                    + "<td>فعَل يفعَل</td>"
                    + "<td>فعِل يفعَل</td>"
                    + "<td>فعُل يفعُل</td>"
                    + "<td>فعِل يفعِل</td>"
                    + "</tr><tr>"
                    + "<td>1</td>"
                    + "<td>2</td>"
                    + "<td>3</td>"
                    + "<td>4</td>"
                    + "<td>5</td>"
                    + "<td>6</td>"
                    + "</tr>"
                    + "</table>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>"
                    + "<div class=banner>الأفعال الثلاثية المزيدة بحرف</div>"
                    + "<table class=mainAu1 id='triAu1'>"
                    + "<tr>"
                    + "<td>أفْعَل يُفْعِل</td>"
                    + "<td>فعَّل يُفعِّل</td>"
                    + "<td>فاعَل يُفاعِل</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>7</td>"
                    + "<td>8</td>"
                    + "<td>9</td>"
                    + "</tr>"
                    + "</table>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>"
                    + "<div class=banner>الأفعال الثلاثية المزيدة بحرفين</div>"
                    + "<table class=mainAu2 id='triAu2'>"
                    + "<tr>"
                    + "<td>انْفَعَل يَنْفَعِل</td>"
                    + "<td>افْتَعَل يَفْتَعِل</td>"
                    + "<td>افْعَلَّ يَفْعَلُّ</td>"
                    + "<td>تَفاعَل يَتَفاعَل</td>"
                    + "<td>تَفَعَّل يَتَفَعَّل</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>10</td>"
                    + "<td>11</td>"
                    + "<td>12</td>"
                    + "<td>13</td>"
                    + "<td>14</td>"
                    + "</tr>"
                    + "</table>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>"
                    + "<div class=banner>الأفعال الثلاثية المزيدة بثلاث أحرف</div>"
                    + "<table class=mainAu3 id='triAu3'>"
                    + "<tr>"
                    + "<td>اسْتَفْعَل يَسْتَفْعِل</td>"
                    + "<td>افْعَوْعَل يَفْعَوْعِل</td>"
                    + "<td>افْعَوَّل يَفْعَوِّل</td>"
                    + "<td>افْعَالَّ يَفْعَالُّ</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>15</td>"
                    + "<td>16</td>"
                    + "<td>17</td>"
                    + "<td>18</td>"
                    + "</tr>"
                    + "</table>"
                    + " </td>"
                    + "</tr>"
                    + "</table>";


            for (int i = 0; i < 18; i++) {
                if (list.get(i) != "") {
                    res = res.replace("<td>" + (1 + i) + "</td>", "<td><button onclick=setConjugation(" + (i + 1) + ") class='main'>" + list.get(i) + "</button></td>");
                } else {
                    res = res.replace("<td>" + (1 + i) + "</td>", "<td></td>");
                }
            }


            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).equals("")) {
                    break;
                }
                if (i == list.size() - 1) {
                    return "<table class=main><tr><td class=outputText>لا يوجد نتائج للبحث</td></tr></table>";
                }
            }
            return res;
        } else {
            return "<table class=main><tr><td class=outputText>لا يوجد نتائج للبحث</td></tr></table>";
        }
    }

    public String getMainQuadrilateral(List list) {
        if (list != null) {
            String res = "<table class='main'>"
                    + "<tr>"
                    + "<td>"
                    + "<div class=banner>الأفعال الرباعية المجردة</div>"
                    + "<table id='quaUn'>"
                    + "<tr><td>فَعْلَل يُفَعْلِل</td></tr>"
                    + "<tr><td>0</td></tr>"
                    + "</table>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>"
                    + "<div class=banner>الأفعال الرباعية المزيدة بحرف</div>"
                    + "<table id='quaAu1'>"
                    + "<tr><td>تَفعْلَل يَتفَعْلَل</td></tr>"
                    + "<tr><td>1</td></tr>"
                    + "</table>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>"
                    + "<div class=banner>الأفعال الرباعية المزيدة بحرفين</div>"
                    + "<table id='quaAu2'>"
                    + "<tr>"
                    + "<td>افْعَلَلّ يَفعَلِلّ</td>"
                    + "<td>افْعَنْلَل يَفْعَنْلِل</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>3</td>"
                    + "<td>2</td>"
                    + "</tr>"
                    + "<table>"
                    + "</td>"
                    + "</tr>"
                    + "</table>";

            for (int i = 0; i < 4; i++) {
                if (list.get(i) != "") {
                    res = res.replace("<td>" + i + "</td>", "<td><button onclick=setConjugation(" + i + ") class='main'>" + list.get(i) + "</button></td>");
                } else {
                    res = res.replace("<td>" + i + "</td>", "<td></td>");
                }
            }



            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).equals("")) {
                    break;
                }
                if (i == list.size() - 1) {
                    return "<table class=main><tr><td class=outputText>لا يوجد نتائج للبحث</td></tr></table>";
                }
            }
            return res;
        } else {
            return "<table class=main><tr><td class=outputText>لا يوجد نتائج للبحث</td></tr></table>";
        }
    }

    public String getNouns(List list) {
        try {
            if (list != null) {
                return "<table  width=100% hieght=100% class=main>"
                        + "<tr>"
                        + "<td width=25%></td>"
                        + "<td width=25%></td>"
                        + "<td width=25% class=banner>مذكر</td>"
                        + "<td width=25% class=banner>مؤنث</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=3>الرفع</td>"
                        + "<td rowspan=1>مفرد</td>"
                        + "<td rowspan=1>" + list.get(0) + "</td>"
                        + "<td rowspan=1>" + list.get(1) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=1>مثنى</td>"
                        + "<td rowspan=1>" + list.get(2) + "</td>"
                        + "<td rowspan=1>" + list.get(3) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=1>جمع</td>"
                        + "<td rowspan=1>" + list.get(4) + "</td>"
                        + "<td rowspan=1>" + list.get(5) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=3>النصب</td>"
                        + "<td rowspan=1>مفرد</td>"
                        + "<td rowspan=1>" + list.get(6) + "</td>"
                        + "<td rowspan=1>" + list.get(7) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=1>مثنى</td>"
                        + "<td rowspan=1>" + list.get(8) + "</td>"
                        + "<td rowspan=1>" + list.get(9) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=1>جمع</td>"
                        + "<td rowspan=1>" + list.get(10) + "</td>"
                        + "<td rowspan=1>" + list.get(11) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=3>الجر</td>"
                        + "<td rowspan=1>مفرد</td>"
                        + "<td rowspan=1>" + list.get(12) + "</td>"
                        + "<td rowspan=1>" + list.get(13) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=1>مثنى</td>"
                        + "<td rowspan=1>" + list.get(14) + "</td>"
                        + "<td rowspan=1>" + list.get(15) + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td rowspan=1>جمع</td>"
                        + "<td rowspan=1>" + list.get(16) + "</td>"
                        + "<td rowspan=1>" + list.get(17) + "</td>"
                        + "</tr>"
                        + "</table>";
            }
            return "List is NULL";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String getVerbs(List list) {
        return "<table class='main'>"
                + "<tr>"
                + "<td>أنا</td>"
                + "<td>" + list.get(0) + "</td>"
                + "<td>هو</td>"
                + "<td>" + list.get(7) + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>نحن</td>"
                + "<td>" + list.get(1) + "</td>"
                + "<td>هي</td>"
                + "<td>" + list.get(8) + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>أنتَ</td>"
                + "<td>" + list.get(2) + "</td>"
                + "<td>هما(مذ)</td>"
                + "<td>" + list.get(9) + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>أنتِ</td>"
                + "<td>" + list.get(3) + "</td>"
                + "<td>هما(مؤ)</td>"
                + "<td>" + list.get(10) + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>أنتما</td>"
                + "<td>" + list.get(4) + "</td>"
                + "<td>هم</td>"
                + "<td>" + list.get(11) + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>أنتم</td>"
                + "<td>" + list.get(5) + "</td>"
                + "<td>هنَّ</td>"
                + "<td>" + list.get(12) + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td>أنتنَّ</td>"
                + "<td>" + list.get(6) + "</td>"
                + "<td></td>"
                + "<td></td>"
                + "</tr>"
                + "</table>";
    }

    public String rootIsNotValidResponse(AjaxRequest ajaxReq) {
        String res = "<table class=main><tr>";
        List<String> roots = SearchController.getRoots(ajaxReq.getRoot());
        if (roots.size() > 1) {
            for (int p = 0; p < roots.size(); p++) {
                res += "<td><a href='#' onclick=document.getElementById('root').value='" + roots.get(p) + "';setRoot();>" + roots.get(p) + "</a></td>";
            }
        } else {
            List<String> options = SearchController.getOptions(ajaxReq.getRoot(), true);// true=byRoot
            if (options.size() != 0) {
                res +="<td class=outputText>هل تقصد:</td>";
                for (int p = 0; p < options.size(); p++) {
                    res += "<td><a href='#' onclick=document.getElementById('root').value='" + options.get(p) + "';setRoot();>" + options.get(p) + "</a></td>";
                }
            }else{
                res+="<td class=outputText>لا يوجد نتائج للبحث</td>";
            }
        }
        res += "</table></tr>";
        return res;
    }
}
