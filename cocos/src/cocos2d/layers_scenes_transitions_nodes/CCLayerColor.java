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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import cocos2d.CCDirector;
import cocos2d.cocoa.CCSize;
import cocos2d.platform.CCDrawManager;
import cocos2d.predefine.CCColor3B;
import cocos2d.predefine.CCColor4B;
import cocos2d.predefine.CCBlendFunc;
import cocos2d.predefine.ICCBlendProtocol;
/// <summary>
/// CCLayerColor is a subclass of CCLayer that implements the CCRGBAProtocol protocol
/// All features from CCLayer are valid, plus the following new features:
/// - opacity
/// - RGB colors
/// </summary>
public class CCLayerColor extends CCLayerRGBA  implements ICCBlendProtocol
{
    //protected VertexPositionColor[] m_pSquareVertices = new VertexPositionColor[4];
    protected CCBlendFunc m_tBlendFunc;

    public CCLayerColor()
    {
        init();
    }

    /// <summary>
    /// creates a CCLayer with color, width and height in Points
    /// </summary>
    public CCLayerColor (CCColor4B color, float width, float height)
    {
    	this();
        initWithColor(color, width, height);
    }
    
    /// <summary>
    /// creates a CCLayer with color. Width and height are the window size. 
    /// </summary>
    public CCLayerColor (CCColor4B color)
    {
    	this();
        initWithColor(color);
    }

    /// <summary>
    /// override contentSize
    /// </summary>
    
    @Override
    public CCSize getContentSize()
    {
        return super.getContentSize();
    }

    @Override
    public void setContentSize(CCSize value)
    {  
            //1, 2, 3, 3
//            m_pSquareVertices[1].Position.X = value.Width;
//            m_pSquareVertices[2].Position.Y = value.Height;
//            m_pSquareVertices[3].Position.X = value.Width;
//            m_pSquareVertices[3].Position.Y = value.Height;

            //m_pSquareVertices[1].Position.X = value.Height;
            //m_pSquareVertices[2].Position.Y = value.Width;
            //m_pSquareVertices[3].Position.X = value.Width;
            //m_pSquareVertices[3].Position.Y = value.Height;

            super.setContentSize(value);
        
    }

    @Override
    public boolean init()
    {
        CCSize s = CCDirector.sharedDirector().getWinSize();
        return initWithColor(new CCColor4B(0, 0, 0, 0), s.width, s.height);
    }

    /// <summary>
    /// initializes a CCLayer with color
    /// </summary>
    public boolean initWithColor(CCColor4B color)
    {
        CCSize s = CCDirector.sharedDirector().getWinSize();
        return initWithColor(color, s.width, s.height);
    }

    /// <summary>
    /// initializes a CCLayer with color, width and height in Points
    /// </summary>
    public boolean initWithColor(CCColor4B color, float width, float height)
    {
        super.init();

        // default blend function
        m_tBlendFunc = CCBlendFunc.NonPremultiplied;

        _displayedColor.R = _realColor.R = color.R;
        _displayedColor.G = _realColor.G = color.G;
        _displayedColor.B = _realColor.B = color.B;
        _displayedOpacity = _realOpacity = color.A;

//        for (int i = 0; i < m_pSquareVertices.length; i++)
//        {
//            m_pSquareVertices[i].Position.X = 0.0f;
//            m_pSquareVertices[i].Position.Y = 0.0f;
//        }

        updateColor();
        setContentSize(new CCSize(width, height));
        
        return true;
    }

    /// <summary>
    /// change width in Points
    /// </summary>
    /// <param name="w"></param>
    public void changeWidth(float w)
    {
        setContentSize(new CCSize(w, m_obContentSize.height));
    }

    /// <summary>
    /// change height in Points
    /// </summary>
    /// <param name="h"></param>
    public void changeHeight(float h)
    {
        setContentSize(new CCSize(m_obContentSize.width, h));
    }

    /// <summary>
    ///  change width and height in Points
    ///  @since v0.8
    /// </summary>
    /// <param name="w"></param>
    /// <param name="h"></param>
    public void changeWidthAndHeight(float w, float h)
    {
        setContentSize(new CCSize(w, h));
    }

    /// <summary>
    /// BlendFunction. Conforms to CCBlendProtocol protocol 
    /// </summary>
    public CCBlendFunc getBlendFunc()
    {
        return m_tBlendFunc;
    }
    
    public void setBlendFunc(CCBlendFunc value)
    {
        m_tBlendFunc = new CCBlendFunc(value);
    }

    @Override
    public CCColor3B getColor()
    {
        return super.getColor();
    }

    @Override
    public void setColor(CCColor3B value)
    {
        super.setColor(value);
        updateColor();    
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
    public void draw()
    {
    	CCDrawManager.SPRITE_BATCH.end();
    	
    	
    	Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    
    	CCDrawManager.SHAPE_RENDERER.setProjectionMatrix(CCDrawManager.SPRITE_BATCH.getProjectionMatrix());
    	CCDrawManager.SHAPE_RENDERER.setTransformMatrix(CCDrawManager.SPRITE_BATCH.getTransformMatrix());
    	CCDrawManager.SHAPE_RENDERER.setColor(_displayedColor.R / 255.0f, _displayedColor.G / 255.0f, _displayedColor.B / 255.0f, _displayedOpacity / 255.0f);
    	CCDrawManager.SHAPE_RENDERER.begin(ShapeType.Filled);	
        CCDrawManager.SHAPE_RENDERER.rect(0, 0, m_obContentSize.width, m_obContentSize.height);
        CCDrawManager.SHAPE_RENDERER.end();
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        CCDrawManager.SPRITE_BATCH.begin();
    }

    protected void updateColor()
    {
//        var color = new Color(_displayedColor.R / 255.0f, _displayedColor.G / 255.0f, _displayedColor.B / 255.0f, _displayedOpacity / 255.0f);
//
//        m_pSquareVertices[0].Color = color;
//        m_pSquareVertices[1].Color = color;
//        m_pSquareVertices[2].Color = color;
//        m_pSquareVertices[3].Color = color;
    }
}