package enigma.engine.apphack;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class Game extends ApplicationAdapter {
	protected static boolean dev = true;
	private SpriteBatch batch;
	protected static OrthographicCamera camera;
	private Actor enemy;
	private Player player;
	protected static Home bk;

	private ArrayList<Home> ends = new ArrayList<Home>();

	private Actor dummy;
	private BackgroundHandler bg1;
	private GameTimer gameTimer;

	private ShaderProgram defaultShader;
	private ShaderProgram finalShader;
	protected Vector3 blueColor = new Vector3(0.3f, 0.3f, 0.7f);
	protected PointLight pntLight;

	private FrameBuffer fb;

	// how intense the blue is
	private float intensity = 0.1f;
	private boolean drawLight = true;

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		TextureHolder.initTextures();
		SoundHolder.initSounds();

		// All dependent initializations are safe
		player = new Player(TextureHolder.characterSpriteSheet);
		player.setScale(3.0f);
		player.setPosition(120, -50);

		enemy = new Enemy(TextureHolder.enemySpriteSheet);
		enemy.setScale(3.0f);
		enemy.setPosition(-100f, -100f);

		dummy = new Actor(TextureHolder.characterSpriteSheet);
		dummy.setScale(3.0f);

		bg1 = new BackgroundHandler(TextureHolder.grass);

		pntLight = new PointLight();
		pntLight.setScale(0.6f);

		String defaultPixelShader = Gdx.files.internal("defaultPixelShader.glsl").readString();
		String finalShaderString = Gdx.files.internal("pixelShader.glsl").readString();
		String vertexShader = Gdx.files.internal("vertexShader.glsl").readString();

		ShaderProgram.pedantic = false;
		defaultShader = new ShaderProgram(vertexShader, defaultPixelShader);
		finalShader = new ShaderProgram(vertexShader, finalShaderString);

		updateShaders();

		gameTimer = new GameTimer();

		bk = new Home(0, 0, false);
	}

	private void updateShaders() {
		fb = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

		finalShader.begin();
		finalShader.setUniformf("resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		finalShader.setUniformi("u_lightmap", 1);
		finalShader.setUniformf("ambientColor", blueColor.x, blueColor.y, blueColor.z, intensity);
		finalShader.end();
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
		// batch.setShader(fragShader);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// background
		bg1.render(batch, player.getX(), player.getY());

		// lighting (this should be drawn before background when shaders are employed)
		LongLight.render(batch);
		PointLight.render(batch);
		pntLight.setXY(player.getCenterX(), player.getCenterY());

		// actors
		player.render(batch);
		enemy.render(batch);
		// dummy.render(batch);

		batch.end();

	}

	private void renderWithLight() {

		// DRAW THE LIGHTS
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(defaultShader);
		fb.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int src = batch.getBlendSrcFunc();
		int dst = batch.getBlendDstFunc();
		batch.setBlendFunction(GL20.GL_SRC_COLOR, GL20.GL_SRC_ALPHA);
		batch.begin();
		pntLight.setXY(player.getCenterX(), player.getCenterY());

		LongLight.render(batch);
		PointLight.render(batch);

		batch.end();
		fb.end();
		batch.setBlendFunction(src, dst);

		// DRAW THE WORLD
		batch.setShader(finalShader);
		// Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		fb.getColorBufferTexture().bind(1);
		TextureHolder.longLight.bind(0);

		// background
		bg1.render(batch, player.getX(), player.getY());
		bk.render(batch);

		// actors
		player.render(batch);
		// dummy.render(batch);
		enemy.render(batch);

		batch.end();

	}

	private void updateCamera() {
		camera.position.set(player.getX(), player.getY(), 0);
		camera.update();
	}

	private void logic() {
		startLogic();
		endLogic();
		bk.logic(gameTimer);

		((Enemy) enemy).logic((Player) player);

		player.logic(enemy);

	}

	public static boolean spawnedEnd = false;

	private void endLogic() {
		if (gameTimer.hasStarted()) {
			// if the game has lived for a long enough time, spawn the house to win.
			if (gameTimer.gameTimeOver()) {
				if (spawnedEnd == false) {
					spawnedEnd = true;
					spawnEnd();
				}
			}
		}
	}

	private void spawnEnd() {
		// spawn 4 homes in each direction
		float x = player.getX();
		float y = player.getY();

		float xOffset = Gdx.graphics.getWidth();
		float yOffset = Gdx.graphics.getHeight();

		ends.add(new Home(x + xOffset, y + yOffset, true));
		ends.add(new Home(x + xOffset, y - yOffset, true));
		ends.add(new Home(x - xOffset, y + yOffset, true));
		ends.add(new Home(x - xOffset, y - yOffset, true));

	}

	private void startLogic() {
		if (gameTimer.attemptStart(player)) {
			// start ronand
		}
		gameTimer.logic();
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
		if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
			player.toggleFlashLight();
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
			if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
				intensity += 0.3f;
				updateShaders();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
				intensity -= 0.3f;
				updateShaders();
			}
		}
	}
	
	public void resize(int width, int height) {
		updateCamera();
		updateShaders();
    }
}
