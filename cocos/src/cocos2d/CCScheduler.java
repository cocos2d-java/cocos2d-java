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
import java.util.Hashtable;
import java.util.LinkedList;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import cocos2d.platform.ISelector;
import cocos2d.predefine.ICCSelectorProtocol;

public class CCScheduler 
{
	public final static int kCCRepeatForever = Integer.MAX_VALUE - 1;
    public final static int kCCPrioritySystem = Integer.MIN_VALUE;
    public final static int kCCPriorityNonSystemMin = kCCPrioritySystem + 1;
	
	private final ArrayMap<ICCSelectorProtocol, HashTimeEntry> m_pHashForTimers =
            new ArrayMap<ICCSelectorProtocol, HashTimeEntry>();

    private final ArrayMap<ICCSelectorProtocol, HashUpdateEntry> m_pHashForUpdates =
            new ArrayMap<ICCSelectorProtocol, HashUpdateEntry>();
	
 // hash used to fetch quickly the list entries for pause,delete,etc
    private final Array<ListEntry> m_pUpdates0List = new Array<ListEntry>(); // list priority == 0
    private final Array<ListEntry> m_pUpdatesNegList = new Array<ListEntry>(); // list of priority < 0
    private final Array<ListEntry> m_pUpdatesPosList = new Array<ListEntry>(); // list priority > 0
    
    
    private HashTimeEntry m_pCurrentTarget;
    public float TimeScale = 1.0f;
    private boolean m_bCurrentTargetSalvaged = false;
    private boolean m_bUpdateHashLocked = false;
    
    private static HashTimeEntry[] s_pTmpHashSelectorArray = new HashTimeEntry[128];
    private static ICCSelectorProtocol[] s_pTmpSelectorArray = new ICCSelectorProtocol[128];
    
	public void update(float dt) throws Exception
	{
		m_bUpdateHashLocked = true;
		try
		{
			if (TimeScale != 1.0f)
            {
                dt *= TimeScale;
            }


            // updates with priority < 0
            for (int i = 0, n = m_pUpdatesNegList.size; i < n;i++)
            {
            	if (!m_pUpdatesNegList.get(i).Paused && !m_pUpdatesNegList.get(i).MarkedForDeletion)
            	{
            		m_pUpdatesNegList.get(i).Target.update(dt);
            	}
            }
            
            
            // updates with priority == 0
            for (int i = 0, n = m_pUpdates0List.size; i < n;i++)
            {
            	if (!m_pUpdates0List.get(i).Paused && !m_pUpdates0List.get(i).MarkedForDeletion)
            	{
            		m_pUpdates0List.get(i).Target.update(dt);
            	}
            }
            

            // updates with priority > 0
            for (int i = 0, n = m_pUpdatesPosList.size; i < n;i++)
            {
            	if (!m_pUpdatesPosList.get(i).Paused && !m_pUpdatesPosList.get(i).MarkedForDeletion)
            	{
            		m_pUpdatesPosList.get(i).Target.update(dt);
            	}
            }
            
            
            
            
         // Iterate over all the custom selectors
            int count = m_pHashForTimers.size;
            if (s_pTmpSelectorArray.length < count)
            {
                s_pTmpSelectorArray = new ICCSelectorProtocol[s_pTmpSelectorArray.length * 2];
            }
            s_pTmpSelectorArray = m_pHashForTimers.keys().toArray().toArray(ICCSelectorProtocol.class);

            for (int i = 0; i < count; i++)
            {
                ICCSelectorProtocol key = s_pTmpSelectorArray[i];
                if (!m_pHashForTimers.containsKey(key))
                {
                    continue;
                }
                HashTimeEntry elt = m_pHashForTimers.get(key);

                m_pCurrentTarget = elt;
                m_bCurrentTargetSalvaged = false;

                if (!m_pCurrentTarget.Paused)
                {
                    // The 'timers' array may change while inside this loop
                    for (elt.TimerIndex = 0; elt.TimerIndex < elt.Timers.size; ++elt.TimerIndex)
                    {
                        elt.CurrentTimer = elt.Timers.get(elt.TimerIndex);
						if(elt.CurrentTimer != null) {
                            elt.CurrentTimerSalvaged = false;

                            elt.CurrentTimer.update(dt);

                            elt.CurrentTimer = null;
						}
                    }
                }

                // only delete currentTarget if no actions were scheduled during the cycle (issue #481)
                if (m_bCurrentTargetSalvaged && m_pCurrentTarget.Timers.size == 0)
                {
                    removeHashElement(m_pCurrentTarget);
                }
            }
            
            
            
         // delete all updates that are marked for deletion
            // updates with priority < 0
            for (int i = 0, n = m_pUpdatesNegList.size; i < n;i++)
            {
                if (m_pUpdatesNegList.get(i).MarkedForDeletion)
                {
                    m_pUpdatesNegList.removeIndex(i);
                    removeUpdateFromHash(m_pUpdatesNegList.get(i));
                }
            }

            // updates with priority == 0
            for (int i = 0, n = m_pUpdates0List.size; i < n;i++)
            {
                if (m_pUpdates0List.get(i).MarkedForDeletion)
                {
                    m_pUpdates0List.removeIndex(i);
                    removeUpdateFromHash(m_pUpdates0List.get(i));
                }
            }

            // updates with priority > 0
            for (int i = 0, n = m_pUpdatesPosList.size; i < n;i++)
            {
                if (m_pUpdatesPosList.get(i).MarkedForDeletion)
                {
                    m_pUpdatesPosList.removeIndex(i);
                    removeUpdateFromHash(m_pUpdatesPosList.get(i));
                }
            }
            
            
		}
		finally
		{
			// Always do this just in case there is a problem

            m_bUpdateHashLocked = false;
            m_pCurrentTarget = null;
		}
		
	}
	
	/** The scheduled method will be called every 'interval' seconds.
    If paused is YES, then it won't be called until it is resumed.
    If 'interval' is 0, it will be called every frame, but if so, it's recommended to use 'scheduleUpdateForTarget:' instead.
    If the selector is already scheduled, then only the interval parameter will be updated without re-scheduling it again.
    repeat let the action be repeated repeat + 1 times, use kCCRepeatForever to let the action run continuously
    delay is the amount of time the action will wait before it'll start

    @since v0.99.3, repeat and delay added in v1.1
    */

   public void scheduleSelector(ISelector selector, ICCSelectorProtocol target, float interval, int repeat,
                                float delay, boolean paused)
   {
       //Debug.Assert(selector != null);
       //Debug.Assert(target != null);

	   
       HashTimeEntry element = null;

       synchronized (m_pHashForTimers)
       {
//           if (!m_pHashForTimers.TryGetValue(target, out element))
//           {
//               element = new HashTimeEntry { Target = target };
//               m_pHashForTimers[target] = element;
//
//               // Is this the 1st element ? Then set the pause level to all the selectors of this target
//               element.Paused = paused;
//           }
//    	   boolean tempIsContainsTarget = false;
//    	   if (m_pHashForTimers.containsKey(target))
//    	   {
//    		   element = m_pHashForTimers.get(target);
//    		   tempIsContainsTarget = true;
//    	   }
//    	   if (!tempIsContainsTarget)
//    	   {
//    		   element = new HashTimeEntry();
//    		   element.Target = target;
//               m_pHashForTimers.put(target, element);
//
//              // Is this the 1st element ? Then set the pause level to all the selectors of this target
//              element.Paused = paused;
//    	   }
    	   if (!m_pHashForTimers.containsKey(target))
    	   {
    		   element = new HashTimeEntry();
   		       element.Target = target;
               m_pHashForTimers.put(target, element);

               // Is this the 1st element ? Then set the pause level to all the selectors of this target
               element.Paused = paused;
    	   }   
           else
           {
        	   element = m_pHashForTimers.get(target);
               if (element != null)
               {
                   //Debug.Assert(element.Paused == paused);
               }
           }
           if (element != null)
           {
               if (element.Timers == null)
               {
                   element.Timers = new Array<CCTimer>();
               }
               else
               {
            	   CCTimer[] timers = new CCTimer[element.Timers.size];
                   timers = element.Timers.toArray(CCTimer.class);
                   for (CCTimer timer : timers)
                   {
                       if (timer == null)
                       {
                           continue;
                       }
                       if (selector == timer.Selector)
                       {
                           //CCLog.Log(
                            //   "CCSheduler#scheduleSelector. Selector already scheduled. Updating interval from: {0} to {1}",
                            //   timer.Interval, interval);
                           timer.Interval = interval;
                           return;
                       }
                   }
               }

               element.Timers.add(new CCTimer(this, target, selector, interval, repeat, delay));
           }
       }
   }
	
	
	/** Schedules the 'update' selector for a given target with a given priority.
    The 'update' selector will be called every frame.
    The lower the priority, the earlier it is called.
    @since v0.99.3
    */

	public void scheduleUpdateForTarget(ICCSelectorProtocol target, int priority, Boolean paused)
	{
	   HashUpdateEntry element = null;
	
	   if (m_pHashForUpdates.containsKey(target))
	   {
		   element = m_pHashForUpdates.get(target);
	       //Debug.Assert(element.Entry.MarkedForDeletion);
	
	       // TODO: check if priority has changed!
	       element.Entry.MarkedForDeletion = false;
	
	       return;
	   }
	
	   // most of the updates are going to be 0, that's way there
	   // is an special list for updates with priority 0
	   if (priority == 0)
	   {
	       appendIn(m_pUpdates0List, target, paused);
	   }
	   else if (priority < 0)
	   {
	       priorityIn(m_pUpdatesNegList, target, priority, paused);
	   }
	   else
	   {
	       priorityIn(m_pUpdatesPosList, target, priority, paused);
	   }
	}
	
	/** Unschedule a selector for a given target.
    If you want to unschedule the "update", use unscheudleUpdateForTarget.
    @since v0.99.3
    */

public void unscheduleSelector(ISelector selector, ICCSelectorProtocol target)
{
   // explicity handle nil arguments when removing an object
   if (selector == null || target == null)
   {
       return;
   }

   HashTimeEntry element = null;
   if (m_pHashForTimers.containsKey(target))
   {
	   element = m_pHashForTimers.get(target);
	   for (int i = 0; i < element.Timers.size; i++)
       {
           CCTimer timer = element.Timers.get(i);

           if (selector == timer.Selector)
           {
               if (timer == element.CurrentTimer && (!element.CurrentTimerSalvaged))
               {
                   element.CurrentTimerSalvaged = true;
               }

               element.Timers.removeIndex(i);

               // update timerIndex in case we are in tick:, looping over the actions
               if (element.TimerIndex >= i)
               {
                   element.TimerIndex--;
               }

               if (element.Timers.size == 0)
               {
                   if (m_pCurrentTarget == element)
                   {
                       m_bCurrentTargetSalvaged = true;
                   }
                   else
                   {
                       removeHashElement(element);
                   }
               }

               return;
           }
       }
   }
}
	
	
	/** Unschedules all selectors for a given target.
    This also includes the "update" selector.
    @since v0.99.3
    */

	public void unscheduleAllForTarget(ICCSelectorProtocol target)
	{
	   // explicit NULL handling
	   if (target == null)
	   {
	       return;
	   }
	
	   // custom selectors           
	   HashTimeEntry element = null;
	   if (m_pHashForTimers.containsKey(target))
	   {
		   element = m_pHashForTimers.get(target);
		   if (element.Timers.contains(element.CurrentTimer, true))
	       {
	           element.CurrentTimerSalvaged = true;
	       }
	       element.Timers.clear();
	
	       if (m_pCurrentTarget == element)
	       {
	           m_bCurrentTargetSalvaged = true;
	       }
	       else
	       {
	           removeHashElement(element);
	       }
	   }
	   
	   // update selector
	   unscheduleUpdateForTarget(target);
	}
	
	public void unscheduleUpdateForTarget(ICCSelectorProtocol target)
    {
        if (target == null)
        {
            return;
        }

        HashUpdateEntry element = null;
        if (m_pHashForUpdates.containsKey(target))
        {
        	element = m_pHashForUpdates.get(target);
        	if (m_bUpdateHashLocked)
            {
               element.Entry.MarkedForDeletion = true;
            }
            else
            {
               removeUpdateFromHash(element.Entry);
            }
        }
    }
	
	public void resumeTargets(ArrayList<ICCSelectorProtocol> targetsToResume)
    {
		for (ICCSelectorProtocol target : targetsToResume)
		{
			resumeTarget(target);
		}
    }

	public void pauseTarget(ICCSelectorProtocol target)
    {
        //Debug.Assert(target != null);

        // custom selectors
        HashTimeEntry entry;
        if (m_pHashForTimers.containsKey(target))
        {
        	entry = m_pHashForTimers.get(target);
        	entry.Paused = true;
        }

        // Update selector
        HashUpdateEntry updateEntry;
        if (m_pHashForUpdates.containsKey(target))
        {
        	updateEntry = m_pHashForUpdates.get(target);
        	updateEntry.Entry.Paused = true;
        }
    }
	
	public void resumeTarget(ICCSelectorProtocol target)
    {
        //Debug.Assert(target != null);

        // custom selectors
        HashTimeEntry element = new HashTimeEntry();
        if (m_pHashForTimers.containsKey(target))
        {
        	element = m_pHashForTimers.get(target);
            element.Paused = false;
        }

        // Update selector
        HashUpdateEntry elementUpdate;
        if (m_pHashForUpdates.containsKey(target))
        {
        	elementUpdate = m_pHashForUpdates.get(target);
            elementUpdate.Entry.Paused = false;
        }
    }
	
	private void removeHashElement(HashTimeEntry element)
    {
        m_pHashForTimers.removeKey(element.Target);

        element.Timers.clear();
        element.Target = null;
    }
	
	private void removeUpdateFromHash(ListEntry entry)
    {
        HashUpdateEntry element = null;
        if (m_pHashForUpdates.containsKey(entry.Target))
        {
        	element = m_pHashForUpdates.get(entry.Target);
        	// list entry
            element.List.removeValue(entry, true);
            element.Entry = null;

          // hash entry
            m_pHashForUpdates.removeKey(entry.Target);

            element.Target = null;
        }
    }
	
	private void priorityIn(Array<ListEntry> list, ICCSelectorProtocol target, int priority, Boolean paused)
    {
		ListEntry listElement = new ListEntry();
          
		listElement.Target = target;
		listElement.Priority = priority;
		listElement.Paused = paused;
		listElement.MarkedForDeletion = false;
            

        if (list.size == 0)
        {
            list.insert(0,listElement);
        }
        else
        {
            boolean added = false;           
            for (int i=0, n=list.size; i<n; i++)
            {
            	if (priority < list.get(i).Priority)
            	{
            		list.insert(i, listElement);
            		added = true;
            		break;
            	}
            }

            if (!added)
            {
                list.add(listElement);
            }
        }

        // update hash entry for quick access
        HashUpdateEntry hashElement = new HashUpdateEntry();
        
        hashElement.Target = target;
        hashElement.List = list;
        hashElement.Entry = listElement;
        

        m_pHashForUpdates.put(target, hashElement);
    }
	private void appendIn(Array<ListEntry> list, ICCSelectorProtocol target, Boolean paused)
    {
		ListEntry listElement = new ListEntry(); 
		listElement.Target = target;
		listElement.Paused = paused;
		listElement.MarkedForDeletion = false;

        list.add(listElement);

        // update hash entry for quicker access
        HashUpdateEntry hashElement = new HashUpdateEntry();    
        hashElement.Target = target;
        hashElement.List = list;
        hashElement.Entry = listElement;

        m_pHashForUpdates.put(target, hashElement);
    }

    class HashTimeEntry
    {
        public CCTimer CurrentTimer;
        public Boolean CurrentTimerSalvaged;
        public Boolean Paused;
        public ICCSelectorProtocol Target;
        public int TimerIndex;
        public Array<CCTimer> Timers;
    }


    class HashUpdateEntry
    {
        public ListEntry Entry; // entry in the list
        public Array<ListEntry> List; // Which list does it belong to ?
        public ICCSelectorProtocol Target; // hash key
    }


    class ListEntry
    {
        public Boolean MarkedForDeletion;
        public Boolean Paused;
        public int Priority;
        public ICCSelectorProtocol Target;
    }

}
