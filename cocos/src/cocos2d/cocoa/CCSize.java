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

package cocos2d.cocoa;

import cocos2d.CCDirector;

public class CCSize {
	public static final CCSize Zero = new CCSize(0, 0);

    public float width;
    public float height;

    public CCSize()
    {
    	this.width = 0;
    	this.height = 0;
    }
    
    public CCSize(float width, float height)
    {
    	this.width = width;
    	this.height = height;
    }
    
    public CCSize(CCSize size)
    {
    	width = size.width;
    	height = size.height;
    }
    
    //Returns the inversion of this size, which is the height and width swapped.

    public static CCSize zero() {
        return new CCSize(0, 0);
    }
    
    public CCSize Inverted()
    {
        return new CCSize(height, width);
    }
    
    public static boolean equalToSize(CCSize size1, CCSize size2)
    {
        return ((size1.width == size2.width) && (size1.height == size2.height));
    }
    
    public  int GetHashCode()
    {
        return (Float.valueOf(width).hashCode() + Float.valueOf(height).hashCode());
    }
    
    public boolean Equals(CCSize s)
    {
        return width == s.width && height == s.height;
    }

    public boolean Equals(Object obj)
    {
        return (Equals((CCSize) obj));
    }
    
    public CCPoint getCenter()
    {
        return new CCPoint(width / 2f, height / 2f);
    }
    

    public String ToString()
    {
    	//ÐèÒªÐÞ¸Ä
        return String.format("{0} x {1}", width, height);
    }
    
    public CCSize pointsToPixels()
    {
    	float cs = CCDirector.sharedDirector().getContentScaleFactor();
        return new CCSize(this.width * cs, this.height * cs);
    }
    
    public CCSize pixelsToPoints()
    {
        float cs = CCDirector.sharedDirector().getContentScaleFactor();
        return new CCSize(this.width / cs, this.height / cs);
    }
    
    
    
    
    
    
//    private CCSize() {
//        this(0, 0);
//    }
//
//    private CCSize(float w, float h) {
//        width = w;
//        height = h;
//    }
//
//    public static CCSize make(float w, float h) {
//        return new CCSize(w, h);
//    }
//
//    public static CCSize zero() {
//        return new CCSize(0, 0);
//    }
    
//	public void set(CCSize s) {  	
//		width = s.width; 
//		height = s.height;
//	}
	
//	public void set(float w, float h) {  	
//		width = w; 
//		height = h;
//	}
	
	

//    public String toString() {
//        return "<" + width + ", " + height + ">";
//    }
}
