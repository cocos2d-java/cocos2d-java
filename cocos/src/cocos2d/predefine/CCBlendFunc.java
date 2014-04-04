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

package cocos2d.predefine;

import cocos2d.platform.CCOGLES;

/// <summary>
/// Blend Function used for textures
/// </summary>
public class CCBlendFunc
{
    public static final CCBlendFunc AlphaBlend = new CCBlendFunc(CCOGLES.GL_ONE, CCOGLES.GL_ONE_MINUS_SRC_ALPHA);
    public static final CCBlendFunc Additive = new CCBlendFunc(CCOGLES.GL_SRC_ALPHA, CCOGLES.GL_ONE);
    public static final CCBlendFunc NonPremultiplied = new CCBlendFunc(CCOGLES.GL_SRC_ALPHA, CCOGLES.GL_ONE_MINUS_SRC_ALPHA);
    public static final CCBlendFunc Opaque = new CCBlendFunc(CCOGLES.GL_ONE, CCOGLES.GL_ZERO);

    public CCBlendFunc()
    {
    	
    }
    
    public CCBlendFunc(int src, int dst)
    {
        this.Source = src;
        this.Destination = dst;
    }

    public CCBlendFunc(CCBlendFunc value)
    {
    	this.Source = value.Source;
    	this.Destination = value.Destination;
    }
    
    /// <summary>
    /// source blend function
    /// </summary>
    public int Source = 0;

    /// <summary>
    /// destination blend function
    /// </summary>
    public int Destination = 0;

    //operator ==
    public static boolean cppEual(CCBlendFunc b1, CCBlendFunc b2)
    {
        return b1.Source == b2.Source && b1.Destination == b2.Destination;
    }

    //operator !=
    public static boolean cppNotEual(CCBlendFunc b1, CCBlendFunc b2)
    {
        return b1.Source != b2.Source || b1.Destination != b2.Destination;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof CCBlendFunc)
        {
            return this == (CCBlendFunc) obj;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
