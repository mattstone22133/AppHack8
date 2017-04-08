package enigma.engine.apphack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Home {
	public Sprite house;
	public PointLight HouseLight;
	private boolean disappear = false;

	public Home(float x, float y, boolean start) {
		if (start) {
			house = new Sprite(TextureHolder.bkdone);
		} else {
			house = new Sprite(TextureHolder.home);
		}
		house.setX(x);
		house.setY(y);
		HouseLight = new PointLight();
		HouseLight.setScale(5f);
		HouseLight.setXY(house.getX() + house.getWidth() / 2, house.getY());
	}

	public void render(SpriteBatch batch) {
		if (!disappear) {
			house.draw(batch);
		}
	}

	public boolean contains(Actor actor) {
		// NOT SCALED
		float acOfSet = actor.getScaledHeight();
		if (!disappear) {
			if (actor.getX() + acOfSet >= house.getX() && actor.getX() <= house.getX() + house.getWidth()) {
				if (actor.getY() >= house.getY() && actor.getY() <= house.getY() + house.getHeight()) {
					return true;
				}
			}
		}

		return false;
	}

	public void logic(GameTimer gt) {
		if (gt.hasStarted() && !disappear) {
			disappear = true;
			HouseLight.toggle();
		}
	}

	public void setPosition(int i, int j) {
		HouseLight.setXY(0, 0);
		house.setPosition(0, 0);
	}

}
