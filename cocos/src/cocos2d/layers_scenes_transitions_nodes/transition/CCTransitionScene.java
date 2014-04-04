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

package cocos2d.layers_scenes_transitions_nodes.transition;

import cocos2d.CCDirector;
import cocos2d.cocoa.CCPoint;
import cocos2d.layers_scenes_transitions_nodes.CCScene;
import cocos2d.platform.ISelector;
import cocos2d.platform.Selector;

public class CCTransitionScene extends CCScene
{
    protected boolean m_bIsInSceneOnTop;
    protected boolean m_bIsSendCleanupToScene;
    protected float m_fDuration;
    protected CCScene m_pInScene;
    protected CCScene m_pOutScene;
    public ISelector hideOutShowIn;
    public ISelector finish;
    
    public ISelector setNewScene;

    // This can be taken out once all the transitions have been modified with constructors.
    protected CCTransitionScene() 
    {
    	hideOutShowIn = new hideOutShowIn();
    	finish = new finish();
    }

    /*
    public CCTransitionScene(float t)
    {
        m_fDuration = t;
    }
    */

    public CCTransitionScene (float t, CCScene scene)
    {
        initWithDuration(t, scene);
        hideOutShowIn = new hideOutShowIn();
    	finish = new finish();
    }
    

    @Override
    public boolean isTransition()
    {   
        return (true);
    }

    @Override
    public void draw()
    {
        super.draw();

        if (m_bIsInSceneOnTop)
        {
            m_pOutScene.visit();
            m_pInScene.visit();
        }
        else
        {
            m_pInScene.visit();
            m_pOutScene.visit();
        }
    }

    @Override
    public void onEnter()
    {
        super.onEnter();

        // disable events while transitions
        CCDirector.sharedDirector().getTouchDispatcher().setIsDispatchEvents(false);

        // outScene should not receive the onEnter callback
        // only the onExitTransitionDidStart
        m_pOutScene.onExitTransitionDidStart();

        m_pInScene.onEnter();
    }

    @Override
    public void onExit()
    {
        super.onExit();

        // enable events while transitions
        CCDirector.sharedDirector().getTouchDispatcher().setIsDispatchEvents(true);

        m_pOutScene.onExit();

        // m_pInScene should not receive the onEnter callback
        // only the onEnterTransitionDidFinish
        m_pInScene.onEnterTransitionDidFinish();
    }

    @Override
    public void cleanup()
    {
        super.cleanup();

        if (m_bIsSendCleanupToScene)
            m_pOutScene.cleanup();
    }

    public void reset(float t, CCScene scene)
    {
        initWithDuration(t, scene);
    }

    protected boolean initWithDuration(float t, CCScene scene)
    {
        //Debug.Assert(scene != null, "Argument scene must be non-nil");

        if (super.init())
        {
            m_fDuration = t;

            // retain
            m_pInScene = scene;
            m_pOutScene = CCDirector.sharedDirector().getRunningScene();
            if (m_pOutScene == null)
            {
                // Creating an empty scene.
                m_pOutScene = new CCScene();
                m_pOutScene.init();
            }

            //Debug.Assert(m_pInScene != m_pOutScene, "Incoming scene must be different from the outgoing scene");

            sceneOrder();

            return true;
        }
        return false;
    }

    class finish extends Selector
    {
		@Override
		public void method()
		{
			// clean up     
	        m_pInScene.setVisible(true);
	        m_pInScene.setPosition(CCPoint.Zero);
	        m_pInScene.setScale(1.0f);
	        m_pInScene.setRotation(0.0f);
	        //m_pInScene.Camera.Restore();

	        m_pOutScene.setVisible(false);
	        m_pOutScene.setPosition(CCPoint.Zero);
	        m_pOutScene.setScale(1.0f);
	        m_pOutScene.setRotation(0.0f);
	        //m_pOutScene.Camera.Restore();

	        setNewScene = new setNewScene();
	        schedule(setNewScene, 0);
		}	
    }
    
//    public void finish() throws Exception
//    {
//        // clean up     
//        m_pInScene.setVisible(true);
//        m_pInScene.setPosition(CCPoint.Zero);
//        m_pInScene.setScale(1.0f);
//        m_pInScene.setRotation(0.0f);
//        //m_pInScene.Camera.Restore();
//
//        m_pOutScene.setVisible(false);
//        m_pOutScene.setPosition(CCPoint.Zero);
//        m_pOutScene.setScale(1.0f);
//        m_pOutScene.setRotation(0.0f);
//        //m_pOutScene.Camera.Restore();
//
//        this.setNewScene = new setNewScene();
//        schedule(setNewScene, 0);
//    }

    class hideOutShowIn extends Selector
    {
		@Override
		public void method() {
			m_pInScene.setVisible(true);
	        m_pOutScene.setVisible(false);
		}
    	
    }
    
//    public void hideOutShowIn()
//    {
//        m_pInScene.setVisible(true);
//        m_pOutScene.setVisible(false);
//    }

    protected void sceneOrder()
    {
        m_bIsInSceneOnTop = true;
    }

    class setNewScene extends Selector
    {
		public void method(float dt) 
		{
			unschedule(setNewScene);

	        // Before replacing, save the "send cleanup to scene"
	        CCDirector director = CCDirector.sharedDirector();
	        m_bIsSendCleanupToScene = director.isSendCleanupToScene();
	        director.replaceScene(m_pInScene);

	        // issue #267
	        m_pOutScene.setVisible(true);
			
		}
    	
    }
    
}