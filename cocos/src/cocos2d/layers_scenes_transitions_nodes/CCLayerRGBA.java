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

import cocos2d.predefine.CCTypes;
import cocos2d.predefine.ICCRGBAProtocol;
import cocos2d.predefine.CCColor3B;

public class CCLayerRGBA extends CCLayer implements ICCRGBAProtocol
{
    protected int _displayedOpacity;
    protected int _realOpacity;
    protected CCColor3B _displayedColor;
    protected CCColor3B _realColor;
    protected boolean _cascadeColorEnabled;
    protected boolean _cascadeOpacityEnabled;

    public CCColor3B getColor()
    {
        return new CCColor3B(_realColor);
    }

    public void setColor(CCColor3B value)
    {
        _displayedColor = new CCColor3B(value);
        _realColor = new CCColor3B(value);

        if (_cascadeColorEnabled)
        {
        	CCColor3B parentColor = new CCColor3B(CCTypes.CCWhite);
            //var parent = m_pParent as ICCRGBAProtocol;
        	ICCRGBAProtocol parent = null;
        	if (m_pParent instanceof ICCRGBAProtocol)
        	{
        		parent = (ICCRGBAProtocol)m_pParent;
        	}
            if (parent != null && parent.getCascadeColorEnabled())
            {
                parentColor = new CCColor3B(parent.getDisplayedColor());
            }

            updateDisplayedColor(parentColor);
        } 
    }
    
    @Override
    public  CCColor3B getDisplayedColor()
    {
        return _displayedColor;
    }

    @Override
    public int getOpacity()
    {
        return _realOpacity;
    }

    @Override
    public void setOpacity(int value)
    { 
        _displayedOpacity = _realOpacity = value;

        if (_cascadeOpacityEnabled)
        {
            int parentOpacity = 255;
            ICCRGBAProtocol pParent = null;
            if (m_pParent instanceof ICCRGBAProtocol)
            {
            	pParent = (ICCRGBAProtocol)m_pParent;
            }
            if (pParent != null && pParent.getCascadeOpacityEnabled())
            {
                parentOpacity = pParent.getDisplayedOpacity();
            }
            updateDisplayedOpacity(parentOpacity);
        }
    }
    
    @Override
    public int getDisplayedOpacity()
    {
        return _displayedOpacity;
    }

    @Override
    public boolean getIsOpacityModifyRGB()
    {
        return false;
    }
       
    @Override
    public void setIsOpacityModifyRGB(boolean value)
    {
        
    }
    
    @Override
    public boolean getCascadeColorEnabled()
    {
        return _cascadeColorEnabled;
    }

    @Override
    public void setCascadeColorEnabled(boolean value)
    {
        _cascadeColorEnabled = value;
    }
  
    @Override
    public boolean getCascadeOpacityEnabled()
    {
        return _cascadeOpacityEnabled;
    }

    @Override
    public void setCascadeOpacityEnabled(boolean value)
    {
        _cascadeOpacityEnabled = value;
    }
    
    public CCLayerRGBA()
    {
        _displayedOpacity = 255;
        _realOpacity = 255;
        _displayedColor = new CCColor3B(CCTypes.CCWhite);
        _realColor = new CCColor3B(CCTypes.CCWhite);
        
        _cascadeColorEnabled = false;
        _cascadeOpacityEnabled = false;
    }

    @Override
    public boolean init()
    {
        super.init();

        _displayedOpacity = _realOpacity = 255;
        _displayedColor = new CCColor3B(CCTypes.CCWhite);
        _realColor = new CCColor3B(CCTypes.CCWhite);
        setCascadeOpacityEnabled(false);
        setCascadeColorEnabled(false);

        return true;
    }

    public void updateDisplayedColor(CCColor3B parentColor)
    {
        _displayedColor.R = (int) (_realColor.R * parentColor.R / 255.0f);
        _displayedColor.G = (int) (_realColor.G * parentColor.G / 255.0f);
        _displayedColor.B = (int) (_realColor.B * parentColor.B / 255.0f);

        if (_cascadeColorEnabled)
        {
            if (_cascadeOpacityEnabled && m_pChildren != null)
            {
                for (int i = 0, count = m_pChildren.size; i < count; i++)
                {
                    //var item = m_pChildren.Elements[i] as ICCRGBAProtocol;
                	ICCRGBAProtocol item = null;
                	if (m_pChildren.get(i) instanceof ICCRGBAProtocol)
                	{
                		item = (ICCRGBAProtocol)(m_pChildren.get(i));
                	}
                    if (item != null)
                    {
                        item.updateDisplayedColor(_displayedColor);
                    }
                }
            }
        }
    }

    public void updateDisplayedOpacity(int parentOpacity)
    {
        _displayedOpacity = (int) (_realOpacity * parentOpacity / 255.0f);

        if (_cascadeOpacityEnabled && m_pChildren != null)
        {
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
            	ICCRGBAProtocol item = null;
            	if (m_pChildren.get(i) instanceof ICCRGBAProtocol)
            	{
            		item = (ICCRGBAProtocol)(m_pChildren.get(i));
            	}
                if (item != null)
                {
                    item.updateDisplayedOpacity(_displayedOpacity);
                }
            }
        }
    }

	
}