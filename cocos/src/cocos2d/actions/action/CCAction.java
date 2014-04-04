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

import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.ICCCopyable;


public class CCAction implements ICCCopyable
{
	protected int m_nTag;
    protected CCNode m_pOriginalTarget;
    protected CCNode m_pTarget;
    
    
    public CCAction()
    {
        m_nTag =  CCActionTag.Invalid.getValue();
    }

    protected CCAction(CCAction action)
    {
        m_nTag = action.m_nTag;
    }

    public CCNode getTarget()
    {
        return m_pTarget;
    }

    public void setTarget(CCNode value)
    {
        m_pTarget = value;
    }
    public CCNode getOriginalTarget()
    {
        return m_pOriginalTarget;
    }

    public int getTag()
    {
        return m_nTag;
    }
    
    public void setTag(int value)
    {
        m_nTag = value;
    }
    
    public CCAction copy()
    {
        return (CCAction) copy(null);
    }
    
    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
            ((CCAction) zone).m_nTag = m_nTag;
            return zone;
        }
        else
        {
            return new CCAction(this);
        }
    }
    
    public void startWithTarget(CCNode target)
    {
        m_pOriginalTarget = m_pTarget = target;
    }
    
    
    public boolean isDone()
    {
        return true;
    }
    
    
	public enum CCActionTag
	{
	    //! Default tag
	    Invalid(-1);
	    private int value;
	    
	    private CCActionTag(int value) {
	        this.value = value;
	    }
	    
	    public int getValue() {
	        return value;
	    }
	}
	
	public void stop()
    {
        m_pTarget = null;
    }
	
	
	public void step(float dt) throws Exception
	{
		
	}

	public void update(float time) throws Exception
    {
        //CCLog.Log("[Action update]. override me");
    }
	
}
