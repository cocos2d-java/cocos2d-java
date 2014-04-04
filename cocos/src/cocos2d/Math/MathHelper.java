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

package cocos2d.Math;

public class MathHelper 
{
	/// <summary>
    /// Represents the mathematical constant e(2.71828175).
    /// </summary>
    public static final float E = (float)Math.E;
    
    /// <summary>
    /// Represents the log base ten of e(0.4342945).
    /// </summary>
    public static final float Log10E = 0.4342945f;
    
    /// <summary>
    /// Represents the log base two of e(1.442695).
    /// </summary>
    public static final float Log2E = 1.442695f;
    
    /// <summary>
    /// Represents the value of pi(3.14159274).
    /// </summary>
    public static final float Pi = (float)Math.PI;
    
    /// <summary>
    /// Represents the value of pi divided by two(1.57079637).
    /// </summary>
    public static final float PiOver2 = (float)(Math.PI / 2.0);
    
    /// <summary>
    /// Represents the value of pi divided by four(0.7853982).
    /// </summary>
    public static final float PiOver4 = (float)(Math.PI / 4.0);
    
    /// <summary>
    /// Represents the value of pi times two(6.28318548).
    /// </summary>
	/// </summary>
    public static final float TwoPi = (float)(Math.PI * 2.0);
    
    
  /// <summary>
    /// Restricts a value to be within a specified range.
    /// </summary>
    /// <param name="value">The value to clamp.</param>
    /// <param name="min">The minimum value. If <c>value</c> is less than <c>min</c>, <c>min</c> will be returned.</param>
    /// <param name="max">The maximum value. If <c>value</c> is greater than <c>max</c>, <c>max</c> will be returned.</param>
    /// <returns>The clamped value.</returns>
    public static float Clamp(float value, float min, float max)
    {
        // First we check to see if we're greater than the max
        value = (value > max) ? max : value;

        // Then we check to see if we're less than the min.
        value = (value < min) ? min : value;

        // There's no check to see if min > max.
        return value;
    }
}
