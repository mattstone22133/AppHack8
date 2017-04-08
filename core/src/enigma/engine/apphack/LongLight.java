package enigma.engine.apphack;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LongLight {
	public static ArrayList<LongLight> allLights = new ArrayList<LongLight>();
	public Sprite sprite;
	private boolean active = true;
	public float scaleFactor = 1.0f;

	public LongLight() {
		LongLight.allLights.add(this);
		sprite = new Sprite(TextureHolder.longLight);

	}

	public static void render(SpriteBatch batch) {
		for (LongLight curLight : allLights) {
			if (curLight.active) {
				curLight.sprite.draw(batch);
			}
		}
	}

	public void toggle() {
		this.active = !this.active;
	}

	private Point2D.Float oriPoint = new Point2D.Float(0, 0);

	public void setScale(float scale) {
		this.scaleFactor = scale;
		this.oriPoint.setLocation(sprite.getX(), sprite.getY());
		this.sprite.setScale(scale);

		sprite.setPosition(0, 0);
		sprite.setOrigin(0, sprite.getHeight() / 2);
		sprite.setPosition(oriPoint.x, oriPoint.y);

		updateOrigin();

	}

	public void updateSpritePositionHacked(float x, float y) {
		sprite.setPosition(x, y - sprite.getHeight() / 2);
	}

	public void updateOrigin() {
		float rotation = sprite.getRotation();
		sprite.setRotation(0);
		// sprite.setOrigin(0, -(sprite.getHeight() * scaleFactor) / 2);
		// sprite.setOrigin(0, -(sprite.getHeight() * scaleFactor) / 2);

		sprite.setRotation(rotation);
	}

	public void setPosition(float x, float y) {
		updateSpritePositionHacked(x, y);
	}

	public void rotateBy(float amount) {
		sprite.rotate(amount);
	}

	public void setAngle(float angleDegrees) {
		sprite.setRotation(angleDegrees);

	}

	public boolean collision(Actor player, Actor enemy) {
		float threshold = 15f;
		float maxDistance = sprite.getWidth() * scaleFactor * 0.75f;

		float angleFlash = sprite.getRotation(); // degrees

		// DISTANCE CHECK

		// calculate the angle between the player and the enemy
		float deltaX = (enemy.getX()) - player.getX();
		float deltaY = (enemy.getY()) - player.getY();
		float hypo = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		if(hypo > maxDistance){
			return false;
		}

		// angle between player and enemy (radians)
		float anglePE = (float) Math.atan(deltaY / deltaX);
		anglePE = (float) (anglePE * 180 / Math.PI);
		if (deltaX < 0) {
			anglePE += 180f;
		}

		// create an angle of the enemy being rotated by enough to rotate the box to horrizontal
		float angleRotPE = anglePE - angleFlash;

		if (Math.abs(angleRotPE) < threshold) {
			return true;
		} else {
			return false;
		}

		// float hypo = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY));
		//
		// // below will require correcting for certain quadrants
		// float eNewX = hypo * (float) (Math.cos(angleRotPE * Math.PI / 180));
		// float eNewY = hypo * (float) (Math.sin(angleRotPE * Math.PI / 180));
		//
		// eNewX += sprite.getX();
		// eNewY += sprite.getY();
		//
		// // simple check for now
		// float blBoxX = sprite.getX();
		// float blBoxY = sprite.getY() + (sprite.getHeight() * scaleFactor /2);
		//
		// // System.out.printf("enemy x:%2.2f y:%2.2f; blBoxX:%2.2f, blBoxY:%2.2f\n", enemy.getX(),
		// // enemy.getY(), blBoxX, blBoxY);
		// System.out.printf("x:%2.2f y:%2.2f; x:%2.2f, y:%2.2f\n", eNewX, eNewY, blBoxX, blBoxY);
		// return pointInBox(eNewX, eNewY, blBoxX, blBoxY);
	}

	public void setOnOff(boolean onOff) {
		active = onOff;
	}

	public boolean getOnOff() {
		return active;
	}

//	private boolean pointInBox(float x, float y, float blBoxX, float blBoxY) {
//
//		// check if X in range
//		if (x > blBoxX && x < blBoxX + sprite.getWidth() * scaleFactor) {
//			if (y > blBoxY && y < blBoxY + sprite.getHeight() * scaleFactor) {
//				return true;
//			}
//		}
//
//		return false;
//	}

}
