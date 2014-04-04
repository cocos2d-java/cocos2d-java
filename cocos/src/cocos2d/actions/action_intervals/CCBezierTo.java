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

public class CCBezierTo extends CCBezierBy
{
    public CCBezierTo(float t, CCBezierConfig c)
    {
    	super(t,c);
        initWithDuration(t, c);
    }

    protected CCBezierTo(CCBezierTo bezierTo)
    {
    	super(bezierTo);
        initWithDuration(bezierTo.m_fDuration, bezierTo.m_sConfig);
    }

//    @Override
//    public void StartWithTarget(CCNode target)
//    {
//        super.StartWithTarget(target);
//        m_sConfig.ControlPoint1 = (m_sConfig.ControlPoint1 - m_startPosition);
//        m_sConfig.ControlPoint2 = (m_sConfig.ControlPoint2 - m_startPosition);
//        m_sConfig.EndPosition = (m_sConfig.EndPosition - m_startPosition);
//    }
//
//    public override object Copy(ICCCopyable zone)
//    {
//        if (zone != null && zone != null)
//        {
//            var ret = zone as CCBezierTo;
//            base.Copy(zone);
//            ret.InitWithDuration(m_fDuration, m_sConfig);
//
//            return ret;
//        }
//        return new CCBezierTo(this);
//    }
}