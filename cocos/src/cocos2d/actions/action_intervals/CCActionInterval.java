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

public class CCActionInterval extends CCFiniteTimeAction
{
    protected Boolean m_bFirstTick = false;
    protected float m_elapsed;

    protected CCActionInterval()
    {
    }

    public CCActionInterval(float d)
    {
        initWithDuration(d);
    }

    protected CCActionInterval(CCActionInterval actionInterval)
    {
    	super(actionInterval);
        initWithDuration(actionInterval.m_fDuration);
    }

    public float getElapsed()
    {
        return m_elapsed;
    }

    protected boolean initWithDuration(float d)
    {
        m_fDuration = d;

        // prevent division by 0
        // This comparison could be in step:, but it might decrease the performance
        // by 3% in heavy based action games.
        if (m_fDuration == 0)
        {
            //m_fDuration = float.Epsilon;
        	m_fDuration = 1.401298E-45f;
        }

        m_elapsed = 0;
        m_bFirstTick = true;

        return true;
    }

    @Override
    public boolean isDone()
    {
        return m_elapsed >= m_fDuration;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCActionInterval ret = (CCActionInterval) (zone);
            super.copy(zone);

            ret.initWithDuration(m_fDuration);
            return ret;
        }
        else
        {
            return new CCActionInterval(this);
        }
    }

    @Override
    public void step(float dt) throws Exception
    {
        if (m_bFirstTick)
        {
            m_bFirstTick = false;
            m_elapsed = 0f;
        }
        else
        {
            m_elapsed += dt;
        }

        update(Math.max(0f,
                        Math.min(1, m_elapsed /
                                    Math.max(m_fDuration, 1.401298E-45f)
                            )
                   )
            );
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_elapsed = 0.0f;
        m_bFirstTick = true;
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        //throw new NotImplementedException();
    	return null;
    }

    public float getAmplitudeRate()
    {
        //Debug.Assert(false);
        return 0;
    }
    
    public void setAmplitudeRate(float value)
    {     
        //Debug.Assert(false); 
    }
}