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

public class CCTypes
{
    //ccColor3B predefined colors
    //! White color (255,255,255)
    public static final CCColor3B CCWhite = new CCColor3B(255, 255, 255);
    //! Yellow color (255,255,0)
    public static final CCColor3B CCYellow = new CCColor3B(255, 255, 0);
    //! Blue color (0,0,255)
    public static final CCColor3B CCBlue = new CCColor3B(0, 0, 255);
    //! Green Color (0,255,0)
    public static final CCColor3B CCGreen = new CCColor3B(0, 255, 0);
    //! Red Color (255,0,0,)
    public static final CCColor3B CCRed = new CCColor3B(255, 0, 0);
    //! Magenta Color (255,0,255)
    public static final CCColor3B CCMagenta = new CCColor3B(255, 0, 255);
    //! Black Color (0,0,0)
    public static final CCColor3B CCBlack = new CCColor3B(0, 0, 0);
    //! Orange Color (255,127,0)
    public static final CCColor3B CCOrange = new CCColor3B(255, 127, 0);
    //! Gray Color (166,166,166)
    public static final CCColor3B CCGray = new CCColor3B(166, 166, 166);

    //! helper macro that creates an ccColor3B type
    static public CCColor3B CreateColor(byte r, byte g, byte b)
    {
        CCColor3B c = new CCColor3B(r, g, b);
        return c;
    }

    //! helper macro that creates an ccColor4B type
    public static CCColor4B CreateColor(byte r, byte g, byte b, byte o)
    {
        CCColor4B c = new CCColor4B(r, g, b, o);
        return c;
    }

    /** Returns a ccColor4F from a ccColor3B. Alpha will be 1.
     @since v0.99.1
     */
    public static CCColor4F CreateColor(CCColor3B c)
    {
        CCColor4F c4 = new CCColor4F(c.R / 255.0f, c.G / 255.0f, c.B / 255.0f, 1.0f);
        return c4;
    }

    /** Returns a ccColor4F from a ccColor4B.
     @since v0.99.1
     */
    public static CCColor4F CreateColor(CCColor4B c)
    {
        CCColor4F c4 = new CCColor4F(c.R / 255.0f, c.G / 255.0f, c.B / 255.0f, c.A / 255.0f);
        return c4;
    }

    /** returns YES if both ccColor4F are equal. Otherwise it returns NO.
     @since v0.99.1
     */
    public static boolean ColorsAreEqual(CCColor4F a, CCColor4F b)
    {
        return a.R == b.R && a.G == b.G && a.B == b.B && a.A == b.A;
    }

    public static CCVertex2F Vertex2(float x, float y)
    {
        CCVertex2F c = new CCVertex2F(x, y);
        return c;
    }

    public static CCVertex3F Vertex3(float x, float y, float z)
    {
        CCVertex3F c = new CCVertex3F(x, y, z);
        return c;
    }

    public static CCTex2F Tex2(float u, float v)
    {
        CCTex2F t = new CCTex2F(u, v);
        return t;
    }

    //! helper function to create a ccGridSize
    public static CCGridSize GridSize(int x, int y)
    {
        CCGridSize v = new CCGridSize(x, y);
        return v;
    }
    
    
    
    public enum CCTextAlignment
    {
        Left,
        Center,
        Right,
    }

    public enum CCVerticalTextAlignment
    {
        Top,
        Center,
        Bottom
    }
}