package enigma.engine.apphack;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BackgroundHandler {
	private Sprite bgSprite;
	private Image tempBg;
	private Point2D.Float grassTileCenter;
	private static final int GRASSTILESIZE = 15;
	private static final float GRASSTILEOFFSET = (GRASSTILESIZE * TextureHolder.grass.getWidth()) / 2;

	public BackgroundHandler(Texture text) {
		bgSprite = new Sprite(text);
		tempBg = new Image(text);
		grassTileCenter = new Point2D.Float(0, 0);
	}

	public void render(SpriteBatch batch, float charX, float charY) {
		
		if (charX < (grassTileCenter.x - GRASSTILEOFFSET)) {
			grassTileCenter.setLocation(grassTileCenter.x - (GRASSTILEOFFSET * 2), grassTileCenter.y);
		}
		else if (charX > (grassTileCenter.x + GRASSTILEOFFSET)) {
			grassTileCenter.setLocation(grassTileCenter.x + (GRASSTILEOFFSET * 2), grassTileCenter.y);
		}
		else if (charY < (grassTileCenter.y - GRASSTILEOFFSET)) {
			grassTileCenter.setLocation(grassTileCenter.x , grassTileCenter.y - (GRASSTILEOFFSET * 2));
		}
		else if (charY > (grassTileCenter.y + GRASSTILEOFFSET)) {
			grassTileCenter.setLocation(grassTileCenter.x , grassTileCenter.y + (GRASSTILEOFFSET * 2));
		}
		
		bgSprite.draw(batch);
		
			
		TextureHolder.grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

		//center Tile
		batch.draw(TextureHolder.grass, grassTileCenter.x - GRASSTILEOFFSET, grassTileCenter.y - GRASSTILEOFFSET, 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//leftTile
		batch.draw(TextureHolder.grass, (grassTileCenter.x - GRASSTILEOFFSET) - (GRASSTILEOFFSET * 2), grassTileCenter.y - GRASSTILEOFFSET, 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//rightTile
		batch.draw(TextureHolder.grass, (grassTileCenter.x - GRASSTILEOFFSET) + (GRASSTILEOFFSET * 2), grassTileCenter.y - GRASSTILEOFFSET, 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//topTile
		batch.draw(TextureHolder.grass, grassTileCenter.x - GRASSTILEOFFSET, (grassTileCenter.y - GRASSTILEOFFSET) + (GRASSTILEOFFSET * 2), 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//bottomTile
		batch.draw(TextureHolder.grass, grassTileCenter.x - GRASSTILEOFFSET, (grassTileCenter.y - GRASSTILEOFFSET) - (GRASSTILEOFFSET * 2), 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		
		
		//topLeft
		batch.draw(TextureHolder.grass, (grassTileCenter.x - GRASSTILEOFFSET) - (GRASSTILEOFFSET * 2), (grassTileCenter.y - GRASSTILEOFFSET) + (GRASSTILEOFFSET * 2), 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//topRight
		batch.draw(TextureHolder.grass, (grassTileCenter.x - GRASSTILEOFFSET) + (GRASSTILEOFFSET * 2), (grassTileCenter.y - GRASSTILEOFFSET) + (GRASSTILEOFFSET * 2), 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//bottomLeft
		batch.draw(TextureHolder.grass, (grassTileCenter.x - GRASSTILEOFFSET) - (GRASSTILEOFFSET * 2), (grassTileCenter.y - GRASSTILEOFFSET) - (GRASSTILEOFFSET * 2), 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());
		//bottomRight
		batch.draw(TextureHolder.grass, (grassTileCenter.x - GRASSTILEOFFSET) + (GRASSTILEOFFSET * 2), (grassTileCenter.y - GRASSTILEOFFSET) - (GRASSTILEOFFSET * 2), 0, 0, GRASSTILESIZE * TextureHolder.grass.getWidth(), GRASSTILESIZE * TextureHolder.grass.getHeight());

		
		//batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);

	}
}
