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

package cocos2d.sprite_nodes;

import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.cocoa.ICCCopyable;
import cocos2d.textures.CCTexture2D;

public class CCSpriteFrame implements ICCCopyable
{
    protected boolean m_bRotated;
    protected CCPoint m_obOffset;
    protected CCPoint m_obOffsetInPixels;
    protected CCSize m_obOriginalSize;
    protected CCSize m_obOriginalSizeInPixels;
    protected CCRect m_obRect;
    protected CCRect m_obRectInPixels;
    protected CCTexture2D m_pobTexture;
    protected String m_strTextureFilename;

    /// <summary>
    /// get or set rect of the frame
    /// </summary>
    public CCRect getRect()
    {
        return m_obRect;
    }

    public void setRect(CCRect value)
    {  
        m_obRect = value;
        m_obRectInPixels = m_obRect.pointsToPixels();
    }
    
    public CCRect getRectInPixels()
    {
        return m_obRectInPixels;
    }

    public void setRectInPixels(CCRect value)
    {   
        m_obRectInPixels = value;
        m_obRect = m_obRectInPixels.pixelsToPoints(); 
    }
    
    public CCPoint getOffset()
    {
        return m_obOffset;
    }

    public void setOffset(CCPoint value)
    { 
        m_obOffset = value;
        m_obOffsetInPixels = m_obOffset.pointsToPixels();  
    }
    
    public CCPoint getOffsetInPixels()
    {
        return m_obOffsetInPixels;
    }

    public void setOffsetInPixels(CCPoint value)
    { 
        m_obOffsetInPixels = value;
        m_obOffset = m_obOffsetInPixels.pixelsToPoints();  
    }
    
    public boolean getIsRotated()
    {
        return m_bRotated;
    }

    public void setIsRotated(boolean value)
    {
        m_bRotated = value;
    }

    public CCSize getOriginalSizeInPixels()
    {
        return m_obOriginalSizeInPixels;
    }

    public void setOriginalSizeInPixels(CCSize value)
    {
        m_obOriginalSizeInPixels = new CCSize(value);
    }
    
    public CCSize getOriginalSize()
    {
        return m_obOriginalSize;
    }

    public void setOriginalSize(CCSize value)
    {
        m_obOriginalSize = new CCSize(value);
    }
    
    /// <summary>
    /// get or set texture of the frame
    /// </summary>
    public CCTexture2D getTexture()
    {
        return m_pobTexture;
    }

    public void setTexture(CCTexture2D value)
    {
        m_pobTexture = value;
    }
    
    public CCSpriteFrame() { }
  
	public CCSpriteFrame copy()
	{
		return (CCSpriteFrame)copy(null);
	}

    public Object copy(ICCCopyable pZone)
    {
    	CCSpriteFrame pCopy = new CCSpriteFrame();
        pCopy.initWithTexture(m_pobTexture, m_obRectInPixels, m_bRotated, m_obOffsetInPixels, m_obOriginalSizeInPixels);
        return pCopy;
    }

    public CCSpriteFrame(CCTexture2D pobTexture, CCRect rect)
    {
        initWithTexture(pobTexture, rect);
    }

    public CCSpriteFrame(CCTexture2D pobTexture, CCRect rect, boolean rotated, CCPoint offset,
                                       CCSize originalSize)
    {
        initWithTexture(pobTexture, rect, rotated, offset, originalSize);
    }

    protected boolean initWithTexture(CCTexture2D pobTexture, CCRect rect)
    {
        CCRect rectInPixels = new CCRect(rect.pointsToPixels());
        return initWithTexture(pobTexture, rectInPixels, false, new CCPoint(0, 0), rectInPixels.Size);
    }

    protected boolean initWithTexture(CCTexture2D pobTexture, CCRect rect, boolean rotated, CCPoint offset,
                                CCSize originalSize)
    {
        m_pobTexture = pobTexture;

        m_obRectInPixels = rect;
        m_obRect = rect.pixelsToPoints();
        m_obOffsetInPixels = offset;
        m_obOffset = m_obOffsetInPixels.pixelsToPoints();
        m_obOriginalSizeInPixels = originalSize;
        m_obOriginalSize = m_obOriginalSizeInPixels.pixelsToPoints();
        m_bRotated = rotated;

        return true;
    }

    protected boolean initWithTextureFilename(String filename, CCRect rect)
    {
        return initWithTextureFilename(filename, rect, false, CCPoint.Zero, rect.Size);
    }

    protected boolean initWithTextureFilename(String filename, CCRect rect, boolean rotated, CCPoint offset,
                                        CCSize originalSize)
    {
        m_pobTexture = null;
        m_strTextureFilename = filename;
        m_obRectInPixels = rect;
        m_obRect = rect.pixelsToPoints();
        m_obOffsetInPixels = offset;
        m_obOffset = m_obOffsetInPixels.pixelsToPoints();
        m_obOriginalSizeInPixels = originalSize;
        m_obOriginalSize = m_obOriginalSizeInPixels.pixelsToPoints();
        m_bRotated = rotated;

        return true;
    }
}