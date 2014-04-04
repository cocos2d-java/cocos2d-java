package cocos2djava.SimpleGame;

import android.os.Bundle;
import cocos2d.Cocos2d;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        new AppDelegate();
        initialize(Cocos2d.Game(), cfg);
    }
}