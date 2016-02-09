/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sarfDic.Midleware;

import java.util.List;

/**
 *
 * @author riad
 */
public interface Generator {
    public List<String> executeSimpleGenerator( String root);
    public List<String> executeDetailedGenerator( String root);
}