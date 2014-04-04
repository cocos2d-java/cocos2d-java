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

import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.actions.action_intervals.CCActionInterval;
import cocos2d.predefine.CCGridSize;

/// <summary>
/// @brief CCTransitionFadeTR:
/// Fade the tiles of the outgoing scene from the left-bottom corner the to top-right corner.
/// </summary>
public class CCTransitionFadeTR extends CCTransitionScene implements ICCTransitionEaseScene
{
    public CCFiniteTimeAction easeAction(CCActionInterval action)
    {
        return action;
    }

//    public CCActionInterval CreateAction(CCGridSize size)
//    {
//        return new CCFadeOutTRTiles(m_fDuration, size);
//    }
//
//    public override void OnEnter()
//    {
//        base.OnEnter();
//
//        CCSize s = CCDirector.SharedDirector.WinSize;
//        float aspect = s.Width / s.Height;
//        var x = (int) (12 * aspect);
//        int y = 12;
//
//        CCActionInterval action = CreateAction(new CCGridSize(x, y));
//
//        m_pOutScene.RunAction
//            (
//                new CCSequence
//                    (
//                        EaseAction(action),
//                        new CCCallFunc(Finish),
//                        new CCStopGrid()
//                    )
//            );
//    }
//
//    public CCTransitionFadeTR() { }
//    public CCTransitionFadeTR(float t, CCScene scene) : base(t, scene)
//    {
//        InitWithDuration(t, scene);
//    }
//
//    protected override void SceneOrder()
//    {
//        m_bIsInSceneOnTop = false;
//    }
}
