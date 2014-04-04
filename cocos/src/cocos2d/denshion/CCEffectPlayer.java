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
import com.badlogic.gdx.audio.Sound;

public class CCEffectPlayer
{
	private Sound m_effect;
	private int m_nSoundId;
	private long m_idOfSoundInstance;
	
	public CCEffectPlayer()
    {
        m_nSoundId = 0;
    }
	
	
	public void setVolume(float volume)
    {
        if (volume >= 0.0f && volume <= 1.0f)
        {
        	m_effect.setVolume(m_idOfSoundInstance, volume);
        }
    }
	
	public void open(String pFileName, int uId)
    {
		if (pFileName.isEmpty() || pFileName == null)
        {
            return;
        }
		
		m_effect = Gdx.audio.newSound(Gdx.files.internal(pFileName));
		
		//Close();
		
		m_nSoundId = uId;
    }
	
	public void close()
    {
		if (m_effect != null)
		{
			m_effect.dispose();
	        m_effect = null;
		}
		
    }
	
	public void play()
	{
		if (null == m_effect)
        {
            return;
        }
		m_idOfSoundInstance = m_effect.play();
	}
	
	public void pause()
    {
		m_effect.stop();
    }
	
	public void stop()
    {
		m_effect.stop();
    }
}
