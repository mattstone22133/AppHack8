package enigma.engine.apphack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Player extends Actor {
	private LongLight flashLight;
	private FlashLightLogic flLogic;

	public Player(TextureRegion[][] spriteSheetArg) {
		super(spriteSheetArg);
		flashLight = new LongLight();
		flashLight.setScale(0.7f);
		flLogic = new FlashLightLogic();

	}

	public void logic(Actor enemy) {
		updateFlashLight();
		if (Game.dev) {
			if (Gdx.input.isKeyPressed(Input.Keys.I)) {
				flashLight.rotateBy(5f);
			} else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
				flashLight.rotateBy(-5f);
				
			}
		}

		checkCollision(enemy);
		
		
		if (getDistanceFromEnemy(enemy) < 60 && damageCooldown <= 0 && !isDead) {
			hitPoints -= 34;
			damageCooldown = 26;
			SoundHolder.humanPain.play(.2f);
		}
		
		if (hitPoints <= 0 && !isDead) {
			die();
		}
		damageCooldown--;
	}
	
	
	public float getDistanceFromEnemy(Actor enemy) {
		float dist = ((sprite.getX() - enemy.getX()) * (sprite.getX() - enemy.getX())) + ((sprite.getY() - enemy.getY()) * (sprite.getY() - enemy.getY()));
		return (float) Math.sqrt(dist);
	}

	private void checkCollision(Actor enemy) {
		if(flashLight.getOnOff() && flashLight.collision(this, enemy)){
			// System.out.println("collision detected on enemy" + enemy.toString());
			if(enemy instanceof Enemy){
				Enemy enemyCast = (Enemy) enemy;
				enemyCast.runAway();
			}
		} else {
		}
	}

	public Vector3 convVector = new Vector3();

	private void updateFlashLight() {
		flashLight.setPosition(sprite.getX() + this.scale * sprite.getWidth() / 2, sprite.getY() + this.scale * sprite.getHeight() / 2);
		convVector.x = Gdx.input.getX();
		convVector.y = Gdx.input.getY();
		Game.camera.unproject(convVector);

		// flashLight.setPosition(convVector.x, convVector.y); //temp

		float deltaX = convVector.x - (sprite.getX() + sprite.getWidth() / 2 * scale);
		float deltaY = convVector.y - (sprite.getY() + sprite.getHeight() / 2 * scale);

		float angle = (float) Math.atan(deltaY / deltaX);
		angle = (float)(angle * 180 / Math.PI);
		if(deltaX < 0){
			//angle = 90 - angle;
			angle += 180f;
		}
		
		flashLight.setAngle(angle);
		flashLight.setOnOff(flLogic.logic());

		

	}

	@Override
	public void moveUp() {
		super.moveUp();
		updateFlashLight();
	}

	@Override
	public void moveDown() {
		super.moveDown();
		updateFlashLight();
	}

	@Override
	public void moveLeft() {
		super.moveLeft();
		updateFlashLight();
	}

	@Override
	public void moveRight() {
		super.moveRight();
		updateFlashLight();
	}

	public float getCenterX() {
		return sprite.getX() + sprite.getWidth() * scale /2;
	}

	public float getCenterY() {
		return sprite.getY() + sprite.getHeight() * scale /2;

	}
	
	public void toggleFlashLight(){
		flLogic.toggle();
		
	}


}
