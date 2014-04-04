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
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.utils.Array;

import cocos2d.predefine.CCTouch;

public class CCTouchDispatcher implements ICCEGLTouchDelegate
{
	private static Array<CCTouch> pMutableTouches;
    private boolean m_bDispatchEvents = false;
    private boolean m_bLocked = false;
    private boolean m_bToAdd = false;
    private boolean m_bToQuit = false;
    private boolean m_bToRemove = false;
    private Array<CCTouchHandler> m_pHandlersToAdd;
    private Array<Object> m_pHandlersToRemove;
    protected Array<CCTouchHandler> m_pStandardHandlers;
    protected Array<CCTouchHandler> m_pTargetedHandlers;
    
  /// <summary>
    /// Whether or not the events are going to be dispatched. Default: true
    /// </summary>
    public boolean getIsDispatchEvents()
    {
        return m_bDispatchEvents;
    }
    
  /// <summary>
    /// Whether or not the events are going to be dispatched. Default: true
    /// </summary>
    public void setIsDispatchEvents(boolean value)
    {
        m_bDispatchEvents = value;
    }
    
    
    public boolean init()
    {
        m_bDispatchEvents = true;
        m_pTargetedHandlers = new Array<CCTouchHandler>();
        m_pStandardHandlers = new Array<CCTouchHandler>();

        m_pHandlersToAdd = new Array<CCTouchHandler>();
        m_pHandlersToRemove = new Array<Object>();

        m_bToRemove = false;
        m_bToAdd = false;
        m_bToQuit = false;
        m_bLocked = false;

        return true;
    }
  /// <summary>
    /// Adds a standard touch delegate to the dispatcher's list.
    /// See StandardTouchDelegate description.
    /// IMPORTANT: The delegate will be retained.
    /// </summary>
	public void addStandardDelegate(ICCStandardTouchDelegate pDelegate, int nPriority)
    {
        CCTouchHandler pHandler = CCStandardTouchHandler.HandlerWithDelegate(pDelegate, nPriority);
        if (!m_bLocked)
        {
            //ForceAddHandler(pHandler, m_pStandardHandlers);
        }
        else
        {
            m_pHandlersToAdd.add(pHandler);
            m_bToAdd = true;
        }
    }
	/// <summary>
    /// Adds a targeted touch delegate to the dispatcher's list.
    /// See TargetedTouchDelegate description.
    /// IMPORTANT: The delegate will be retained.
    /// </summary>
    public void addTargetedDelegate(ICCTargetedTouchDelegate pDelegate, int nPriority, Boolean bSwallowsTouches)
    {
    	CCTouchHandler pHandler = CCTargetedTouchHandler.HandlerWithDelegate(pDelegate, nPriority, bSwallowsTouches);
        if (!m_bLocked)
        {
            forceAddHandler(pHandler, m_pTargetedHandlers);
        }
        else
        {
            m_pHandlersToAdd.add(pHandler);
            m_bToAdd = true;
        }
    }
		
  /// <summary>
    /// Removes a touch delegate.
    /// The delegate will be released
    /// </summary>
    public void removeDelegate(ICCTouchDelegate pDelegate)
    {
        if (pDelegate == null)
        {
            return;
        }

        if (!m_bLocked)
        {
            forceRemoveDelegate(pDelegate);
        }
        else
        {
            m_pHandlersToRemove.add(pDelegate);
            m_bToRemove = true;
        }
    }
    
  /// <summary>
    /// Removes all touch delegates, releasing all the delegates
    /// </summary>
    public void removeAllDelegates()
    {
        if (!m_bLocked)
        {
            forceRemoveAllDelegates();
        }
        else
        {
            m_bToQuit = true;
        }
    }
    
	protected void forceAddHandler(CCTouchHandler pHandler, Array<CCTouchHandler> pArray)
    {
        int u = 0;
        for (int i = 0; i < pArray.size; i++)
        {
            CCTouchHandler h = pArray.get(i);

            if (h != null)
            {
                if (h.getPriority() < pHandler.getPriority())
                {
                    ++u;
                }

                if (h.getDelegate() == pHandler.getDelegate())
                {
                    return;
                }
            }
        }

        pArray.insert(u, pHandler);
    }
	@Override
	public void touchesBegan(Array<CCTouch> touches) 
	{
		if (m_bDispatchEvents)
		{
			touches(touches, CCTouchType.Began);
		}	
	}
	@Override
	public void touchesMoved(Array<CCTouch> touches)
	{

	}
	@Override
	public void touchesEnded(Array<CCTouch> touches)
	{
		if (m_bDispatchEvents)
        {
            touches(touches, CCTouchType.Ended);
        }
	}
	@Override
	public void touchesCancelled(Array<CCTouch> touches)
	{
		
	}
	
	public void touches(Array<CCTouch> pTouches, CCTouchType touchType)
	{
		m_bLocked = true;

        // optimization to prevent a mutable copy when it is not necessary
        int uTargetedHandlersCount = m_pTargetedHandlers.size;
        int uStandardHandlersCount = m_pStandardHandlers.size;
        boolean bNeedsMutableSet = (uTargetedHandlersCount > 0 && uStandardHandlersCount > 0);

        if (bNeedsMutableSet)
        {
        	CCTouch[] tempArray = new CCTouch[pTouches.size];
            tempArray = pTouches.toArray();
            //Collections.addAll(pMutableTouches, tempArray);
            pMutableTouches = new Array<CCTouch>(tempArray);
        }
        else
        {
            pMutableTouches = pTouches;
        }
		
        CCTouchType sHelper = touchType;
		
        
     // process the target handlers 1st
        if (uTargetedHandlersCount > 0)
        {
            for (CCTouch pTouch : pTouches)
            {
                for (CCTouchHandler pcHandler : m_pTargetedHandlers)
                {
                	CCTargetedTouchHandler pHandler = (CCTargetedTouchHandler)pcHandler;
                	ICCTargetedTouchDelegate pDelegate = (ICCTargetedTouchDelegate) (pHandler.getDelegate());

                    boolean bClaimed = false;
                    if (sHelper == CCTouchType.Began)
                    {
                        bClaimed = pDelegate.touchBegan(pTouch);

                        if (bClaimed)
                        {
                            pHandler.getClaimedTouches().add(pTouch);
                        }
                    }
                    else
                    {
                        if (pHandler.getClaimedTouches().contains(pTouch))
                        {
                            // moved ended cancelled
                            bClaimed = true;

                            switch (sHelper)
                            {
                                case Moved:
                                    pDelegate.touchMoved(pTouch);
                                    break;
                                case Ended:
                                    pDelegate.touchEnded(pTouch);
                                    pHandler.getClaimedTouches().remove(pTouch);
                                    break;
                                case Cancelled:
                                    pDelegate.touchCancelled(pTouch);
                                    pHandler.getClaimedTouches().remove(pTouch);
                                    break;
                            }
                        }
                    }

                    if (bClaimed && pHandler.getIsSwallowsTouches())
                    {
                        if (bNeedsMutableSet)
                        {
                            pMutableTouches.removeValue(pTouch, true);
                        }

                        break;
                    }
                }
            }

        }

        // process standard handlers 2nd
        if (uStandardHandlersCount > 0 && pMutableTouches.size > 0)
        {
            for (CCTouchHandler pcHandler : m_pStandardHandlers)
            {
            	CCStandardTouchHandler pHandler = (CCStandardTouchHandler)pcHandler;
            	ICCStandardTouchDelegate pDelegate = (ICCStandardTouchDelegate) pHandler.getDelegate();
                switch (sHelper)
                {
                    case Began:
                        pDelegate.touchesBegan(pMutableTouches);
                        break;
                    case Moved:
                        pDelegate.touchesMoved(pMutableTouches);
                        break;
                    case Ended:
                        pDelegate.touchesEnded(pMutableTouches);
                        break;
                    case Cancelled:
                        pDelegate.touchesCancelled(pMutableTouches);
                        break;
                }
            }

        }

        if (bNeedsMutableSet)
        {
            pMutableTouches = null;
        }

        //
        // Optimization. To prevent a [handlers copy] which is expensive
        // the add/removes/quit is done after the iterations
        //
        m_bLocked = false;
        if (m_bToRemove)
        {
            m_bToRemove = false;
            for (int i = 0; i < m_pHandlersToRemove.size; ++i)
            {
                forceRemoveDelegate((ICCTouchDelegate) m_pHandlersToRemove.get(i));
            }
            m_pHandlersToRemove.clear();
        }

        if (m_bToAdd)
        {
            m_bToAdd = false;
            for (CCTouchHandler pHandler : m_pHandlersToAdd)
            {
                if (pHandler instanceof CCTargetedTouchHandler && pHandler.getDelegate() instanceof ICCTargetedTouchDelegate)
                {
                    forceAddHandler(pHandler, m_pTargetedHandlers);
                }
                else if (pHandler instanceof CCStandardTouchHandler && pHandler.getDelegate() instanceof ICCStandardTouchDelegate)
                {
                    forceAddHandler(pHandler, m_pStandardHandlers);
                }
                else
                {
                    //CCLog.Log("ERROR: inconsistent touch handler and delegate found in m_pHandlersToAdd of CCTouchDispatcher");
                }
            }

            m_pHandlersToAdd.clear();
        }

        if (m_bToQuit)
        {
            m_bToQuit = false;
            forceRemoveAllDelegates();
        }
        
    
        
//		for (CCTouchHandler pHandler : m_pTargetedHandlers)
//		{
//			ICCTargetedTouchDelegate pDelegate = (ICCTargetedTouchDelegate)pHandler.getDelegate();
//			pDelegate.touchBegan(pTouches.get(0));
//		}
        
	}
	
	
	protected void forceRemoveDelegate(ICCTouchDelegate pDelegate)
    {
        // remove handler from m_pStandardHandlers
        for (CCTouchHandler pHandler : m_pStandardHandlers)
        {
            if (pHandler != null && pHandler.getDelegate() == pDelegate)
            {
                m_pStandardHandlers.removeValue(pHandler, true);
                break;
            }
        }

        // remove handler from m_pTargetedHandlers
        for (CCTouchHandler pHandler : m_pTargetedHandlers)
        {
            if (pHandler != null && pHandler.getDelegate() == pDelegate)
            {
                m_pTargetedHandlers.removeValue(pHandler, true);
                break;
            }
        }
    }
	
	protected void forceRemoveAllDelegates()
    {
        m_pStandardHandlers.clear();
        m_pTargetedHandlers.clear();
    }
	
	public enum CCTouchType
    {
        Began,
        Moved,
        Ended,
        Cancelled,
        TouchMax
    }
}
