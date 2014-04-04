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

import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;

public class CCCardinalSplineBy extends CCCardinalSplineTo
{
    protected CCPoint m_startPosition;

    public CCCardinalSplineBy(float duration, ArrayList<CCPoint> points, float tension)
    {
    	super(duration, points, tension);
        initWithDuration(duration, points, tension);
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        m_startPosition = target.getPosition();
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        ArrayList<CCPoint> copyConfig = new  ArrayList<CCPoint>(m_pPoints);

        //
        // convert "absolutes" to "diffs"
        //
        CCPoint p = copyConfig.get(0);

        for (int i = 1; i < copyConfig.size(); ++i)
        {
            CCPoint current = new CCPoint(copyConfig.get(i));
            CCPoint diff = CCPoint.ccpSub(current, p);
            copyConfig.set(i, diff);

            p = current;
        }

        // convert to "diffs" to "reverse absolute"
        Collections.reverse(copyConfig);

        // 1st element (which should be 0,0) should be here too

        p = new CCPoint(copyConfig.get(copyConfig.size() - 1));
        copyConfig.remove(copyConfig.size() - 1);

        p = CCPoint.ccpNeg(p);
        copyConfig.add(0, p);

        for (int i = 1; i < copyConfig.size(); ++i)
        {
            CCPoint current = new CCPoint(copyConfig.get(i));
            current = CCPoint.ccpNeg(current);
            CCPoint abs = CCPoint.ccpAdd(current, p);
            copyConfig.set(i, abs);

            p = abs;
        }

        return new CCCardinalSplineBy(m_fDuration, copyConfig, m_fTension);
    }

    @Override
    public void updatePosition(CCPoint newPos)
    {
        m_pTarget.setPosition(CCPoint.ccpAdd(newPos, m_startPosition));
        m_previousPosition = m_pTarget.getPosition();
    }
}