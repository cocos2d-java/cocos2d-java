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

package cocos2d.utils;

import cocos2d.cocoa.CCAffineTransform;
import cocos2d.cocoa.CCPoint;


public final class CCPointUtil {
	
	/**
	 * Multiply point by floating, write result in the same point.
	 * @param v - src/dst point
	 * @param s - factor value
	 */
	public static void mult(CCPoint v, float s) {
		v.x *= s;
		v.y *= s;
	}
	
	/**
	 * Multiply point by floating, write result in another point.
	 * @param v - src point
	 * @param s - factor value
	 * @param res - dst point
	 */
	public static void mult(CCPoint v, float s, CCPoint res) {
		res.x = v.x * s;
		res.y = v.y * s;		
	}
	
	public static void applyAffineTransform(CCPoint p, CCAffineTransform t, CCPoint res) {
		applyAffineTransform(p.x, p.y, t, res);
	}
	
	public static void applyAffineTransform(float x, float y, CCAffineTransform t, CCPoint res) {
		res.x = (float) (x * t.m00 + y * t.m01 + t.m02);
		res.y = (float) (x * t.m10 + y * t.m11 + t.m12);
	}
	
	public static void zero(CCPoint p) {
		p.x = 0;
		p.y = 0;
	}

//	public static void normalize(CCPoint src, CCPoint dst) {
//		float invLen = 1 / CCPoint.Distance(v1, v2)(src);
//		dst.set(src.x * invLen, src.y * invLen);
//	}

	public static void add(CCPoint first, CCPoint second, CCPoint ret) {
		ret.x = first.x + second.x;
		ret.y = first.y + second.y;
	}

	public static void add(CCPoint v, CCPoint toAdd) {
		v.x += toAdd.x;
		v.y += toAdd.y;
	}

	public static void sub(CCPoint first, CCPoint second, CCPoint ret) {
		ret.x = first.x - second.x;
		ret.y = first.y - second.y;
	}
	
	public static void sub(CCPoint v, CCPoint toAdd) {
		v.x -= toAdd.x;
		v.y -= toAdd.y;
	}

//	public static float distance(CCPoint p1, CCPoint p2) {
//		float dx = p2.x - p1.x;
//		float dy = p2.y - p1.y;
//		return (float)Math.sqrt(dx * dx + dy * dy);
//	}
	
//	public static void rotateByAngle(CCPoint v, CCPoint pivot, float angle, CCPoint ret) 
//	{
//		CCPointUtil.sub(v, pivot,ret);
//		float t = ret.x;
//		float cosa = (float)Math.cos(angle);
//		float sina = (float)Math.sin(angle);
//		ret.x = t*cosa - ret.y*sina;
//		ret.y = t*sina + ret.y*cosa;
//		CCPointUtil.add(ret, pivot);
//	}
}
