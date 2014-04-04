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
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.ICCCopyable;

public class CCMoveBy extends CCActionInterval
{
    protected CCPoint m_positionDelta = new CCPoint();
    protected CCPoint m_endPosition = new CCPoint();
    protected CCPoint m_startPosition = new CCPoint();
    protected CCPoint m_previousPosition = new CCPoint();

    public CCMoveBy(float duration, CCPoint position) 
    {
        initWithDuration(duration, position);
    }

    protected CCMoveBy(CCMoveBy moveBy)
    {
    	super(moveBy);
        initWithDuration(moveBy.m_fDuration, moveBy.m_positionDelta);
    }

    protected boolean initWithDuration(float duration, CCPoint position)
    {
        if (super.initWithDuration(duration))
        {
            m_positionDelta = new CCPoint(position);
            return true;
        }
        return false;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCMoveBy ret = null;                	
            if (zone instanceof CCMoveBy)
            {
            	ret = (CCMoveBy)zone;
            }

            if (ret == null)
            {
                return null;
            }

            super.copy(zone);

            ret.initWithDuration(m_fDuration, m_positionDelta);

            return ret;
        }
        else
        {
            return new CCMoveBy(this);
        }
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_previousPosition = new CCPoint(target.getPosition());
        m_startPosition = new CCPoint(target.getPosition());
    }

    @Override
    public void update(float time)
    {
        if (m_pTarget != null)
        {
            CCPoint currentPos = new CCPoint(m_pTarget.getPosition());
            CCPoint diff = new CCPoint(CCPoint.ccpSub(currentPos, m_previousPosition));
            m_startPosition = new CCPoint(CCPoint.ccpAdd(m_startPosition, diff));
            CCPoint newPos = new CCPoint(CCPoint.ccpAdd(m_startPosition, CCPoint.ccpMult(m_positionDelta, time)));
            m_pTarget.setPosition(newPos);
            m_previousPosition = new CCPoint(newPos);
        }
    }

    @Override
    public  CCFiniteTimeAction reverse()
    {
        return new CCMoveBy(m_fDuration, new CCPoint(-m_positionDelta.x, -m_positionDelta.y));
    }
}
