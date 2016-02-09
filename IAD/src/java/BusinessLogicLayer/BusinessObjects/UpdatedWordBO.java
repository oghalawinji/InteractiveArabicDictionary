/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BusinessObjects;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedWordBO extends WordBO{

    private Integer oldRawWordId;
    private String oldRawWord;

    public UpdatedWordBO() {
        super();
    }

    public String getOldRawWord() {
        return oldRawWord;
    }

    public void setOldRawWord(String oldRawWord) {
        this.oldRawWord = oldRawWord;
    }

    public Integer getOldRawWordId() {
        return oldRawWordId;
    }

    public void setOldRawWordId(Integer oldRawWordId) {
        this.oldRawWordId = oldRawWordId;
    }



}
