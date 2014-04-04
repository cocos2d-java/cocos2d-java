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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

import cocos2d.actions.CCActionManager;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCSize;
import cocos2d.layers_scenes_transitions_nodes.CCScene;
import cocos2d.platform.CCDrawManager;
import cocos2d.predefine.CCTouch;
import cocos2d.touch_dispatcher.CCInputProcessor;
import cocos2d.touch_dispatcher.CCTouchDispatcher;
import cocos2d.touch_dispatcher.CCTouchDispatcher.CCTouchType;
import cocos2d.touch_dispatcher.ICCEGLTouchDelegate;

public abstract class CCDirector 
{
	private static CCDirector s_sharedDirector;
	
	private final Array<CCScene> m_pobScenesStack = new Array<CCScene>();
	private CCSize m_obWinSizeInPoints;
	private Boolean m_NeedsInit = true;
	/** The running CCScene */
	private CCScene m_pRunningScene;
	/** will be the next 'runningCCScene' in the next frame */
	protected CCScene m_pNextScene;
	private CCScheduler m_pScheduler;
	private CCTouchDispatcher m_pTouchDispatcher;
	private CCActionManager m_pActionManager;
	private boolean m_bSendCleanupToScene;
	private float m_fContentScaleFactor = 1.0f;
	
	
	//processTouch
	public ICCEGLTouchDelegate m_pDelegate;
 	public int _lastMouseId;
 	protected boolean m_bCaptured;
 	private final Array<CCTouch> endedTouches = new Array<CCTouch>();
    private final LinkedHashMap<Integer, CCTouch> m_pTouchMap = new LinkedHashMap<Integer, CCTouch>();
    private final LinkedList<CCTouch> m_pTouches = new LinkedList<CCTouch>();
    private final Array<CCTouch> movedTouches = new Array<CCTouch>();
    private final Array<CCTouch> newTouches = new Array<CCTouch>();
 	
	public TouchDelegate m_pTouchDelegate = new TouchDelegate();
	
	public class TouchDelegate
	{
		public int _lastMouseId_pt;
		public boolean Captured;
		public CCTouchType TouchType;
		public int screenX;
		public int screenY;
		public int pointer;
		public int button;
	}
	
	//class CCInputProcessor implements com.badlogic.gdx.InputProcessor
	private CCInputProcessor m_pInputProcessor;
	
	public static CCDirector sharedDirector()
    {
		 if (s_sharedDirector == null)
         {
             s_sharedDirector = new CCDisplayLinkDirector();
             s_sharedDirector.init();
         }
         return s_sharedDirector;
         
    }

	public CCTouchDispatcher getTouchDispatcher()
    {
        return m_pTouchDispatcher; 
    }
	
	public void setTouchDispatcher(CCTouchDispatcher value)
    {
        m_pTouchDispatcher = value;
    }
	
	public void setTouchDelegate(ICCEGLTouchDelegate value)
    {
        m_pDelegate = value;
    }
	
	
	public boolean init(){
		
		// touchDispatcher
        m_pTouchDispatcher = new CCTouchDispatcher();
        m_pTouchDispatcher.init();
        
        m_pInputProcessor = new CCInputProcessor();
        Gdx.input.setInputProcessor(m_pInputProcessor);
		
        // scheduler
        m_pScheduler = new CCScheduler();
        // action manager
        m_pActionManager = new CCActionManager();
        m_pScheduler.scheduleUpdateForTarget(m_pActionManager, CCScheduler.kCCPrioritySystem, false);
		m_NeedsInit = false;
		return true;
	}
	
	public void update() throws Exception
	{
		// Process touch events 
        processTouch();
     // In Seconds
       // m_pScheduler.update(Gdx.graphics.getDeltaTime());
        m_pScheduler.update(Gdx.graphics.getRawDeltaTime());
        if (m_pNextScene != null)
        {
            setNextScene();
        }
	}
	
	
	public abstract void mainLoop();
	
	public CCSize getWinSize(){
		return new CCSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public CCSize getVisibleSize()
    {
        return new CCSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
	
	public CCPoint getVisibleOrigin()
    {
        return new CCPoint(0,0); 
    }
	
	public CCScheduler getScheduler()
    {
        return m_pScheduler;
    }
	
	public void setScheduler(CCScheduler value)
    {
        m_pScheduler = value;
    }
	
	
	public CCActionManager getActionManager()
    {
        return m_pActionManager;
    }
	
	public void setActionManager(CCActionManager value)
    {
        m_pActionManager = value;
    }
	
	public CCScene getRunningScene()
    {
        return m_pRunningScene;
    }
	
	protected void drawScene(){
		if (m_NeedsInit)
        {
            return;
        }
		
		//update
		if (m_pNextScene != null)
        {
            setNextScene();
        }
		
		
		CCDrawManager.PushMatrix();
		// draw the scene
        if (m_pRunningScene != null)
        {
            m_pRunningScene.visit();
        }
		CCDrawManager.PopMatrix();
		
	}
	
	public void runWithScene(CCScene pScene)
    {
         //Debug.Assert(pScene != null, "the scene should not be null");
         //Debug.Assert(m_pRunningScene == null, "Use runWithScene: instead to start the director");

         pushScene(pScene);
         //StartAnimation();
    }
	

    /**Replaces the current scene at the top of the stack with the given scene.*/
    public void replaceScene(CCScene pScene)
    {
        //Debug.Assert(m_pRunningScene != null, "Use runWithScene: instead to start the director");
        //Debug.Assert(pScene != null, "the scene should not be null");

        int index = m_pobScenesStack.size;

        m_bSendCleanupToScene = true;
        if (index == 0)
        {
            m_pobScenesStack.add(pScene);
        }
        else
        {
        m_pobScenesStack.set(index - 1, pScene);
        }
        m_pNextScene = pScene;
    }
	
	
    // Push the given scene to the top of the scene stack.
    public void pushScene(CCScene pScene)
    {
       // m_bSendCleanupToScene = false;
        m_pobScenesStack.add(pScene);
        m_pNextScene = pScene;
    }
	
    protected void setNextScene(){
    	boolean runningIsTransition = m_pRunningScene != null && m_pRunningScene.isTransition();// is CCTransitionScene;
    	 // If it is not a transition, call onExit/cleanup
        if (!m_pNextScene.isTransition())
        {
	    	if (m_pRunningScene != null)
	        {
	            m_pRunningScene.onExitTransitionDidStart(); 
	            m_pRunningScene.onExit();
	
	            // issue #709. the root node (scene) should receive the cleanup message too
	            // otherwise it might be leaked.
	            if (m_bSendCleanupToScene)
	            {
	                m_pRunningScene.cleanup();
	
	                System.gc();
	            }
	        }
        }
    	m_pRunningScene = m_pNextScene;
        m_pNextScene = null;

        if (!runningIsTransition && m_pRunningScene != null)
        {
            m_pRunningScene.onEnter();
            m_pRunningScene.onEnterTransitionDidFinish();
        }
    }
    
    public CCPoint convertToGl(CCPoint uiPoint)
    {
        return new CCPoint(uiPoint.x, m_obWinSizeInPoints.height - uiPoint.y);
    }
    
    
    public void setOpenGlView()
    {
        // set size
        m_obWinSizeInPoints = new CCSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        CreateStatsLabel();
//
//        SetGlDefaultValues();
//
        //CCApplication.sharedApplication().setTouchDelegate(m_pTouchDispatcher);
//        m_pTouchDispatcher.IsDispatchEvents = true;
        setTouchDelegate(m_pTouchDispatcher);
    }
    
    private void processTouch()
    {
    	if (m_pDelegate != null)
        {
            newTouches.clear();
            movedTouches.clear();
            endedTouches.clear();
            
            if (m_pTouchDelegate.TouchType == CCTouchType.Began)
            {
            	_lastMouseId++;
            	//kktest
            	System.out.println(_lastMouseId);
                m_pTouches.addLast(new CCTouch(_lastMouseId, m_pTouchDelegate.screenX, m_pTouchDelegate.screenY));
                m_pTouchMap.put(_lastMouseId, m_pTouches.getLast());
                newTouches.add(m_pTouches.getLast());

                m_bCaptured = true;
            }
            else if (m_pTouchDelegate.TouchType == CCTouchType.Moved)
            {
            	if (m_pTouchMap.containsKey(_lastMouseId))
            	{
            		movedTouches.add(m_pTouchMap.get(_lastMouseId));
                    m_pTouchMap.get(_lastMouseId).SetTouchInfo(_lastMouseId, m_pTouchDelegate.screenX, m_pTouchDelegate.screenY);
            	}
            }
            else if (m_pTouchDelegate.TouchType == CCTouchType.Ended)
            {
            	if (m_pTouchMap.containsKey(_lastMouseId))
                {
                    endedTouches.add(m_pTouchMap.get(_lastMouseId));
                    m_pTouches.remove(m_pTouchMap.get(_lastMouseId));
                    m_pTouchMap.remove(_lastMouseId);
                }
            }
            
            if (newTouches.size > 0)
            {
                m_pDelegate.touchesBegan(newTouches);
            }

            if (movedTouches.size > 0)
            {
                m_pDelegate.touchesMoved(movedTouches);
            }

            if (endedTouches.size > 0)
            {
                m_pDelegate.touchesEnded(endedTouches);
            }
            
        }
    	
    	
    	m_pTouchDelegate.TouchType = null;
    }
    
    public boolean isSendCleanupToScene()
    {
        return m_bSendCleanupToScene;
    }
    
    
    public float getContentScaleFactor()
    {
        return m_fContentScaleFactor;
    }
  
    public void setContentScaleFactor(float value)
    {
        if (value != m_fContentScaleFactor)
        {
           m_fContentScaleFactor = value;
        }
        
    }
    
    public void setGlClearColor(Color color)
    {
    	CCDrawManager.GlClearColor = color;
    }
    
}
