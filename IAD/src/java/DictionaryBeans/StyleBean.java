package DictionaryBeans;

/**
 *
 * @author Fadel
 */
public class StyleBean {

    private static String style_green = "/resources/style_green.css";
    private static String style_blue = "/resources/style_blue.css";

    public static String setStyle(Object style){
        if(style!=null){
        if(style.toString().equals("green"))
            return style_green;
        else
            return style_blue;
        }else
            return style_green;
    }
}
