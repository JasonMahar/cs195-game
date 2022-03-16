package me.project.ninja_pie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	NinjaGame game;
	float y, x;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

	}

	@Override
	public void render (float delta	) {
		ScreenUtils.clear(0.3f, 0.3f, 0.3f, 1);
		//Gdx.gl.glClearColor(1,0,0,1); red, green, blue, apha
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			y = y + 4;
		}else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			y = y - 4;
		}else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			x = x - 4;
		}else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x = x + 4;
		}

		batch.begin();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
