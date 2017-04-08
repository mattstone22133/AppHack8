package enigma.engine.apphack;

import java.awt.geom.Point2D;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends Actor 
{
	protected Point2D.Float playerPos;
	protected int ttatimer = 0; //time 'till attack
	protected int timeUntilNextMotionChange = 0;
	protected Random rand = new Random();
	//protected Point2D.Float randomPosition = new Point2D.Float(0, 0);
	protected enum states {ATTACKING, WAITING, FLEEING};
	protected states currentState = states.ATTACKING;
	

	public Enemy(TextureRegion[][] spriteSheetArg) {
		super(spriteSheetArg);
		moveSpeed = 9f;
		playerPos = new Point2D.Float(0, 0);
		
		
	}
	
	public void logic(Player player) {
		playerPos.setLocation(player.getX(), player.getY());
		float distx = playerPos.x - sprite.getX();
		float disty = playerPos.y - sprite.getY();
		
			
		if (currentState == states.ATTACKING) {
			
			if (Math.abs(Math.abs(distx) + Math.abs(disty)) > 1000) {
				moveToRandomLocation(player);
				ttatimer = 50; //shorter time if player is running away
			}
			
			if (timeUntilNextMotionChange <= 0) {
				if (Math.abs(distx) > Math.abs(disty)) {
					if (distx >= 0) {
						moveRight();
					}
					else {
						moveLeft();
					}
				}
				else {
					if (disty >= 0) {
						moveUp();
					}
					else {
						moveDown();
					}
				}
				timeUntilNextMotionChange = 10;
			}
			else {
				if (actorFacingDirection == direction.UP) {
					moveUp();
				}
				else if (actorFacingDirection == direction.DOWN) {
					moveDown();
				}
				else if (actorFacingDirection == direction.LEFT) {
					moveLeft();
				}
				else if (actorFacingDirection == direction.RIGHT) {
					moveRight();
				}
				timeUntilNextMotionChange--;				
			}
		}
		else if (currentState == states.WAITING) {
			if (ttatimer <= 0) {
				currentState = states.ATTACKING;
			} else {
				ttatimer--;
			}
		}
		else if (currentState == states.FLEEING) {
			
			//same code as attacking but runs away instead of towards.
			
			if (Math.abs(Math.abs(distx) + Math.abs(disty)) > 1000) {
				moveToRandomLocation(player);
				ttatimer = 50; //shorter time if player is running away
			}
			
			if (timeUntilNextMotionChange <= 0) {
				if (Math.abs(distx) > Math.abs(disty)) {
					if (distx >= 0) {
						moveLeft();
					}
					else {
						moveRight();
					}
				}
				else {
					if (disty >= 0) {
						moveDown();
					}
					else {
						moveRight();
					}
				}
				timeUntilNextMotionChange = 10;
			}
			else {
				if (actorFacingDirection == direction.UP) {
					moveUp();
				}
				else if (actorFacingDirection == direction.DOWN) {
					moveDown();
				}
				else if (actorFacingDirection == direction.LEFT) {
					moveLeft();
				}
				else if (actorFacingDirection == direction.RIGHT) {
					moveRight();
				}
				timeUntilNextMotionChange--;				
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			moveToRandomLocation(player);
		}
		
	}
	
	public void runAway() {
		currentState = states.FLEEING;
	}
	
	public void forceToWait() {
		currentState = states.WAITING;
		ttatimer = 150;
	}
	
	public void moveToRandomLocation(Player player) {
		float x = ((rand.nextLong() % 700) + player.getX()); 
		float y = ((rand.nextLong() % 700) + player.getY());
		
		//I know, it is an ugly hack...
		while (Math.abs(x - player.getX()) < 400) {
			x = ((rand.nextLong() % 700) + player.getX());
		}
		while (Math.abs(y - player.getY()) < 400) {
			y = ((rand.nextLong() % 700) + player.getY());
		}
		sprite.setPosition(x, y);
		forceToWait();
	}
	
	
	@Override
	public String toString() {
		return null;
	}

}
