package com.dprieto.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainMenu implements Screen {

  SpriteBatch batch;
  Camera camera;
  HUDButton start;
  HUDButton quit;
  HUDText title;

  BitmapFont font;
  public MainMenu()
  {
    batch = new SpriteBatch();
    camera = new Camera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    font = new BitmapFont();
    font.setColor(Color.WHITE);

    start = new HUDButton("button", new Vector2(0, 100), HUDElement.Anchor.MiddleScreen, this.camera, "START", font){
      @Override
      public void OnClicked() {
        PayDay.instance.setScreen(new GameScreen());
      }
    };
    quit = new HUDButton("button", new Vector2(0, 0), HUDElement.Anchor.MiddleScreen, this.camera, "QUIT", font){
      @Override
      public void OnClicked(){
        Gdx.app.exit();
      }
    };
    title = new HUDText(new Vector2(0, 200), HUDElement.Anchor.MiddleScreen, font, camera);
    title.setText("PAY DAY");
  }
  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

    if(Gdx.input.justTouched())
    {
      Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
      Vector3 worldPosition = camera.orthographicCamera.unproject(mousePosition);
      Vector2 position = new Vector2(worldPosition.x, worldPosition.y);
      start.checkClicked(position);
      quit.checkClicked(position);
    }

    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.begin();
    batch.setProjectionMatrix(camera.orthographicCamera.combined);
    title.render(batch);
    start.render(batch);
    quit.render(batch);

    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    camera.ExpandResize(width, height);
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
  public void dispose() {
    batch.dispose();
  }
}
