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

import cocos2d.cocoa.CCAffineTransform;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public  class  CCDrawManager 
{
	
	public static  SpriteBatch SPRITE_BATCH;
	private static final Matrix4[] m_matrixStack = new Matrix4[100];
	private static int m_stackIndex;
	public static Color GlClearColor = Color.valueOf("5C96F1");
	public static ShapeRenderer SHAPE_RENDERER;
	
	private static float m_fScaleX;
    private static float m_fScaleY;
    private static CCRect m_obViewPortRect;
    private static CCSize m_obScreenSize;
    private static CCSize m_obDesignResolutionSize;
	
    private static float[] transfrom4x4 = new float[16];
    
	static
	{
		SPRITE_BATCH = new SpriteBatch();
		SHAPE_RENDERER = new ShapeRenderer();
		Matrix4 matrix= new Matrix4();
		matrix.setToOrtho(0,Gdx.graphics.getWidth(),0,Gdx.graphics.getHeight(),-1024.0f, 1024.0f);
		//matrix.setToOrtho(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),-1f, 1f);
		
		SPRITE_BATCH.setProjectionMatrix(matrix);
	}
	//private static Matrix4 m_Matrix = SPRITE_BATCH.getTransformMatrix();
	private static Matrix4 m_Matrix = new Matrix4();
	private static final Matrix4 Identity_Matrix = new Matrix4().idt();
	
	
	//Flags
    private static Boolean m_worldMatrixChanged;
    public static int DrawCount;
	
	public static void PushMatrix()
    {
		//m_matrixStack[m_stackIndex++] = m_Matrix.cpy();
		
		
		if (m_matrixStack[m_stackIndex] != null)
		{
			m_matrixStack[m_stackIndex].set(m_Matrix);
			m_stackIndex++;
		}
		else
		{
			m_matrixStack[m_stackIndex++] = m_Matrix.cpy();
		}
        
    }

    public static void PopMatrix()
    {
        //m_Matrix = m_matrixStack[--m_stackIndex];
    	m_Matrix.set(m_matrixStack[--m_stackIndex]);
        m_worldMatrixChanged = true;
        
    }
	
    
    public static void Clear(float red, float green, float blue, float alpha)
    {
        Gdx.gl.glClearColor(red, green, blue, alpha);
    }
    public static void Clear(Color color)
    {
    	 Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
    	 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
    
    public static void BeginDraw()
    {
        //Clear(Color.BLACK);
    	Clear(GlClearColor);
        DrawCount = 0;
        m_Matrix.set(Identity_Matrix);
        SPRITE_BATCH.begin();
    }

    public static void EndDraw()
    {
    	SPRITE_BATCH.end();	
    }
	
    public static void MultMatrix( CCAffineTransform transform, float z)
    {
    	//float[] transfrom4x4 = new float[16];
    	 // Convert 3x3 into 4x4 matrix
    	CCAffineTransform.CGAffineToGL(transform, transfrom4x4);
        //Matrix.Multiply(ref m_pTransform, ref m_Matrix, out m_Matrix);
    	//Matrix4 tempMatrix4 = new Matrix4(m_Matrix.val);
    	
    	//Matrix4.mul(m_Matrix.val, transfrom4x4);
    	Matrix4.mul(transfrom4x4, m_Matrix.val);
    	m_Matrix.set(transfrom4x4);
    	//Matrix4.mul(transfrom4x4, m_Matrix.val);
    	SPRITE_BATCH.setTransformMatrix(m_Matrix);
    	
        m_worldMatrixChanged = true;
    }
	
    public static void setColor(int opacity)
    {
    	SPRITE_BATCH.setColor(1f*opacity/255f,1f*opacity/255f,1f*opacity/255f,1f*opacity/255f);
    }
	
}
