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

import cocos2d.actions.action_instants.callfunc.CCCallFunc;
import cocos2d.actions.action_intervals.CCActionInterval;
import cocos2d.actions.action_intervals.CCFadeIn;
import cocos2d.actions.action_intervals.CCFadeOut;
import cocos2d.actions.action_intervals.CCSequence;
import cocos2d.base_nodes.CCNode;
import cocos2d.layers_scenes_transitions_nodes.CCLayerColor;
import cocos2d.layers_scenes_transitions_nodes.CCScene;
import cocos2d.predefine.CCColor3B;
import cocos2d.predefine.CCColor4B;

public class CCTransitionFade extends CCTransitionScene
{
    private final int kSceneFade = 2147483647;
    protected CCColor4B m_tColor;

    /// <summary>
    /// creates the transition with a duration and with an RGB color
    /// Example: FadeTransition::create(2, scene, ccc3(255,0,0); // red color
    /// </summary>
    public CCTransitionFade (float duration, CCScene scene, CCColor3B color)
    {
    	super(duration, scene);
        initWithDuration(duration, scene, color);
    }

    public CCTransitionFade (float t, CCScene scene) 
    {
    	this (t, scene, new CCColor3B());
        //return Create(t, scene, new CCColor3B());
    }

    /// <summary>
    /// initializes the transition with a duration and with an RGB color 
    /// </summary>
    protected boolean initWithDuration(float duration, CCScene scene, CCColor3B color)
    {
        if (super.initWithDuration(duration, scene))
        {
            m_tColor = new CCColor4B(color.R, color.G, color.B, 0);
        }
        return true;
    }

    @Override
    protected boolean initWithDuration(float t, CCScene scene)
    {
        initWithDuration(t, scene, new CCColor3B(0,0,0));
        return true;
    }

    @Override
    public void onEnter()
    {
        super.onEnter();

        CCLayerColor l = new CCLayerColor(m_tColor);
        m_pInScene.setVisible(false);

        addChild(l, 2, kSceneFade);
        CCNode f = getChildByTag(kSceneFade);

        CCActionInterval a = (CCActionInterval) new CCSequence
                                       (
                                           new CCFadeIn (m_fDuration / 2),
                                          new CCCallFunc(this.hideOutShowIn),
                                           new CCFadeOut  (m_fDuration / 2),
                                           new CCCallFunc(this.finish)
                                      );

        f.runAction(a);
    }

    @Override
    public void onExit()
    {
        super.onExit();
        removeChildByTag(kSceneFade, false);
    }
}