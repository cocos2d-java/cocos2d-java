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

public class CCRect
{
    public static final CCRect Zero = new CCRect(0, 0, 0, 0);

    public CCPoint Origin = new CCPoint();
    public CCSize Size = new CCSize();

    /// <summary>
    ///     Creates the rectangle at (x,y) -> (width,height)
    /// </summary>
    /// <param name="x">Lower Left corner X</param>
    /// <param name="y">Lower left corner Y</param>
    /// <param name="width">width of the rectangle</param>
    /// <param name="height">height of the rectangle</param>
    public CCRect(float x, float y, float width, float height)
    {
        // Only support that, the width and height > 0
        //Debug.Assert(width >= 0 && height >= 0);

        Origin.x = x;
        Origin.y = y;

        Size.width = width;
        Size.height = height;
    }

    public CCRect(CCRect rect)
    {
    	Origin.x = rect.Origin.x;
    	Origin.y = rect.Origin.y;
    	
    	Size.height = rect.Size.height;
    	Size.width = rect.Size.width;
    }
    
    /// <summary>
    ///     Returns the inversion of this rect's size, which is the height and width swapped, while the origin stays unchanged.
    /// </summary>
    public CCRect getInvertedSize()
    {
        return new CCRect(Origin.x, Origin.y, Size.height, Size.width);
    }

    // return the rightmost x-value of 'rect'
    public float getMaxX()
    {
        return Origin.x + Size.width;
    }

    // return the midpoint x-value of 'rect'
    public float getMidX()
    {
        return Origin.x + Size.width / 2.0f;
    }

    // return the leftmost x-value of 'rect'
    public float getMinX()
    {
        return Origin.x;
    }

    // Return the topmost y-value of 'rect'
    public float getMaxY()
    {
        return Origin.y + Size.height;
    }

    // Return the midpoint y-value of 'rect'
    public float getMidY()
    {
        return Origin.y + Size.height / 2.0f;
    }

    // Return the bottommost y-value of 'rect'
    public float getMinY()
    {
        return Origin.y;
    }

    public CCPoint getCenter()
    {  
        CCPoint pt = new CCPoint();
        pt.x = getMaxX();
        pt.y = getMidY();
        return pt;
    }

    public CCPoint getUpperRight()
    {
        CCPoint pt = new CCPoint();
        pt.x = getMaxX();
        pt.y = getMaxY();
        return (pt);
    }

    public CCPoint getLowerLeft()
    {   
        CCPoint pt = new CCPoint();
        pt.x = getMinX();
        pt.y = getMinY();
        return (pt);
    }

    public CCRect Intersection(CCRect rect)
    {
        if (!IntersectsRect(rect))
        {
            return (Zero);
        }

        /*       +-------------+
         *       |             |
         *       |         +---+-----+
         * +-----+---+     |   |     |
         * |     |   |     |   |     |
         * |     |   |     +---+-----+
         * |     |   |         |
         * |     |   |         |
         * +-----+---+         |
         *       |             |
         *       +-------------+
         */
        float minx = 0, miny = 0, maxx = 0, maxy = 0;
        // X
        if (rect.getMinX() < getMinX())
        {
            minx = getMinX();
        }
        else if (rect.getMinX() < getMaxX())
        {
            minx = rect.getMinX();
        }
        if (rect.getMaxX() < getMaxX())
        {
            maxx = rect.getMaxX();
        }
        else if (rect.getMaxX() > getMaxX())
        {
            maxx = getMaxX();
        }
        //  Y
        if (rect.getMinY() < getMinY())
        {
            miny = getMinY();
        }
        else if (rect.getMinY() < getMaxY())
        {
            miny = rect.getMinY();
        }
        if (rect.getMaxY() < getMaxY())
        {
            maxy = rect.getMaxY();
        }
        else if (rect.getMaxY() > getMaxY())
        {
            maxy = getMaxY();
        }
        return new CCRect(minx, miny, maxx - minx, maxy - miny);
    }

    public boolean IntersectsRect(CCRect rect)
    {
        return !(getMaxX() < rect.getMinX() || rect.getMaxX() < getMinX() || getMaxY() < rect.getMinY() || rect.getMaxY() < getMinY());
    }

    public boolean ContainsPoint(CCPoint point)
    {
        return point.x >= getMinX() && point.x <= getMaxX() && point.y >= getMinY() && point.y <= getMaxY();
    }

    public boolean ContainsPoint(float x, float y)
    {
        return x >= getMinX() && x <= getMaxX() && y >= getMinY() && y <= getMaxY();
    }

    public static boolean Equal(CCRect rect1, CCRect rect2)
    {
        return rect1.Origin.Equals(rect2.Origin) && rect1.Size.Equals(rect2.Size);
    }

    public static boolean ContainsPoint(CCRect rect, CCPoint point)
    {
        boolean bRet = false;

        if (Float.isNaN(point.x))
        {
            point.x = 0;
        }

        if (Float.isNaN(point.y))
        {
            point.y = 0;
        }

        if (point.x >= rect.getMinX() && point.x <= rect.getMaxX() && point.y >= rect.getMinY() &&
            point.y <= rect.getMaxY())
        {
            bRet = true;
        }

        return bRet;
    }

    public static boolean IntersetsRect(CCRect rectA, CCRect rectB)
    {
        return
            !(rectA.getMaxX() < rectB.getMinX() || rectB.getMaxX() < rectA.getMinX() || rectA.getMaxY() < rectB.getMinY() ||
              rectB.getMaxY() < rectA.getMinY());
    }

    //operator ==
    public static boolean ccpIsEqual(CCRect p1, CCRect p2)
    {
        return (p1.Equals(p2));
    }

    //operator !=
    public static boolean ccpIsNotEqual(CCRect p1, CCRect p2)
    {
        return (!p1.Equals(p2));
    }

    @Override
    public int hashCode()
    {
        return Origin.GetHashCode() + Size.GetHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (Equals((CCRect) obj));
    }

    public boolean Equals(CCRect rect)
    {
        return Origin.Equals(rect.Origin) && Size.Equals(rect.Size);
    }

    @Override
    public String toString()
    {
        return String.format("CCRect : (x={0}, y={1}, width={2}, height={3})", Origin.x, Origin.y, Size.width,
                             Size.height);
    }

//    public static CCRect Parse(String s)
//    {
//#if !WINDOWS_PHONE && !XBOX && !NETFX_CORE
//        return (CCRect) TypeDescriptor.GetConverter(typeof (CCRect)).ConvertFromString(s);
//#else
//        return (CCRectConverter.CCRectFromString(s));
//#endif
//    }
    
    public CCRect pixelsToPoints()
    {
        float cs = CCDirector.sharedDirector().getContentScaleFactor();
        return new CCRect(this.Origin.x / cs, this.Origin.y / cs, this.Size.width / cs, this.Size.height / cs);
    }

    public CCRect pointsToPixels()
    {
        float cs = CCDirector.sharedDirector().getContentScaleFactor();
        return new CCRect(this.Origin.x * cs, this.Origin.y * cs, this.Size.width * cs, this.Size.height * cs);
    }
    
    
    
}