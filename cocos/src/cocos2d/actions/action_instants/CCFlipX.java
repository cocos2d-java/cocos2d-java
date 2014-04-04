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

package cocos2d.actions.action_instants;

import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.ICCCopyable;
import cocos2d.sprite_nodes.CCSprite;

public class CCFlipX extends CCActionInstant
{
    private Boolean m_bFlipX;

    protected CCFlipX()
    {
    }

    public CCFlipX(Boolean x)
    {
        initWithFlipX(x);
    }

    protected CCFlipX(CCFlipX flipX)
    {
    	super(flipX);
        initWithFlipX(m_bFlipX);
    }

    protected Boolean initWithFlipX(Boolean x)
    {
        m_bFlipX = x;
        return true;
    }

    @Override
    public void startWithTarget(CCNode target)
    {
        super.startWithTarget(target);
        ((CCSprite) (target)).setFlipX(m_bFlipX);
    }

    @Override
    public CCFiniteTimeAction reverse()
    {
        return new CCFlipX(!m_bFlipX);
    }

    @Override
    public Object copy(ICCCopyable pZone)
    {
        if (pZone != null)
        {
        	CCFlipX pRet = (CCFlipX) (pZone);
            super.copy(pZone);
            pRet.initWithFlipX(m_bFlipX);
            return pRet;
        }
        return new CCFlipX(this);
    }
}
