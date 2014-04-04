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

package cocos2d.menu_nodes;

import cocos2d.actions.action.CCAction;
import cocos2d.actions.action_intervals.CCScaleTo;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.sprite_nodes.CCSprite;

public class CCMenuItemSprite extends CCMenuItem
{
    protected float m_fOriginalScale;
    private CCNode m_pDisabledImage;
    private CCNode m_pNormalImage;

    private CCNode m_pSelectedImage;

    private boolean m_bZoomBehaviorOnTouch;
    
    public CCNode getNormalImage()
    {
        return m_pNormalImage;
    }

    public void setNormalImage(CCNode value)
    { 
        if (value != null)
        {
            addChild(value);
            value.setAnchorPoint(new CCPoint(0, 0));
            setContentSize(value.getContentSize());
        }

        if (m_pNormalImage != null)
        {
            removeChild(m_pNormalImage, true);
        }

        m_pNormalImage = value;
        UpdateImagesVisibility();   
    }
    
    public CCNode getSelectedImage()
    {
        return m_pSelectedImage;
    }

    public void setSelectedImage(CCNode value)
    {  
        if (value != null)
        {
            addChild(value);
            value.setAnchorPoint(new CCPoint(0, 0));
        }

        if (m_pSelectedImage != null)
        {
            removeChild(m_pSelectedImage, true);
        }

        m_pSelectedImage = value;
        UpdateImagesVisibility(); 
    }
    
    public CCNode getDisabledImage()
    {
        return m_pDisabledImage;
    }

    public void setDisabledImage(CCNode value)
    { 
        if (value != null)
        {
            addChild(value);
            value.setAnchorPoint(new CCPoint(0, 0));
        }

        if (m_pDisabledImage != null)
        {
            removeChild(m_pDisabledImage, true);
        }

        m_pDisabledImage = value;
        UpdateImagesVisibility(); 
    }
    
    @Override
    public boolean getEnabled()
    {
        return super.getEnabled();
    }

    @Override
    public void setEnabled(boolean value)
    { 
        super.setEnabled(value);
        UpdateImagesVisibility();
    }
    
    public CCMenuItemSprite()
    {
    	this(null, null, null, null);
        setZoomBehaviorOnTouch(false);
    }

	public CCMenuItemSprite(String selector)
    {
		super(selector);
    }

	public CCMenuItemSprite(String normalSprite, String selectedSprite, CCNode target, String selector)
    {
		this(new CCSprite(normalSprite), new CCSprite(selectedSprite), null, target, selector);
    }

    public CCMenuItemSprite(CCNode normalSprite, CCNode selectedSprite)
    {
    	this(normalSprite, selectedSprite,null, null, null);
    }

	public CCMenuItemSprite(CCNode normalSprite, CCNode selectedSprite, String selector)
    {
		this(normalSprite, selectedSprite, null,null, selector);
    }

	public CCMenuItemSprite(CCNode normalSprite, CCNode selectedSprite, CCNode disabledSprite,CCNode target, String selector)
    {
        initWithTarget(target,selector);

        setNormalImage(normalSprite);
        setSelectedImage(selectedSprite);
        setDisabledImage(disabledSprite);

        if (m_pNormalImage != null)
        {
            setContentSize(m_pNormalImage.getContentSize());
        }

        setCascadeColorEnabled(true);
        setCascadeOpacityEnabled(true);
    }

    /// <summary>
    /// Set this to true if you want to zoom-in/out on the button image like the CCMenuItemLabel works.
    /// </summary>
    public boolean getZoomBehaviorOnTouch() 
    {  
    	return m_bZoomBehaviorOnTouch;
    }
    
    public void setZoomBehaviorOnTouch(boolean value) 
    {  
    	m_bZoomBehaviorOnTouch = value;
    }

    @Override
    public void selected()
    {
        super.selected();

        if (m_pNormalImage != null)
        {
            if (m_pDisabledImage != null)
            {
                m_pDisabledImage.setVisible(false);
            }

            if (m_pSelectedImage != null)
            {
                m_pNormalImage.setVisible(false);
                m_pSelectedImage.setVisible(true);
            }
            else
            {
                m_pNormalImage.setVisible(true);
                if (getZoomBehaviorOnTouch())
                {
                    CCAction action = getActionByTag(-2);
                    if (action != null)
                    {
                        stopAction(action);
                    }
                    else
                    {
                        m_fOriginalScale = getScale();
                    }

                    CCAction zoomAction = new CCScaleTo(0.1f, m_fOriginalScale * 1.2f);
                    zoomAction.setTag(-2);
                    runAction(zoomAction);
                }
            }
        }
    }

    @Override
    public void unselected()
    {
        super.unselected();
        if (m_pNormalImage != null)
        {
            m_pNormalImage.setVisible(true);

            if (m_pSelectedImage != null)
            {
                m_pSelectedImage.setVisible(false);
                if (getZoomBehaviorOnTouch())
                {
                    stopActionByTag(-2);
                    CCAction zoomAction = new CCScaleTo(0.1f, m_fOriginalScale);
                    zoomAction.setTag(-2);
                    runAction(zoomAction);
                }
            }

            if (m_pDisabledImage != null)
            {
                m_pDisabledImage.setVisible(false);
            }
        }
    }

    @Override
    public void activate() throws Exception
    {
        if (m_bIsEnabled)
        {
            if (getZoomBehaviorOnTouch())
            {
                stopAllActions();
                setScale(m_fOriginalScale);
            }
            super.activate();
        }
    }

    // Helper 
    private void UpdateImagesVisibility()
    {
        if (m_bIsEnabled)
        {
            if (m_pNormalImage != null) m_pNormalImage.setVisible(true);
            if (m_pSelectedImage != null) m_pSelectedImage.setVisible(false);
            if (m_pDisabledImage != null) m_pDisabledImage.setVisible(false);
        }
        else
        {
            if (m_pDisabledImage != null)
            {
                if (m_pNormalImage != null) m_pNormalImage.setVisible(false);
                if (m_pSelectedImage != null) m_pSelectedImage.setVisible(false);
                if (m_pDisabledImage != null) m_pDisabledImage.setVisible(true);
            }
            else
            {
                if (m_pNormalImage != null) m_pNormalImage.setVisible(true);
                if (m_pSelectedImage != null) m_pSelectedImage.setVisible(false);
                if (m_pDisabledImage != null) m_pDisabledImage.setVisible(false);
            }
        }
    }
}