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

public class CCSequence extends CCActionInterval
{
    protected int m_last;
    protected CCFiniteTimeAction[] m_pActions = new CCFiniteTimeAction[2];
    protected float m_split;
    private boolean _HasInfiniteAction = false;

    public CCSequence(CCFiniteTimeAction action1, CCFiniteTimeAction action2)
    {
        initOneTwo(action1, action2);
    }

    public CCSequence(CCFiniteTimeAction... actions)
    {
        CCFiniteTimeAction prev = actions[0];

        if (actions.length == 1)
        {
            initOneTwo(prev, new CCExtraAction());
        }
        else
        {
            for (int i = 1; i < actions.length - 1; i++)
            {
                prev = new CCSequence(prev, actions[i]);
            }

            initOneTwo(prev, actions[actions.length - 1]);
        }
    }

    protected CCSequence(CCSequence sequence)
    {
    	super(sequence);
    	//可能性能有缺失，未来去优化
    	CCFiniteTimeAction param1 = null;
    	if (sequence.m_pActions[0].copy() instanceof CCFiniteTimeAction)
    	{
    		param1 = (CCFiniteTimeAction)sequence.m_pActions[0].copy();
    	}
    	
    	CCFiniteTimeAction param2 = null;
    	if (sequence.m_pActions[1].copy() instanceof CCFiniteTimeAction)
    	{
    		param2 = (CCFiniteTimeAction)sequence.m_pActions[1].copy();
    	}
    	
        initOneTwo(param1, param2);
    }

    protected boolean initOneTwo(CCFiniteTimeAction actionOne, CCFiniteTimeAction actionTwo)
    {
        //Debug.Assert(actionOne != null);
        //Debug.Assert(actionTwo != null);

        float d = actionOne.getDuration() + actionTwo.getDuration();
        super.initWithDuration(d);

        m_pActions[0] = actionOne;
        m_pActions[1] = actionTwo;

        _HasInfiniteAction = (actionOne instanceof CCRepeatForever) || (actionTwo instanceof CCRepeatForever);

        return true;
    }

    @Override
    public boolean isDone()
    { 
        if (_HasInfiniteAction && m_pActions[m_last] instanceof CCRepeatForever)
        {
            return (false);
        }
        return super.isDone();  
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        ICCCopyable tmpZone = zone;
        CCSequence ret = null;

        if (tmpZone != null && tmpZone != null)
        {
        	if (tmpZone instanceof CCSequence)
        	{
        		ret = (CCSequence)tmpZone;
        	}
        	
            if (ret == null)
            {
                return null;
            }
            super.copy(tmpZone);

            CCFiniteTimeAction param1 = null;
            if (m_pActions[0].copy() instanceof CCFiniteTimeAction)
            {
            	param1 = (CCFiniteTimeAction)(m_pActions[0].copy());
            }
            CCFiniteTimeAction param2 = null;
            if (m_pActions[1].copy() instanceof CCFiniteTimeAction)
            {
            	param2 = (CCFiniteTimeAction)(m_pActions[1].copy());
            }
            
            if (param1 == null || param2 == null)
            {
                return null;
            }

            ret.initOneTwo(param1, param2);

            return ret;
        }
        else
        {
            return new CCSequence(this);
        }
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_split = m_pActions[0].getDuration() / m_fDuration;
        m_last = -1;
    }

    @Override
    public void stop()
    {
        // Issue #1305
        if (m_last != - 1)
        {
            m_pActions[m_last].stop();
        }

        super.stop();
    }

    @Override
    public void step(float dt) throws Exception
    {
        if (m_last > -1 && (m_pActions[m_last] instanceof CCRepeat || m_pActions[m_last] instanceof CCRepeatForever))
        {
            // Repeats are step based, not update
            m_pActions[m_last].step(dt);
        }
        else
        {
            super.step(dt);
        }
    }

    @Override
    public void update(float t) throws Exception
    {
        boolean bRestart = false;
        int found;
        float new_t;

        if (t < m_split)
        {
            // action[0]
            found = 0;
            if (m_split != 0)
                new_t = t / m_split;
            else
                new_t = 1;
        }
        else
        {
            // action[1]
            found = 1;
            if (m_split == 1)
                new_t = 1;
            else
                new_t = (t - m_split) / (1 - m_split);
        }

        if (found == 1)
        {
            if (m_last == -1)
            {
                // action[0] was skipped, execute it.
                m_pActions[0].startWithTarget(m_pTarget);
                m_pActions[0].update(1.0f);
                m_pActions[0].stop();
            }
            else if (m_last == 0)
            {
                // switching to action 1. stop action 0.
                m_pActions[0].update(1.0f);
                m_pActions[0].stop();
            }
        }
        else if (found == 0 && m_last == 1)
        {
            // Reverse mode ?
            // XXX: Bug. this case doesn't contemplate when _last==-1, found=0 and in "reverse mode"
            // since it will require a hack to know if an action is on reverse mode or not.
            // "step" should be overriden, and the "reverseMode" value propagated to inner Sequences.
            m_pActions[1].update(0);
            m_pActions[1].stop();
        }
        // Last action found and it is done.
        if (found == m_last && m_pActions[found].isDone())
        {
            return;
        }

        // Last action found and it is done
        if (found != m_last || bRestart)
        {
            m_pActions[found].startWithTarget(m_pTarget);
        }

        m_pActions[found].update(new_t);
        m_last = found;
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        return new CCSequence(m_pActions[1].reverse(), m_pActions[0].reverse());
    }
}
