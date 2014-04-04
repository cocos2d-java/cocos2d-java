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

package cocos2d.platform;

import java.util.LinkedList;

public class CCFocusManager
{
    //public event CCFocusChangeDelegate OnFocusChanged;

    /// <summary>
    /// The list of nodes that can receive focus
    /// </summary>
    private LinkedList<ICCFocusable> _FocusList = new LinkedList<ICCFocusable>();
    /// <summary>
    /// The current node with focus
    /// </summary>
    //private LinkedListNode<ICCFocusable> _Current = null;

    /// <summary>
    /// Removes the given focusable node
    /// </summary>
    /// <param name="f"></param>
    public void remove(ICCFocusable f)
    {
        _FocusList.remove(f);
    }

    /// <summary>
    /// Adds the given node to the list of focus nodes. If the node has the focus, then it is
    /// given the current focused item status. If there is already a focused item and the
    /// given node has focus, the focus is disabled.
    /// </summary>
    /// <param name="f"></param>
//    public void add(ICCFocusable f)
//    {
//        LinkedListNode<ICCFocusable> i = _FocusList.addLast(f);
//        if (f.HasFocus)
//        {
//            if (_Current == null)
//            {
//                _Current = i;
//            }
//            else
//            {
//                f.HasFocus = false;
//            }
//        }
//    }
//
//    /// <summary>
//    /// When false, the focus will not traverse on the keyboard or dpad events.
//    /// </summary>
//    public bool Enabled
//    {
//        get;
//        set;
//    }
//
//    /// <summary>
//    /// Scroll to the next item in the focus list.
//    /// </summary>
//    public void FocusNextItem()
//    {
//        if (_Current == null && _FocusList.Count > 0)
//        {
//            _Current = _FocusList.First;
//            _Current.Value.HasFocus = true;
//            if (OnFocusChanged != null)
//            {
//                OnFocusChanged(null, _Current.Value);
//            }
//        }
//        else if (_Current != null)
//        {
//            ICCFocusable lostItem = _Current.Value;
//            // Search for the next node.
//            LinkedListNode<ICCFocusable> nextItem = null;
//            for (LinkedListNode<ICCFocusable> p = _Current.Next; p != null; p = p.Next)
//            {
//                if (p.Value.CanReceiveFocus)
//                {
//                    nextItem = p;
//                }
//            }
//            if (nextItem != null)
//            {
//                _Current = nextItem;
//            }
//            else
//            {
//                _Current = _FocusList.First;
//            }
//            lostItem.HasFocus = false;
//            _Current.Value.HasFocus = true;
//            if (OnFocusChanged != null)
//            {
//                OnFocusChanged(lostItem, _Current.Value);
//            }
//        }
//        else
//        {
//            _Current = null;
//        }
//    }
//
//    /// <summary>
//    /// Scroll to the previous item in the focus list.
//    /// </summary>
//    public void FocusPreviousItem()
//    {
//        if (ItemWithFocus == null && _FocusList.Count > 0)
//        {
//            _Current = _FocusList.Last;
//            _Current.Value.HasFocus = true;
//            if (OnFocusChanged != null)
//            {
//                OnFocusChanged(null, _Current.Value);
//            }
//        }
//        else if (_Current != null)
//        {
//            ICCFocusable lostItem = _Current.Value;
//            LinkedListNode<ICCFocusable> nextItem = null;
//            for (LinkedListNode<ICCFocusable> p = _Current.Previous; p != null; p = p.Previous)
//            {
//                if (p.Value.CanReceiveFocus)
//                {
//                    nextItem = p;
//                }
//            }
//            if (_Current.Previous != null)
//            {
//                _Current = _Current.Previous;
//            }
//            else
//            {
//                _Current = _FocusList.Last;
//            }
//            lostItem.HasFocus = false;
//            _Current.Value.HasFocus = true;
//            if (OnFocusChanged != null)
//            {
//                OnFocusChanged(lostItem, _Current.Value);
//            }
//        }
//        else
//        {
//            _Current = null;
//        }
//    }
//
//    /// <summary>
//    /// Returns the item with the current focus. This test will create a copy 
//    /// of the master item list.
//    /// </summary>
//    public ICCFocusable ItemWithFocus
//    {
//        get
//        {
//            return (_Current != null ? _Current.Value : null);
//        }
//    }
//
//
//    private long m_lTimeOfLastFocus = 0L;
//    private bool m_bScrollingPrevious = false;
//    private bool m_bScrollingNext = false;
//    /// <summary>
//    /// Scrolling focus delay used to slow down automatic focus changes when the dpad is held.
//    /// </summary>
//    public static float MenuScrollDelay = 50f;
//
//    #region Singleton
//
//    private CCFocusManager()
//    {
//        CCApplication.SharedApplication.GamePadDPadUpdate += new CCGamePadDPadDelegate(SharedApplication_GamePadDPadUpdate);
//    }
//
//    private void SharedApplication_GamePadDPadUpdate(CCGamePadButtonStatus leftButton, CCGamePadButtonStatus upButton, CCGamePadButtonStatus rightButton, CCGamePadButtonStatus downButton, Microsoft.Xna.Framework.PlayerIndex player)
//    {
//        if (!Enabled)
//        {
//            return;
//        }
//        if (leftButton == CCGamePadButtonStatus.Released || upButton == CCGamePadButtonStatus.Released || rightButton == CCGamePadButtonStatus.Released || downButton == CCGamePadButtonStatus.Released)
//        {
//            m_bScrollingPrevious = false;
//            m_lTimeOfLastFocus = 0L;
//        }
//        // Left and right d-pad shuffle through the menus
//        else if (leftButton == CCGamePadButtonStatus.Pressed || upButton == CCGamePadButtonStatus.Pressed)
//        {
//            if (m_bScrollingPrevious)
//            {
//                TimeSpan ts = new TimeSpan(DateTime.Now.Ticks - m_lTimeOfLastFocus);
//                if (ts.TotalMilliseconds > MenuScrollDelay)
//                {
//                    FocusPreviousItem();
//                }
//            }
//            else
//            {
//                m_bScrollingPrevious = true;
//                m_lTimeOfLastFocus = DateTime.Now.Ticks;
//            }
//        }
//        else if (rightButton == CCGamePadButtonStatus.Pressed || downButton == CCGamePadButtonStatus.Pressed)
//        {
//            if (m_bScrollingNext)
//            {
//                TimeSpan ts = new TimeSpan(DateTime.Now.Ticks - m_lTimeOfLastFocus);
//                if (ts.TotalMilliseconds > MenuScrollDelay)
//                {
//                    FocusNextItem();
//                }
//            }
//            else
//            {
//                m_bScrollingNext = true;
//                m_lTimeOfLastFocus = DateTime.Now.Ticks;
//            }
//        }
//    }
//
//    private static CCFocusManager _Instance = new CCFocusManager();
//
//    public static CCFocusManager Instance
//    {
//        get
//        {
//            return (_Instance);
//        }
//    }
//    #endregion
}