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

package cocos2d.sprite_nodes;

import cocos2d.cocoa.ICCCopyable;

public class CCAnimationFrame implements ICCCopyable
{
    private float m_fDelayUnits;
    private CCSpriteFrame m_pSpriteFrame;
    //private PlistDictionary m_pUserInfo;

    public CCSpriteFrame getSpriteFrame()
    {
        return m_pSpriteFrame;
    }

    public float getDelayUnits()
    {
        return m_fDelayUnits;
    }

//    public PlistDictionary UserInfo
//    {
//        get { return m_pUserInfo; }
//    }

	public CCAnimationFrame copy()
	{
		return (CCAnimationFrame)copy(null);
	}

    public Object copy(ICCCopyable pZone)
    {
        CCAnimationFrame pCopy;
        if (pZone != null)
        {
            //in case of being called at sub class
            pCopy = (CCAnimationFrame) (pZone);
        }
        else
        {
            pCopy = new CCAnimationFrame();
        }

        pCopy.initWithSpriteFrame((CCSpriteFrame) m_pSpriteFrame.copy(), m_fDelayUnits);

        return pCopy;
    }

    public boolean initWithSpriteFrame(CCSpriteFrame spriteFrame, float delayUnits)
    {
        m_pSpriteFrame = spriteFrame;
        m_fDelayUnits = delayUnits;
        //m_pUserInfo = userInfo;
        return true;
    }
}