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

package cocos2d.predefine;

import cocos2d.CCDirector;
import cocos2d.cocoa.CCPoint;

public class CCTouch {
	private int m_nId;

    
    /// Point of action
    private CCPoint m_point;

    /// Previous point in the action
    private CCPoint m_prevPoint;

    private CCPoint m_startPoint;
    private Boolean m_startPointCaptured = false;

    public CCTouch()
    {
    	this(0, 0, 0);
    }

    public CCTouch(int id, float x, float y)
    {
        m_nId = id;
        m_point = new CCPoint(x, y);
        m_prevPoint = new CCPoint(x, y);
    }

    /** returns the start touch location in OpenGL coordinates */
    public CCPoint getStartLocation()
    {
        return CCDirector.sharedDirector().convertToGl(m_startPoint); 
    }

    public CCPoint getLocationInView()
    {
        return m_point;
    }

    /** returns the start touch location in screen coordinates */
    public CCPoint getStartLocationInView()
    {
        return m_startPoint;
    }

    public CCPoint getPreviousLocationInView()
    {
        return m_prevPoint;
    }

    public CCPoint getLocation()
    {
        return CCDirector.sharedDirector().convertToGl(m_point); 
    }

    public CCPoint getPreviousLocation()
    {
        return CCDirector.sharedDirector().convertToGl(m_prevPoint);
    }


    public int getId()
    {
        return m_nId;
    }

    public CCPoint getDelta()
    {
        return CCPoint.ccpSub(getLocation(),getPreviousLocation());
    }

    public void SetTouchInfo(int id, float x, float y)
    {
        m_nId = id;
        m_prevPoint = m_point;
        m_point.x = x;
        m_point.y = y;
        if (!m_startPointCaptured)
        {
            m_startPoint = m_point;
            m_startPointCaptured = true;
        }
    }

}
