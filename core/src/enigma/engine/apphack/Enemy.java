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
	protected int suspenseTimer = 0;
	protected Random rand = new Random();
	//protected Point2D.Float randomPosition = new Point2D.Float(0, 0);
	protected enum states {ATTACKING, WAITING, FLEEING, START, SUSPENSE};
	protected states currentState = states.START;
	

	public Enemy(TextureRegion[][] spriteSheetArg) {
		super(spriteSheetArg);
		moveSpeed = 11f;
		playerPos = new Point2D.Float(0, 0);
		
		
	}
	
	public void logic(Player player) {
		playerPos.setLocation(player.getX(), player.getY());
		float distx = playerPos.x - sprite.getX();
		float disty = playerPos.y - sprite.getY();
		float dist = getDistanceFromPlayer();
		if (currentState == states.START) {
			if (getDistanceFromPlayer(0,0) > 1000) {
				moveToRandomLocation(player);
			}
			else {
				sprite.setPosition(playerPos.x, playerPos.y + 900);
			}							
		}			
		else if (currentState == states.ATTACKING) {
			
			if (dist > 1000) {
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
			
			if (dist > 900) {
				
				if (rand.nextInt(15) == 1) {
					System.out.println(rand.nextInt(3));
					
					createSuspense();
				}
				else {
					moveToRandomLocation(player);
					ttatimer = 50; //shorter time if player is running away
				}		
				
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
		else if(currentState == states.SUSPENSE) {
			if (suspenseTimer <= 0) {
				moveToRandomLocation(player);
				SoundHolder.mcDWhistle.play();
			}
			else {
				sprite.setPosition(playerPos.x, playerPos.y + 900); //move it out of sight
				suspenseTimer--;
			}
		}
		
	}
	
	public void createSuspense() {
		currentState = states.SUSPENSE;
		suspenseTimer = rand.nextInt(1000) + 500;
	}
	
	public float getDistanceFromPlayer() {
		float dist = ((sprite.getX() - playerPos.x) * (sprite.getX() - playerPos.x)) + ((sprite.getY() - playerPos.y) * (sprite.getY() - playerPos.y));
		return (float) Math.sqrt(dist);
	}
	
	public float getDistanceFromPlayer(float x, float y) {
		float dist = ((x - playerPos.x) * (x - playerPos.x)) + ((y - playerPos.y) * (y - playerPos.y));
		return (float) Math.sqrt(dist);
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
		while (getDistanceFromPlayer(x, y) < 400) {
			x = ((rand.nextLong() % 700) + player.getX());
			y = ((rand.nextLong() % 700) + player.getY());
		}
		sprite.setPosition(x, y);
		forceToWait();
	}
	
	@Override
	public void playRFootSound() {
		SoundHolder.rightFootStepEnemy.play(1 / (getDistanceFromPlayer() / 2));
	}
	
	@Override
	public void playLFootSound() {
		SoundHolder.leftFootStepEnemy.play(1 / (getDistanceFromPlayer() / 2));
	}
	
	
	@Override
	public String toString() {
		return null;
	}

}
