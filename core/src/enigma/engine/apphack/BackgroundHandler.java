package enigma.engine.apphack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BackgroundHandler {
	private Sprite bgSprite;
	private Image tempBg;

	public BackgroundHandler(Texture text) {
		bgSprite = new Sprite(text);
		tempBg = new Image(text);

	}

	public void render(SpriteBatch batch, float charX, float charY) {
		bgSprite.draw(batch);

		TextureHolder.grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		batch.draw(TextureHolder.grass, 0, 0, 0, 0, 10 * TextureHolder.grass.getWidth(), 10 * TextureHolder.grass.getHeight());

	}
}
