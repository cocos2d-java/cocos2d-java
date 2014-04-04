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

import cocos2d.Math.CCSplineMath;
import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.ICCCopyable;

public class CCBezierBy extends CCActionInterval
{
    protected CCBezierConfig m_sConfig;
    protected CCPoint m_startPosition = new CCPoint();;
    protected CCPoint m_previousPosition = new CCPoint();;

    public CCBezierBy(float t, CCBezierConfig c)
    {
        initWithDuration(t, c);
    }

    protected CCBezierBy(CCBezierBy bezierBy)
    {
    	super(bezierBy);
        initWithDuration(bezierBy.m_fDuration, bezierBy.m_sConfig);
    }

    protected boolean initWithDuration(float t, CCBezierConfig c)
    {
        if (super.initWithDuration(t))
        {
            m_sConfig = c;
            return true;
        }
        return false;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
            //var ret = zone as CCBezierBy;
        	CCBezierBy ret = (CCBezierBy)zone;
            super.copy(zone);

            ret.initWithDuration(m_fDuration, m_sConfig);

            return ret;
        }
        return new CCBezierBy(this);
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_previousPosition = m_startPosition = target.getPosition();
    }

    @Override
    public void update(float time)
    {
        if (m_pTarget != null)
        {
            float xa = 0;
            float xb = m_sConfig.ControlPoint1.x;
            float xc = m_sConfig.ControlPoint2.x;
            float xd = m_sConfig.EndPosition.x;

            float ya = 0;
            float yb = m_sConfig.ControlPoint1.y;
            float yc = m_sConfig.ControlPoint2.y;
            float yd = m_sConfig.EndPosition.y;

            float x = CCSplineMath.CubicBezier(xa, xb, xc, xd, time);
            float y = CCSplineMath.CubicBezier(ya, yb, yc, yd, time);

            CCPoint currentPos = m_pTarget.getPosition();
            CCPoint diff = CCPoint.ccpSub(currentPos, m_previousPosition);
            m_startPosition = CCPoint.ccpAdd(m_startPosition, diff);
    
            CCPoint newPos = CCPoint.ccpAdd(m_startPosition, new CCPoint(x, y));
            m_pTarget.setPosition(newPos);

            m_previousPosition = newPos;
        }
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        CCBezierConfig r = new CCBezierConfig();

        r.EndPosition = CCPoint.ccpNeg(m_sConfig.EndPosition);
        r.ControlPoint1 = CCPoint.ccpAdd(m_sConfig.ControlPoint2, CCPoint.ccpNeg(m_sConfig.EndPosition));
        r.ControlPoint2 = CCPoint.ccpAdd(m_sConfig.ControlPoint1, CCPoint.ccpNeg(m_sConfig.EndPosition));

        CCBezierBy action = new CCBezierBy(m_fDuration, r);
        return action;
    }
}

