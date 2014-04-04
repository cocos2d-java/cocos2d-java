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

import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.ICCCopyable;

public class CCScaleTo extends CCActionInterval
{
    protected float m_fDeltaX;
    protected float m_fDeltaY;
    protected float m_fEndScaleX;
    protected float m_fEndScaleY;
    protected float m_fScaleX;
    protected float m_fScaleY;
    protected float m_fStartScaleX;
    protected float m_fStartScaleY;

    protected CCScaleTo(CCScaleTo copy)
    {
    	super(copy);
        m_fEndScaleX = copy.m_fEndScaleX;
        m_fEndScaleY = copy.m_fEndScaleY;
    }

    public CCScaleTo(float duration, float s)
    {
    	super(duration);
        m_fEndScaleX = s;
        m_fEndScaleY = s;
    }

    public CCScaleTo(float duration, float sx, float sy)
    {
    	super(duration);
        m_fEndScaleX = sx;
        m_fEndScaleY = sy;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCScaleTo ret = null;
        	if (zone instanceof CCScaleTo)
        	{
        		ret = (CCScaleTo)zone;
        	}
            super.copy(zone);
            m_fEndScaleX = ret.m_fEndScaleX;
            m_fEndScaleY = ret.m_fEndScaleY;
            return ret;
        }
        else
        {
            return new CCScaleTo(this);
        }
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_fStartScaleX = target.getScaleX();
        m_fStartScaleY = target.getScaleY();
        m_fDeltaX = m_fEndScaleX - m_fStartScaleX;
        m_fDeltaY = m_fEndScaleY - m_fStartScaleY;
    }

    @Override
    public void update(float time)
    {
        if (m_pTarget != null)
        {
            m_pTarget.setScaleX(m_fStartScaleX + m_fDeltaX * time);
            m_pTarget.setScaleY(m_fStartScaleY + m_fDeltaY * time);
        }
    }
}
