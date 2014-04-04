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


//  Object than contains the delegate and priority of the event handler.
public class CCTouchHandler
{
    protected int m_nEnabledSelectors;
    protected int m_nPriority;
    protected ICCTouchDelegate m_pDelegate;

    /// <summary>
    /// delegate
    /// </summary>
    public ICCTouchDelegate getDelegate()
    {
       return m_pDelegate;    
    }
    
    public void setDelegate(ICCTouchDelegate delegate)
    {    
        m_pDelegate = delegate;
    }
    /// <summary>
    /// priority
    /// </summary>
    public int getPriority()
    {
        return m_nPriority;     
    }
    public void setPriority(int priority)
    {
        m_nPriority = priority;
    }
    /// <summary>
    /// enabled selectors 
    /// </summary>
    public int getEnabledSelectors()
    {
        return m_nEnabledSelectors;
    }

    public void EnabledSelectors(int value)
    {       
         m_nEnabledSelectors = value;
    }
    
    /// <summary>
    /// initializes a TouchHandler with a delegate and a priority 
    /// </summary>
    public Boolean InitWithDelegate(ICCTouchDelegate pDelegate, int nPriority)
    {
        m_pDelegate = pDelegate;
        m_nPriority = nPriority;
        m_nEnabledSelectors = 0;

        return true;
    }

    /// <summary>
    /// allocates a TouchHandler with a delegate and a priority 
    /// </summary>
    public static CCTouchHandler Create(ICCTouchDelegate pDelegate, int nPriority)
    {
    	CCTouchHandler pHandler = new CCTouchHandler();
        pHandler.InitWithDelegate(pDelegate, nPriority);
        return pHandler;
    }
}