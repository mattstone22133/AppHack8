package enigma.engine.apphack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundHolder {
	protected static Sound leftFootStepHum;
	protected static Sound rightFootStepHum;
	protected static Sound leftFootStepEnemy;
	protected static Sound rightFootStepEnemy;
	protected static Sound mcDWhistle;
	protected static Sound mcDLaugh;
	protected static Sound humanPain;
	protected static Sound humanDead;
	protected static Sound buzz1;
	protected static Sound buzz2;
	protected static Sound buzz3;
	protected static Sound buzz4;
	
	public static void initSounds() {
		leftFootStepHum = Gdx.audio.newSound(Gdx.files.internal("working sound/hum/walking/left foot hum.ogg"));
		rightFootStepHum = Gdx.audio.newSound(Gdx.files.internal("working sound/hum/walking/Right foot hum.ogg"));
		leftFootStepEnemy = Gdx.audio.newSound(Gdx.files.internal("working sound/Clown/walking/left foot.ogg"));
		rightFootStepEnemy = Gdx.audio.newSound(Gdx.files.internal("working sound/Clown/walking/right foot.ogg"));
		mcDWhistle = Gdx.audio.newSound(Gdx.files.internal("working sound/Clown/sound/McDonalds whistle.ogg"));
		humanPain = Gdx.audio.newSound(Gdx.files.internal("working sound/hum/sounds/huminpain.ogg"));
		humanDead = Gdx.audio.newSound(Gdx.files.internal("working sound/hum/sounds/humDead.ogg"));
		mcDLaugh = Gdx.audio.newSound(Gdx.files.internal("working sound/Clown/sound/McDonalds laugh.ogg"));
		buzz1 = Gdx.audio.newSound(Gdx.files.internal("working sound/lightsounds/dyinglight1.ogg"));
		buzz2 = Gdx.audio.newSound(Gdx.files.internal("working sound/lightsounds/dyinglight2.ogg"));
		buzz3 = Gdx.audio.newSound(Gdx.files.internal("working sound/lightsounds/dyinglight3.ogg"));
		buzz4 = Gdx.audio.newSound(Gdx.files.internal("working sound/lightsounds/dyinglight4.ogg"));

	}

	public static void dispose() {
		leftFootStepHum.dispose();
		rightFootStepHum.dispose();
		leftFootStepEnemy.dispose();
		rightFootStepEnemy.dispose();
		mcDWhistle.dispose();
		humanPain.dispose();
		humanDead.dispose();
		mcDLaugh.dispose();
		buzz1.dispose();
		buzz2.dispose();
		buzz3.dispose();
		buzz4.dispose();
	}

}
