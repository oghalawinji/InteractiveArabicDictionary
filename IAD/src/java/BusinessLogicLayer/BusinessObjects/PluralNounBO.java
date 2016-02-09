/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author riad
 */
public class PluralNounBO implements java.io.Serializable {
    String plural;
    String pluralType;

    public PluralNounBO() {
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public String getPluralType() {
        return pluralType;
    }

    public void setPluralType(String pluralType) {
        this.pluralType = pluralType;
    }

}
