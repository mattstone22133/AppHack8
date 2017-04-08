package enigma.engine.apphack;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureHolder {

	public static ArrayList<Texture> allTextures = new ArrayList<Texture>();
	public static Texture character;
	public static TextureRegion[][] characterSpriteSheet;
	public static Texture grass;

	public static void initTextures() {
		character = new Texture("Zelda16x16packed.png");
		allTextures.add(character);
		characterSpriteSheet = TextureRegion.split(character, 16, 16);

		grass = new Texture("Grass2.png");
		allTextures.add(grass);

	}

	public static void dispose() {
		for (Texture currentTexture : allTextures) {
			currentTexture.dispose();
		}
	}
}