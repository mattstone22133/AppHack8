package enigma.engine.apphack;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Actor {
	private LongLight flashLight;
	
	public Player(TextureRegion[][] spriteSheetArg) {
		super(spriteSheetArg);
		flashLight = new LongLight();
		flashLight.setScale(0.5f);
		

	}

	public void logic() {
		
	}
	
	private void updateFlashLight() {
		//flashLight.sprite.setPosition(sprite.getX(), sprite.getY());
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

}
