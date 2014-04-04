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

import cocos2d.base_nodes.CCNode;
import cocos2d.base_nodes.CCNodeRGBA;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;

/// <summary>
/// @brief CCMenuItem base class
/// Subclass CCMenuItem (or any subclass) to create your custom CCMenuItem objects.
/// </summary>
public class CCMenuItem extends CCNodeRGBA
{
    public static final int kCurrentItem = 32767;
    public static final long kZoomActionTag = 0xc0c05002;
    protected static int _fontSize = 32;
    protected static String _fontName = "arial";
    protected static boolean _fontNameRelease = false;

    protected boolean m_bIsEnabled;
    protected boolean m_bIsSelected;

    protected String m_functionName;
    protected String m_pfnSelector;
    
    //touch target
    protected CCNode m_pTarget;

    public CCMenuItem()
    {
        m_bIsSelected = false;
        m_bIsEnabled = false;
        m_pfnSelector = null;
    }

    /// <summary>
    /// Creates a CCMenuItem with a target/selector
    /// </summary>
    /// <param name="selector"></param>
    /// <returns></returns>
    public CCMenuItem(String selector)
    {
        initWithTarget(null,selector);
    }

    public boolean getEnabled()
    {
    	return m_bIsEnabled;
    }

    public void setEnabled(boolean value)
    {
        m_bIsEnabled = value;
    }

    
    public boolean isSelected()
    {
        return m_bIsSelected;
    }

    /// <summary>
    /// Initializes a CCMenuItem with a target/selector
    /// </summary>
    /// <param name="selector"></param>
    /// <returns></returns>
    public boolean initWithTarget(CCNode target, String selector)
    {
    	m_pTarget = target;
    	setAnchorPoint(new CCPoint(0.5f, 0.5f));
        m_pfnSelector = selector;
        m_bIsEnabled = true;
        m_bIsSelected = false;
        return true;
    }

    /// <summary>
    /// Returns the outside box
    /// </summary>
    /// <returns></returns>
    public CCRect getRectangle()
    {	
		return new CCRect (m_obPosition.x - m_obContentSize.width * m_obAnchorPoint.x,
			                          m_obPosition.y - m_obContentSize.height * m_obAnchorPoint.y,
			                          m_obContentSize.width,
			                          m_obContentSize.height);	
    }

    /// <summary>
    /// Activate the item
    /// </summary>
    public void activate() throws Exception 
    {
        if (m_bIsEnabled)
        {
            if (m_pfnSelector != null)
            {
                //m_pfnSelector(this);
            	m_pTarget.getClass().getMethod(m_pfnSelector, Object.class).invoke(m_pTarget, this);
            }

            //if (m_functionName.size() && CCScriptEngineManager.sharedScriptEngineManager().getScriptEngine())
            //{
            //CCScriptEngineManager.sharedScriptEngineManager().getScriptEngine().executeCallFuncN(m_functionName.c_str(), this);
            //}
        }
    }

    /// <summary>
    /// The item was selected (not activated), similar to "mouse-over"
    /// </summary>
    public void selected()
    {
        m_bIsSelected = true;
    }

    /// <summary>
    /// The item was unselected
    /// </summary>
    public void unselected()
    {
        m_bIsSelected = false;
    }

    /// <summary>
    /// Register a script function, the function is called in activete
    /// If pszFunctionName is NULL, then unregister it.
    /// </summary>
    /// <param name="pszFunctionName"></param>
//    public void registerScriptHandler(string pszFunctionName)
//    {
//        throw new NotImplementedException("CCMenuItem.RegisterScriptHandler is not supported in this version of Cocos2d-XNA");
//    }

    /// <summary>
    /// set the target/selector of the menu item
    /// </summary>
    /// <param name="selector"></param>
    public void setTarget(String selector)
    {
        m_pfnSelector = selector;
    }
}