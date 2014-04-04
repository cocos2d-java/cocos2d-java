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

public interface ICCRGBAProtocol
{
    /// <summary>
    /// Gets or sets the color
    /// </summary>
    CCColor3B getColor();
    void setColor(CCColor3B value);
    
    CCColor3B getDisplayedColor();

    /// <summary>
    /// Gets or sets the Opacity
    /// @warning If the the texture has premultiplied alpha then, the R, G and B channels will be modifed.
    /// Values goes from 0 to 255, where 255 means fully opaque.
    /// </summary>
    int getOpacity();
    void setOpacity(int value);

    int getDisplayedOpacity();

    /** sets the premultipliedAlphaOpacity property.
     If set to NO then opacity will be applied as: glColor(R,G,B,opacity);
     If set to YES then oapcity will be applied as: glColor(opacity, opacity, opacity, opacity );
     Textures with premultiplied alpha will have this property by default on YES. Otherwise the default value is NO
     @since v0.8
     */

    boolean getIsOpacityModifyRGB();
    void setIsOpacityModifyRGB(boolean value);

    /** returns whether or not the opacity will be applied using glColor(R,G,B,opacity) or glColor(opacity, opacity, opacity, opacity);
     @since v0.8
     */

    /**
     *  whether or not color should be propagated to its children.
     */
    boolean getCascadeColorEnabled();
    void setCascadeColorEnabled(boolean value);

    /** 
    *  recursive method that updates display color 
    */
    void updateDisplayedColor(CCColor3B color);

    /** 
     *  whether or not opacity should be propagated to its children.
     */
    boolean getCascadeOpacityEnabled();
    void setCascadeOpacityEnabled(boolean value);

    /**
     *  recursive method that updates the displayed opacity.
     */
    void updateDisplayedOpacity(int opacity);
}