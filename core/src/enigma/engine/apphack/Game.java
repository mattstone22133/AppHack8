package enigma.engine.apphack;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Game extends ApplicationAdapter {
	protected static boolean dev = true;
	private SpriteBatch batch;
	static OrthographicCamera camera;
	private Player player;
	private Actor dummy;
	private BackgroundHandler bg1;

	private ShaderProgram normalFragmentShader;
	private ShaderProgram vertexShader;
	private ShaderProgram fragShader;

	private FrameBuffer fb;
	
	// how intense the blue is
	private float intensity = 0.7f;
	private boolean drawLight = false;

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		TextureHolder.initTextures();
		SoundHolder.initSounds();

		// All dependent initializations are safe
		player = new Player(TextureHolder.characterSpriteSheet);
		player.setScale(3.0f);

		dummy = new Actor(TextureHolder.characterSpriteSheet);
		dummy.setScale(3.0f);

		bg1 = new BackgroundHandler(TextureHolder.grass);

		String normalFragShader = Gdx.files.internal("normalShader.glsl").readString();
		String fragShaderString = Gdx.files.internal("fragmentShader.glsl").readString();
		String vertexShader = Gdx.files.internal("fragmentShader.glsl").readString();

		ShaderProgram.pedantic = false;
		normalFragmentShader = new ShaderProgram(vertexShader, normalFragShader);
		fragShader = new ShaderProgram(vertexShader, fragShaderString);

		updateShaders();

	}

	private void updateShaders() {
		fb = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		
		fragShader.begin();
		fragShader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fragShader.setUniformi("u_lightmap", 1);
		fragShader.setUniformf("ambientColor", 0.3f, 0.3f, 0.7f, intensity);
		fragShader.end();
	}

	@Override
	public void render() {
		IO();
		updateCamera();
		logic();

		if (drawLight) {
			renderWithLight();
		} else {
			renderNoLight();
		}

	
	}

	private void renderNoLight() {
		//batch.setShader(fragShader);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// background
		bg1.render(batch, player.getX(), player.getY());

		// lighting (this should be drawn before background when shaders are employed)
		LongLight.render(batch);

		// actors
		player.render(batch);
		dummy.render(batch);

		batch.end();
		
	}

	private void renderWithLight() {
		
		// DRAW THE LIGHTS
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(normalFragmentShader);
		fb.begin();
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
			batch.begin();
				LongLight.render(batch);		
		
				batch.end();
		fb.end();
		
		
		// DRAW THE WORLD
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setShader(fragShader);
		batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
		
			fb.getColorBufferTexture().bind(0);
			TextureHolder.character.bind(1);

			// background
			bg1.render(batch, player.getX(), player.getY());

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
		player.logic(dummy);
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
			if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
				drawLight = !drawLight;
			}
		}
	}
}
