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

package cocos2d.touch_dispatcher;

import java.util.ArrayList;

import cocos2d.predefine.CCTouch;

public class CCTargetedTouchHandler extends CCTouchHandler
{
	protected Boolean m_bSwallowsTouches;
    protected ArrayList<CCTouch> m_pClaimedTouches;

    /// <summary>
    /// whether or not the touches are swallowed
    /// </summary>
    public Boolean getIsSwallowsTouches()
    {
        return m_bSwallowsTouches;     
    }
    public void setIsSwallowsTouches(Boolean isSwallowsTouches)
    {
         m_bSwallowsTouches = isSwallowsTouches; 
    }
    /// <summary>
    /// MutableSet that contains the claimed touches 
    /// </summary>
    public ArrayList<CCTouch> getClaimedTouches()
    {
        return m_pClaimedTouches;
    }

    /// <summary>
    ///  initializes a TargetedTouchHandler with a delegate, a priority and whether or not it swallows touches or not
    /// </summary>
    public Boolean InitWithDelegate(ICCTargetedTouchDelegate pDelegate, int nPriority, Boolean bSwallow)
    {
        if (super.InitWithDelegate(pDelegate, nPriority))
        {
            m_pClaimedTouches = new ArrayList<CCTouch>();
            m_bSwallowsTouches = bSwallow;

            return true;
        }

        return false;
    }

    /// <summary>
    /// allocates a TargetedTouchHandler with a delegate, a priority and whether or not it swallows touches or not 
    /// </summary>
    public static CCTargetedTouchHandler HandlerWithDelegate(ICCTargetedTouchDelegate pDelegate, int nPriority, Boolean bSwallow)
    {
    	CCTargetedTouchHandler pHandler = new CCTargetedTouchHandler();
        pHandler.InitWithDelegate(pDelegate, nPriority, bSwallow);
        return pHandler;
    }
}
