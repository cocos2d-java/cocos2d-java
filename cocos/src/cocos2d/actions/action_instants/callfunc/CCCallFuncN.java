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

package cocos2d.actions.action_instants.callfunc;

import cocos2d.cocoa.ICCCopyable;
import cocos2d.platform.ISelector;

public class CCCallFuncN extends CCCallFunc
{
    private ISelector m_pCallFuncN;

    public CCCallFuncN()
    {
    	super();
        m_pCallFuncN = null;
    }


    public CCCallFuncN(ISelector selector)
    {
        initWithTarget(selector);
    }

    public CCCallFuncN(CCCallFuncN callFuncN)
    {
    	super(callFuncN);
        initWithTarget(callFuncN.m_pCallFuncN);
    }

    public boolean initWithTarget(ISelector selector)
    {
        m_pCallFuncN = selector;
        return false;
    }

    @Override
    public Object copy(ICCCopyable zone)
    {
        if (zone != null)
        {
            //in case of being called at sub class
        	CCCallFuncN pRet = (CCCallFuncN) (zone);
            super.copy(zone);

            pRet.initWithTarget(m_pCallFuncN);

            return pRet;
        }
        else
        {
            return new CCCallFuncN(this);
        }
    }

    @Override
    public void execute()
    {
        if (null != m_pCallFuncN)
        {
            //m_pCallFuncN(m_pTarget);
        	m_pCallFuncN.method(m_pTarget);
        }
        //if (m_nScriptHandler) {
        //    CCScriptEngineManager::sharedManager()->getScriptEngine()->executeFunctionWithobject(m_nScriptHandler, m_pTarget, "CCNode");
        //}
    }
}