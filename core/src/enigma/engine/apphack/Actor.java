package enigma.engine.apphack;

import java.awt.geom.Point2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Actor {
	private Sprite sprite;
	private TextureRegion[][] spriteSheet;
	private float moveSpeed = 10.0f;

	public Actor(TextureRegion[][] spriteSheetArg) {
		this.spriteSheet = spriteSheetArg;
		sprite = new Sprite(spriteSheet[0][0]);
	}

	private Point2D.Float oriPosition = new Point2D.Float(0, 0);

	public void setScale(float scale) {
		this.sprite.setScale(scale);
		oriPosition.setLocation(sprite.getX(), sprite.getY());

		// do unit magic at 0, 0
		sprite.setPosition(0, 0);
		sprite.setOrigin(sprite.getX(), sprite.getY());
		sprite.setPosition(oriPosition.x, oriPosition.y);

	}

	public void render(SpriteBatch batch) {
		if (sprite != null) {
			sprite.draw(batch);
		}
	}

	public float getX() {
		// TODO make this centered.
		return sprite.getX();
	}

	public float getY() {
		return sprite.getY();
	}

	public void moveUp() {
		sprite.translateY(moveSpeed);
	}

	public void moveDown() {
		sprite.translateY(-moveSpeed);
	}

	public void moveLeft() {
		sprite.translateX(-moveSpeed);
	}

	public void moveRight() {
		sprite.translateX(moveSpeed);
	}
}
