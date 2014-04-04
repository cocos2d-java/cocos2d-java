package cocos2djava.SimpleGame;

import cocos2d.Cocos2d;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SimpleGame";
		cfg.useGL20 = true;
		cfg.width = 852;
		cfg.height = 480;
		
		new AppDelegate();
		new LwjglApplication(Cocos2d.Game(), cfg);
	}
}
