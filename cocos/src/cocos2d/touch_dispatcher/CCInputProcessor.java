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

package cocos2d.touch_dispatcher;

import cocos2d.CCDirector;
import cocos2d.touch_dispatcher.CCTouchDispatcher.CCTouchType;
import com.badlogic.gdx.InputProcessor;

public class CCInputProcessor implements InputProcessor
{
//	private CCTouchDispatcher m_pDelegate;
//	private int _lastMouseId;
//	
//	public CCInputProcessor(){
//		m_pDelegate = CCDirector.sharedDirector().getTouchDispatcher();
//	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		_lastMouseId++;
//		ArrayList<CCTouch> newTouches = new ArrayList<CCTouch>();
//		newTouches.add(new CCTouch(_lastMouseId, screenX, screenY));
//		m_pDelegate.touchesBegan(newTouches);
//		//System.out.println("你太伟大了，主人。。。");
		CCDirector director = CCDirector.sharedDirector();
		director.m_pTouchDelegate.TouchType = CCTouchType.Began;
		director.m_pTouchDelegate.screenX = screenX;
		director.m_pTouchDelegate.screenY = screenY;
		director.m_pTouchDelegate.pointer = pointer;
		director.m_pTouchDelegate.button = button;
		
		System.out.println("1");
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		ArrayList<CCTouch> newTouches = new ArrayList<CCTouch>();
//		newTouches.add(new CCTouch(_lastMouseId, screenX, screenY));
//		m_pDelegate.touchesEnded(newTouches);
		
		CCDirector director = CCDirector.sharedDirector();
		director.m_pTouchDelegate.TouchType = CCTouchType.Ended;
		director.m_pTouchDelegate.screenX = screenX;
		director.m_pTouchDelegate.screenY = screenY;
		director.m_pTouchDelegate.pointer = pointer;
		director.m_pTouchDelegate.button = button;
		

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
//		ArrayList<CCTouch> newTouches = new ArrayList<CCTouch>();
//		newTouches.add(new CCTouch(_lastMouseId, screenX, screenY));
//		m_pDelegate.touchesMoved(newTouches);
		
		CCDirector director = CCDirector.sharedDirector();
		director.m_pTouchDelegate.TouchType = CCTouchType.Moved;
		director.m_pTouchDelegate.screenX = screenX;
		director.m_pTouchDelegate.screenY = screenY;
		director.m_pTouchDelegate.pointer = pointer;
		director.m_pTouchDelegate.button = 0;
		
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}