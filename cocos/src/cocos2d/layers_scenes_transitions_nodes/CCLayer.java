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

package cocos2d.layers_scenes_transitions_nodes;

import cocos2d.CCDirector;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCSize;

public class CCLayer extends CCNode
{
	private boolean m_bDidInit = false;
	
	public CCLayer()
    {
        setAnchorPoint(new CCPoint(0.5f, 0.5f));
        m_bIgnoreAnchorPointForPosition = true;
        CCDirector director = CCDirector.sharedDirector();
        if (director != null)
        {
            setContentSize(new CCSize(director.getWinSize()));
        }
        init();
    }
	
	
	@Override
	public CCSize getContentSize()
    {
        return super.getContentSize();
    }

	@Override
	public void setContentSize(CCSize value)
    {
        super.setContentSize(value);
        //InitClipping(); 
    }
	
	@Override
	public boolean init()
    {
        if (m_bDidInit)
        {
            return (true);
        }

        setTouchMode(CCTouchMode.AllAtOnce);
        boolean bRet = false;
        CCDirector director = CCDirector.sharedDirector();
        if (director != null)
        {
            //                ContentSize = director.WinSize;
            //m_bIsAccelerometerEnabled = false;
            bRet = true;
            m_bDidInit = true;
        }
        return bRet;
    }
	
	@Override
	public void onEnter()
    {
        if(!m_bDidInit) {
            init();
        }

        // then iterate over all the children
        super.onEnter();
    }
}
