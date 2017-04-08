package enigma.engine.apphack;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHolder {

	public static ArrayList<Texture> allTextures = new ArrayList<Texture>();
	public static Texture character;
	public static Texture enemy;
	public static TextureRegion[][] characterSpriteSheet;
	public static TextureRegion[][] enemySpriteSheet;
	public static Texture grass;
	public static Texture pointLight;
	public static Texture longLight;
	public static Texture home;
	public static Texture win;
	public static Texture lose;
	public static Texture blackbg;
	public static Texture bkdone;
	public static Texture instructions;

	public static void initTextures() {
		character = new Texture("Man16x16packed.png");
		enemy = new Texture("clown16x16packed.png");
		allTextures.add(character);
		allTextures.add(enemy);
		characterSpriteSheet = TextureRegion.split(character, 16, 16);
		enemySpriteSheet = TextureRegion.split(enemy, 16, 16);

		grass = new Texture("Grass2.png");
		allTextures.add(grass);

		// lights
		pointLight = new Texture("light.png");
		allTextures.add(pointLight);

		longLight = new Texture("StretchedLight.png");
		allTextures.add(longLight);

		home = new Texture("homedone.png");
		allTextures.add(home);
		
		bkdone = new Texture("BKdone.png");
		allTextures.add(bkdone);

		win = new Texture("youwin.png");
		allTextures.add(win);

		lose = new Texture("youlose2.png");
		allTextures.add(lose);

		blackbg = new Texture("blackbg.png");
		allTextures.add(blackbg);
		
		instructions = new Texture("instruction.png");
		allTextures.add(instructions);

	}

	public static void dispose() {
		for (Texture currentTexture : allTextures) {
			currentTexture.dispose();
		}
	}
}
