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
import cocos2d.actions.action_instants.CCActionInstant;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.ICCCopyable;

public class CCRepeat extends CCActionInterval
{
    protected boolean m_bActionInstant;
    protected float m_fNextDt;
    protected CCFiniteTimeAction m_pInnerAction;
    protected int m_uTimes;
    protected int m_uTotal;


    public CCRepeat(CCFiniteTimeAction action, int times)
    {
        initWithAction(action, times);
    }

    protected CCRepeat(CCRepeat repeat)
    {
    	super(repeat);
    	CCFiniteTimeAction param = null;
    	if (repeat.m_pInnerAction.copy() instanceof CCFiniteTimeAction)
    	{
    		param = (CCFiniteTimeAction)(repeat.m_pInnerAction.copy());
    	}
        initWithAction(param, repeat.m_uTimes);
    }

    public CCFiniteTimeAction getInnerAction()
    {
        return m_pInnerAction;
    }

    public void setInnerAction(CCFiniteTimeAction value)
    {
        m_pInnerAction = value;
    }
    
    public boolean initWithAction(CCFiniteTimeAction action, int times)
    {
        float d = action.getDuration() * times;

        if (super.initWithDuration(d))
        {
            m_uTimes = times;
            m_pInnerAction = action;

            m_bActionInstant = action instanceof CCActionInstant;
            //an instant action needs to be executed one time less in the update method since it uses startWithTarget to execute the action
            if (m_bActionInstant)
            {
                m_uTimes -= 1;
            }
            m_uTotal = 0;

            return true;
        }

        return false;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCRepeat ret = null;
        	if (zone instanceof CCRepeat)
        	{
        		ret = (CCRepeat)zone;
        	}
            if (ret == null)
            {
                return null;
            }
            super.copy(zone);

            CCFiniteTimeAction param = null;
            if (m_pInnerAction.copy() instanceof CCFiniteTimeAction)
            {
            	param = (CCFiniteTimeAction)(m_pInnerAction.copy());
            }
            if (param == null)
            {
                return null;
            }
            ret.initWithAction(param, m_uTimes);

            return ret;
        }
        else
        {
            return new CCRepeat(this);
        }
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        m_uTotal = 0;
        m_fNextDt = m_pInnerAction.getDuration() / m_fDuration;
        super.startWithTarget(target);
        m_pInnerAction.startWithTarget(target);
    }

    @Override
    public void stop()
    {
        m_pInnerAction.stop();
        super.stop();
    }

    // issue #80. Instead of hooking step:, hook update: since it can be called by any 
    // container action like Repeat, Sequence, AccelDeccel, etc..
    @Override
    public void update(float dt) throws Exception
    {
        if (dt >= m_fNextDt)
        {
            while (dt > m_fNextDt && m_uTotal < m_uTimes)
            {
                m_pInnerAction.update(1.0f);
                m_uTotal++;

                m_pInnerAction.stop();
                m_pInnerAction.startWithTarget(m_pTarget);
                m_fNextDt += m_pInnerAction.getDuration() / m_fDuration;
            }

            // fix for issue #1288, incorrect end value of repeat
            if (dt >= 1.0f && m_uTotal < m_uTimes)
            {
                m_uTotal++;
            }

            // don't set an instant action back or update it, it has no use because it has no duration
            if (!m_bActionInstant)
            {
                if (m_uTotal == m_uTimes)
                {
                    m_pInnerAction.update(1f);
                    m_pInnerAction.stop();
                }
                else
                {
                    // issue #390 prevent jerk, use right update
                    m_pInnerAction.update(dt - (m_fNextDt - m_pInnerAction.getDuration() / m_fDuration));
                }
            }
        }
        else
        {
            m_pInnerAction.update((dt * m_uTimes) % 1.0f);
        }
    }

    @Override
    public boolean isDone()
    {
        return m_uTotal == m_uTimes;
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        return new CCRepeat(m_pInnerAction.reverse(), m_uTimes);
    }
}