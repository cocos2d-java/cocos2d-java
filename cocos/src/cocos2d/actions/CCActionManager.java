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

package cocos2d.actions;

import java.util.LinkedHashMap;
import com.badlogic.gdx.utils.Array;
import cocos2d.actions.action.CCAction;
import cocos2d.base_nodes.CCNode;
import cocos2d.predefine.ICCSelectorProtocol;

public class CCActionManager implements ICCSelectorProtocol
{
	private static CCNode[] m_pTmpKeysArray = new CCNode[128];
    private Boolean m_bCurrentTargetSalvaged;
    private HashElement m_pCurrentTarget;
    private final LinkedHashMap<Object, HashElement> m_pTargets = new LinkedHashMap<Object, HashElement>();

	@Override
	public void update(float dt) throws Exception
	{
		int count = m_pTargets.size();
        while (m_pTmpKeysArray.length < count)
        {
            m_pTmpKeysArray = new CCNode[m_pTmpKeysArray.length * 2];
        }

        m_pTmpKeysArray  = m_pTargets.keySet().toArray(m_pTmpKeysArray);

        for (int i = 0; i < count; i++)
        {
            HashElement elt;
            if (!m_pTargets.containsKey(m_pTmpKeysArray[i]))
            {
                continue;
            }
            else
            {
				elt = m_pTargets.get(m_pTmpKeysArray[i]);
			}

            m_pCurrentTarget = elt;
            m_bCurrentTargetSalvaged = false;

            if (!m_pCurrentTarget.Paused)
            {
                // The 'actions' may change while inside this loop.
                for (m_pCurrentTarget.ActionIndex = 0;
                     m_pCurrentTarget.ActionIndex < m_pCurrentTarget.Actions.size;
                     m_pCurrentTarget.ActionIndex++)
                {
                    m_pCurrentTarget.CurrentAction = m_pCurrentTarget.Actions.get(m_pCurrentTarget.ActionIndex);
                    if (m_pCurrentTarget.CurrentAction == null)
                    {
                        continue;
                    }

                    m_pCurrentTarget.CurrentActionSalvaged = false;

                    m_pCurrentTarget.CurrentAction.step(dt);

                    if (m_pCurrentTarget.CurrentActionSalvaged)
                    {
                        // The currentAction told the node to remove it. To prevent the action from
                        // accidentally deallocating itself before finishing its step, we retained
                        // it. Now that step is done, it's safe to release it.

                        //m_pCurrentTarget->currentAction->release();
                    }
                    else if (m_pCurrentTarget.CurrentAction.isDone())
                    {
                        m_pCurrentTarget.CurrentAction.stop();

                        CCAction action = m_pCurrentTarget.CurrentAction;
                        // Make currentAction nil to prevent removeAction from salvaging it.
                        m_pCurrentTarget.CurrentAction = null;
                        removeAction(action);
                    }

                    m_pCurrentTarget.CurrentAction = null;
                }
            }

            // only delete currentTarget if no actions were scheduled during the cycle (issue #481)
            if (m_bCurrentTargetSalvaged && m_pCurrentTarget.Actions.size == 0)
            {
                deleteHashElement(m_pCurrentTarget);
            }
        }

        // issue #635
        m_pCurrentTarget = null;
		
	}

	protected void deleteHashElement(HashElement element)
    {
        element.Actions.clear();
        m_pTargets.remove(element.Target);
        element.Target = null;
    }
	
	
	protected void removeActionAtIndex(int index, HashElement element)
    {
        CCAction action = element.Actions.get(index);

        if (action == element.CurrentAction && (!element.CurrentActionSalvaged))
        {
            element.CurrentActionSalvaged = true;
        }

        element.Actions.removeIndex(index);

        // update actionIndex in case we are in tick. looping over the actions
        if (element.ActionIndex >= index)
        {
            element.ActionIndex--;
        }

        if (element.Actions.size == 0)
        {
            if (m_pCurrentTarget == element)
            {
                m_bCurrentTargetSalvaged = true;
            }
            else
            {
                deleteHashElement(element);
            }
        }
    }
	
	protected void actionAllocWithHashElement(HashElement element)
    {
        if (element.Actions == null)
        {
            element.Actions = new Array<CCAction>();
        }
    }
	
	public void pauseTarget(Object target)
    {
        HashElement element;
        if (m_pTargets.containsKey(target))
        {
        	element = m_pTargets.get(target);
        	element.Paused = true;
        }
    }
	
	public void resumeTarget(Object target)
    {
        HashElement element;
        if (m_pTargets.containsKey(target))
        {
        	element = m_pTargets.get(target);
        	element.Paused = false;
        }
    }
	
	
	public void addAction(CCAction action, CCNode target, Boolean paused)
    {
        //Debug.Assert(action != null);
        //Debug.Assert(target != null);

        HashElement element;
        if (!m_pTargets.containsKey(target))
        {
            element = new HashElement();
            element.Paused = paused;
            element.Target = target;
            m_pTargets.put(target, element);
        }
        else
        {
			element = m_pTargets.get(target);
		}

        actionAllocWithHashElement(element);

        //Debug.Assert(!element.Actions.Contains(action));
        element.Actions.add(action);

        action.startWithTarget(target);
    }
	
	public void removeAllActionsFromTarget(CCNode target)
    {
        if (target == null)
        {
            return;
        }

        HashElement element = null;
        if (m_pTargets.containsKey(target))
        {
        	element = m_pTargets.get(target);
        	if (element.Actions.contains(element.CurrentAction, true) && (!element.CurrentActionSalvaged))
	        {
	            element.CurrentActionSalvaged = true;
	        }
  
            element.Actions.clear();
  
            if (m_pCurrentTarget == element)
            {
                m_bCurrentTargetSalvaged = true;
            }
            else
            {
                deleteHashElement(element);
            }
        }
    }
	
	public void removeAction(CCAction action)
    {
        if (action == null || action.getOriginalTarget() == null)
        {
            return;
        }

        Object target = action.getOriginalTarget();
        HashElement element;
        if (m_pTargets.containsKey(target))
        {
        	element = m_pTargets.get(target);
            int i = element.Actions.indexOf(action, true);

            if (i != -1)
            {
                removeActionAtIndex(i, element);
            }
            else
            {
                //CCLog.Log("cocos2d: removeAction: Action not found");
            }
        }
        else
        {
            //CCLog.Log("cocos2d: removeAction: Target not found");
        }
    }
	
	public void removeActionByTag(int tag, CCNode target)
    {
        //Debug.Assert((tag != (int) CCActionTag.Invalid));
        //Debug.Assert(target != null);

        HashElement element = null;
        if (m_pTargets.containsKey(target))
        {
        	element = m_pTargets.get(target);
        	int limit = element.Actions.size;
            for (int i = 0; i < limit; i++)
            {
                CCAction action = element.Actions.get(i);

                if (action.getTag() == tag && action.getOriginalTarget() == target)
                {
                    removeActionAtIndex(i, element);
                    break;
                }
            }
            //CCLog.Log("cocos2d : removeActionByTag: Tag " + tag + " not found");
        }
        else
        {
            //CCLog.Log("cocos2d : removeActionByTag: Target not found");
        }
    }
	
	
	public CCAction getActionByTag(int tag, CCNode target)
    {
        //Debug.Assert(tag != (int) CCActionTag.Invalid);

        HashElement element = null;
        if (m_pTargets.containsKey(target))
        {
        	element = m_pTargets.get(target);
        	if (element.Actions != null)
            {
                int limit = element.Actions.size;
                for (int i = 0; i < limit; i++)
                {
                    CCAction action = element.Actions.get(i);

                    if (action.getTag() == tag)
                    {
                        return action;
                    }
                }
                //CCLog.Log("cocos2d : getActionByTag: Tag " + tag + " not found");
            }
        }
        else
        {
            //CCLog.Log("cocos2d : getActionByTag: Target not found");
        }
        return null;
    }
	
	
	
	protected class HashElement
	{
		public int ActionIndex;
		public Array<CCAction> Actions;
		public CCAction CurrentAction;
		public Boolean CurrentActionSalvaged;
		public Boolean Paused;
		public Object Target;
	}
}
