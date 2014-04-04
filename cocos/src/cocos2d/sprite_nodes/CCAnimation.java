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

import com.badlogic.gdx.utils.Array;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.ICCCopyable;
import cocos2d.textures.CCTexture2D;

public class CCAnimation implements ICCCopyable
{
    protected boolean m_bRestoreOriginalFrame;
    protected float m_fDelayPerUnit;
    protected float m_fTotalDelayUnits;
    protected Array<CCAnimationFrame> m_pFrames;
    protected int m_uLoops;

    public CCAnimation()
    {
    	this (new Array<CCSpriteFrame>(), 0);
    }

//    public CCAnimation(CCSpriteSheet cs, String[] frames, float delay)
//    {
//    	ArrayList<CCSpriteFrame> l = new ArrayList<CCSpriteFrame>();
//        for(String f : frames) 
//        {
//            CCSpriteFrame cf = cs[f];
//            if (cf != null)
//            {
//                l.Add(cs[f]);
//            }
//        }
//        initWithSpriteFrames(l, delay);
//    }
//
//    public CCAnimation(CCSpriteSheet cs, float delay)
//    {
//        InitWithSpriteFrames(cs.Frames, delay);
//    }

    public CCAnimation (Array<CCSpriteFrame> frames, float delay)
    {
        initWithSpriteFrames(frames, delay);
    }
    
    public CCAnimation (Array<CCAnimationFrame> arrayOfAnimationFrameNames, float delayPerUnit, int loops)
    {
        initWithAnimationFrames(arrayOfAnimationFrameNames, delayPerUnit, loops);
    }

    public float getDuration()
    {
        return m_fTotalDelayUnits * m_fDelayPerUnit;
    }

    public float getDelayPerUnit()
    {
        return m_fDelayPerUnit;
    }

    public void setDelayPerUnit(float value)
    {
        m_fDelayPerUnit = value;
    }
    
    public Array<CCAnimationFrame> getFrames()
    {
        return m_pFrames;
    }

    public boolean getRestoreOriginalFrame()
    {
        return m_bRestoreOriginalFrame;
    }

    public void setRestoreOriginalFrame(boolean value)
    {
        m_bRestoreOriginalFrame = value;
    }
    
    public int getLoops()
    {
        return m_uLoops;
    }

    public void setLoops(int value)
    {
        m_uLoops = value;
    }
    
    public float getTotalDelayUnits()
    {
        return m_fTotalDelayUnits;
    }

    public boolean initWithSpriteFrames(Array<CCSpriteFrame> pFrames, float delay)
    {
        if (pFrames != null)
        {/*
            foreach (object frame in pFrames)
            {
                Debug.Assert(frame is CCSpriteFrame, "element type is wrong!");
            }
          */
        }

        m_uLoops = 1;
        m_fDelayPerUnit = delay;
        m_pFrames = new Array<CCAnimationFrame>();

        if (pFrames != null)
        {
            for (CCSpriteFrame pObj : pFrames)
            {
            	CCSpriteFrame frame = (CCSpriteFrame) pObj;
            	CCAnimationFrame animFrame = new CCAnimationFrame();
                animFrame.initWithSpriteFrame(frame, 1);
                m_pFrames.add(animFrame);

                m_fTotalDelayUnits++;
            }
        }

        return true;
    }

    public boolean initWithAnimationFrames(Array<CCAnimationFrame> arrayOfAnimationFrames, float delayPerUnit, int loops)
    {
        if (arrayOfAnimationFrames != null)
        {/*
            foreach (object frame in arrayOfAnimationFrames)
            {
                Debug.Assert(frame is CCAnimationFrame, "element type is wrong!");
            }
          */
        }

        m_fDelayPerUnit = delayPerUnit;
        m_uLoops = loops;

        m_pFrames = new Array<CCAnimationFrame>(arrayOfAnimationFrames);

        for (CCAnimationFrame pObj : m_pFrames)
        {
        	CCAnimationFrame animFrame = (CCAnimationFrame) pObj;
            m_fTotalDelayUnits += animFrame.getDelayUnits();
        }

        return true;
    }

    public void addSprite(CCSprite sprite)
    {
        CCSpriteFrame f = new CCSpriteFrame(sprite.getTexture(), new CCRect(0, 0, sprite.getContentSize().width, sprite.getContentSize().height));
        addSpriteFrame(f);
    }

    public void addSpriteFrame(CCSpriteFrame pFrame)
    {
    	CCAnimationFrame animFrame = new CCAnimationFrame();
        animFrame.initWithSpriteFrame(pFrame, 1.0f);
        m_pFrames.add(animFrame);

        // update duration
        m_fTotalDelayUnits++;
    }

//    public void AddSpriteFrameWithFileName(string pszFileName)
//    {
//        CCTexture2D pTexture = CCTextureCache.SharedTextureCache.AddImage(pszFileName);
//        CCRect rect = CCRect.Zero;
//        rect.Size = pTexture.ContentSize;
//        CCSpriteFrame pFrame = new CCSpriteFrame(pTexture, rect);
//        AddSpriteFrame(pFrame);
//    }

    public void addSpriteFrameWithTexture(CCTexture2D pobTexture, CCRect rect)
    {
        CCSpriteFrame pFrame = new CCSpriteFrame(pobTexture, rect);
        addSpriteFrame(pFrame);
    }

	public CCAnimation copy()
	{
		return (CCAnimation)copy(null);
	}

    public Object copy(ICCCopyable pZone)
    {
        CCAnimation pCopy = null;
        if (pZone != null)
        {
            //in case of being called at sub class
            pCopy = (CCAnimation) (pZone);
        }
        else
        {
            pCopy = new CCAnimation();
        }

        pCopy.initWithAnimationFrames(m_pFrames, m_fDelayPerUnit, m_uLoops);
        pCopy.setRestoreOriginalFrame(m_bRestoreOriginalFrame);

        return pCopy;
    }
}
