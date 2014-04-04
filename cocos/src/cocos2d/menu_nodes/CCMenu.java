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

import java.util.ArrayList;
import java.util.LinkedList;

import cocos2d.CCDirector;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.layers_scenes_transitions_nodes.CCLayerRGBA;
import cocos2d.predefine.CCTouch;

/// <summary>
/// A CCMenu
/// Features and Limitation:
///  You can add MenuItem objects in runtime using addChild:
///  But the only accecpted children are MenuItem objects
/// </summary>
public class CCMenu extends CCLayerRGBA
{
    public static final float kDefaultPadding = 5;
    public static final int kCCMenuHandlerPriority = -128;

    protected boolean m_bEnabled;
    protected CCMenuState m_eState;
    protected CCMenuItem m_pSelectedItem;

    private LinkedList<CCMenuItem> _Items = new LinkedList<CCMenuItem>();


    /// <summary>
    /// Default ctor that sets the content size of the menu to match the window size.
    /// </summary>
    private CCMenu() 
    {
        init();
//        ContentSize = CCDirector.SharedDirector.WinSize;
    }
    public CCMenu(CCMenuItem... items)
    {
        initWithItems(items);
    }

    @Override
    public void setHasFocus(boolean value)
    {
        super.setHasFocus(value);
        // Set the first menu item to have the focus
        if (getFocusedItem() == null)
        {
            //_Items.First.Value.HasFocus = true;
        	_Items.getFirst().setHasFocus(true);
        }   
    }


    /// <summary>
    /// Returns the menu item with the focus. Note that this only has a value if the GamePad or Keyboard is enabled. Touch
    /// devices do not have a "focus" concept.
    /// </summary>
    public CCMenuItem getFocusedItem()
    {
        // Find the item with the focus
        for (CCMenuItem item : _Items) 
        {
            if (item.getHasFocus())
            {
                return (item);
            }
        }
        return (null);
    }

    public boolean getEnabled()
    {
        return m_bEnabled;
    }

    public void setEnabled(boolean value)
    {
        m_bEnabled = value;
    }
 
    @Override
    public boolean init()
    {
    	if (_Items == null)
    	{
    		_Items = new LinkedList<CCMenuItem>();
    	}
        return initWithArray(null);
    }

    public boolean initWithItems(CCMenuItem... items)
    {
    	if (_Items == null)
    	{
    		_Items = new LinkedList<CCMenuItem>();
    	}
        return initWithArray(items);
    }

    /// <summary>
    /// The position of the menu is set to the center of the main screen
    /// </summary>
    /// <param name="items"></param>
    /// <returns></returns>
    private boolean initWithArray(CCMenuItem[] items)
    {
        if (_Items.size() > 0)
        {
            ArrayList<CCMenuItem> copy = new ArrayList<CCMenuItem>(_Items);
            for (CCMenuItem i : copy)
            {
                removeChild(i, false);
            }
        }
        if (super.init())
        {
            setTouchPriority(kCCMenuHandlerPriority);
            setTouchMode(CCTouchMode.OneByOne);
            setTouchEnabled(true);

            m_bEnabled = true;
            // menu in the center of the screen
            CCSize s = new CCSize(CCDirector.sharedDirector().getWinSize());

            setIgnoreAnchorPointForPosition(true);
            setAnchorPoint(new CCPoint(0.5f, 0.5f));
            setContentSize(s);

            setPosition(new CCPoint(s.width / 2, s.height / 2));

            if (items != null)
            {
                int z = 0;
                for (CCMenuItem item : items)
                {
                    addChild(item, z);
                    z++;
                }
            }

            //    [self alignItemsVertically];
            m_pSelectedItem = null;
            m_eState = CCMenuState.Waiting;

            // enable cascade color and opacity on menus
            setCascadeColorEnabled(true);
            setCascadeOpacityEnabled(true);

            return true;
        }
        return false;
    }

    /*
    * override add:
    */

    @Override
    public void addChild(CCNode child, int zOrder, int tag)
    {
        //Debug.Assert(child is CCMenuItem, "Menu only supports MenuItem objects as children");
        super.addChild(child, zOrder, tag);
        if (_Items.size() == 0)
        {
            //_Items.AddFirst(child as CCMenuItem);
        	if (child instanceof CCMenuItem)
        	{
        		_Items.addFirst((CCMenuItem)child);
        	}
        	else
        	{
        		_Items.addFirst(null);
			}

        }
        else
        {
            //_Items.AddLast(child as CCMenuItem);
        	if (child instanceof CCMenuItem)
        	{
        		_Items.addLast((CCMenuItem)child);
        	}
        	else
        	{
        		_Items.addLast(null);
			}
        }
    }

    @Override
    public void removeChild(CCNode child, boolean cleanup)
    {
        //Debug.Assert(child is CCMenuItem, "Menu only supports MenuItem objects as children");

        if (m_pSelectedItem == child)
        {
            m_pSelectedItem = null;
        }

        super.removeChild(child, cleanup);
        if (child instanceof CCMenuItem)
        {
        	if (_Items.contains((CCMenuItem)child))
            {
                _Items.remove((CCMenuItem)child);
            }
        }   
    }

    @Override
    public void onEnter()
    {
        super.onEnter();
        
//        for (CCMenuItem item : _Items)
//        {
//            CCFocusManager.Instance.Add(item);
//        }
    }
//
//    public override void OnExit()
//    {
//        if (m_eState == CCMenuState.TrackingTouch)
//        {
//            if (m_pSelectedItem != null)
//            {
//                m_pSelectedItem.Unselected();
//                m_pSelectedItem = null;
//            }
//            m_eState = CCMenuState.Waiting;
//        }
//        
//        foreach (CCMenuItem item in _Items)
//        {
//            CCFocusManager.Instance.Remove(item);
//        }
//        
//        base.OnExit();
//    }
//
//    #region Menu - Events
//
//    public void SetHandlerPriority(int newPriority)
//    {
//        CCTouchDispatcher pDispatcher = CCDirector.SharedDirector.TouchDispatcher;
//        pDispatcher.SetPriority(newPriority, this);
//    }
//
    @Override
    public void registerWithTouchDispatcher()
    {
        CCDirector pDirector = CCDirector.sharedDirector();
        pDirector.getTouchDispatcher().addTargetedDelegate(this, kCCMenuHandlerPriority, true);
    }
    
    @Override
    public Boolean touchBegan(CCTouch touch)
    {
        if (m_eState != CCMenuState.Waiting || !m_bVisible || !m_bEnabled)
        {
            return false;
        }

        for (CCNode c = m_pParent; c != null; c = c.getParent())
        {
            if (c.getVisible() == false)
            {
                return false;
            }
        }

        m_pSelectedItem = ItemForTouch(touch);
        if (m_pSelectedItem != null)
        {
            m_eState = CCMenuState.TrackingTouch;
            m_pSelectedItem.selected();
            return true;
        }
        return false;
    }

    @Override
    public void touchEnded(CCTouch touch)
    {
        //Debug.Assert(m_eState == CCMenuState.TrackingTouch, "[Menu TouchEnded] -- invalid state");
        if (m_pSelectedItem != null)
        {
            m_pSelectedItem.unselected();
            try 
            {
				m_pSelectedItem.activate();
			} 
            catch (Exception e)
            {
				e.printStackTrace();
			}
        }
        m_eState = CCMenuState.Waiting;
    }
//
//    public override void TouchCancelled(CCTouch touch)
//    {
//        Debug.Assert(m_eState == CCMenuState.TrackingTouch, "[Menu ccTouchCancelled] -- invalid state");
//        if (m_pSelectedItem != null)
//        {
//            m_pSelectedItem.Unselected();
//        }
//        m_eState = CCMenuState.Waiting;
//    }
//
//    public override void TouchMoved(CCTouch touch)
//    {
//        Debug.Assert(m_eState == CCMenuState.TrackingTouch, "[Menu TouchMoved] -- invalid state");
//        CCMenuItem currentItem = ItemForTouch(touch);
//        if (currentItem != m_pSelectedItem)
//        {
//            if (m_pSelectedItem != null)
//            {
//                m_pSelectedItem.Unselected();
//            }
//
//            m_pSelectedItem = currentItem;
//
//            if (m_pSelectedItem != null)
//            {
//                m_pSelectedItem.Selected();
//            }
//        }
//    }
//
//    #endregion
//
//    #region Menu - Alignment
//
//    public void AlignItemsVertically()
//    {
//        AlignItemsVerticallyWithPadding(kDefaultPadding);
//    }
//
//    public void AlignItemsVerticallyWithPadding(float padding)
//    {
//        float width = 0f;
//        float height = -padding;
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren[i];
//                if (!pChild.Visible)
//                {
//                    continue;
//                }
//                height += pChild.ContentSize.Height * pChild.ScaleY + padding;
//                width = Math.Max(width, pChild.ContentSize.Width);
//            }
//        }
//
//        float y = height / 2.0f;
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren[i];
//                if (!pChild.Visible)
//                {
//                    continue;
//                }
//                pChild.Position = new CCPoint(0, y - pChild.ContentSize.Height * pChild.ScaleY / 2.0f);
//                y -= pChild.ContentSize.Height * pChild.ScaleY + padding;
//                width = Math.Max(width, pChild.ContentSize.Width);
//            }
//        }
//        ContentSize = new CCSize(width, height);
//    }
//
//    public void AlignItemsHorizontally()
//    {
//        AlignItemsHorizontallyWithPadding(kDefaultPadding);
//    }
//
//    public void AlignItemsHorizontallyWithPadding(float padding)
//    {
//        float height = 0f;
//        float width = -padding;
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren[i];
//                if (pChild.Visible)
//                {
//                    width += pChild.ContentSize.Width * pChild.ScaleX + padding;
//                    height = Math.Max(height, pChild.ContentSize.Height);
//                }
//            }
//        }
//
//        float x = -width / 2.0f;
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren[i];
//                if (pChild.Visible)
//                {
//                    pChild.Position = new CCPoint(x + pChild.ContentSize.Width * pChild.ScaleX / 2.0f, 0);
//                    x += pChild.ContentSize.Width * pChild.ScaleX + padding;
//                    height = Math.Max(height, pChild.ContentSize.Height);
//                }
//            }
//        }
//        ContentSize = new CCSize(width, height);
//    }
//
//    public void AlignItemsInColumns(params int[] columns)
//    {
//        int[] rows = columns;
//
//        int height = -5;
//        int row = 0;
//        int rowHeight = 0;
//        int columnsOccupied = 0;
//        int rowColumns;
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren.Elements[i];
//                if (!pChild.Visible)
//                {
//                    continue;
//                }
//                Debug.Assert(row < rows.Length, "");
//
//                rowColumns = rows[row];
//                // can not have zero columns on a row
//                Debug.Assert(rowColumns > 0, "");
//
//                float tmp = pChild.ContentSize.Height;
//                rowHeight = (int) ((rowHeight >= tmp || float.IsNaN(tmp)) ? rowHeight : tmp);
//
//                ++columnsOccupied;
//                if (columnsOccupied >= rowColumns)
//                {
//                        height += rowHeight + (int)kDefaultPadding;
//
//                    columnsOccupied = 0;
//                    rowHeight = 0;
//                    ++row;
//                }
//            }
//        }
//
//        // check if too many rows/columns for available menu items
//        Debug.Assert(columnsOccupied == 0, "");
//
//        CCSize winSize = ContentSize; // CCDirector.SharedDirector.WinSize;
//
//        row = 0;
//        rowHeight = 0;
//        rowColumns = 0;
//        float w = 0.0f;
//        float x = 0.0f;
//        float y = (height / 2f);
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren.Elements[i];
//                if (!pChild.Visible)
//                {
//                    continue;
//                }
//                if (rowColumns == 0)
//                {
//                    rowColumns = rows[row];
//                        if (rowColumns == 0)
//                        {
//                            throw (new ArgumentException("Can not have a zero column size for a row."));
//                        }
//                        w = (winSize.Width - 2 * kDefaultPadding) / rowColumns; // 1 + rowColumns
//                        x = w/2f; // center of column
//                }
//
//                    float tmp = pChild.ContentSize.Height*pChild.ScaleY;
//                rowHeight = (int) ((rowHeight >= tmp || float.IsNaN(tmp)) ? rowHeight : tmp);
//
//                    pChild.Position = new CCPoint(kDefaultPadding + x - (winSize.Width - 2*kDefaultPadding) / 2,
//                                           y - pChild.ContentSize.Height*pChild.ScaleY / 2);
//
//                x += w;
//                ++columnsOccupied;
//
//                if (columnsOccupied >= rowColumns)
//                {
//                    y -= rowHeight + 5;
//
//                    columnsOccupied = 0;
//                    rowColumns = 0;
//                    rowHeight = 0;
//                    ++row;
//                }
//            }
//        }
//    }
//
//
//    public void AlignItemsInRows(params int[] rows)
//    {
//        int[] columns = rows;
//
//        List<int> columnWidths = new List<int>();
//
//        List<int> columnHeights = new List<int>();
//
//
//        int width = -10;
//        int columnHeight = -5;
//        int column = 0;
//        int columnWidth = 0;
//        int rowsOccupied = 0;
//        int columnRows;
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren.Elements[i];
//                if (!pChild.Visible)
//                {
//                    continue;
//                }
//
//                // check if too many menu items for the amount of rows/columns
//                Debug.Assert(column < columns.Length, "");
//
//                columnRows = columns[column];
//                // can't have zero rows on a column
//                Debug.Assert(columnRows > 0, "");
//
//                // columnWidth = fmaxf(columnWidth, [item contentSize].width);
//                float tmp = pChild.ContentSize.Width * pChild.ScaleX;
//                columnWidth = (int)((columnWidth >= tmp || float.IsNaN(tmp)) ? columnWidth : tmp);
//
//
//                columnHeight += (int)(pChild.ContentSize.Height * pChild.ScaleY + 5);
//                ++rowsOccupied;
//
//                if (rowsOccupied >= columnRows)
//                {
//                    columnWidths.Add(columnWidth);
//                    columnHeights.Add(columnHeight);
//                    width += columnWidth + 10;
//
//                    rowsOccupied = 0;
//                    columnWidth = 0;
//                    columnHeight = -5;
//                    ++column;
//                }
//            }
//        }
//
//        // check if too many rows/columns for available menu items.
//        Debug.Assert(rowsOccupied == 0, "");
//
//        CCSize winSize = ContentSize; // CCDirector.SharedDirector.WinSize;
//
//        column = 0;
//        columnWidth = 0;
//        columnRows = 0;
//        float x = (-width / 2f);
//        float y = 0.0f;
//
//        if (m_pChildren != null && m_pChildren.count > 0)
//        {
//            for (int i = 0, count = m_pChildren.count; i < count; i++)
//            {
//                CCNode pChild = m_pChildren.Elements[i];
//                if (!pChild.Visible)
//                {
//                    continue;
//                }
//
//                if (columnRows == 0)
//                {
//                    columnRows = columns[column];
//                    y = columnHeights[column];
//                }
//
//                // columnWidth = fmaxf(columnWidth, [item contentSize].width);
//                float tmp = pChild.ContentSize.Width * pChild.ScaleX;
//                columnWidth = (int)((columnWidth >= tmp || float.IsNaN(tmp)) ? columnWidth : tmp);
//
//                pChild.Position = new CCPoint(x + columnWidths[column] / 2,
//                                              y - winSize.Height / 2);
//                y -= pChild.ContentSize.Height * pChild.ScaleY + 10;
//                ++rowsOccupied;
//
//                if (rowsOccupied >= columnRows)
//                {
//                    x += columnWidth + 5;
//                    rowsOccupied = 0;
//                    columnRows = 0;
//                    columnWidth = 0;
//                    ++column;
//                }
//            }
//        }
//    }
//

//
    protected CCMenuItem ItemForTouch(CCTouch touch)
    {
        CCPoint touchLocation =  new CCPoint(touch.getLocation());

        if (m_pChildren != null && m_pChildren.size > 0)
        {
            for (int i = m_pChildren.size -1; i >= 0; i--)
            {
                //var pChild = m_pChildren.Elements[i] as CCMenuItem;
            	CCMenuItem pChild = null;
            	if (m_pChildren.get(i) instanceof CCMenuItem)
            	{
            		pChild = (CCMenuItem)m_pChildren.get(i);
            	}       	
                if (pChild != null && pChild.getVisible() && pChild.getEnabled())
                {
                    CCPoint local = new CCPoint(pChild.convertToNodeSpace(touchLocation));
                    CCRect r = new CCRect(pChild.getRectangle());
                    r.Origin = CCPoint.Zero;

                    if (r.ContainsPoint(local))
                    {
                        return pChild;
                    }
                }
            }
        }

        return null;
    }
}
