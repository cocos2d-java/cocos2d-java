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

/// <summary>
/// RGB color composed of bytes 3 bytes
/// @since v0.8
/// </summary>
public class CCColor3B
{
    //ccColor3B predefined colors
    //! White color (255,255,255)
    public static final CCColor3B White = new CCColor3B(255, 255, 255);
    //! Yellow color (255,255,0)
    public static final CCColor3B Yellow = new CCColor3B(255, 255, 0);
    //! Blue color (0,0,255)
    public static final CCColor3B Blue = new CCColor3B(0, 0, 255);
    //! Green Color (0,255,0)
    public static final CCColor3B Green = new CCColor3B(0, 255, 0);
    //! Red Color (255,0,0,)
    public static final CCColor3B Red = new CCColor3B(255, 0, 0);
    //! Magenta Color (255,0,255)
    public static final CCColor3B Magenta = new CCColor3B(255, 0, 255);
    //! Black Color (0,0,0)
    public static final CCColor3B Black = new CCColor3B(0, 0, 0);
    //! Orange Color (255,127,0)
    public static final CCColor3B Orange = new CCColor3B(255, 127, 0);
    //! Gray Color (166,166,166)
    public static final CCColor3B Gray = new CCColor3B(166, 166, 166);

    
    public CCColor3B()
    {
        R = 0;
        G = 0;
        B = 0;
    }
    
    public CCColor3B(int red, int green, int blue)
    {
        R = red;
        G = green;
        B = blue;
    }

    public CCColor3B(CCColor3B color)
    {
    	R = color.R;
    	G = color.G;
    	B = color.B;
    }
    /// <summary>
    /// Convert Color value of XNA Framework to CCColor3B type
    /// </summary>
//    public CCColor3B(Microsoft.Xna.Framework.Color color)
//    {
//        R = color.R;
//        G = color.G;
//        B = color.B;
//    }

    public int R;
    public int G;
    public int B;

//    public static implicit operator Color(CCColor3B point)
//    {
//        return new Color(point.R, point.G, point.B);
//    }
}