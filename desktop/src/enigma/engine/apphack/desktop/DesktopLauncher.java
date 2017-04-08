package enigma.engine.apphack.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import enigma.engine.apphack.Game;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//set up a screen with a multiple of 1080x1920
		config.height = (int)(1080 * 0.75f);
		config.width = (int)(1920 * 0.75f);

		new LwjglApplication(new Game(), config);
	}
}
