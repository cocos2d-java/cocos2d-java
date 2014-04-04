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

public class CCOGLES
{
    /* AlphaFunction */
    public final int GL_NEVER = 0x0200;
    public static final int GL_LESS = 0x0201;
    public static final int GL_EQUAL = 0x0202;
    public static final int GL_LEQUAL = 0x0203;
    public static final int GL_GREATER = 0x0204;
    public static final int GL_NOTEQUAL = 0x0205;
    public static final int GL_GEQUAL = 0x0206;
    public static final int GL_ALWAYS = 0x0207;

    /* BlendingFactorDest */
    public static final int GL_ZERO = 0;
    public static final int GL_ONE = 1;
    public static final int GL_SRC_COLOR = 0x0300;
    public static final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
    public static final int GL_SRC_ALPHA = 0x0302;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
    public static final int GL_DST_ALPHA = 0x0304;
    public static final int GL_ONE_MINUS_DST_ALPHA = 0x0305;

    /* BlendingFactorSrc */
    /*      GL_ZERO */
    /*      GL_ONE */
    public static final int GL_DST_COLOR = 0x0306;
    public static final int GL_ONE_MINUS_DST_COLOR = 0x0307;
    public static final int GL_SRC_ALPHA_SATURATE = 0x0308;
    /*      GL_SRC_ALPHA */
    /*      GL_ONE_MINUS_SRC_ALPHA */
    /*      GL_DST_ALPHA */
    /*      GL_ONE_MINUS_DST_ALPHA */

    public static Blend GetXNABlend(int glBlend)
    {
        switch (glBlend)
        {
            case GL_ZERO:
                return Blend.Zero;
            case GL_ONE:
                return Blend.One;
            case GL_SRC_COLOR:
                return Blend.SourceColor;
            case GL_ONE_MINUS_SRC_COLOR:
                return Blend.InverseSourceColor;
            case GL_SRC_ALPHA:
                return Blend.SourceAlpha;
            case GL_ONE_MINUS_SRC_ALPHA:
                return Blend.InverseSourceAlpha;
            case GL_DST_ALPHA:
                return Blend.DestinationAlpha;
            case GL_ONE_MINUS_DST_ALPHA:
                return Blend.InverseDestinationAlpha;

            /* BlendingFactorSrc */
            /*      GL_ZERO */
            /*      GL_ONE */
            case GL_DST_COLOR:
                return Blend.DestinationColor;
            case GL_ONE_MINUS_DST_COLOR:
                return Blend.InverseDestinationColor;
            case GL_SRC_ALPHA_SATURATE:
                return Blend.SourceAlphaSaturation;
        }

        return Blend.One;
    }
    
    public enum Blend
	{
		One,				// Each component of the color is multiplied by (1, 1, 1, 1).
		Zero,	 			// Each component of the color is multiplied by (0, 0, 0, 0).
		SourceColor,		// Each component of the color is multiplied by the source color. This can be represented as (Rs, Gs, Bs, As), where R, G, B, and A respectively stand for the red, green, blue, and alpha source values.
		InverseSourceColor,	// Each component of the color is multiplied by the inverse of the source color. This can be represented as (1 ? Rs, 1 ? Gs, 1 ? Bs, 1 ? As) where R, G, B, and A respectively stand for the red, green, blue, and alpha destination values.
		SourceAlpha,		// Each component of the color is multiplied by the alpha value of the source. This can be represented as (As, As, As, As), where As is the alpha source value. 
		InverseSourceAlpha,	// Each component of the color is multiplied by the inverse of the alpha value of the source. This can be represented as (1 ? As, 1 ? As, 1 ? As, 1 ? As), where As is the alpha destination value.
		DestinationColor,	//Each component color is multiplied by the destination color. This can be represented as (Rd, Gd, Bd, Ad), where R, G, B, and A respectively stand for red, green, blue, and alpha destination values.
		InverseDestinationColor,	// Each component of the color is multiplied by the inverse of the destination color. This can be represented as (1 ? Rd, 1 ? Gd, 1 ? Bd, 1 ? Ad), where Rd, Gd, Bd, and Ad respectively stand for the red, green, blue, and alpha destination values.
		DestinationAlpha,	// Each component of the color is multiplied by the alpha value of the destination. This can be represented as (Ad, Ad, Ad, Ad), where Ad is the destination alpha value.
		InverseDestinationAlpha,	// Each component of the color is multiplied by the inverse of the alpha value of the source. This can be represented as (1 ? As, 1 ? As, 1 ? As, 1 ? As), where As is the alpha destination value.
		BlendFactor,		// Each component of the color is multiplied by a constant set in BlendFactor.
		InverseBlendFactor,	//Each component of the color is multiplied by the inverse of a constant set in BlendFactor.
		SourceAlphaSaturation,	// Each component of the color is multiplied by either the alpha of the source color, or the inverse of the alpha of the source color, whichever is greater. This can be represented as (f, f, f, 1), where f = min(A, 1 ? Ad).
	}
}
