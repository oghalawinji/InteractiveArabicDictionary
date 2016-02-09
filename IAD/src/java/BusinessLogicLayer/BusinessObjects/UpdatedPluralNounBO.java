/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedPluralNounBO extends PluralNounBO{

    String oldPlural;
    String oldPluralType;

    public UpdatedPluralNounBO() {
        super();
    }

    public String getOldPlural() {
        return oldPlural;
    }

    public void setOldPlural(String oldPlural) {
        this.oldPlural = oldPlural;
    }

    public String getOldPluralType() {
        return oldPluralType;
    }

    public void setOldPluralType(String oldPluralType) {
        this.oldPluralType = oldPluralType;
    }

}
