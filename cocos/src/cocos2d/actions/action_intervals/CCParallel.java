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

public class CCParallel extends CCActionInterval
{
    protected CCFiniteTimeAction[] m_pActions;

    public CCParallel()
    {
    }

    /** Constructs the parallel sequence from the given array of actions.*/
    public CCParallel(CCFiniteTimeAction... actions)
    {
        m_pActions = actions;
        float duration = 0f;
        for (int i = 0; i < m_pActions.length; i++)
        {
            float actionDuration = m_pActions[i].getDuration();
            if (duration < actionDuration)
            {
                duration = actionDuration;
            }
        }

        for (int i = 0; i < m_pActions.length; i++)
        {
        	float actionDuration = m_pActions[i].getDuration();
            if (actionDuration < duration)
            {
                m_pActions[i] = new CCSequence(m_pActions[i], new CCDelayTime(duration - actionDuration));
            }
        }

        super.initWithDuration(duration);
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        for (int i = 0; i < m_pActions.length; i++)
        {
            m_pActions[i].startWithTarget(target);
        }
    }

    public CCParallel(CCParallel copy)
    {
    	super(copy);
        CCFiniteTimeAction[] cp = new CCFiniteTimeAction[copy.m_pActions.length];
        for (int i = 0; i < copy.m_pActions.length; i++)
        {
        	if (copy.m_pActions[i].copy() instanceof CCFiniteTimeAction)
        	{
        		cp[i] = (CCFiniteTimeAction)(copy.m_pActions[i].copy());
        	}
        	else
        	{
        		cp[i] = null;
			}
        }
        m_pActions = cp;
    }

    /** Reverses the current parallel sequence.*/
    @Override
    public CCFiniteTimeAction reverse()
    {
        CCFiniteTimeAction[] rev = new CCFiniteTimeAction[m_pActions.length];
        for (int i = 0; i < m_pActions.length; i++)
        {
            rev[i] = m_pActions[i].reverse();
        }

        return new CCParallel(rev);
    }

    /** Makea full copy of this object and does not make any reference copies.*/
    @Override
    public Object copy(ICCCopyable zone)
    {
        ICCCopyable tmpZone = zone;
        CCParallel ret = null;

        if (tmpZone != null && tmpZone != null)
        {
        	if (zone instanceof CCParallel)
        	{
        		ret = (CCParallel)zone;
        	}
            super.copy(zone);

            CCFiniteTimeAction[] cp = new CCFiniteTimeAction[m_pActions.length];
            for (int i = 0; i < m_pActions.length; i++)
            {
            	if (m_pActions[i].copy() instanceof CCFiniteTimeAction)
            	{
            		cp[i] = (CCFiniteTimeAction)(m_pActions[i].copy());
            	}
            }
            ret.m_pActions = cp;
            return ret;
        }
        else
        {
            return new CCParallel(this);
        }
    }

    @Override
    public void stop()
    {
        for (int i = 0; i < m_pActions.length; i++)
        {
            m_pActions[i].stop();
        }
        super.stop();
    }

    @Override
    public void update(float time) throws Exception
    {
        for (int i = 0; i < m_pActions.length; i++)
        {
            m_pActions[i].update(time);
        }
    }
}