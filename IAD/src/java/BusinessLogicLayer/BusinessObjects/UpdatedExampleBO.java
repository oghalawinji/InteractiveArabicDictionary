/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BusinessObjects;

import java.util.List;

/**
 *
 * @author Waleed Al-Hasan
 */
public class UpdatedExampleBO extends ExampleBO {

    private Integer newExampleId;
    private String newExample;
    private String newSource;
    private List<byte[]> newSound;

    public UpdatedExampleBO() {
        super();
    }

    public String getNewExample() {
        return newExample;
    }

    public void setNewExample(String newExample) {
        this.newExample = newExample;
    }

    public Integer getNewExampleId() {
        return newExampleId;
    }

    public void setNewExampleId(Integer newExampleId) {
        this.newExampleId = newExampleId;
    }

    public String getNewSource() {
        return newSource;
    }

    public void setNewSource(String newSource) {
        this.newSource = newSource;
    }

    public List<byte[]> getNewSound() {
        return newSound;
    }

    public void setNewSound(List<byte[]> newSound) {
        this.newSound = newSound;
    }
}
