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

import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.ICCCopyable;

public class CCBlink extends CCActionInterval
{
	//数据类型有待确定
	//protected uint m_nTimes;
    protected int m_nTimes;
    protected boolean m_bOriginalState;

    public CCBlink(float duration, int uBlinks)
    {
        initWithDuration(duration, uBlinks);
    }

    protected CCBlink(CCBlink blink)
    {
    	super(blink);
        initWithDuration(m_fDuration, m_nTimes);
    }

    protected boolean initWithDuration(float duration, int uBlinks)
    {
        if (super.initWithDuration(duration))
        {
            m_nTimes = uBlinks;
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
        	CCBlink pCopy = (CCBlink) (pZone);
            super.copy(pZone);

            pCopy.initWithDuration(m_fDuration, m_nTimes);
            return pCopy;
        }
        else
        {
            return new CCBlink(this);
        }
    }

    @Override
    public void stop()
    {
        m_pTarget.setVisible(m_bOriginalState);
        super.stop();
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_bOriginalState = target.getVisible();
    }

    @Override
    public void update(float time)
    {
        if (m_pTarget != null && ! isDone())
        {
            float slice = 1.0f / m_nTimes;
            // float m = fmodf(time, slice);
            float m = time % slice;
            m_pTarget.setVisible(m > (slice / 2));
        }
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        return new CCBlink(m_fDuration, m_nTimes);
    }
}
