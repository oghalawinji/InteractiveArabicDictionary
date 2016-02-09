/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer;

/**
 *
 * @author riad
 */
public class SystemStateManager {

    public static boolean available(String mode, String infoStatus, int checkStatus) {
        if( infoStatus.equals("I"))
            return true;//Fadel-Note001:just for test operation....
        return true;
    }

}
