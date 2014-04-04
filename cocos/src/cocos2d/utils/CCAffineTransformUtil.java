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


public final class CCAffineTransformUtil {
	
	public static void copy(CCAffineTransform src, CCAffineTransform dst) {
		dst.setTransform(src);
	}
	
	public static void inverse(CCAffineTransform tr) {
        double det = tr.getDeterminant();
        if (Math.abs(det) >= CCAffineTransform.ZERO) {
        	double invDet = 1 / det;
        	tr.setTransform(tr.m11 * invDet, // m00
                -tr.m10 * invDet, // m10
                -tr.m01 * invDet, // m01
                 tr.m00 * invDet, // m11
                (tr.m01 * tr.m12 - tr.m11 * tr.m02) * invDet, // m02
                (tr.m10 * tr.m02 - tr.m00 * tr.m12) * invDet // m12
        	);
        }
	}
	
	public static void inverse(CCAffineTransform src, CCAffineTransform dst) {
        double det = src.getDeterminant();
        if (Math.abs(det) < CCAffineTransform.ZERO) {
//            throw new NoninvertibleTransformException("Determinant is zero");
            dst.setTransform(src);
        } else {
        	double invDet = 1 / det;
        	dst.setTransform(src.m11 * invDet, // m00
                -src.m10 * invDet, // m10
                -src.m01 * invDet, // m01
                 src.m00 * invDet, // m11
                (src.m01 * src.m12 - src.m11 * src.m02) * invDet, // m02
                (src.m10 * src.m02 - src.m00 * src.m12) * invDet // m12
        	);
        }
	}

	public static void multiply(CCAffineTransform t, CCAffineTransform m) {
        t.multiply(m);
	}
	
	/**
	 * preConcate t1 with t2
	 * @param t1 in/out
	 * @param t2 in
	 */
	public static void preConcate(CCAffineTransform t1, CCAffineTransform t2) {
		double m00 = t1.m00 * t2.m00 + t1.m10 * t2.m01;
		double m01 = t1.m00 * t2.m10 + t1.m10 * t2.m11;
		double m10 = t1.m01 * t2.m00 + t1.m11 * t2.m01;
		double m11 = t1.m01 * t2.m10 + t1.m11 * t2.m11;
		double m02 = t1.m02 * t2.m00 + t1.m12 * t2.m01 + t2.m02;
		double m12 = t1.m02 * t2.m10 + t1.m12 * t2.m11 + t2.m12;
		
		t1.m00 = m00;
		t1.m10 = m10;
		t1.m01 = m01;
		t1.m11 = m11;
		t1.m02 = m02;
		t1.m12 = m12;
	}
}
