package sarfDic.Ajax;

import java.io.Serializable;

/**
 *
 * @author ahmad
 */
public class AjaxRequest implements Serializable {

    private String root = "";// The root    
    private String conjugation = ""; // verb pattern
    private String inflection = "";// noun, gerund, active, passive
    private String vocalization = "";// true, false
    private String verbTense = "";// normalImperative, emphsizedImperative, past, normalPresent...
    private String nounType = "";// gerund type (normal, meem, ..) or noun type (ActiveParticiple, Exaggeration, ..)
    private String nounPattern = "";
    private String nounState = "";// InDefinite, Definite, Annexed

    public AjaxRequest() {
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String value) {

        String arabicAlphabet = "دجحخهعغفقثصضشسيبلاتنمكطظزوةىرؤءئذ";
        boolean needEncoding = true;

        for (int i = 0; i < arabicAlphabet.length(); i++) {
            if (value.contains(arabicAlphabet.charAt(i) + "")) {
                needEncoding = false;
                break;
            }
        }
        
        if (value.contains("?")) {
            needEncoding = true;
        }

        if (needEncoding) {
            root = BeansUtil.getFormatedText(value);
        } else {
            root = value;
        }
    }

    public String getConjugation() {
        return conjugation;
    }

    public void setConjugation(String value) {
        conjugation = value;
    }

    public String getInflection() {
        return inflection;
    }

    public void setInflection(String value) {
        inflection = value;
    }

    public String getVocalization() {
        return vocalization;
    }

    public void setVocalization(String value) {
        vocalization = value;
    }

    public String getVerbTense() {
        return verbTense;
    }

    public void setVerbTense(String value) {
        verbTense = value;
    }

    public String getNounType() {
        return nounType;
    }

    public void setNounType(String value) {
        nounType = value;
    }

    public String getNounPattern() {
        return nounPattern;
    }

    public void setNounPattern(String value) {
        nounPattern = value;
    }

    public String getNounState() {
        return nounState;
    }

    public void setNounState(String value) {
        nounState = value;
    }
}
