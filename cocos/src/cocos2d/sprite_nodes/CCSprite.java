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

import cocos2d.base_nodes.CCNodeRGBA;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.platform.CCDrawManager;
import cocos2d.predefine.ICCTextureProtocol;
import cocos2d.textures.CCTexture2D;
import cocos2d.textures.CCTextureCache;

public class CCSprite extends CCNodeRGBA implements ICCTextureProtocol
{
	
	protected boolean m_bDirty = false;  // Sprite needs to be updated
	protected boolean m_bFlipX = false;
    protected boolean m_bFlipY = false;
	protected boolean m_bHasChildren; // optimization to check if it contain children
	protected boolean m_bOpacityModifyRGB;
	protected boolean m_bRecursiveDirty;
	protected CCTexture2D m_pobTexture; // Texture used to render the sprite
	protected boolean m_bRectRotated;
	protected CCPoint m_obUnflippedOffsetPositionFromCenter;
	
	// Offset Position (used by Zwoptex)
    protected CCPoint m_obOffsetPosition = CCPoint.zero();
	
	//SetTextureRect
	protected CCRect m_obRect;
	
	
	public CCSprite()
	{
		super();
		
	}
	
	public CCSprite (String fileName)
    {
        if (!initWithFile(fileName))
        {
			//CCLog.Log("CCSprite (string fileName): Problems initializing class"); 
		}
    }
	
	public boolean getDirty()
    {
        return m_bDirty;
    }
	
	public void setDirty(boolean value)
    {
        m_bDirty = value;
        setDirtyRecursively(value); 
    }
	
	@Override
	public CCPoint getPosition()
	{		
		return super.m_obPosition;
	}
	
	@Override
	public void setPosition(CCPoint point)
	{
		super.setPosition(point);
		SET_DIRTY_RECURSIVELY();		
	}
	private void SET_DIRTY_RECURSIVELY()
	{
		if (!m_bTransformDirty)
		{
			m_bDirty = m_bRecursiveDirty = true;
			if (m_bHasChildren)
            {
                setDirtyRecursively(true);
            }
		}
		
	}

	public CCTexture2D getTexture()
	{
		return m_pobTexture;
	}
	
	public void setTexture(CCTexture2D texture)
	{
		if (m_pobTexture != texture)
		{
			m_pobTexture = texture;
		}
	}
	
	private void setDirtyRecursively(boolean bValue)
	{
		m_bDirty = m_bRecursiveDirty = bValue;
		
	}

//	public CCSprite(Texture texture){
//		//super(texture);
//	}
	
	
	@Override
	public CCPoint getAnchorPoint()
	{	
		return super.getAnchorPoint();
	}

	@Override
	public void setAnchorPoint(CCPoint point)
	{
		super.setAnchorPoint(point);
		SET_DIRTY_RECURSIVELY();
	}

	public Boolean getFlipX()
    {
        return m_bFlipX;
        
    }
	
	public void setFlipX(boolean value)
    {
        
        if (m_bFlipX != value)
        {
            m_bFlipX = value;
            //SetTextureRect(m_obRect, m_bRectRotated, m_obContentSize);
        }
        
    }
	
	public Boolean getFlipY()
    {
        return m_bFlipY;
        
    }
	
	public void setFlipY(boolean value)
    {
        
        if (m_bFlipY != value)
        {
            m_bFlipY = value;
            //SetTextureRect(m_obRect, m_bRectRotated, m_obContentSize);
        }
        
    }
	
	public CCSpriteFrame getDisplayFrame()
    {
        return new CCSpriteFrame(
            m_pobTexture,
            m_obRect.pointsToPixels(),
            m_bRectRotated,
            m_obUnflippedOffsetPositionFromCenter.pointsToPixels(),
            m_obContentSize.pointsToPixels()
            );
    }
	
	public void setDisplayFrame(CCSpriteFrame value)
    {
        m_obUnflippedOffsetPositionFromCenter = new CCPoint(value.getOffset());

        CCTexture2D pNewTexture = value.getTexture();
        // update texture before updating texture rect
        if (pNewTexture != m_pobTexture)
        {
            setTexture(pNewTexture);
        }

        // update rect
        m_bRectRotated = value.getIsRotated();
        setTextureRect(value.getRect(), m_bRectRotated, value.getOriginalSize());   
        
        setAnchorPoint(new CCPoint(0.5f, 0.5f));
    }
	
	
	
	private void updateColor()
	{
		
	}
	
	@Override
	public int getOpacity()
    {
        return super.getOpacity();    
    }
	
	@Override
	public void setOpacity(int value)
    {  
        super.setOpacity(value);
        updateColor();
    }
	
	@Override
	public boolean getIsOpacityModifyRGB()
    {
       return m_bOpacityModifyRGB;
    }
	
	@Override
	public void setIsOpacityModifyRGB(boolean value)
    {
        if (m_bOpacityModifyRGB != value)
        {
            m_bOpacityModifyRGB = value;
            updateColor();
        }   
    }
	
	
	public Boolean initWithFile(String fileName)
	{
		m_bOpacityModifyRGB = true;
		CCTexture2D texture = new CCTexture2D();
		//texture.initWithFile(fileName);
		CCTexture2D pTexture = CCTextureCache.sharedTextureCache().addImage(fileName);
		m_pobTexture = pTexture;
		
		setContentSize(new CCSize(m_pobTexture.getTexture().getTexture().getWidth(),m_pobTexture.getTexture().getTexture().getHeight()));
		// default transform anchor: center
        setAnchorPoint(new CCPoint(0.5f, 0.5f));
     // zwoptex default values
        //m_obOffsetPosition = CCPoint.zero();
        
        
        m_bFlipX = m_bFlipY = false;
        m_bHasChildren = false;
		
		return true;
	}
	
	public void setTextureRect(CCRect rect)
	{
//		TextureRegion region = new TextureRegion(m_pobTexture.getTexture().getTexture(), (int)rect.Origin.X, (int)rect.Origin.Y, (int)rect.Size.Width, (int)rect.Size.Height);
//		m_pobTexture.getTexture().setTextureRegion(region);
		m_obRect = new CCRect(rect);
		setContentSize(new CCSize(rect.Size));
		
	}
	
	public void setTextureRect(CCRect value, boolean rotated, CCSize untrimmedSize)
    {
        m_bRectRotated = rotated;

        setContentSize(untrimmedSize);
        m_obRect = new CCRect(value);

        CCPoint relativeOffset = new CCPoint(m_obUnflippedOffsetPositionFromCenter);

        // issue #732
        if (m_bFlipX)
        {
            relativeOffset.x = -relativeOffset.x;
        }
        if (m_bFlipY)
        {
            relativeOffset.y = -relativeOffset.y;
        }

        m_obOffsetPosition.x = relativeOffset.x + (m_obContentSize.width - m_obRect.Size.width) / 2;
        m_obOffsetPosition.y = relativeOffset.y + (m_obContentSize.height - m_obRect.Size.height) / 2;
        
        setContentSize(new CCSize(value.Size));
    }
	
	@Override
	public void draw() 
	{
		if (isOpacityChanged)
		{
			CCDrawManager.setColor(_realOpacity);
		}
		
		// draw something
		
		if (m_obRect != null)
		{
			CCDrawManager.SPRITE_BATCH.draw(m_pobTexture.getTexture().getTexture(), 0, 0, m_obRect.Size.width,m_obRect.Size.height,(int)m_obRect.Origin.x,(int)m_obRect.Origin.y,(int)m_obRect.Size.width, (int)m_obRect.Size.height,m_bFlipX,m_bFlipY);
		}
		else
		{
			CCDrawManager.SPRITE_BATCH.draw(m_pobTexture.getTexture().getTexture(), 0, 0,m_pobTexture.getTexture().getTexture().getWidth(),m_pobTexture.getTexture().getTexture().getHeight(),0,0,m_pobTexture.getTexture().getTexture().getWidth(),m_pobTexture.getTexture().getTexture().getHeight(),m_bFlipX,m_bFlipY);
		}
		
		
		
		
		if (isOpacityChanged)
		{
			CCDrawManager.setColor(255);
		}
	}
	
	
	
}
