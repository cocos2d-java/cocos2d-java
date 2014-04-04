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

public class CCRepeatForever extends CCActionInterval
{
    protected CCActionInterval m_pInnerAction;

    public CCActionInterval getInnerAction()
    {
        return m_pInnerAction;
    }

    public void setInnerAction(CCActionInterval value)
    {
        m_pInnerAction = value;
    }
    public CCRepeatForever(CCActionInterval action)
    {
        initWithAction(action);
    }

    protected CCRepeatForever(CCRepeatForever repeatForever)
    {
    	super(repeatForever);
    	CCActionInterval param = null;
    	if (repeatForever.m_pInnerAction.copy() instanceof CCActionInterval)
    	{
    		param = (CCActionInterval)(repeatForever.m_pInnerAction.copy());
    	}
        initWithAction(param);
    }

    protected boolean initWithAction(CCActionInterval action)
    {
        //Debug.Assert(action != null);
        m_pInnerAction = action;
        // Duration = action.Duration;
        return true;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCRepeatForever ret = null;
        	if (zone instanceof CCRepeatForever)
        	{
        		ret = (CCRepeatForever)zone;
        	}
            if (ret == null)
            {
                return null;
            }
            super.copy(zone);

            CCActionInterval param = null;
            if (m_pInnerAction.copy() instanceof CCActionInterval)
            {
            	param = (CCActionInterval)(m_pInnerAction.copy());
            }
            if (param == null)
            {
                return null;
            }
            ret.initWithAction(param);

            return ret;
        }
        else
        {
            return new CCRepeatForever(this);
        }
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_pInnerAction.startWithTarget(target);
    }

    @Override
    public void step(float dt) throws Exception
    {
        m_pInnerAction.step(dt);

        if (m_pInnerAction.isDone())
        {
            float diff = m_pInnerAction.getElapsed() - m_pInnerAction.getDuration();
            m_pInnerAction.startWithTarget(m_pTarget);
            m_pInnerAction.step(0f);
            m_pInnerAction.step(diff);
        }
    }

    @Override
    public boolean isDone()
    {
        return false;
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
    	if (m_pInnerAction.reverse() instanceof CCActionInterval)
    	{
    		return new CCRepeatForever((CCActionInterval)(m_pInnerAction.reverse()));
    	}
    	else
    	{
			return new CCRepeatForever(null);
		}
    }
}
