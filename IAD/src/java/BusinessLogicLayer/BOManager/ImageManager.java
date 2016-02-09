/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.Util.BLUtil;
import PersistenceLayer.Entryimage;
import PersistenceLayer.Semanticentry;
import Util.RawNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riad
 */
public class ImageManager {

    public static List<byte[]> getImage( Integer semanticEntryId){
        try {
            Semanticentry semEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semanticEntryId);
            return getImage(semEntry);
        } catch (RawNotFoundException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static List<byte[]> getImage( Semanticentry semanticEntry){
       List<byte[]> imgList = new ArrayList();
       Set<Entryimage> entryImageSet = semanticEntry.getEntryimages();
        for( Iterator iter = entryImageSet.iterator(); iter.hasNext();){
            byte[] img = ((Entryimage)iter.next()).getImage().getImage();
            imgList.add( img );
        }
       return imgList;
    }
/*
    public static void deleteImage( Integer semEntryId, Integer imageId){
        EntryimageHibernateDAO relationDao = BLUtil.daoFactory.getEntryimageDAO();
        Semanticentry semanticEntry = BLUtil.daoFactory.getSemanticentryDAO().getById(semEntryId);
        Set<Entryimage> entryImages = semanticEntry.getEntryimages();
        for( Iterator iter = entryImages.iterator(); iter.hasNext(); ){
            Entryimage entryImage = (Entryimage) iter.next();
            //relationDao.
        }

    }
 */
}
