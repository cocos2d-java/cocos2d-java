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

package cocos2d.base_nodes;

import cocos2d.predefine.CCTypes;
import cocos2d.predefine.ICCRGBAProtocol;
import cocos2d.predefine.CCColor3B;

/** CCNodeRGBA is a subclass of CCNode that implements the CCRGBAProtocol protocol.

All features from CCNode are valid, plus the following new features:
- opacity
- RGB colors

Opacity/Color propagates into children that conform to the CCRGBAProtocol if cascadeOpacity/cascadeColor is enabled.
@since v2.1
*/
public class CCNodeRGBA extends CCNode implements ICCRGBAProtocol
{
   protected int _displayedOpacity;
   protected int _realOpacity;
   protected CCColor3B _displayedColor;
   protected CCColor3B _realColor;
   protected boolean _cascadeColorEnabled;
   protected boolean _cascadeOpacityEnabled;
   
   protected boolean isOpacityChanged = false;

   public CCColor3B getColor()
   {
	   return _realColor;
   }
   
   public void setColor(CCColor3B value)
   {
       _displayedColor = _realColor = value;

       if (_cascadeColorEnabled)
       {
    	   CCColor3B parentColor = CCTypes.CCWhite;
           //var parent = m_pParent as ICCRGBAProtocol;
    	   ICCRGBAProtocol parent = null;
    	   if (m_pParent instanceof ICCRGBAProtocol)
    	   {
    		   parent = (ICCRGBAProtocol)m_pParent;
    	   }
           if (parent != null && parent.getCascadeColorEnabled())
           {
               parentColor = parent.getDisplayedColor();
           }

           updateDisplayedColor(parentColor);
       }
       
   }

   @Override
   public  CCColor3B getDisplayedColor()
   {
      return _displayedColor; 
   }

   public int getOpacity()
   {
       return _realOpacity;
   }

   public void setOpacity(int value)
   { 
       _displayedOpacity = _realOpacity = value;
       isOpacityChanged = true;

       if (_cascadeOpacityEnabled)
       {
           int parentOpacity = 255;
           //var pParent = m_pParent as ICCRGBAProtocol;
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
   
   public CCNodeRGBA()
   {
       _displayedOpacity = 255;
       _realOpacity = 255;
       _displayedColor = CCTypes.CCWhite;
       _realColor = CCTypes.CCWhite;
       _cascadeColorEnabled = false;
       _cascadeOpacityEnabled = false;
   }

   @Override
   public boolean init()
   {
       //base.Init();

       _displayedOpacity = _realOpacity = 255;
       _displayedColor = _realColor = CCTypes.CCWhite;
       _cascadeOpacityEnabled = _cascadeColorEnabled = false;
       
       return true;
   }

   @Override
   public void updateDisplayedColor(CCColor3B parentColor)
   {
       _displayedColor.R = (byte) (_realColor.R * parentColor.R / 255.0f);
       _displayedColor.G = (byte) (_realColor.G * parentColor.G / 255.0f);
       _displayedColor.B = (byte) (_realColor.B * parentColor.B / 255.0f);

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

   @Override
   public void updateDisplayedOpacity(int parentOpacity)
   {
       _displayedOpacity = (byte) (_realOpacity * parentOpacity / 255.0f);

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
                   item.updateDisplayedOpacity(_displayedOpacity);
               }
           }
       }
   }

}