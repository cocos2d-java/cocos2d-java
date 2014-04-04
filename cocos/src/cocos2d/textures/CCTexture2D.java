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

package cocos2d.textures;

import cocos2d.cocoa.CCSize;

import com.badlogic.gdx.graphics.Texture;

public class CCTexture2D {
	private Texture2D m_Texture2D;
	private CCSize m_tContentSize = new CCSize(0,0);
	
	private void initWithTexture(Texture texture){
		m_Texture2D = new Texture2D(texture);
		m_tContentSize.width = m_Texture2D.getTexture().getWidth();
		m_tContentSize.height = m_Texture2D.getTexture().getHeight();
	}
	
	public Boolean initWithFile(String file){
		
		m_Texture2D = new Texture2D(file);
		m_tContentSize.width = m_Texture2D.getTexture().getWidth();
		m_tContentSize.height = m_Texture2D.getTexture().getHeight();
		
		return true;
	}
	
	public void dispose(){
		if (m_Texture2D != null){
			m_Texture2D.dispose();
		}
		m_Texture2D = null;
	}
	
	public Texture2D getTexture(){
		return  m_Texture2D;
	}
	
	public CCSize getContentSize()
    {
        return m_tContentSize.pixelsToPoints();
    }
}
