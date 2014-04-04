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
/// RGBA color composed of 4 ints
/// @since v0.8
/// </summary>
public class CCColor4B
{
    //ccColor4B predefined colors
    //! White color (255,255,255)
    public static final CCColor4B White = new CCColor4B(255, 255, 255, 255);
    //! Yellow color (255,255,0)
    public static final CCColor4B Yellow = new CCColor4B(255, 255, 0, 255);
    //! Blue color (0,0,255)
    public static final CCColor4B Blue = new CCColor4B(0, 0, 255, 255);
    //! Green Color (0,255,0)
    public static final CCColor4B Green = new CCColor4B(0, 255, 0, 255);
    //! Red Color (255,0,0,)
    public static final CCColor4B Red = new CCColor4B(255, 0, 0, 255);
    //! Magenta Color (255,0,255)
    public static final CCColor4B Magenta = new CCColor4B(255, 0, 255, 255);
    //! Black Color (0,0,0)
    public static final CCColor4B Black = new CCColor4B(0, 0, 0, 255);
    //! Orange Color (255,127,0)
    public static final CCColor4B Orange = new CCColor4B(255, 127, 0, 255);
    //! Gray Color (166,166,166)
    public static final CCColor4B Gray = new CCColor4B(166, 166, 166, 255);

    public CCColor4B()
    {
    	
    }
    public CCColor4B(int inr, int ing, int inb, int ina)
    {
        R = inr;
        G = ing;
        B = inb;
        A = ina;
    }

    public CCColor4B(float inr, float ing, float inb, float ina)
    {
        R = (int)inr;
        G = (int)ing;
        B = (int)inb;
        A = (int)ina;
    }

    /// <summary>
    /// Convert Color value of XNA Framework to CCColor4B type
    /// </summary>
//    public CCColor4B(Microsoft.Xna.Framework.Color color)
//    {
//        R = color.R;
//        G = color.G;
//        B = color.B;
//        A = color.A;
//    }

    public int R = 0;
    public int G = 0;
    public int B = 0;
    public int A = 0;

    public String ToString()
    {
    	//需要修改的
        return (String.format("{0},{1},{2},{3}", R, G, B, A));
    }

    public static CCColor4B Parse(String s)
    {
        String[] f = s.split(",");
        return (new CCColor4B(Integer.valueOf(f[0]), Integer.valueOf(f[1]), Integer.valueOf(f[2]), Integer.valueOf(f[3])));
    }

  //需要修改的
//    public static implicit operator Color(CCColor4B point)
//    {
//        return new Color(point.R, point.G, point.B, point.A);
//    }
//
//    public static implicit operator CCColor3B(CCColor4B point)
//    {
//        return new CCColor3B(point.R, point.G, point.B);
//    }
//
//    public static implicit operator CCColor4B(CCColor3B point)
//    {
//        return new CCColor4B(point.R, point.G, point.B, 255);
//    }
}