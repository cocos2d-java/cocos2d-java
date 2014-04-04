package cocos2d.denshion;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class CCSimpleAudioEngine
{

    // The list of sounds that are configured for looping. These need to be stopped when the game pauses.
    private ArrayMap<Integer, Integer> _LoopedSounds = new ArrayMap<Integer, Integer>();

    /**The shared sound effect list. The key is the hashcode of the file path.*/
    public static ArrayMap<Integer, CCEffectPlayer> sharedList()
    {
        return (s_List);
    }

    /**The shared music player.*/
    private static CCMusicPlayer sharedMusic()
    {
        return(s_Music); 
    }

    /** The singleton instance of this class.*/
    public static CCSimpleAudioEngine sharedEngine()
    {
        return _Instance;
    }

    public float getBackgroundMusicVolume()
    {
        return sharedMusic().getVolume();
    }

    public void setBackgroundMusicVolume(float volume)
    {
        sharedMusic().setVolume(volume);
    }
    
//    /**The volume of the effects max value is 1.0,the min value is 0.0*/
//    public void setEffectsVolume(float value)
//    {
//        CCEffectPlayer.setVolume(value);
//    }
    
    public static String FullPath(String szPath)
    {
        // todo: return self now
        return szPath;
    }

    /**
     Release the shared Engine object
     <p>Warn: It must be called before the application exit, or a memroy leak will be casued.</p>
    */
    public void end ()
    {
        sharedMusic().close ();

        synchronized (sharedList())
        {
        	Iterator<Entry<Integer, CCEffectPlayer>> iter = sharedList().entries().iterator();
        	while (iter.hasNext())
        	{
        		Entry<Integer, CCEffectPlayer> entry =  iter.next();
        		entry.value.close();
        	}
        	
            sharedList().clear ();
        }
    }

   
//    /** Restore the media player's state to how it was prior to the game launch. You need to do this when the game terminates
//     if you run music that clobbers the music that was playing before the game launched.*/
//    public void restoreMediaState()
//    {
//        sharedMusic().restoreMediaState();
//    }


//    /** Save the media player's current playback state.*/
//    public void saveMediaState()
//    {
//        sharedMusic().saveMediaState();
//    }
    
    
    /**

    /**
     @brief Preload background music
     @param pszFilePath The path of the background music file,or the FileName of T_SoundResInfo
     */

    public void preloadBackgroundMusic(String pszFilePath)
    {
        sharedMusic().open(FullPath(pszFilePath), pszFilePath.hashCode());
    }

    /**
    @brief Play background music
    @param pszFilePath The path of the background music file,or the FileName of T_SoundResInfo
    @param bLoop Whether the background music loop or not
    */

    public void playBackgroundMusic(String pszFilePath, boolean bLoop)
    {
        if (null == pszFilePath)
        {
            return;
        }

        sharedMusic().open(FullPath(pszFilePath), pszFilePath.hashCode());
        sharedMusic().play(bLoop);
    }

    /**
    @brief Play background music
    @param pszFilePath The path of the background music file,or the FileName of T_SoundResInfo
    */

    public void playBackgroundMusic(String pszFilePath)
    {
        playBackgroundMusic(pszFilePath, false);
    }

    /**
    @brief Stop playing background music
    @param bReleaseData If release the background music data or not.As default value is false
    */

    public void stopBackgroundMusic(boolean bReleaseData)
    {
        if (bReleaseData)
        {
            sharedMusic().close();
        }
        else
        {
            sharedMusic().stop();
        }
    }

    /**
    @brief Stop playing background music
    */

    public void stopBackgroundMusic()
    {
        stopBackgroundMusic(false);
    }

    /**
    @brief Pause playing background music
    */

    public void pauseBackgroundMusic()
    {
        sharedMusic().pause();
    }

    /**
    @brief Resume playing background music
    */

    public void resumeBackgroundMusic()
    {
        sharedMusic().resume();
    }

    /**
    @brief Rewind playing background music
    */

    public void rewindBackgroundMusic()
    {
        sharedMusic().rewind();
    }

    public boolean willPlayBackgroundMusic()
    {
        return false;
    }

    /**
    @brief Whether the background music is playing
    @return If is playing return true,or return false
    */

    public boolean isBackgroundMusicPlaying()
    {
        return sharedMusic().isPlaying();
    }

    public void pauseEffect(int fxid) 
    {
        try
        {
            if (sharedList().containsKey(fxid))
            {
                sharedList().get(fxid).pause();
            }
        }
        catch (Exception ex)
        {
//            CCLog.Log("Unexpected exception while playing a SoundEffect: {0}", fxid);
//            CCLog.Log(ex.ToString());
        }
    }

//    public void stopAllEffects()
//    {
//        Array<CCEffectPlayer> l = new Array<CCEffectPlayer>();
//
//        synchronized (sharedList())
//        {
//            try
//            {
//                l.AddRange(sharedList().Values);
//                SharedList.Clear();
//            }
//            catch (Exception ex)
//            {
//                CCLog.Log("Unexpected exception while stopping all effects.");
//                CCLog.Log(ex.ToString());
//            }
//        }
//        for (CCEffectPlayer p : l)
//        {
//            p.Stop();
//        }
//
//    }


    /// <summary>
    /// Play the sound effect with the given path and optionally set it to lopo.
    /// </summary>
    /// <param name="pszFilePath">The path to the sound effect file.</param>
    /// <param name="bLoop">True if the sound effect will play continuously, and false if it will play then stop.</param>
    /// <returns></returns>
    public int PlayEffect (String pszFilePath)
    {
        int nId = pszFilePath.hashCode ();

        preloadEffect (pszFilePath);

        synchronized (sharedList())
        {
            try
            {
                if (sharedList().containsKey(nId))
                {
                    sharedList().get(nId).play();
                   
                }
            }
            catch (Exception ex)
            {
               
            }
        }

        return nId;
    }
    

    /// <summary>
    /// Stops the sound effect with the given id. 
    /// </summary>
    /// <param name="nSoundId"></param>
    public void stopEffect(int nSoundId)
    {
        synchronized (sharedList())
        {
            if (sharedList().containsKey(nSoundId))
            {
            	sharedList().get(nSoundId).stop();
            }
        }
        synchronized (_LoopedSounds)
        {
            if (_LoopedSounds.containsKey(nSoundId))
            {
                _LoopedSounds.removeKey(nSoundId);
            }
        }
    }

//    /** Stops all of the sound effects that are currently playing and looping.*/
//    public void stopAllLoopingEffects()
//    {
//        synchronized (sharedList())
//        {
//            if (_LoopedSounds.size > 0)
//            {
//                int[] a = new int[_LoopedSounds.keys.length];
//                _LoopedSounds.Keys.CopyTo(a, 0);
//                for (int key : a)
//                {
//                    stopEffect(key);
//                }
//            }
//        }
//    }

    /**
    @brief  		preload a compressed audio file
    @details	    the compressed audio will be decode to wave, then write into an 
    internal buffer in SimpleaudioEngine
    */


    /// <summary>
    /// Load the sound effect found with the given path. The sound effect is only loaded one time and the
    /// effect is cached as an instance of EffectPlayer.
    /// </summary>
    public void preloadEffect(String pszFilePath)
    {
        if (pszFilePath == null || pszFilePath == "")
        {
            return;
        }

        int nId = pszFilePath.hashCode();
        synchronized (sharedList())
        {
            if (sharedList().containsKey(nId))
            {
                return;
            }
        }
        CCEffectPlayer eff = new CCEffectPlayer();
        eff.open(FullPath(pszFilePath), nId);
        sharedList().put(nId, eff);
    }

    /**
    @brief  		unload the preloaded effect from internal buffer
    @param[in]		pszFilePath		The path of the effect file,or the FileName of T_SoundResInfo
    */

    public void unloadEffect (String pszFilePath)
    {
        int nId = pszFilePath.hashCode ();
        synchronized (sharedList()) {
            if (sharedList().containsKey(nId))
            {
            	sharedList().removeKey(nId);
            }
        }
        synchronized (_LoopedSounds)
        {
            if (_LoopedSounds.containsKey(nId))
            {
                _LoopedSounds.removeKey(nId);
            }
        }
    }

    private static ArrayMap<Integer, CCEffectPlayer> s_List = new ArrayMap<Integer,CCEffectPlayer>();
    private static CCMusicPlayer s_Music = new CCMusicPlayer();
    private static CCSimpleAudioEngine _Instance = new CCSimpleAudioEngine();
}