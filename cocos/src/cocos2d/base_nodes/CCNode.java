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

package cocos2d.base_nodes;

import java.util.ArrayList;
import java.util.Hashtable;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import cocos2d.CCDirector;
import cocos2d.CCScheduler;
import cocos2d.actions.CCActionManager;
import cocos2d.actions.action.CCAction;
import cocos2d.cocoa.CCAffineTransform;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCSize;
import cocos2d.layers_scenes_transitions_nodes.transition.CCTransitionScene;
import cocos2d.platform.CCDrawManager;
import cocos2d.platform.ICCFocusable;
import cocos2d.platform.ISelector;
import cocos2d.predefine.CCTouch;
import cocos2d.predefine.ICCSelectorProtocol;
import cocos2d.touch_dispatcher.CCTouchDispatcher;
import cocos2d.touch_dispatcher.ICCStandardTouchDelegate;
import cocos2d.touch_dispatcher.ICCTargetedTouchDelegate;
import cocos2d.utils.CCAffineTransformUtil;
import cocos2d.utils.CCPointUtil;
import cocos2d.utils.MathUtil;

public class CCNode implements ICCSelectorProtocol,ICCTargetedTouchDelegate,ICCStandardTouchDelegate, ICCFocusable
{
     /** Use this to determine if a tag has been set on the node.*/
     public final int kCCNodeTagInvalid = -1;
     
     private static int s_globalOrderOfArrival = 1;
	 protected CCAffineTransform m_sTransform;
	 protected boolean m_bRunning;
	 	 
	 protected float m_fRotation = 0.0f;
	 protected float m_fRotationX = 0.0f;         ///< rotation angle on x-axis
	 protected float m_fRotationY = 0.0f;         ///< rotation angle on y-axis
	 
	 
	 protected float m_fScaleX;                   ///< scaling factor on x-axis
     protected float m_fScaleY;                   ///< scaling factor on y-axis
     
     float m_fVertexZ;                              ///< OpenGL real Z vertex
     
     protected CCPoint m_obPosition;               ///< position of the node
     
     protected float m_fSkewX;                     ///< skew angle on x-axis
     protected float m_fSkewY;                     ///< skew angle on y-axis
     protected int m_uOrderOfArrival;
     
     protected CCPoint m_obAnchorPointInPoints;    ///< anchor point in points
     protected CCPoint m_obAnchorPoint;            ///< anchor point normalized (NOT in points)
     
     protected CCSize m_obContentSize;             ///< untransformed size of the node
     
     protected Hashtable<Integer, ArrayList<CCNode>> m_pChildrenByTag;
     
     protected CCAffineTransform m_sAdditionalTransform; ///< transform
     
     
     protected boolean m_bTransformDirty;            ///< transform dirty flag
     protected boolean m_bInverseDirty;               ///< transform dirty flag
     protected boolean m_bAdditionalTransformDirty;   ///< The flag to check whether the additional transform is dirty
     protected boolean m_bVisible;                    ///< is this node visible
     
     protected boolean m_bIgnoreAnchorPointForPosition; ///< true if the Anchor Point will be (0,0) when you position the CCNode, false otherwise.
     ///< Used by CCLayer and CCScene.
     
  // array of children
     protected Array<CCNode> m_pChildren;
     
     protected Boolean m_bReorderChildDirty;
     
     private CCTouchMode m_eTouchMode = CCTouchMode.OneByOne;
     
     
     protected CCNode m_pParent;
     protected CCScheduler m_pScheduler;
     private int m_nZOrder;
     private int m_nTag;
     
     private int m_nTouchPriority;
     
     protected CCActionManager m_pActionManager;
     
     
     private boolean m_bTouchEnabled;
     private boolean m_bHasFocus = false;
     
     public enum CCTouchMode
     {
         AllAtOnce,
         OneByOne
     }
     
     public CCNode(){
    	 m_fScaleX = 1.0f;
    	 m_fScaleY = 1.0f;
    	 m_fVertexZ = 0.0f;
    	 m_obPosition = new CCPoint(0,0);
    	 m_fSkewX = 0.0f;
    	 m_fSkewY = 0.0f;
    	 m_obAnchorPointInPoints = new CCPoint(0,0);
    	 m_obAnchorPoint = new CCPoint(0,0);
    	 m_obContentSize = new CCSize(0, 0);
    	 m_bVisible = true;
    	 m_nTag = kCCNodeTagInvalid;
    	 
    	 m_sTransform = CCAffineTransform.identity();
    	 
    	 
    	// set default scheduler and actionManager
         CCDirector director = CCDirector.sharedDirector();
         m_pActionManager = director.getActionManager();
         m_pScheduler = director.getScheduler();
         
     }
     
     public boolean init()
     {
       return true;
     }
     
     public int getTag()
     {
         return m_nTag;
     }
     
     public void setTag(int value)
     {
         if (m_nTag != value)
         {
             if (getParent() != null)
             {
            	 getParent().changedChildTag(this, m_nTag, value);
             }
             m_nTag = value;
         } 
     }
     
     /// rotation getter
     public float getRotation() {
    	 //some code need to add
    	 return m_fRotationX;
 	 }
      
   /// rotation setter
 	 public void setRotation(float newRotation) {
 		m_fRotationX = m_fRotationY = newRotation;
 		m_bTransformDirty = m_bInverseDirty = true;
 	 }
 	 
 	public float getRotationX()
 	{
 	    return m_fRotationX;
 	}

 	public void setRotationX(float fRotationX)
 	{
 	    m_fRotationX = fRotationX;
 	    m_bTransformDirty = m_bInverseDirty = true;
 	}

 	public float getRotationY()
 	{
 	    return m_fRotationY;
 	}

 	public void setRotationY(float fRotationY)
 	{
 	    m_fRotationY = fRotationY;
 	    m_bTransformDirty = m_bInverseDirty = true;
 	}
 	
 	
 	/// scale getter
    public float getScale() {
        if (m_fScaleX == m_fScaleY) {
            return m_fScaleX;
        } 
        return -1.0f;
    }
    
    public void setScale(float scale) {
    	m_fScaleX = m_fScaleY = scale;
        m_bTransformDirty = m_bInverseDirty = true;
    }
    
    /// scale setter
    public void setScale(float fScaleX,float fScaleY)
    {
        m_fScaleX = fScaleX;
        m_fScaleY = fScaleY;
        m_bTransformDirty = m_bInverseDirty = true;
    }
    
    /// scaleX getter
    public float getScaleX() {
        return m_fScaleX;
    }
    
    /// scaleX setter
    public void setScaleX(float newScaleX) {
    	m_fScaleX = newScaleX;
        m_bTransformDirty = m_bInverseDirty = true;      
    }
    
    
    /// Scale of the node in the Y direction (top to bottom)
    public float getScaleY()
    {
        return m_fScaleY;
    }
    
    public void setScaleY(float value)
    {
        m_fScaleY = value;
        m_bTransformDirty = m_bInverseDirty = true;      
    }
    
    public float getSkewX() {
    	return m_fSkewX;
    }
    public void setSkewX(float s) {
    	m_fSkewX = s;
    	m_bTransformDirty = true;
    	
    }
    
    /// scaleY getter
    public float getSkewY() {
    	return m_fSkewY;
    }
    
    public void setSkewY(float newScaleY) {
    	m_fScaleY = newScaleY;
        m_bTransformDirty = m_bInverseDirty = true;    	
    }
    
    public int getZOrder()
    {
        return m_nZOrder;
    }
    
    public void setZOrder(int value)
    {
        m_nZOrder = value;
        if (m_pParent != null)
        {
            m_pParent.reorderChild(this, value);
        }
    }
    
    public CCPoint getPosition(){
    	return new CCPoint(m_obPosition);
    }
    
    public void setPosition(CCPoint point){
    	m_obPosition.set(point);
    	m_bTransformDirty = true;
    }
    
    public float getPositionX(){
    	return m_obPosition.x;
    }
    
    public void setPositionX(float x){
    	m_obPosition.set(x, m_obPosition.y);
    	m_bTransformDirty = true;
    }
    
    public float getPositionY(){
    	return m_obPosition.y;
    }
    
    public void setPosition(float x, float y){
    	m_obPosition.set(x, y);
    	m_bTransformDirty = true;
    }
    
    public boolean getVisible(){
    	return m_bVisible;
    }
    
    public void setVisible(boolean value){
    	m_bVisible = value;
    }
    
    public CCSize getContentSize(){
    	//return new CCSize(m_obContentSize); 
    	return m_obContentSize; 
    }
    
    public void setContentSize(CCSize size){
    	if (!CCSize.equalToSize(size, m_obContentSize)){
    		m_obContentSize = new CCSize(size);
            m_obAnchorPointInPoints = new CCPoint(m_obContentSize.width * m_obAnchorPoint.x,
                                                          m_obContentSize.height * m_obAnchorPoint.y);
            m_bTransformDirty = m_bInverseDirty = true;
    	}
    }
    
    
    
  /// anchorPoint getter
    public CCPoint getAnchorPoint()
    {
        return new CCPoint(m_obAnchorPoint);
    }

    public void setAnchorPoint(CCPoint point)
    {
        if( !point.equalToPoint(m_obAnchorPoint))
        {
            m_obAnchorPoint = new CCPoint(point);
            m_obAnchorPointInPoints = new CCPoint(m_obContentSize.width * m_obAnchorPoint.x, m_obContentSize.height * m_obAnchorPoint.y );
            m_bTransformDirty = m_bInverseDirty = true;
        }
    }
    
    public boolean isRunning()
    {
        // read only
        return m_bRunning;
    }
       
    public CCNode getParent(){
    	return m_pParent;
    }
    
    public void setParent(CCNode parent){
    	m_pParent = parent;
    }
    
    public boolean getIgnoreAnchorPointForPosition()
    {
        return m_bIgnoreAnchorPointForPosition;     
    }
    
    public void setIgnoreAnchorPointForPosition(boolean value)
    { 
        if (value != m_bIgnoreAnchorPointForPosition)
        {
            m_bIgnoreAnchorPointForPosition = value;
            m_bTransformDirty = m_bInverseDirty = true;
        } 
    }
    
    private boolean m_bCleaned = false;
    
    @Override
	public void update(float dt) {
		
		
	}
    
    public CCScheduler getScheduler()
    {
        return m_pScheduler;
    }
    
    public void setScheduler(CCScheduler value)
    {
        if (value != m_pScheduler)
        {
            unscheduleAllSelectors();
            m_pScheduler = value;
        }
    }
    
    public void scheduleUpdate()
    {
        scheduleUpdateWithPriority(0);
    }

    public void scheduleUpdateWithPriority(int priority)
    {
        m_pScheduler.scheduleUpdateForTarget(this, priority, !m_bRunning);
    }
    
    public void unscheduleUpdate()
    {
        m_pScheduler.unscheduleUpdateForTarget(this);
    }
    
    public void schedule(ISelector selector)
    {
        schedule(selector, 0.0f, CCScheduler.kCCRepeatForever, 0.0f);
    }
    
    public void schedule(ISelector selector, float interval)
    {
        schedule(selector, interval, CCScheduler.kCCRepeatForever, 0.0f);
    }
    
    public void schedule(ISelector selector, float interval, int repeat, float delay)
    {
        //Debug.Assert(selector != null, "Argument must be non-nil");
        //Debug.Assert(interval >= 0, "Argument must be positive");

        m_pScheduler.scheduleSelector(selector, this, interval, repeat, delay, !m_bRunning);
    }
    
    public void resumeSchedulerAndActions()
    {
        m_pScheduler.resumeTarget(this);
        m_pActionManager.resumeTarget(this);
    }
    
    public void pauseSchedulerAndActions()
    {
        m_pScheduler.pauseTarget(this);
        m_pActionManager.pauseTarget(this);
    }
    
    public void scheduleOnce(ISelector selector, float delay)
    {
        schedule(selector, 0.0f, 0, delay);
    }

    public void unschedule(ISelector selector)
    {
        // explicit nil handling
        if (selector == null)
            return;

        m_pScheduler.unscheduleSelector(selector, this);
    }
    
    public void unscheduleAllSelectors()
    {
        m_pScheduler.unscheduleAllForTarget(this);
    }
    
    
    /// This is called from the Visit() method. This is where you DRAW your node. Only
    /// draw stuff from this method call.
    public void draw()
    {
    	// Does nothing in the root node class.
	}
    
    public void visit(){
    	// quick return if not visible. children won't be drawn.
        if (!m_bVisible)
        {
            return;
        }
        
    	CCDrawManager.PushMatrix();
    	transform();
    	
    	int i = 0;

        if ((m_pChildren != null) && (m_pChildren.size > 0))
        {
            //SortAllChildren();

            
            int count = m_pChildren.size;

            // draw children zOrder < 0
            for (; i < count; ++i)
            {
            	CCNode child = m_pChildren.get(i);
                if (child.getVisible() && child.m_nZOrder < 0)
                {
                	child.visit();
                }
                else
                {
                    break;
                }
            }

            // self draw
            draw();
            // draw the children
            for (; i < count; ++i)
            {
            	CCNode child = m_pChildren.get(i);
                // Draw the z >= 0 order children next.
                if (child.getVisible()/* && elements[i].m_nZOrder >= 0*/)
                {
                	child.visit();
            }
        }
        }
        else
        {
            // self draw
            draw();
        }
    	
    	
        CCDrawManager.PopMatrix();
    }

    public void onEnter()
	{
    	// register 'parent' nodes first
        // since events are propagated in reverse order
        if (m_bTouchEnabled)
        {
            registerWithTouchDispatcher();
        }
        
		if (m_pChildren != null && m_pChildren.size > 0)
        {
            
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
                m_pChildren.get(i).onEnter();
            }
        }
		
		resumeSchedulerAndActions();
		
		m_bRunning = true;
	}
	
    public void onEnterTransitionDidFinish()
    {
        if (m_pChildren != null && m_pChildren.size > 0)
        {
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
            	m_pChildren.get(i).onEnterTransitionDidFinish();
            }
        }
    }
    
    public void onExitTransitionDidStart()
    {
        if (m_pChildren != null && m_pChildren.size > 0)
        {
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
            	m_pChildren.get(i).onExitTransitionDidStart();
            }
        }
    }
    
    public void onExit()
    {
    	CCDirector director = CCDirector.sharedDirector();

        if (m_bTouchEnabled)
        {
            director.getTouchDispatcher().removeDelegate(this);
            //unregisterScriptTouchHandler();
        }
        pauseSchedulerAndActions();
    	m_bRunning = false;
    	if (m_pChildren != null && m_pChildren.size > 0)
        {
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
            	m_pChildren.get(i).onExit();
            }
        }
    }
    
    public void stopAllActions()
    {
        m_pActionManager.removeAllActionsFromTarget(this);
    }
    
    public void stopAction(CCAction action)
    {
        m_pActionManager.removeAction(action);
    }
    
    public void stopActionByTag(int tag)
    {
        //Debug.Assert(tag != (int) CCNodeTag.Invalid, "Invalid tag");
        m_pActionManager.removeActionByTag(tag, this);
    }
    
    public CCAction getActionByTag(int tag)
    {
        //Debug.Assert(tag != (int) CCNodeTag.Invalid, "Invalid tag");
        return m_pActionManager.getActionByTag(tag, this);
    }
    
    
	private CCAffineTransform nodeToParentTransform(){
		if (m_bTransformDirty){
//			CCPoint zero = CCPoint.getZero();
//			m_sTransform.setToIdentity();
//			
//			
//			if (m_fRotation != 0)
//				m_sTransform.rotate(-m_fRotation * MathUtils.degreesToRadians);
//			
//			m_bTransformDirty = false;
			
			// Translate values
	        float x = m_obPosition.x;
	        float y = m_obPosition.y;

	        if (m_bIgnoreAnchorPointForPosition) 
	        {
	            x += m_obAnchorPointInPoints.x;
	            y += m_obAnchorPointInPoints.y;
	        }
	     // Rotation values
			// Change rotation code to handle X and Y
			// If we skew with the exact same value for both x and y then we're simply just rotating
	        float cx = 1, sx = 0, cy = 1, sy = 0;
	        if ( (m_fRotationX != 0) || (m_fRotationY != 0) )
	        {
	            float radiansX = -MathUtil.CC_DEGREES_TO_RADIANS(m_fRotationX);
	            float radiansY = -MathUtil.CC_DEGREES_TO_RADIANS(m_fRotationY);
	            cx = MathUtils.cos(radiansX);
	            sx = MathUtils.sin(radiansX);
	            cy = MathUtils.cos(radiansY);
	            sy = MathUtils.sin(radiansY);
	        }

	        boolean needsSkewMatrix = ( (m_fSkewX != 0) || (m_fSkewY != 0) );


	        // optimization:
	        // inline anchor point calculation if skew is not needed
	        // Adjusted transform calculation for rotational skew
	        if (! needsSkewMatrix && !m_obAnchorPointInPoints.equalToPoint(CCPoint.Zero))
	        {
	            x += cy * -m_obAnchorPointInPoints.x * m_fScaleX + -sx * -m_obAnchorPointInPoints.y * m_fScaleY;
	            y += sy * -m_obAnchorPointInPoints.x * m_fScaleX +  cx * -m_obAnchorPointInPoints.y * m_fScaleY;
	        }


	        // Build Transform Matrix
	        // Adjusted transform calculation for rotational skew
	        
//	        m_sTransform = new CCAffineTransform( cy * m_fScaleX,  sy * m_fScaleX,
//	            -sx * m_fScaleY, cx * m_fScaleY,
//	            x, y );
	        m_sTransform.set(cy * m_fScaleX, sy * m_fScaleX, -sx * m_fScaleY, cx * m_fScaleY, x, y);
	        
	        

	        // XXX: Try to inline skew
	        // If skew is needed, apply skew and then anchor point
	        if (needsSkewMatrix) 
	        {
	            CCAffineTransform skewMatrix = new CCAffineTransform(1.0f, MathUtil.tanf(MathUtil.CC_DEGREES_TO_RADIANS(m_fSkewY)),
	            		MathUtil.tanf(MathUtil.CC_DEGREES_TO_RADIANS(m_fSkewX)), 1.0f,
	                0.0f, 0.0f );
	            m_sTransform = CCAffineTransform.multiply(skewMatrix, m_sTransform);

	            // adjust anchor point
	            if (!m_obAnchorPointInPoints.equalToPoint(CCPoint.Zero))
	            {
	                m_sTransform = CCAffineTransform.CCAffineTransformTranslate(m_sTransform, -m_obAnchorPointInPoints.x, -m_obAnchorPointInPoints.y);
	            }
	        }
	        
	        if (m_bAdditionalTransformDirty)
	        {
	            //m_sTransform = CCAffineTransform.multiply(m_sTransform, m_sAdditionalTransform);
	        	m_sTransform.multiply(m_sAdditionalTransform);
	            m_bAdditionalTransformDirty = false;
	        }

	        m_bTransformDirty = false;
			
			
		}
		
		return m_sTransform;
	}
    
	
	public void transform(){
		
		
	    CCAffineTransform tmpAffine = nodeToParentTransform();
	   
	    CCDrawManager.MultMatrix(tmpAffine, 0);
		
		
	}
	
	public CCAffineTransform nodeToWorldTransform()
    {
        CCAffineTransform t = new CCAffineTransform(nodeToParentTransform());

        CCNode p = m_pParent;
        while (p != null)
        {
        	t = t.preConcatenate(p.nodeToParentTransform());
            p = p.getParent();
        }

        return t;
    }
	
	
	public CCAffineTransform worldToNodeTransform()
    {
        return nodeToWorldTransform().getTransformInvert();
    }
	
	/** converts a world coordinate to local coordinate
    @since v0.7.1
  */
	
	/**
     * This is analog method, result is written to ret. No garbage.
     */
    private void worldToNodeTransform(CCAffineTransform ret) {
    	nodeToWorldTransform(ret);
        CCAffineTransformUtil.inverse(ret);
    }
    
    private void nodeToWorldTransform(CCAffineTransform ret) {
        ret.setTransform(nodeToParentTransform());

        for (CCNode p = getParent(); p != null; p = p.getParent()) {
        	CCAffineTransformUtil.preConcate(ret, p.nodeToParentTransform());
        }
    }
    
	public CCPoint convertToNodeSpace(float x, float y)
	{
	   
	      
	    CCAffineTransform temp = new CCAffineTransform();
	    worldToNodeTransform(temp);
	      
	    CCPoint ret = new CCPoint();
	  	CCPointUtil.applyAffineTransform(x, y, temp, ret);
	  	
	      
	    return ret;
	  }
  
  /** converts a world coordinate to local coordinate
    @since v0.7.1
  */
  public CCPoint convertToNodeSpace(CCPoint p) {
  	return convertToNodeSpace(p.x, p.y);
  }
	
    protected void resetCleanState()
    {
       m_bCleaned = false;
       if (m_pChildren != null && m_pChildren.size > 0)
       {
          CCNode[] elements = null;
          elements = m_pChildren.toArray();
          for (int i = 0, count = m_pChildren.size; i < count; i++)
          {
              elements[i].resetCleanState();
          }
       }
    }
	
	
	public void cleanup()
    {
        if (m_bCleaned == true)
        {
            return;
        }
        // actions
        stopAllActions();

        // timers
        unscheduleAllSelectors();

        if (m_pChildren != null && m_pChildren.size > 0)
        {         
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
                m_pChildren.get(i).cleanup();
            }
        }
        m_bCleaned = true;
    }
	
	public CCNode getChildByTag(int tag)
    {
        //Debug.Assert(tag != (int) CCNodeTag.Invalid, "Invalid tag");

        if (m_pChildrenByTag != null && m_pChildrenByTag.size() > 0)
        {
            //Debug.Assert(m_pChildren != null && m_pChildren.count > 0);

            ArrayList<CCNode> list = null;
            if (m_pChildrenByTag.containsKey(tag))
            {
            	list = m_pChildrenByTag.get(tag);
            	if (list.size() > 0)
                {
                    return list.get(0);
                }
            }
        }
        return null;
    }
	
	public void addChild(CCNode child)
    {
        //Debug.Assert(child != null, "Argument must be no-null");
        addChild(child, child.getZOrder(), child.getTag());
    }
	
	public void addChild(CCNode child, int zOrder)
    {
        //Debug.Assert(child != null, "Argument must be no-null");
        addChild(child, zOrder, child.getTag());
    }
	
	public void addChild(CCNode child, int zOrder, int tag)
    {
        //Debug.Assert(child != null, "Argument must be non-null");
        //Debug.Assert(child.m_pParent == null, "child already added. It can't be added again");
        //Debug.Assert(child != this, "Can not add myself to myself.");

        if (m_pChildren == null)
        {
            m_pChildren = new Array<CCNode>();
        }

        insertChild(child, zOrder, tag);

        child.setParent(this);
        child.m_nTag = tag;
        child.m_uOrderOfArrival = s_globalOrderOfArrival++;
        if (child.m_bCleaned)
        {
            child.resetCleanState();
        }

        if (m_bRunning)
        {
            child.onEnter();
            child.onEnterTransitionDidFinish();
        }
    }
	
	public void removeChild(CCNode child)
    {
        removeChild(child, true);
    }

    public void removeChild(CCNode child, boolean cleanup)
    {
        // explicit nil handling
        if (m_pChildren == null || child == null)
        {
            return;
        }

        changedChildTag(child, child.getTag(), kCCNodeTagInvalid);

        if (m_pChildren.contains(child, true))
        {
            detachChild(child, cleanup);
        }
    }

    public void removeChildByTag(int tag)
    {
        removeChildByTag(tag, true);
    }

    public void removeChildByTag(int tag, boolean cleanup)
    {
        //Debug.Assert(tag != (int) CCNodeTag.Invalid, "Invalid tag");

        CCNode child = getChildByTag(tag);

        if (child == null)
        {
            //CCLog.Log("cocos2d: removeChildByTag: child not found!");
        }
        else
        {
            removeChild(child, cleanup);
        }
    }

    public void removeAllChildren()
    {
        removeAllChildrenWithCleanup(true);
    }

    public void removeAllChildrenWithCleanup(boolean cleanup)
    {
        // not using detachChild improves speed here
        if (m_pChildren != null && m_pChildren.size > 0)
        {
            if (m_pChildrenByTag != null)
            {
                m_pChildrenByTag.clear();
            }
            
            
            for (int i = 0, count = m_pChildren.size; i < count; i++)
            {
                CCNode node = m_pChildren.get(i);

                // IMPORTANT:
                //  -1st do onExit
                //  -2nd cleanup
                if (m_bRunning)
                {
                    node.onExitTransitionDidStart();
                    node.onExit();
                }

                if (cleanup)
                {
                    node.cleanup();
                }

                // set parent nil at the end
                node.setParent(null);
            }

            m_pChildren.clear();
        }
    }

    private void detachChild(CCNode child, boolean doCleanup)
    {
        // IMPORTANT:
        //  -1st do onExit
        //  -2nd cleanup
        if (m_bRunning)
        {
            child.onExitTransitionDidStart();
            child.onExit();
        }

        // If you don't do cleanup, the child's actions will not get removed and the
        // its scheduledSelectors_ dict will not get released!
        if (doCleanup)
        {
            child.cleanup();
        }

        // set parent nil at the end
        child.setParent(null);

        m_pChildren.removeValue(child, true);
    }

	
	public void registerWithTouchDispatcher()
    {
        CCTouchDispatcher pDispatcher = CCDirector.sharedDirector().getTouchDispatcher();
        
        if (m_eTouchMode == CCTouchMode.AllAtOnce)
        {
            pDispatcher.addStandardDelegate(this, 0);
        }
        else
        {
            pDispatcher.addTargetedDelegate(this, m_nTouchPriority, true);
        }
    }
	
	
	public boolean getTouchEnabled()
    {
        return m_bTouchEnabled; 
    }
	
	public void setTouchEnabled(boolean value)
    {
        if (m_bTouchEnabled != value)
        {
            m_bTouchEnabled = value;

            if (m_bRunning)
            {
                if (value)
                {
                    registerWithTouchDispatcher();
                }
                else
                {
                    CCDirector.sharedDirector().getTouchDispatcher().removeDelegate(this);
                }
            }
        }
    }
	
	
	private void changedChildTag(CCNode child, int oldTag, int newTag)
    {
        ArrayList<CCNode> list = null;

        if (m_pChildrenByTag != null && oldTag != kCCNodeTagInvalid)
        {
        	if (m_pChildrenByTag.containsKey(oldTag))
        	{
        		list = m_pChildrenByTag.get(oldTag);
        		list.remove(child);
        	}
        }

        if (newTag != kCCNodeTagInvalid)
        {
            if (m_pChildrenByTag == null)
            {
                m_pChildrenByTag = new Hashtable<Integer, ArrayList<CCNode>>();
            }
            if (!m_pChildrenByTag.containsKey(newTag))
            {
            	list = new ArrayList<CCNode>();
            	m_pChildrenByTag.put(newTag, list);
            }
            else
            {
            	list = m_pChildrenByTag.get(newTag);
			}
            list.add(child);
        }
    }
	
	private void insertChild(CCNode child, int z, int tag)
    {
        m_bReorderChildDirty = true;
        m_pChildren.add(child);

        changedChildTag(child, kCCNodeTagInvalid, tag);
        
        //KK: 可能有问题的语句
        child.m_nZOrder = z;
    }

	public void reorderChild(CCNode child, int zOrder)
    {
        //Debug.Assert(child != null, "Child must be non-null");

        m_bReorderChildDirty = true;
        child.m_uOrderOfArrival = s_globalOrderOfArrival++;
        child.m_nZOrder = zOrder;
    }
	
	@Override
	public Boolean touchBegan(CCTouch pTouch)
	{
		return true;
	}

	@Override
	public void touchMoved(CCTouch pTouch)
	{
		
	}

	@Override
	public void touchEnded(CCTouch pTouch)
	{
		
	}

	@Override
	public void touchCancelled(CCTouch pTouch)
	{
		
	}

	@Override
	public void touchesBegan(Array<CCTouch> pTouches)
	{
		
	}

	@Override
	public void touchesMoved(Array<CCTouch> pTouches)
	{
		
	}

	@Override
	public void touchesEnded(Array<CCTouch> pTouches)
	{
		
	}

	@Override
	public void touchesCancelled(Array<CCTouch> pTouches)
	{
		
	}

	
	//-------------------Action-------------------
	
	public CCAction runAction(CCAction action)
    {
        //Debug.Assert(action != null, "Argument must be non-nil");
        m_pActionManager.addAction(action, this, !m_bRunning);
        return action;
    }
	
	
	 public CCTouchMode getTouchMode()
     {
         return m_eTouchMode;
     }
	 
	 public void setTouchMode(CCTouchMode value)
     {    
         if (m_eTouchMode != value)
         {
             m_eTouchMode = value;

             if (m_bTouchEnabled)
             {
                 setTouchEnabled(false);
                 setTouchEnabled(true);
             }
         }  
     }

	 public int getTouchPriority()
     {
         return m_nTouchPriority;
     }
	 
	 public void setTouchPriority(int value)
     {  
         if (m_nTouchPriority != value)
         {
             m_nTouchPriority = value;

             if (m_bRunning)
             {
                 setTouchEnabled(false);
                 setTouchEnabled(true);
             }
         }     
     }
	 
	@Override
	public boolean getCanReceiveFocus()
	{
		return (this.getVisible());
	}

	@Override
	public boolean getHasFocus()
	{
		return (m_bHasFocus);
	}

	@Override
	public void setHasFocus(boolean value)
	{
		m_bHasFocus = value;	
	}
}
