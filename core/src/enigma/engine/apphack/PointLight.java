package enigma.engine.apphack;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PointLight {
	public static ArrayList<PointLight> allLights = new ArrayList<PointLight>();
	protected Sprite sprite;
	protected float scale = 1.0f;
	protected boolean toggle = true;

	public PointLight() {
		sprite = new Sprite(TextureHolder.pointLight);
		allLights.add(this);
	}

	public static void render(SpriteBatch batch) {
		for (PointLight light : allLights) {
			if (light.toggle) {
				light.sprite.draw(batch);
			}
		}

	}

	public void setScale(float newScale) {
		scale = newScale;
		float oriX = sprite.getX();
		float oriY = sprite.getY();

		sprite.setScale(scale);

		// do unit magic at 0, 0
		sprite.setPosition(0, 0);
		sprite.setOrigin(sprite.getX(), sprite.getY());
		sprite.setPosition(oriX, oriY);
	}

	public void setXY(float x, float y) {
		sprite.setX(x - sprite.getWidth() / 2 * scale);
		sprite.setY(y - sprite.getHeight() / 2 * scale);
	}

	public void toggle() {
		toggle = !toggle;
	}

}
