package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.OrthographicCamera;
import gdx.menu.GamMenu;

public class ScrMenu implements Screen, InputProcessor {
    Dude dudTitle;
    Button btnPlay, btnQuit;
    GamMenu gamMenu;
    String sMove = "English Project by Kieran Halliday";
    Texture txButtonP, txButtonT;
    OrthographicCamera oc;
    SpriteBatch batch;
    BitmapFont bmf;

    public ScrMenu(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        dudTitle = new Dude(200, 100, Gdx.graphics.getWidth() / 2 - 100, 100, "Hamlet.png");
        bmf = new BitmapFont(true);
        batch = new SpriteBatch();
        btnPlay = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, 200, "Play.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, 320, "Quit.jpg");
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1); //Green background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dudTitle.draw(batch);
        bmf.setColor(Color.BLACK);
        bmf.draw(batch, sMove, Gdx.graphics.getWidth() / 2 - 100, 400);
        batch.setProjectionMatrix(oc.combined);
        btnPlay.draw(batch);
        btnQuit.draw(batch);
        
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
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (isHit(screenX, screenY, btnPlay)) {
                System.out.println("Hit Play");
                gamMenu.updateState(1);
            } else if(isHit(screenX, screenY, btnQuit)){
                System.out.println("Quit");
                System.exit(0);
            } 
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isHit(int nX, int nY, Sprite sprBtn) {
        if (nX > sprBtn.getX() && nX < sprBtn.getX() + sprBtn.getWidth() && nY > sprBtn.getY() && nY < sprBtn.getY() + sprBtn.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
}