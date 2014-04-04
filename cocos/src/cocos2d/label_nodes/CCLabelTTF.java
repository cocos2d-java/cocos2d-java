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

package cocos2d.label_nodes;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.platform.CCDrawManager;
import cocos2d.predefine.CCTypes.CCTextAlignment;
import cocos2d.predefine.CCTypes.CCVerticalTextAlignment;
import cocos2d.predefine.ICCLabelProtocol;
import cocos2d.sprite_nodes.CCSprite;
import cocos2d.textures.CCTexture2D;

public class CCLabelTTF extends CCSprite implements ICCLabelProtocol
{
    private int m_fFontSize;
    private CCTextAlignment m_hAlignment;
    private String m_pFontName;
    protected String m_pString = "";
    private CCSize m_tDimensions;
    private CCVerticalTextAlignment m_vAlignment;
    
    private FreeTypeFontGenerator m_fontGenerator;
    private BitmapFont m_bitmapFont;

    public CCLabelTTF ()
    {
        m_hAlignment = CCTextAlignment.Center;
        m_vAlignment = CCVerticalTextAlignment.Top;
        m_pFontName = "";
        m_fFontSize = 0;

        init();
    }
    
    public CCLabelTTF (String text, String fontName, int fontSize)      
    { 
    	m_fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
    	m_pString = removeRepeatChar(text);
    	m_bitmapFont = m_fontGenerator.generateFont(fontSize, m_pString, false);
    	m_pFontName = fontName;
        m_fFontSize = fontSize;
        
        setContentSize(new CCSize(m_bitmapFont.getBounds(m_pString).width,m_bitmapFont.getCapHeight() -1/2f* m_bitmapFont.getDescent()));
        setAnchorPoint(new CCPoint(0.5f, 0.5f));
    }
    
    
    public static String removeRepeatChar(String str){
		ArrayList<Character> list = new ArrayList<Character>();
		for (int i=0, n = str.length(); i< n; i++){
			if (!list.contains(str.charAt(i)))
			{
				list.add(str.charAt(i));
			}
		}
			
		Iterator it = list.iterator();
        if (! it.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        
        for (;;) {
            Character e = (Character)it.next();
            sb.append(e);
            if (! it.hasNext())
                return sb.toString();
            
        }
		
	}
    
    
    @Override
    public void draw()
    {
    	if (isOpacityChanged)
		{
			CCDrawManager.setColor(_realOpacity);
		}
		
		// draw something
		
		
		
		//CCDrawManager.SPRITE_BATCH.draw(m_pobTexture.getTexture().getTexture(), 0, 0, m_obRect.Size.Width,m_obRect.Size.Height,(int)m_obRect.Origin.X,(int)m_obRect.Origin.Y,(int)m_obRect.Size.Width, (int)m_obRect.Size.Height,m_bFlipX,m_bFlipY);
		m_bitmapFont.draw(CCDrawManager.SPRITE_BATCH, m_pString, 0, (int)(m_bitmapFont.getCapHeight() -1/2f* m_bitmapFont.getDescent()));
		
		if (isOpacityChanged)
		{
			CCDrawManager.setColor(255);
		}
    }

    public String getFontName()
    {
        return m_pFontName;
        
    }

    public void setFontName(String value)
    {
        if (!m_pFontName.equals(value))
        {
            m_pFontName = value;
            if (m_pString.length() > 0)
            {
                refresh();
            }
        }  
    }
    
    public float getFontSize()
    {
        return m_fFontSize;
    }

    public void setFontSize(int value)
    {
        if (m_fFontSize != value)
        {
            m_fFontSize = value;
            if (m_pString.length() > 0)
            {
                refresh();
            }
        }
    }
    
    public CCSize getDimensions()
    {
        return m_tDimensions;    
    }

    public void setDimensions(CCSize value)
    {
        if (!m_tDimensions.Equals(value))
        {
            m_tDimensions =  new CCSize(value);
            if (m_pString.length() > 0)
            {
                refresh();
            }
        }
    }
    
    public CCVerticalTextAlignment getVerticalAlignment()
    {
        return m_vAlignment;
    }

    public void setVerticalAlignment(CCVerticalTextAlignment value)
    {
        if (m_vAlignment != value)
        {
            m_vAlignment = value;
            if (m_pString.length() > 0)
            {
                refresh();
            }
        }
    }
    
    public CCTextAlignment getHorizontalAlignment()
    {
        return m_hAlignment; 
    }

    public void setHorizontalAlignment(CCTextAlignment value)
    {
        if (m_hAlignment != value)
        {
            m_hAlignment = value;
            if (m_pString.length() > 0)
            {
                refresh();
            }
        }          
     }
    
    private void refresh()
    {
        //
        // This can only happen when the frame buffer is ready...
        //
        try
        {
            updateTexture();
            setDirty(false);
        }
        catch (Exception e)
        {
        }
    }


/*
* This is where the texture should be created, but it messes with the drawing 
* of the object tree
* 
    public override void Draw()
    {
        if (Dirty)
        {
            updateTexture();
            Dirty = false;
        }
        base.Draw();
    }
*/
    public String getText()
    {
        return m_pString;
    }

    public void setText(String value)
    { 
        // This is called in the update() call, so it should not do any drawing ...
        if (!m_pString.equals(value))
        {
            m_pString = value;
            updateTexture();
            setDirty(false);
        }
        //            Dirty = true;
    }
    
    public void setString(String label)
    {
        setText(label);
    }
    
    public String getString() 
    {
        return getText();
    }

    @Override
    public  String toString()
    {
        return String.format("FontName:{0}, FontSize:{1}", m_pFontName, m_fFontSize);
    }

    @Override
    public boolean init()
    {
        return initWithString("", "Helvetica", 12);
    }

    public boolean initWithString(String label, String fontName, int fontSize, CCSize dimensions, CCTextAlignment alignment)
    {
        return initWithString(label, fontName, fontSize, dimensions, alignment, CCVerticalTextAlignment.Top);
    }

    public boolean initWithString(String label, String fontName, int fontSize)
    {
        return initWithString(label, fontName, fontSize, CCSize.Zero, CCTextAlignment.Left,
                              CCVerticalTextAlignment.Top);
    }


    public boolean initWithString(String text, String fontName, int fontSize,
                               CCSize dimensions, CCTextAlignment hAlignment,
                               CCVerticalTextAlignment vAlignment)
    {
        if (super.init())
        {
            // shader program
            //this->setShaderProgram(CCShaderCache::sharedShaderCache()->programForKey(SHADER_PROGRAM));

            m_tDimensions = new CCSize(dimensions.width, dimensions.height);
            m_hAlignment = hAlignment;
            m_vAlignment = vAlignment;
            m_pFontName = fontName;
            m_fFontSize = fontSize;

            setText(text);

            return true;
        }

        return false;
    }

    private void updateTexture()
    {
        CCTexture2D tex;

        // Dump the old one
        if (getTexture() != null)
        {
            getTexture().dispose();
        }

        // let system compute label's width or height when its value is 0
        // refer to cocos2d-x issue #1430
        tex = new CCTexture2D();

//        var result = tex.InitWithString(m_pString,
//                           m_tDimensions.PointsToPixels(),
//                           m_hAlignment,
//                           m_vAlignment,
//                           m_pFontName,
//                           m_fFontSize * CCMacros.CCContentScaleFactor());

//#if MACOS || IPHONE || IOS
//		// There was a problem loading the text for some reason or another if result is not true
//		// For MonoMac and IOS Applications we will try to create a Native Label automatically
//		// If the font is not found then a default font will be selected by the device and used.
//		if (!result && !string.IsNullOrEmpty(m_pString)) 
//		{
//			tex = CCLabelUtilities.CreateLabelTexture (m_pString,
//			                                           CCMacros.CCSizePointsToPixels (m_tDimensions),
//			                                           m_hAlignment,
//			                                           m_vAlignment,
//			                                           m_pFontName,
//			                                           m_fFontSize * CCMacros.CCContentScaleFactor (),
//			                                           new CCColor4B(Microsoft.Xna.Framework.Color.White) );
//		}
//#endif
        setTexture(tex);

        CCRect rect = new CCRect(CCRect.Zero);
        rect.Size = m_pobTexture.getContentSize();
        //SetTextureRect(rect);
    }
}