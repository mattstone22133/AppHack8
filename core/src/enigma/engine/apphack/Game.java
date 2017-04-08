package enigma.engine.apphack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	private boolean dev = true;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Actor player;
	private Actor dummy;
	private BackgroundHandler bg1;

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		TextureHolder.initTextures();
		SoundHolder.initSounds();

		// All dependent initializations are safe
		player = new Actor(TextureHolder.characterSpriteSheet);
		player.setScale(3.0f);

		dummy = new Actor(TextureHolder.characterSpriteSheet);
		dummy.setScale(3.0f);
		
		bg1 = new BackgroundHandler(TextureHolder.grass);

	}

	@Override
	public void render() {
		IO();
		logic();
		updateCamera();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// background
		bg1.render(batch, 0, 0);
		

		// actors
		player.render(batch);
		dummy.render(batch);

		batch.end();
	}

	private void updateCamera() {
		camera.position.set(player.getX(), player.getY(), 0);
		camera.update();
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

		// Player io
		if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.moveUp();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.moveDown();

		} else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.moveLeft();

		} else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.moveRight();
		}
	}

	private void devIO() {
		if (dev) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				Gdx.app.exit();
			}

		}
	}
}
