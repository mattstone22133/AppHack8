package enigma.engine.apphack;

import java.awt.geom.Point2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Actor {
	protected Sprite sprite;
	protected TextureRegion[][] spriteSheet;
	protected float moveSpeed = 10.0f;

	protected float lastUpdatePos;
	protected int textureRegionRow = 0;
	protected int textureRegionCol = 0;
	protected static final int MAXDISTFROMLASTPOS = 40;
	protected float hitPoints = 100;
	protected float damageCooldown = 0;
	protected boolean isDead = false;
	

	protected enum direction {
		UP, DOWN, LEFT, RIGHT
	};

	protected direction actorFacingDirection = direction.DOWN;

	protected float scale = 1.0f;


	public Actor(TextureRegion[][] spriteSheetArg) {
		this.spriteSheet = spriteSheetArg;
		sprite = new Sprite(spriteSheet[0][0]);
		lastUpdatePos = sprite.getX() + sprite.getY();
	}

	private Point2D.Float oriPosition = new Point2D.Float(0, 0);

	public void setScale(float scale) {
		this.scale = scale;
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
		return sprite.getX();
	}

	public float getY() {
		return sprite.getY();
	}

	public void moveUp() {
		sprite.translateY(moveSpeed);
		updateSpriteImage(direction.UP);
	}

	public void moveDown() {
		sprite.translateY(-moveSpeed);
		updateSpriteImage(direction.DOWN);
	}

	public void moveLeft() {
		sprite.translateX(-moveSpeed);
		updateSpriteImage(direction.LEFT);
	}

	public void moveRight() {
		sprite.translateX(moveSpeed);
		updateSpriteImage(direction.RIGHT);
	}
	
	public void alterHP(float deltaHP) {
		hitPoints += deltaHP;
	}
	
	public float getHp() {
		return hitPoints;
	}
	
	public void die() {
		if (!isDead) {
			System.out.println("DEAD!!!");
			SoundHolder.humanDead.play(.1f);
			SoundHolder.mcDLaugh.play(.5f);
			isDead = true;
		}		
	}

	public void updateSpriteImage(direction dir) {
		if (actorFacingDirection == dir) {
			if (Math.abs((sprite.getX() + sprite.getY()) - lastUpdatePos) > MAXDISTFROMLASTPOS) {
				lastUpdatePos = sprite.getX() + sprite.getY();
				
				if (textureRegionRow % 2 == 0) {
					textureRegionRow = 1;
					playRFootSound();
				}
				else {
					textureRegionRow = 0;
					playLFootSound();
				}
				
			}
		} else {
			actorFacingDirection = dir;
			if (dir == direction.UP) {
				textureRegionCol = 2;
			} else if (dir == direction.DOWN) {
				textureRegionCol = 0;
			} else if (dir == direction.LEFT) {
				textureRegionCol = 1;
			} else if (dir == direction.RIGHT) {
				textureRegionCol = 3;
			}
		}
		
		sprite.setRegion(spriteSheet[textureRegionRow][textureRegionCol]);
		
	}
	
	public void playRFootSound() {
		SoundHolder.rightFootStepHum.play(.03f);
	}
	
	public void playLFootSound() {
		SoundHolder.leftFootStepHum.play(.03f);
	}

	public void setPosition(float x, float y) {
		sprite.setX(x);
		sprite.setY(y);		
	}

	public float getWidth() {
		return sprite.getWidth();
	}

	public float getHeight() {
		return sprite.getHeight();
	}
}
