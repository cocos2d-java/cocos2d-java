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

public class CCActionTween extends CCActionInterval
{
    protected float m_fDelta;
    protected float m_fFrom, m_fTo;
    protected String m_strKey;

    public CCActionTween(float aDuration, String key, float from, float to)
    {
        initWithDuration(aDuration, key, from, to);
    }

    protected boolean initWithDuration(float aDuration, String key, float from, float to)
    {
        if (super.initWithDuration(aDuration))
        {
            m_strKey = key;
            m_fTo = to;
            m_fFrom = from;
            return true;
        }

        return false;
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        //Debug.Assert(target is ICCActionTweenDelegate, "target must implement CCActionTweenDelegate");
        super.startWithTarget(target);
        m_fDelta = m_fTo - m_fFrom;
    }

    @Override
    public void update(float dt)
    {
        ((ICCActionTweenDelegate) m_pTarget).UpdateTweenAction(m_fTo - m_fDelta * (1 - dt), m_strKey);
    }

    @Override
    public  CCFiniteTimeAction reverse()
    {
        return new CCActionTween(m_fDuration, m_strKey, m_fTo, m_fFrom);
    }
}