package enigma.engine.apphack;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LongLight {
	public static ArrayList<LongLight> allLights = new ArrayList<LongLight>();
	public Sprite sprite;
	private boolean active = true;

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

	public void toggle(){
		this.active = !this.active;
	}

	private Point2D.Float oriPoint = new Point2D.Float(0, 0);
	public void setScale(float scale) {
		this.oriPoint.setLocation(sprite.getX(), sprite.getY());
		this.sprite.setScale(scale);
		
		
		sprite.setPosition(0, 0);
		sprite.setOrigin(0, 0);
		sprite.setPosition(oriPoint.x, oriPoint.y);
		
		
	}
}
