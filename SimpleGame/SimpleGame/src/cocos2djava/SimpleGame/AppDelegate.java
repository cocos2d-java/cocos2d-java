package cocos2djava.SimpleGame;

import com.badlogic.gdx.graphics.Color;

import cocos2d.CCApplication;
import cocos2d.CCDirector;
import cocos2d.layers_scenes_transitions_nodes.CCScene;

public class AppDelegate extends CCApplication {
	public AppDelegate(){
		super();
	}
	
	@Override
	public boolean applicationDidFinishLaunching() {
		//initialize director
        CCDirector pDirector = CCDirector.sharedDirector();
        pDirector.setGlClearColor(Color.WHITE);
        pDirector.setOpenGlView();
        
        CCScene scene = HelloWorldScene.scene();
        
        
        
        pDirector.runWithScene(scene);
        
        
		
		
		
		
		
		return true;
	}

	@Override	
	public void applicationDidEnterBackground() {
		
	}

	@Override
	public void applicationWillEnterForeground() {
		
	}

	@Override
	public void setAnimationInterval(double interval) {
		
	}
	
}
