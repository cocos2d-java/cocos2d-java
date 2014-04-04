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

package cocos2d.actions.action_ease;

import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.actions.action_intervals.CCActionInterval;
import cocos2d.cocoa.ICCCopyable;

public class CCEaseInOut extends CCEaseRateAction
{
    public CCEaseInOut(CCActionInterval pAction, float fRate)
    {
    	super(pAction, fRate);
    }

    public CCEaseInOut(CCEaseInOut easeInOut)
    {
    	super(easeInOut);
    }

    @Override
    public void update(float time) throws Exception
    {
        time *= 2;

        if (time < 1)
        {
            m_pInner.update(0.5f * (float) Math.pow(time, m_fRate));
        }
        else
        {
            m_pInner.update(1.0f - 0.5f * (float) Math.pow(2 - time, m_fRate));
        }
    }

    @Override
    public Object copy(ICCCopyable pZone)
    {
        if (pZone != null)
        {
            //in case of being called at sub class
        	CCEaseInOut pCopy = null;
        	if (pZone instanceof CCEaseInOut)
        	{
        		pCopy = (CCEaseInOut)pZone;
        	}
            pCopy.initWithAction((CCActionInterval) (m_pInner.copy()), m_fRate);

            return pCopy;
        }
        return new CCEaseInOut(this);
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        return new CCEaseInOut((CCActionInterval) m_pInner.reverse(), m_fRate);
    }
}