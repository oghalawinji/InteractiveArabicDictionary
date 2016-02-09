/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BOManager;

import PersistenceLayer.Entryimage;
import PersistenceLayer.Entryvideo;
import PersistenceLayer.Semanticentry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author riad
 */
public class VideoManager {

    public static List<byte[]> getVideo( Semanticentry semanticEntry){
       List<byte[]> videoList = new ArrayList();
       Set<Entryvideo> entryVideoSet = semanticEntry.getEntryvideos();
        for( Iterator iter = entryVideoSet.iterator(); iter.hasNext();){
            byte[] video = ((Entryvideo)iter.next()).getVideo().getVideo();
            videoList.add( video );
        }
       return videoList;
    }
}
