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
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.ICCCopyable;

public class CCMoveTo extends CCMoveBy
{
	CCPoint currentPos = new CCPoint();
	CCPoint newPos = new CCPoint();
	
    public CCMoveTo(float duration, CCPoint position)
    {
    	super(duration, position);
    }

    protected CCMoveTo(CCMoveTo moveTo)
    {
    	super(moveTo);
        initWithDuration(moveTo.m_fDuration, moveTo.m_endPosition);
    }

    @Override
    protected boolean initWithDuration(float duration, CCPoint position)
    {
        if (super.initWithDuration(duration))
        {
            m_endPosition = new CCPoint(position);
            return true;
        }
        return false;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCMoveTo ret = (CCMoveTo) zone;
            super.copy(zone);
            ret.initWithDuration(m_fDuration, m_endPosition);

            return ret;
        }
        else
        {
            return new CCMoveTo(this);
        }
    }

    @Override
    public void update(float time)
    {
        if (m_pTarget != null)
        {
            currentPos.set(m_pTarget.getPosition());
            //CCPoint diff = new CCPoint(CCPoint.ccpSub(currentPos, m_previousPosition));
            //m_startPosition = m_startPosition + diff;
            //newPos.set(CCPoint.ccpAdd(m_startPosition, CCPoint.ccpMult(m_positionDelta, time)));
            newPos.set(m_startPosition.x + m_positionDelta.x * time,  m_startPosition.y + m_positionDelta.y * time);
            m_pTarget.setPosition(newPos);
            m_previousPosition.set(newPos);
        }
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_startPosition = new CCPoint(target.getPosition());
        m_positionDelta = new CCPoint(CCPoint.ccpSub(m_endPosition, target.getPosition()));
    }
}