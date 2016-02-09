/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BOManager;

/**
 *
 * @author riad
 */
public class BOManagerFactory {
    public static VerbBOManager VERB_BOMANAGER = new VerbBOManager();
    public static NounBOManager NOUN_BOMANAGER = new NounBOManager();
}
