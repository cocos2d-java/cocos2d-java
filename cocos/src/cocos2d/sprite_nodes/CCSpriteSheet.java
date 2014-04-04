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

package cocos2d.sprite_nodes;

import java.util.LinkedHashMap;

import cocos2d.textures.CCTexture2D;

public class CCSpriteSheet
{
    private final LinkedHashMap<String, CCSpriteFrame> _spriteFrames = new LinkedHashMap<String, CCSpriteFrame>();
    private final LinkedHashMap<String, String> _spriteFramesAliases = new LinkedHashMap<String, String>();

	private PlistType plistType;

	// We need to read the sprite sheet textures relative to the plist file path.
	// When we have the sprite sheet split between multiple image files 
	// to be loaded this allows us to load those files relative to the plist.  Right now
	// only used for PlistType SpriteKit right now but can be used for other types as well
	// in the future
	private String plistFilePath;

	private enum PlistType
	{
		Cocos2D,
		SpriteKit
	}


//    public CCSpriteSheet(LinkedHashMap<String, CCSpriteFrame> frames)
//    {
//        if (frames != null)
//        {
//            _spriteFrames = new LinkedHashMap<String, CCSpriteFrame>(frames);
//            AutoCreateAliasList();
//        }
//    }
//
//    public CCSpriteSheet(String fileName)
//    {
//        initWithFile(fileName);
//    }
//
//    public CCSpriteSheet(String fileName, String textureFileName)
//    {
//        initWithFile(fileName, textureFileName);
//    }
//
//    public CCSpriteSheet(String fileName, CCTexture2D texture)
//    {
//        initWithFile(fileName, texture);
//    }

//    public CCSpriteSheet(Stream stream, CCTexture2D texture)
//    {
//        InitWithStream(stream, texture);
//    }
//
//    public CCSpriteSheet(Stream stream, string texture)
//    {
//        InitWithStream(stream, texture);
//    }

//    public CCSpriteSheet(PlistDictionary dictionary, CCTexture2D texture)
//    {
//        InitWithDictionary(dictionary, texture);
//    }

    private void autoCreateAliasList()
    {
        for (String key : _spriteFrames.keySet())
        {
            int idx = key.lastIndexOf('.');
            if (idx > -1)
            {
            	String alias = key.substring(0, idx);
                //_spriteFramesAliases[alias] = key;
            	_spriteFramesAliases.put(alias, key);
                //CCLog.Log("Created alias for frame {0} as {1}", key, alias);
            }
        }
    }

//    private PlistType GetPlistType(PlistDictionary dict)
//	{
//		var isSpriteKit = dict.ContainsKey ("format") ? dict ["format"].AsString == "APPL" : false;
//
//		return isSpriteKit ? PlistType.SpriteKit : PlistType.Cocos2D;
//
//	}

//    public void InitWithFile(string fileName)
//    {
//        PlistDocument document = CCContentManager.SharedContentManager.Load<PlistDocument>(fileName);
//
//        var dict = document.Root.AsDictionary;
//        var texturePath = string.Empty;
//		plistFilePath = string.Empty;
//
//		plistType = GetPlistType (dict);
//
//		if (plistType == PlistType.SpriteKit) 
//		{
//			var images = dict.ContainsKey ("images") ? dict ["images"].AsArray : null;
//
//			var imageDict = images [0].AsDictionary;
//
//			if (imageDict != null) {
//				// try to read  texture file name from meta data
//				if (imageDict.ContainsKey ("path")) {
//					texturePath = imageDict ["path"].AsString;
//				}
//			}
//		} 
//		else 
//		{
//			var metadataDict = dict.ContainsKey ("metadata") ? dict ["metadata"].AsDictionary : null;
//
//			if (metadataDict != null) {
//				// try to read  texture file name from meta data
//				if (metadataDict.ContainsKey ("textureFileName")) {
//					texturePath = metadataDict ["textureFileName"].AsString;
//				}
//			}
//		}
//
//        if (!string.IsNullOrEmpty(texturePath))
//        {
//            // build texture path relative to plist file
//            texturePath = CCFileUtils.FullPathFromRelativeFile(texturePath, fileName);
//        }
//        else
//        {
//            // build texture path by replacing file extension
//            texturePath = fileName;
//
//            // remove .xxx
//            texturePath = CCFileUtils.RemoveExtension(texturePath);
//
//            CCLog.Log("cocos2d: CCSpriteFrameCache: Trying to use file {0} as texture", texturePath);
//        }
//
//		plistFilePath = Path.GetDirectoryName (texturePath);
//
//        CCTexture2D pTexture = CCTextureCache.SharedTextureCache.AddImage(texturePath);
//
//        if (pTexture != null)
//        {
//            InitWithDictionary(dict, pTexture);
//        }
//        else
//        {
//            CCLog.Log("CCSpriteSheet: Couldn't load texture");
//        }
//    }

   
}
