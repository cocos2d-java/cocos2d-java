package cocos2d;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cocos2d.sprite_nodes.CCSprite;

public class kktest {
	public static CCSprite sprite;
	public static SpriteBatch spriteBatch;
	public kktest(){
		//sprite = new CCSprite(new Texture("data/libgdx.png"));
		sprite = new CCSprite();
		//sprite.InitWithFile("data/libgdx.png");
		sprite.initWithFile("data/rules.png");
		spriteBatch = new SpriteBatch();
	}
	
	
	public static Pixmap pixmap;
	
}
