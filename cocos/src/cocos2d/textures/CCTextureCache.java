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

package cocos2d.textures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import cocos2d.platform.IDisposable;
import cocos2d.predefine.ICCSelectorProtocol;

public class CCTextureCache implements IDisposable, ICCSelectorProtocol
{
//    struct AsyncStruct
//    {
//        public string  FileName;
//        public Action<CCTexture2D> Action;
//    };

//    private List<AsyncStruct> _asyncLoadedImages = new List<AsyncStruct>();
//    private Action _processingAction;
//    private object _task;
//
    private static CCTextureCache s_sharedTextureCache;
//
    private final Object m_pDictLock = new Object();
    protected HashMap<String, CCTexture2D> m_pTextures = new HashMap<String, CCTexture2D>();

    public void update(float dt)
    {
    }

    public static CCTextureCache sharedTextureCache()
    {
	     if (s_sharedTextureCache == null)
	        {
	            s_sharedTextureCache = new CCTextureCache();
	        }
	     return (s_sharedTextureCache);
    }

    public static void purgeSharedTextureCache()
    {
        if (s_sharedTextureCache != null)
        {
            s_sharedTextureCache.dispose();
            s_sharedTextureCache = null;
        }
    }

    public void unloadContent()
    {
        m_pTextures.clear();
    }

    public boolean contains(String assetFile)
    {
        return m_pTextures.containsKey(assetFile);
    }

    public CCTexture2D addImage(String fileimage)
	{
		//Debug.Assert (!String.IsNullOrEmpty (fileimage), "TextureCache: fileimage MUST not be NULL");

		CCTexture2D texture = null;

		String assetName = fileimage;
		synchronized (m_pDictLock) 
		{
			//m_pTextures.TryGetValue (assetName, out texture);
			if (m_pTextures.containsKey(assetName))
			{
				texture = m_pTextures.get(assetName);
			}
		}
		if (texture == null) {
			texture = new CCTexture2D();

			if (texture.initWithFile (fileimage)) 
			{
				synchronized (m_pDictLock) 
				{
					m_pTextures.put(assetName, texture);
				}
			} else 
			{
				return null;
			}
		}
            
		return texture;
	}
    
    public void removeAllTextures()
    {
        m_pTextures.clear();
    }

    public void removeTexture(CCTexture2D texture)
    {
        if (texture == null)
        {
            return;
        }

        String key = null;

//        foreach (var pair in m_pTextures)
//        {
//            if (pair.Value == texture)
//            {
//                key = pair.Key;
//                break;
//            }
//        }
        Iterator<Entry<String, CCTexture2D>> iter = m_pTextures.entrySet().iterator();
        while (iter.hasNext()) 
      	{
          Entry<String, CCTexture2D> entry = (Entry<String, CCTexture2D>) iter.next();
          if (entry.getValue() == texture)
          {
        	  key = entry.getKey();
        	  break;
          }
        
      	}
        if (key != null)
        {
            m_pTextures.remove(key);
        }
    }

    public void removeTextureForKey(String textureKeyName)
    {
        //if (String.IsNullOrEmpty(textureKeyName))
    	if (textureKeyName == null || textureKeyName.equals(""))
        {
            return;
        }
    	
        m_pTextures.remove(textureKeyName);
    }

    public void dispose()
    {
        if (m_pTextures != null)
        {
            for (CCTexture2D t : m_pTextures.values())
            {
                t.dispose();
            }
        }
    }
}
