package enigma.engine.apphack;

public class GameTimer {
	private long startMS;
	private boolean started = false;
	private boolean gameTimeOver = false;
	public double threshold = 1000;

	public boolean attemptStart(Actor player) {
		// pythagorean theorm distance check.
		if (!started && Math.sqrt(player.getX() * player.getX() + player.getY() * player.getY()) > threshold) {
			System.out.println("Timer started!");
			startMS = System.currentTimeMillis();
			started = true;

			return true;
		}
		return false;
	}

	public void logic() {
		if (started && System.currentTimeMillis() - startMS > 1000 * 60 * 1) {
			System.out.println("Timer has ended!");

			gameTimeOver = true;
		}
	}

	public boolean gameTimeOver() {
		return gameTimeOver;
	}

	public boolean hasStarted() {
		return started;
	}
}
