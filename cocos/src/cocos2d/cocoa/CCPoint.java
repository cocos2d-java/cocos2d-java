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

public class CCPoint
{
    public static final CCPoint Zero = new CCPoint(0, 0);

    public static final CCPoint AnchorMiddle = new CCPoint(0.5f, 0.5f);
    public static final CCPoint AnchorLowerLeft = new CCPoint(0f, 0f);
    public static final CCPoint AnchorUpperLeft = new CCPoint(0f, 1f);
    public static final CCPoint AnchorLowerRight = new CCPoint(1f, 0f);
    public static final CCPoint AnchorUpperRight = new CCPoint(1f, 1f);
    public static final CCPoint AnchorMiddleRight = new CCPoint(1f, .5f);
    public static final CCPoint AnchorMiddleLeft = new CCPoint(0f, .5f);
    public static final CCPoint AnchorMiddleTop = new CCPoint(.5f, 1f);
    public static final CCPoint AnchorMiddleBottom = new CCPoint(.5f, 0f);

    public float x;
    public float y;

    public CCPoint()
    {
    	
    }
    
    public CCPoint(CCSize size)
    {
    	this.x = size.width;
    	this.y = size.height;
    }
    
    public CCPoint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public CCPoint(CCPoint pt)
    {
    	this.x = pt.x;
    	this.y = pt.y;
    }

//    public CCPoint(Point pt)
//    {
//        X = pt.X;
//        Y = pt.Y;
//    }

//    public CCPoint(Vector2 v)
//    {
//        X = v.X;
//        Y = v.Y;
//    }

    public static CCPoint zero() {
        return new CCPoint(0, 0);
    }
    
    /**
     * Set value
     */
    public void set(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
    
    public void set(CCPoint p) {
    	this.x = p.x;
    	this.y = p.y;
    }
    
    
    public static boolean Equal(CCPoint point1, CCPoint point2)
    {
        return ((point1.x == point2.x) && (point1.y == point2.y));
    }

    public CCPoint Offset(float dx, float dy)
    {
        CCPoint pt = new CCPoint();
        pt.x = x + dx;
        pt.y = y + dy;
        return pt;
    }

    public CCPoint Reverse()
    {
        return new CCPoint(-x, -y);
    }

    public int GetHashCode()
    {
        return Float.valueOf(x).hashCode() + Float.valueOf(y).hashCode();
    }

    public boolean Equals(Object obj)
    {
        return (Equals((CCPoint) obj));
    }

    public boolean equalToPoint(CCPoint p)
    {
        return x == p.x && y == p.y;
    }

    public String ToString()
    {
    	//kk: 需要修改的
        return String.format("CCPoint : (x={0}, y={1})", x, y);
    }

    public float DistanceSQ(CCPoint v2)
    {
        return Sub(v2).LengthSQ();
    }

    public CCPoint Sub(CCPoint v2)
    {
        CCPoint pt = new CCPoint();
        pt.x = x - v2.x;
        pt.y = y - v2.y;
        return pt;
    }

    public float LengthSQ()
    {
        return x * x + y * y;
    }

    public float LengthSquare()
    {
        return LengthSQ();
    }


    /** Computes the length of this point as if it were a vector with XY components relative to the
     *origin. This is computed each time this property is accessed, so cache the value that is
     *returned.*/
    public float Length()
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    /** Inverts the direction or location of the Y component.*/
    public CCPoint getInvertY()
    {  
        CCPoint pt = new CCPoint();
        pt.x = x;
        pt.y = -y;
        return pt;  
    }

    /** Normalizes the components of this point (convert to mag 1), and returns the orignial
    *magnitude of the vector defined by the XY components of this point.*/
    public float Normalize()
    {
    	float mag = (float) Math.sqrt(x * x + y * y);
        if (mag < 1.401298E-45f)
        {
            return (0f);
        }
        float l = 1f / mag;
        x *= l;
        y *= l;
        return (mag);
    }

    //region Static Methods

    /** Run a math operation function on each point component
     * absf, fllorf, ceilf, roundf
     * any function that has the signature: float func(float);
     * For example: let's try to take the floor of x,y
     * ccpCompOp(p,floorf);
     @since v0.99.1
     */

    //public delegate float ComputationOperationDelegate(float a);

//    public static CCPoint ComputationOperation(CCPoint p, ComputationOperationDelegate del)
//    {
//        CCPoint pt;
//        pt.X = del(p.X);
//        pt.Y = del(p.Y);
//        return pt;
//    }

    /** Linear Interpolation between two points a and b
        @returns
          alpha == 0 ? a
          alpha == 1 ? b
          otherwise a value between a..b
        @since v0.99.1
   */

    public static CCPoint Lerp(CCPoint a, CCPoint b, float alpha)
    {
        return (ccpAdd(ccpMult(a, (1f - alpha)), ccpMult(b, alpha)));
    }


    /** @returns if points have fuzzy equality which means equal with some degree of variance.
        @since v0.99.1
    */

    public static boolean FuzzyEqual(CCPoint a, CCPoint b, float variance)
    {
        if (a.x - variance <= b.x && b.x <= a.x + variance)
            if (a.y - variance <= b.y && b.y <= a.y + variance)
                return true;

        return false;
    }


    /** Multiplies a nd b components, a.X*b.X, a.y*b.y
        @returns a component-wise multiplication
        @since v0.99.1
    */

    public static CCPoint MultiplyComponents(CCPoint a, CCPoint b)
    {
        CCPoint pt = new CCPoint();
        pt.x = a.x * b.x;
        pt.y = a.y * b.y;
        return pt;
    }

    /** @returns the signed angle in radians between two vector directions
        @since v0.99.1
    */

    public static float AngleSigned(CCPoint a, CCPoint b)
    {
        CCPoint a2 = Normalize(a);
        CCPoint b2 = Normalize(b);
        float angle = (float) Math.atan2(a2.x * b2.y - a2.y * b2.x, DotProduct(a2, b2));

        if (Math.abs(angle) < 1.401298E-45f)
        {
            return 0.0f;
        }

        return angle;
    }

    /** Rotates a point counter clockwise by the angle around a pivot
        @param v is the point to rotate
        @param pivot is the pivot, naturally
        @param angle is the angle of rotation cw in radians
        @returns the rotated point
        @since v0.99.1
    */

    public static CCPoint RotateByAngle(CCPoint v, CCPoint pivot, float angle)
    {
        CCPoint r = ccpSub(v, pivot);
        float cosa = (float) Math.cos(angle), sina = (float) Math.sin(angle);
        float t = r.x;

        r.x = t * cosa - r.y * sina + pivot.x;
        r.y = t * sina + r.y * cosa + pivot.y;

        return r;
    }

    /** A general line-line intersection test
     @param p1 
        is the startpoint for the first line P1 = (p1 - p2)
     @param p2 
        is the endpoint for the first line P1 = (p1 - p2)
     @param p3 
        is the startpoint for the second line P2 = (p3 - p4)
     @param p4 
        is the endpoint for the second line P2 = (p3 - p4)
     @param s 
        is the range for a hitpoint in P1 (pa = p1 + s*(p2 - p1))
     @param t
        is the range for a hitpoint in P3 (pa = p2 + t*(p4 - p3))
     @return bool 
        indicating successful intersection of a line
        note that to truly test intersection for segments we have to make 
        sure that s & t lie within [0..1] and for rays, make sure s & t > 0
        the hit point is		p3 + t * (p4 - p3);
        the hit point also is	p1 + s * (p2 - p1);
     @since v0.99.1
     */

    public static boolean LineIntersect(CCPoint A, CCPoint B, CCPoint C, CCPoint D, float S, float T)
    {
        // FAIL: Line undefined
        if ((A.x == B.x && A.y == B.y) || (C.x == D.x && C.y == D.y))
        {
            return false;
        }

        float BAx = B.x - A.x;
        float BAy = B.y - A.y;
        float DCx = D.x - C.x;
        float DCy = D.y - C.y;
        float ACx = A.x - C.x;
        float ACy = A.y - C.y;

        float denom = DCy * BAx - DCx * BAy;

        S = DCx * ACy - DCy * ACx;
        T = BAx * ACy - BAy * ACx;

        if (denom == 0)
        {
            if (S == 0 || T == 0)
            {
                // Lines incident
                return true;
            }
            // Lines parallel and not incident
            return false;
        }

        S = S / denom;
        T = T / denom;

        // Point of intersection
        // CGPoint P;
        // P.X = A.X + *S * (B.X - A.X);
        // P.y = A.y + *S * (B.y - A.y);

        return true;
    }

    /*
    ccpSegmentIntersect returns YES if Segment A-B intersects with segment C-D
    @since v1.0.0
    */

    public static boolean SegmentIntersect(CCPoint A, CCPoint B, CCPoint C, CCPoint D)
    {
        float S = 0, T = 0;

        if (LineIntersect(A, B, C, D, S, T)
            && (S >= 0.0f && S <= 1.0f && T >= 0.0f && T <= 1.0f))
        {
            return true;
        }

        return false;
    }

    /*
    ccpIntersectPoint returns the intersection point of line A-B, C-D
    @since v1.0.0
    */

    public static CCPoint IntersectPoint(CCPoint A, CCPoint B, CCPoint C, CCPoint D)
    {
        float S = 0, T = 0;

        if (LineIntersect(A, B, C, D, S, T))
        {
            // Point of intersection
            CCPoint P = new CCPoint();
            P.x = A.x + S * (B.x - A.x);
            P.y = A.y + S * (B.y - A.y);
            return P;
        }

        return Zero;
    }

    /** Converts radians to a normalized vector.
        @return CCPoint
        @since v0.7.2
    */

    public static CCPoint ForAngle(float a)
    {
        CCPoint pt = new CCPoint();
        pt.x = (float) Math.cos(a);
        pt.y = (float) Math.sin(a);
        return pt;
        //            return CreatePoint((float)Math.Cos(a), (float)Math.Sin(a));
    }

    /** Converts a vector to radians.
        @return CGFloat
        @since v0.7.2
    */

    public static float ToAngle(CCPoint v)
    {
        return (float) Math.atan2(v.y, v.x);
    }


    /** Clamp a value between from and to.
        @since v0.99.1
    */

    public static float Clamp(float value, float min_inclusive, float max_inclusive)
    {
        if (min_inclusive > max_inclusive)
        {
            float ftmp = min_inclusive;
            min_inclusive = max_inclusive;
            max_inclusive = ftmp;
        }

        return value < min_inclusive ? min_inclusive : value < max_inclusive ? value : max_inclusive;
    }

    /** Clamp a point between from and to.
        @since v0.99.1
    */

    public static CCPoint Clamp(CCPoint p, CCPoint from, CCPoint to)
    {
        CCPoint pt = new CCPoint();
        pt.x = Clamp(p.x, from.x, to.x);
        pt.y = Clamp(p.y, from.y, to.y);
        return pt;
        //            return CreatePoint(Clamp(p.X, from.X, to.X), Clamp(p.Y, from.Y, to.Y));
    }

    /** Quickly convert CCSize to a CCPoint
        @since v0.99.1
    */

    //[Obsolete("Use explicit cast (CCPoint)size.")]
    public static CCPoint FromSize(CCSize s)
    {
        CCPoint pt = new CCPoint();
        pt.x = s.width;
        pt.y = s.height;
        return pt;
    }

    /**
     * Allow Cast CCSize to CCPoint
     */

//    public CCPoint(CCSize size)
//    {
//        CCPoint pt = new CCPoint();
//        pt.x = size.width;
//        pt.y = size.height;
//        
//    }

    public static CCPoint Perp(CCPoint p)
    {
        CCPoint pt = new CCPoint();
        pt.x = -p.y;
        pt.y = p.x;
        return pt;
    }

    public static float Dot(CCPoint p1, CCPoint p2)
    {
        return p1.x * p2.x + p1.y * p2.y;
    }

    public static float Distance(CCPoint v1, CCPoint v2)
    {
        return ccpSub(v1, v2).Length();
    }

    public static CCPoint Normalize(CCPoint p)
    {
        float x = p.x;
        float y = p.y;
        float l = 1f / (float) Math.sqrt(x * x + y * y);
        CCPoint pt = new CCPoint();
        pt.x = x * l;
        pt.y = y * l;
        return pt;
    }

    public static CCPoint Midpoint(CCPoint p1, CCPoint p2)
    {
        CCPoint pt = new CCPoint();
        pt.x = (p1.x + p2.x) / 2f;
        pt.y = (p1.y + p2.y) / 2f;
        return pt;
    }

    public static float DotProduct(CCPoint v1, CCPoint v2)
    {
        return v1.x * v2.x + v1.y * v2.y;
    }

    /** Calculates cross product of two points.
        @return CGFloat
        @since v0.7.2
    */

    public static float CrossProduct(CCPoint v1, CCPoint v2)
    {
        return v1.x * v2.y - v1.y * v2.x;
    }

    /** Calculates perpendicular of v, rotated 90 degrees counter-clockwise -- cross(v, perp(v)) >= 0
        @return CCPoint
        @since v0.7.2
    */

    public static CCPoint PerpendicularCounterClockwise(CCPoint v)
    {
        CCPoint pt = new CCPoint();
        pt.x = -v.y;
        pt.y = v.x;
        return pt;
    }

    /** Calculates perpendicular of v, rotated 90 degrees clockwise -- cross(v, rperp(v)) <= 0
        @return CCPoint
        @since v0.7.2
    */

    public static CCPoint PerpendicularClockwise(CCPoint v)
    {
        CCPoint pt = new CCPoint();
        pt.x = v.y;
        pt.y = -v.x;
        return pt;
    }

    /** Calculates the projection of v1 over v2.
        @return CCPoint
        @since v0.7.2
    */

    public static CCPoint Project(CCPoint v1, CCPoint v2)
    {
        float dp1 = v1.x * v2.x + v1.y * v2.y;
        float dp2 = v2.LengthSQ();
        float f = dp1 / dp2;
        CCPoint pt = new CCPoint();
        pt.x = v2.x * f;
        pt.y = v2.y * f;
        return pt;
        // return Multiply(v2, DotProduct(v1, v2) / DotProduct(v2, v2));
    }

    /** Rotates two points.
        @return CCPoint
        @since v0.7.2
    */

    public static CCPoint Rotate(CCPoint v1, CCPoint v2)
    {
        CCPoint pt = new CCPoint();
        pt.x = v1.x * v2.x - v1.y * v2.y;
        pt.y = v1.x * v2.y + v1.y * v2.x;
        return pt;
    }

    /** Unrotates two points.
        @return CCPoint
        @since v0.7.2
    */

    public static CCPoint Unrotate(CCPoint v1, CCPoint v2)
    {
        CCPoint pt = new CCPoint();
        pt.x = v1.x * v2.x + v1.y * v2.y;
        pt.y = v1.y * v2.x - v1.x * v2.y;
        return pt;
    }


    public static boolean ccpEqu(CCPoint p1, CCPoint p2)
    {
        return p1.x == p2.x && p1.y == p2.y;
    }

    public static boolean ccpNotEqu(CCPoint p1, CCPoint p2)
    {
        return p1.x != p2.x || p1.y != p2.y;
    }

    public static CCPoint ccpSub(CCPoint p1, CCPoint p2)
    {
        CCPoint pt = new CCPoint();
        pt.x = p1.x - p2.x;
        pt.y = p1.y - p2.y;
        return pt;
    }

    public static CCPoint ccpNeg(CCPoint p1)
    {
        CCPoint pt = new CCPoint();
        pt.x = -p1.x;
        pt.y = -p1.y;
        return pt;
    }

    public static CCPoint ccpAdd(CCPoint p1, CCPoint p2)
    {
        CCPoint pt = new CCPoint();
        pt.x = p1.x + p2.x;
        pt.y = p1.y + p2.y;
        return pt;
    }

    public static CCPoint ccpPos(CCPoint p1)
    {
        CCPoint pt = new CCPoint();
        pt.x = +p1.x;
        pt.y = +p1.y;
        return pt;
    }

    public static CCPoint ccpMult(CCPoint p, float value)
    {
        CCPoint pt = new CCPoint();
        pt.x = p.x * value;
        pt.y = p.y * value;
        return pt;
    }

    public CCPoint mult(float value)
    {
        this.x = this.x * value;
        this.y = this.y * value;
        return this;
    }
    
    
    public static CCPoint ccpDiv(CCPoint p, float value)
    {
        CCPoint pt = new CCPoint();
        pt.x = p.x / value;
        pt.y = p.y / value;
        return pt;
    }

    public CCPoint pixelsToPoints()
    {
        float cs = CCDirector.sharedDirector().getContentScaleFactor();
        return new CCPoint(this.x / cs, this.y / cs);
    }

    public CCPoint pointsToPixels()
    {
        float cs = CCDirector.sharedDirector().getContentScaleFactor();
        return new CCPoint(this.x * cs, this.y * cs);
    }
    
    
    
//    public static CCPoint Parse(string s)
//    {
//        return (CCPoint) TypeDescriptor.GetConverter(typeof (CCPoint)).ConvertFromString(s);
//    }

//    public static implicit operator Vector2(CCPoint point)
//    {
//        return new Vector2(point.X, point.Y);
//    }
//
//    public static implicit operator Vector3(CCPoint point)
//    {
//        return new Vector3(point.X, point.Y, 0);
//    }
}