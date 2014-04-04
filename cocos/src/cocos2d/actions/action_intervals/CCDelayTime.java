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
import cocos2d.cocoa.ICCCopyable;

public class CCDelayTime extends CCActionInterval
{
    public CCDelayTime(float d)
    {
        initWithDuration(d);
    }

    protected CCDelayTime(CCDelayTime delayTime)
    {
    	super(delayTime);
    }

    @Override
    public Object copy(ICCCopyable pZone)
    {
        if (pZone != null)
        {
            //in case of being called at sub class
        	CCDelayTime pCopy = (CCDelayTime) (pZone);
            super.copy(pZone);

            return pCopy;
        }
        else
        {
            return new CCDelayTime(this);
        }
    }

    @Override
    public void update(float time)
    {
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        return new CCDelayTime(m_fDuration);
    }
}