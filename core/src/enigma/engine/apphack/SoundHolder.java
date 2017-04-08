package enigma.engine.apphack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundHolder {
	protected static Sound leftFootStepHum;
	protected static Sound rightFootStepHum;
	protected static Sound leftFootStepEnemy;
	protected static Sound rightFootStepEnemy;
	public static void initSounds() {
		leftFootStepHum = Gdx.audio.newSound(Gdx.files.internal("working sound/hum/walking/left foot hum.ogg"));
		rightFootStepHum = Gdx.audio.newSound(Gdx.files.internal("working sound/hum/walking/Right foot hum.ogg"));
		leftFootStepEnemy = Gdx.audio.newSound(Gdx.files.internal("working sound/Clown/walking/left foot.ogg"));
		rightFootStepEnemy = Gdx.audio.newSound(Gdx.files.internal("working sound/Clown/walking/right foot.ogg"));

	}

	public static void dispose() {
		leftFootStepHum.dispose();
		rightFootStepHum.dispose();
		leftFootStepEnemy.dispose();
		rightFootStepEnemy.dispose();
	}

}
