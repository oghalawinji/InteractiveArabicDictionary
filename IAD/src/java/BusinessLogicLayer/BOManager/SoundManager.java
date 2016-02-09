/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogicLayer.BOManager;

import BusinessLogicLayer.BOManager.Util.BOManagerUtil;
import BusinessLogicLayer.Util.BLUtil;
import PersistenceLayer.Entryimage;
import PersistenceLayer.Entrysound;
import PersistenceLayer.Example;
import PersistenceLayer.Examplesound;
import PersistenceLayer.Semanticentry;
import PersistenceLayer.Sound;
import PersistenceLayer.Suggestion;
import Util.RawNotFoundException;
import java.sql.Date;
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
public class SoundManager
{

    public static List<byte[]> getSound ( Semanticentry semanticEntry )
    {
        List<byte[]> soundsList = new ArrayList ();
        Set<Entrysound> entrySoundSet = semanticEntry.getEntrysounds ();
        for ( Iterator iter = entrySoundSet.iterator () ; iter.hasNext () ; )
        {
            byte[] sound = ( ( Entrysound ) iter.next () ).getSound ().getSound ();
            soundsList.add ( sound );
        }
        return soundsList;
    }

    public static List<byte[]> getSoundOfExample ( Integer exampleId )
    {
        try
        {
            Example example = BLUtil.daoFactory.getExampleDAO ().getById ( exampleId );
            return getSoundsOfExample ( example );
        }
        catch ( RawNotFoundException ex )
        {
            Logger.getLogger ( SoundManager.class.getName () ).log ( Level.SEVERE , null , ex );
            return null;
        }
    }

    public static List<byte[]> getSoundsOfExample ( Example example )
    {
        List<byte[]> soundsList = new ArrayList ();
        Set<Examplesound> exampleSoundSet = example.getExamplesounds ();
        for ( Iterator iter = exampleSoundSet.iterator () ; iter.hasNext () ; )
        {
            byte[] sound = ( ( Examplesound ) iter.next () ).getSound ().getSound ();
            soundsList.add ( sound );
        }
        return soundsList;
    }

    public static int suggestAddSound ( byte[] sound ) 
    {
        try
        {
            Sound newSound = new Sound ();
            newSound.setSound ( sound );
            newSound.setChechStatus ( BOManagerUtil.ADDING_STATUS.getCheckStatus () );
            newSound.setInfoStatus ( BOManagerUtil.ADDING_STATUS.getInfoStatus () );
            newSound.setSuggestion ( BOManagerUtil.GET_ADD_SUGGESTION () );
            return BLUtil.daoFactory.getSoundDAO ().insertWithCheck ( newSound , BOManagerUtil.GENERAL_EXCLUDE_PROPERTIES , BOManagerUtil.getAddRestrictions () );
        }
        catch ( RawNotFoundException ex )
        {
            Logger.getLogger ( SoundManager.class.getName() ).log ( Level.SEVERE , null , ex );
            return -1;
        }
    }
}
