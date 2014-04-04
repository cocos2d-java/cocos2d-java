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

import cocos2d.platform.ISelector;
import cocos2d.predefine.ICCSelectorProtocol;

public class CCTimer implements ICCSelectorProtocol
{
	private CCScheduler _scheduler;
    private ICCSelectorProtocol m_pTarget;

    private  boolean m_bRunForever = false;
    private  float m_fDelay = 0f;
    private  int m_uRepeat = 0; //0 = once, 1 is 2 x executed
    private float m_fElapsed = 0f;
    private boolean m_bUseDelay = false;
	
    //private int m_nScriptHandler;
    private int m_uTimesExecuted = 0;

    public float OriginalInterval = 0f;
	public float Interval = 0f;
	
	public ISelector Selector;
	
	public CCTimer()
    {
    }

    /** Initializes a timer with a target and a selector. 
     */

    public CCTimer(CCScheduler scheduler, ICCSelectorProtocol target, ISelector selector)
    {
    	this(scheduler, target, selector, 0, 0, 0);
    }

    /** Initializes a timer with a target, a selector and an interval in seconds. 
     *  Target is not needed in c#, it is just for compatibility.
     */

    public CCTimer(CCScheduler scheduler, ICCSelectorProtocol target, ISelector userMethod, float seconds) 
    {
    	this(scheduler, target, userMethod, seconds, 0, 0);
    }

    public CCTimer(CCScheduler scheduler, ICCSelectorProtocol target, ISelector selector, float seconds,
                   int repeat, float delay)
    {
        _scheduler = scheduler;
        m_pTarget = target;
        this.Selector = selector;
        m_fElapsed = -1;
        OriginalInterval = seconds;
        Interval = seconds;
        m_fDelay = delay;
        m_bUseDelay = delay > 0f;
        m_uRepeat = repeat;
        m_bRunForever = m_uRepeat == Integer.MAX_VALUE;
    }

	
	
	@Override
	public void update(float dt) throws Exception
    {
        if (m_fElapsed == -1)
        {
            m_fElapsed = 0;
            m_uTimesExecuted = 0;
        }
        else
        {
            if (m_bRunForever && !m_bUseDelay)
            {
                //standard timer usage
                m_fElapsed += dt;
                if (m_fElapsed >= Interval)
                {
                    if (Selector != null)
                    {
                    	Selector.method(m_fElapsed);
                    }

                    Interval = OriginalInterval - (m_fElapsed - Interval);
                }
            }
            else
            {
                //advanced usage
                m_fElapsed += dt;

                if (m_bUseDelay)
                {
                    if (m_fElapsed >= m_fDelay)
                    {
                        if (Selector != null)
                        {
                        	Selector.method(m_fElapsed);
                        }

                        /*
                        if (m_nScriptHandler != 0)
                        {
                            CCScriptEngineManager::sharedManager()->getScriptEngine()->executeSchedule(this, m_fElapsed);
                        }
                        */

                        m_fElapsed = m_fElapsed - m_fDelay;
                        m_uTimesExecuted += 1;
                        m_bUseDelay = false;
                    }
                }
                else
                {
                    if (m_fElapsed >= Interval)
                    {
                        if (Selector != null)
                        {
                        	Selector.method(m_fElapsed);
                        }

                        Interval = OriginalInterval - (m_fElapsed - Interval);
                        m_fElapsed = 0;
                        m_uTimesExecuted += 1;
                    }
                }

                if (!m_bRunForever && m_uTimesExecuted > m_uRepeat)
                {
                    //unschedule timer
                    _scheduler.unscheduleSelector(Selector,  m_pTarget);
                }
            }
        }
    }

}
