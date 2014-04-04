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

package cocos2d.actions.action_catmull_rom;

import java.util.ArrayList;
import java.util.Collections;

import cocos2d.Math.CCSplineMath;
import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.actions.action_intervals.CCActionInterval;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.ICCCopyable;

public class CCCardinalSplineTo extends CCActionInterval
{
    protected float m_fDeltaT;
    protected float m_fTension;
    protected ArrayList<CCPoint> m_pPoints;
    protected CCPoint m_previousPosition;
    protected CCPoint m_accumulatedDiff;

    public ArrayList<CCPoint> getPoints()
    {
        return m_pPoints;
    }

    public void setPoints(ArrayList<CCPoint> value)
    {
        m_pPoints = value;
    }
    
    public CCCardinalSplineTo(float duration, ArrayList<CCPoint> points, float tension)
    {
        initWithDuration(duration, points, tension);
    }

    protected CCCardinalSplineTo(CCCardinalSplineTo cardinalSplineTo)
    {
    	super(cardinalSplineTo);
        initWithDuration(cardinalSplineTo.m_fDuration, cardinalSplineTo.m_pPoints, cardinalSplineTo.m_fTension);
    }

    public boolean initWithDuration(float duration, ArrayList<CCPoint> points, float tension)
    {
        //Debug.Assert(points.Count > 0, "Invalid configuration. It must at least have one control point");

        if (super.initWithDuration(duration))
        {
            setPoints(points);
            m_fTension = tension;

            return true;
        }

        return false;
    }

    @Override
    public Object copy(ICCCopyable pZone)
    {
        if (pZone != null) //in case of being called at sub class
        {
        	CCCardinalSplineTo pRet = (CCCardinalSplineTo) (pZone);
            super.copy(pZone);

            pRet.initWithDuration(getDuration(), m_pPoints, m_fTension);

            return pRet;
        }
        return new CCCardinalSplineTo(this);
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);

        m_fDeltaT = 1f / m_pPoints.size();

        m_previousPosition = new CCPoint(target.getPosition());
        m_accumulatedDiff = CCPoint.Zero;
    }

    @Override
    public void update(float time)
    {
        int p;
        float lt;

        // eg.
        // p..p..p..p..p..p..p
        // 1..2..3..4..5..6..7
        // want p to be 1, 2, 3, 4, 5, 6
        if (time == 1)
        {
            p = m_pPoints.size() - 1;
            lt = 1;
        }
        else
        {
            p = (int) (time / m_fDeltaT);
            lt = (time - m_fDeltaT * p) / m_fDeltaT;
        }

        // Interpolate    
        int c = m_pPoints.size() - 1;
        CCPoint pp0 = new CCPoint(m_pPoints.get(Math.min(c, Math.max(p - 1, 0))));
        CCPoint pp1 = new CCPoint(m_pPoints.get(Math.min(c, Math.max(p + 0, 0))));
        CCPoint pp2 = new CCPoint(m_pPoints.get(Math.min(c, Math.max(p + 1, 0))));
        CCPoint pp3 = new CCPoint(m_pPoints.get(Math.min(c, Math.max(p + 2, 0))));

        CCPoint newPos = CCSplineMath.CCCardinalSplineAt(pp0, pp1, pp2, pp3, m_fTension, lt);

        // Support for stacked actions
        CCNode node = m_pTarget;
        CCPoint diff = CCPoint.ccpSub(node.getPosition(), m_previousPosition);
        if (diff.x != 0 || diff.y != 0)
        {
            m_accumulatedDiff = CCPoint.ccpAdd(m_accumulatedDiff, diff);
            newPos = CCPoint.ccpAdd(newPos, m_accumulatedDiff);
        }

        updatePosition(newPos);
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        ArrayList<CCPoint> pReverse = new ArrayList<CCPoint>(m_pPoints);
        Collections.reverse(pReverse);

        return new CCCardinalSplineTo(m_fDuration, pReverse, m_fTension);
    }

    public void updatePosition(CCPoint newPos)
    {
        m_pTarget.setPosition(newPos);
        m_previousPosition = new CCPoint(newPos);
    }
}