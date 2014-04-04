/******************************************************************************
 * Copyright (c) 2014 cocos2d-java.org
 * 
 *   http://www.cocos2d-java.org
 *   
 *   The MIT License (MIT)
 *      
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:

 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.

 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *******************************************************************************/

package cocos2d.denshion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class CCMusicPlayer
{
	private Music m_music;
	private int m_nSoundId;
	
	private boolean m_didPlayGameSong;
	
	public float getVolume()
    { 
        return m_music.getVolume(); 
    }
	
	/** Sets the volume of this music stream. The volume must be given in the range [0,1] with 0 being silent and 1 being the
	 * maximum volume.
	 * 
	 * @param volume */
	public void setVolume(float volume)
    {
        if (volume >= 0.0f && volume <= 1.0f)
        {
            m_music.setVolume(volume);
        }
    }
	
	public void open(String pFileName, int uId)
	{
		if (pFileName.isEmpty() || pFileName == null)
        {
            return;
        }
		
		m_music = Gdx.audio.newMusic(Gdx.files.internal(pFileName));
		
		m_nSoundId = uId;
	}
	
	public void play(boolean bLoop)
    {
        if (null != m_music)
        {
        	m_music.setLooping(bLoop);
        	m_music.play();
        	m_didPlayGameSong = true;
        }
    }
	
	 public void play()
     {
		 play(false);
     }
	 
	 public void close()
	 {
		 if (m_music != null)
		 {
			 m_music.dispose();
		 }
		 m_music = null;
	 }
	 
	 /** Pauses the current song being played. */
     public void pause()
     {
    	 m_music.pause();
     }
     
     /** Resumes playback of the current song. */
     public void resume()
     {
    	 m_music.play();
     }
     
     /** Stops playback of the current song and resets the playback position to zero.*/
     public void stop()
     {
    	 m_music.stop();
     }
     
     /** resets the playback of the current song to its beginning. */
     public void rewind()
     {
         if (null != m_music)
         {
        	 m_music.setPan(-1, getVolume());
         }
     }
     
     /**Returns true if any song is playing in the media player, even if it is not one
        of the songs in the game.*/
     public boolean isPlaying()
     {
         return m_music.isPlaying();
     }
}
