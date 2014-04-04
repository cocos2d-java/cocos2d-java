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
import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.actions.action_ease.CCEaseOut;
import cocos2d.actions.action_instants.callfunc.CCCallFunc;
import cocos2d.actions.action_intervals.CCActionInterval;
import cocos2d.actions.action_intervals.CCMoveTo;
import cocos2d.actions.action_intervals.CCSequence;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCSize;
import cocos2d.layers_scenes_transitions_nodes.CCScene;

public class CCTransitionMoveInL extends CCTransitionScene implements ICCTransitionEaseScene
{

    public CCTransitionMoveInL() { 	}

    public CCTransitionMoveInL (float t, CCScene scene) 
    { 
    	super(t, scene);
    }

    public CCFiniteTimeAction easeAction(CCActionInterval action)
    {
        return new CCEaseOut(action, 2.0f);
    }


    /// <summary>
    /// initializes the scenes
    /// </summary>
    public void initScenes()
    {
        CCSize s = CCDirector.sharedDirector().getWinSize();
        m_pInScene.setPosition(new CCPoint(-s.width, 0));
    }

    /// <summary>
    /// returns the action that will be performed
    /// </summary>
    /// <returns></returns>
    public CCActionInterval action()
    {
        return new CCMoveTo (m_fDuration, new CCPoint(0, 0));
    }

    @Override
    public void onEnter()
    {
        super.onEnter();
        initScenes();

        CCActionInterval a = action();

        m_pInScene.runAction
            (
                new CCSequence
                    (
                        easeAction(a),
                        new CCCallFunc(this.finish)
                    )
            );
    }

}
