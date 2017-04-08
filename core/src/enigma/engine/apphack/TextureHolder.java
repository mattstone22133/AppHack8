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

	public static void initTextures() {
		character = new Texture("Man16x16packed.png");
		enemy = new Texture("clown16x16packed.png");
		allTextures.add(character);
		allTextures.add(enemy);
		characterSpriteSheet = TextureRegion.split(character, 16, 16);
		enemySpriteSheet = TextureRegion.split(enemy, 16, 16);

		grass = new Texture("Grass2.png");
		allTextures.add(grass);

		//lights
		pointLight = new Texture("light.png");
		allTextures.add(pointLight);
		
		longLight = new Texture("StretchedLight.png");
		allTextures.add(longLight);

	}

	public static void dispose() {
		for (Texture currentTexture : allTextures) {
			currentTexture.dispose();
		}
	}
}
