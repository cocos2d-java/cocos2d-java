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

package cocos2d;

import cocos2d.platform.CCDrawManager;
import com.badlogic.gdx.ApplicationListener;

public class Cocos2d 
{	
	//一个单例，只能有一个Cocos2dGame
	private static Cocos2dGame SharedGame = null;
	public static Cocos2dGame Game()
	{
		if (SharedGame == null)
		{
			SharedGame = new Cocos2dGame();
			return SharedGame;
		}
		return SharedGame;
	} 
	
	public static class  Cocos2dGame implements ApplicationListener
	{
		@Override
		public void create()
		{	
			CCApplication.sharedApplication().run();			
		}

		@Override
		public void resize(int width, int height){}

		@Override
		public void render()
		{
			try 
			{
				CCDirector.sharedDirector().update();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			CCDrawManager.BeginDraw();
			CCDirector.sharedDirector().mainLoop();
			CCDrawManager.EndDraw();
		}

		@Override
		public void pause(){}

		@Override
		public void resume(){}

		@Override
		public void dispose(){}	
	}
}


