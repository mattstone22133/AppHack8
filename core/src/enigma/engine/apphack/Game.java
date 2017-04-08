package enigma.engine.apphack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	private boolean dev = true;
	private SpriteBatch batch;
	private Actor player;
	private Actor dummy;

	@Override
	public void create() {
		batch = new SpriteBatch();
		TextureHolder.initTextures();
		SoundHolder.initSounds();

		//All dependent initializations are safe
		player = new Actor(TextureHolder.characterSpriteSheet);
		player.setScale(3.0f);
		
		dummy = new Actor(TextureHolder.characterSpriteSheet);
		dummy.setScale(3.0f);
		
	}

	@Override
	public void render() {
		IO();
		logic();
		updateCamera();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		//background
		
		//actors
		player.render(batch);

		batch.end();
	}

	private void updateCamera() {
		// TODO Auto-generated method stub

	}

	private void logic() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
		TextureHolder.dispose();
		SoundHolder.dispose();
	}

	public void IO() {
		devIO();
		
		//Actor IO
		player.io();
	}

	private void devIO() {
		if (dev) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				Gdx.app.exit();
			}

		}
	}
}
