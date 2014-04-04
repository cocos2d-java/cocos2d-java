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

package cocos2d.actions.action_intervals;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;

import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.ICCCopyable;
import cocos2d.sprite_nodes.CCAnimation;
import cocos2d.sprite_nodes.CCAnimationFrame;
import cocos2d.sprite_nodes.CCSprite;
import cocos2d.sprite_nodes.CCSpriteFrame;


public class CCAnimate extends CCActionInterval
{
    protected CCAnimation m_pAnimation;
    protected Array<Float> m_pSplitTimes = new Array<Float>();
    protected int m_nNextFrame;
    protected CCSpriteFrame m_pOrigFrame;
    private int m_uExecutedLoops;

    public CCAnimate(CCAnimation pAnimation)
    {
        initWithAnimation(pAnimation);
    }

    protected CCAnimate(CCAnimate animate)
    {
    	super(animate);
        initWithAnimation((CCAnimation) animate.m_pAnimation.copy());
    }

    protected boolean initWithAnimation(CCAnimation pAnimation)
    {
        //Debug.Assert(pAnimation != null);

        float singleDuration = pAnimation.getDuration();

        if (super.initWithDuration(singleDuration * pAnimation.getLoops()))
        {
            m_nNextFrame = 0;
            m_pAnimation = pAnimation;
            m_pOrigFrame = null;
            m_uExecutedLoops = 0;

            m_pSplitTimes.ensureCapacity(pAnimation.getFrames().size);

            float accumUnitsOfTime = 0;
            float newUnitOfTimeValue = singleDuration / pAnimation.getTotalDelayUnits();

            Array pFrames = pAnimation.getFrames();

            //TODO: CCARRAY_VERIFY_TYPE(pFrames, CCAnimationFrame *);

            for (Object pObj : pFrames)
            {
            	CCAnimationFrame frame = (CCAnimationFrame) pObj;
                float value = (accumUnitsOfTime * newUnitOfTimeValue) / singleDuration;
                accumUnitsOfTime += frame.getDelayUnits();
                m_pSplitTimes.add(value);
            }
            return true;
        }
        return false;
    }

    @Override
    public Object copy(ICCCopyable pZone)
    {
        if (pZone != null)
        {
            //in case of being called at sub class
        	CCAnimate pCopy = (CCAnimate) (pZone);
            super.copy(pZone);

            pCopy.initWithAnimation((CCAnimation) m_pAnimation.copy());

            return pCopy;
        }
        return new CCAnimate(this);
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        CCSprite pSprite = (CCSprite) (target);

        m_pOrigFrame = null;

        if (m_pAnimation.getRestoreOriginalFrame())
        {
            m_pOrigFrame = pSprite.getDisplayFrame();
        }

        m_nNextFrame = 0;
        m_uExecutedLoops = 0;
    }

    @Override
    public void stop()
    {
        if (m_pAnimation.getRestoreOriginalFrame() && m_pTarget != null)
        {
            ((CCSprite) (m_pTarget)).setDisplayFrame(m_pOrigFrame);
        }

        super.stop();
    }

    @Override
    public void update(float t)
    {
        // if t==1, ignore. Animation should finish with t==1
        if (t < 1.0f)
        {
            t *= m_pAnimation.getLoops();

            // new loop?  If so, reset frame counter
            int loopNumber = (int) t;
            if (loopNumber > m_uExecutedLoops)
            {
                m_nNextFrame = 0;
                m_uExecutedLoops++;
            }

            // new t for animations
            t = t % 1.0f;
        }

        Array frames = m_pAnimation.getFrames();
        int numberOfFrames = frames.size;

        for (int i = m_nNextFrame; i < numberOfFrames; i++)
        {
            float splitTime = m_pSplitTimes.get(i);

            if (splitTime <= t)
            {
            	CCAnimationFrame frame = (CCAnimationFrame) frames.get(i);
                CCSpriteFrame frameToDisplay = frame.getSpriteFrame();
                if (frameToDisplay != null)
                {
                    ((CCSprite) m_pTarget).setDisplayFrame(frameToDisplay);
                }

                //var dict = frame.UserInfo;
//                if (dict != null)
//                {
//                    //TODO: [[NSNotificationCenter defaultCenter] postNotificationName:CCAnimationFrameDisplayedNotification object:target_ userInfo:dict];
//                }
                m_nNextFrame = i + 1;
            }
                // Issue 1438. Could be more than one frame per tick, due to low frame rate or frame delta < 1/FPS
            else
            {
                break;
            }
        }
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        Array<CCAnimationFrame> pOldArray = m_pAnimation.getFrames();
        Array<CCAnimationFrame> pNewArray = new Array<CCAnimationFrame>(pOldArray.size);

        //TODO: CCARRAY_VERIFY_TYPE(pOldArray, CCAnimationFrame*);

        if (pOldArray.size > 0)
        {
            for (int i = pOldArray.size - 1; i >= 0; i--)
            {
            	CCAnimationFrame pElement = (CCAnimationFrame) pOldArray.get(i);
                if (pElement == null)
                {
                    break;
                }
                
                //pNewArray.Add(pElement.copy() as CCAnimationFrame);
                CCAnimationFrame tempAnimationFrame = pElement.copy();
                if (tempAnimationFrame instanceof CCAnimationFrame)
                {
                	pNewArray.add((CCAnimationFrame)tempAnimationFrame);
                }
                else
                {
                	pNewArray.add(null);
				}      
            }
        }

        CCAnimation newAnim = new CCAnimation(pNewArray, m_pAnimation.getDelayPerUnit(), m_pAnimation.getLoops());
        newAnim.setRestoreOriginalFrame(m_pAnimation.getRestoreOriginalFrame());
        return new CCAnimate(newAnim);
    }
}