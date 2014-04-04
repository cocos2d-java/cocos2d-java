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

package cocos2d.actions.action;

import cocos2d.CCDirector;
import cocos2d.Math.MathHelper;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.cocoa.ICCCopyable;

public class CCFollow extends CCAction
{
    protected boolean m_bBoundaryFullyCovered;

    protected boolean m_bBoundarySet;
    protected float m_fBottomBoundary;
    protected float m_fLeftBoundary;
    protected float m_fRightBoundary;
    protected float m_fTopBoundary;
    protected CCPoint m_obFullScreenSize;
    protected CCPoint m_obHalfScreenSize;
    protected CCNode m_pobFollowedNode;

    public CCFollow(CCNode followedNode, CCRect rect)
    {
        initWithTarget(followedNode, rect);
    }

    protected CCFollow(CCFollow follow)
    {
    	super(follow);
        m_nTag = follow.m_nTag;
    }

    /** whether camera should be limited to certain area*/
    public boolean getBoundarySet()
    {
        return m_bBoundarySet;
    }

    public void setBoundarySet(boolean value)
    {
        m_bBoundarySet = value;
    }
    
    private boolean initWithTarget(CCNode pFollowedNode, CCRect rect)
    {
        //Debug.Assert(pFollowedNode != null);

        m_pobFollowedNode = pFollowedNode;
        if (rect.Equals(CCRect.Zero))
        {
            m_bBoundarySet = false;
        }
        else
        {
            m_bBoundarySet = true;
        }

        m_bBoundaryFullyCovered = false;

        CCSize winSize = new CCSize(CCDirector.sharedDirector().getWinSize());
        m_obFullScreenSize = new CCPoint(winSize) ;
        m_obHalfScreenSize = CCPoint.ccpMult(m_obFullScreenSize, 0.5f);

        if (m_bBoundarySet)
        {
            m_fLeftBoundary = -((rect.Origin.x + rect.Size.width) - m_obFullScreenSize.x);
            m_fRightBoundary = -rect.Origin.x;
            m_fTopBoundary = -rect.Origin.y;
            m_fBottomBoundary = -((rect.Origin.y + rect.Size.height) - m_obFullScreenSize.y);

            if (m_fRightBoundary < m_fLeftBoundary)
            {
                // screen width is larger than world's boundary width
                //set both in the middle of the world
                m_fRightBoundary = m_fLeftBoundary = (m_fLeftBoundary + m_fRightBoundary) / 2;
            }
            if (m_fTopBoundary < m_fBottomBoundary)
            {
                // screen width is larger than world's boundary width
                //set both in the middle of the world
                m_fTopBoundary = m_fBottomBoundary = (m_fTopBoundary + m_fBottomBoundary) / 2;
            }

            if ((m_fTopBoundary == m_fBottomBoundary) && (m_fLeftBoundary == m_fRightBoundary))
            {
                m_bBoundaryFullyCovered = true;
            }
        }

        return true;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
        	CCFollow ret = (CCFollow) zone;
            super.copy(zone);
            ret.m_nTag = m_nTag;
            return ret;
        }
        else
        {
            return new CCFollow(this);
        }
    }

    @Override
    public void step(float dt)
    {
        if (m_bBoundarySet)
        {
            // whole map fits inside a single screen, no need to modify the position - unless map boundaries are increased
            if (m_bBoundaryFullyCovered)
            {
                return;
            }

            CCPoint tempPos = CCPoint.ccpSub(m_obHalfScreenSize, m_pobFollowedNode.getPosition());

            m_pTarget.setPosition(new CCPoint(
                    MathHelper.Clamp(tempPos.x, m_fLeftBoundary, m_fRightBoundary),
                    MathHelper.Clamp(tempPos.y, m_fBottomBoundary, m_fTopBoundary)
                    ));
        }
        else
        {
            m_pTarget.setPosition(CCPoint.ccpSub(m_obHalfScreenSize, m_pobFollowedNode.getPosition()) );
        }
    }

    @Override
    public boolean isDone()
    {
        return !m_pobFollowedNode.isRunning();
    }

    @Override
    public void stop()
    {
        m_pTarget = null;
        super.stop();
    }
}
