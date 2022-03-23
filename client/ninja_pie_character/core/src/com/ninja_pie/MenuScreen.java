package com.ninja_pie;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class  MenuScreen extends InputAdapter implements Screen {
    Stage stage;
    SpriteBatch batch;

    Texture player;
    //TextureRegion[] animationFrames;
    //Animation animation;
    //Texture ground;
    float speed = 50.0f;
    float playerx = 0;
    float playery = 8;


    static Texture upPlayer = new Texture("ninja cat/ninja cat/animations/slash/ninja-slash_04.png");
    static Texture   downPlayer = new Texture("ninja cat/ninja cat/animations/pull/ninja-pull lever_09.png");
    static Texture leftPlayer = new Texture("ninja cat/ninja cat/animations/slash/ninja-slash_02.png");
    static Texture rightPlayer = new Texture("ninja cat/ninja cat/animations/die/ninja-die_18.png");
    static Texture idlePlayer = new Texture("ninja cat/ninja cat/animations/die/ninja-die_18.png");

    @Override
    public void show() {
        player = new Texture("ninja cat/ninja cat/animations/run/ninja-run_06.png");
       // TextureRegion[] [] tmpFrames = TextureRegion.split(player,256,256);

        //ground = new Texture("bush.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }



    @Override
    public void render(float delta) {
		//System.out.println( "calling render. delta = " + delta );
		
        Gdx.gl.glClearColor(1, 1, 1, 0);
        ScreenUtils.clear(1,1,1,0);
        batch.begin();
        stage.draw();
        batch.draw(player,playerx,playery);
        //batch.draw(ground, 100, 100);
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && playery<500){
            playery += delta*speed;
            //player = new Texture("ninja cat/ninja cat/animations/slash/ninja-slash_04.png");
			player = upPlayer;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)&& playery>0){
            playery -= delta*speed;
            //player = new Texture("ninja cat/ninja cat/animations/pull/ninja-pull lever_09.png");
			player = downPlayer;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& playerx>0){
            playerx -= delta*speed;
            //player = new Texture("ninja cat/ninja cat/animations/slash/ninja-slash_02.png");
			player = leftPlayer;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& playerx<800){
            playerx += delta*speed;
            //player = new Texture("ninja cat/ninja cat/animations/die/ninja-die_18.png");
			player = rightPlayer;
        }
        //if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
            //player = new Texture("ninja cat/ninja cat/animations/die/ninja-die_18.png");
		//	player = idlePlayer;
        //}
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        player.dispose();
    }


}
