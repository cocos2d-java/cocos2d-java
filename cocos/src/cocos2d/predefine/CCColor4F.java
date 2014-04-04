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
/// RGBA color composed of 4 floats
/// @since v0.8
/// </summary>
public class CCColor4F
{
    public CCColor4F(float inr, float ing, float inb, float ina)
    {
        R = inr;
        G = ing;
        B = inb;
        A = ina;
    }

    public float R;
    public float G;
    public float B;
    public float A;

    public String ToString()
    {
    	//需要修改的
        return (String.format("{0},{1},{2},{3}", R, G, B, A));
    }

    public static CCColor4F Parse(String s)
    {
        String[] f = s.split(",");
        return (new CCColor4F(Float.valueOf(f[0]), Float.valueOf(f[1]), Float.valueOf(f[2]), Float.valueOf(f[3])));
    }

//    public static implicit operator Color(CCColor4F point)
//    {
//        return new Color(point.R, point.G, point.B, point.A);
//    }
}