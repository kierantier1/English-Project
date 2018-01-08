package gdx.menu.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import gdx.menu.GamMenu;

public class ScrSign implements Screen, InputProcessor {
    Dude dud1;
    Button btnMenu, btnPlay, btnAni;
    OrthographicCamera oc;
    Texture txNamS, txSign, txBox;
    GamMenu gamMenu;
    SpriteBatch batch;
    Sprite sprNamSign, sprSign, sprBox;
    int nTrig = 0; //Trigger variable for sign
    public ScrSign(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        btnPlay = new Button(100, 50, 0, Gdx.graphics.getHeight() - 50, "Play.jpg");
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnAni = new Button(100, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight()-50, "Animation.jpg");
        txNamS = new Texture("S.png");
        sprNamSign = new Sprite(txNamS);
        sprNamSign.setFlip(false, true);
        sprNamSign.setSize(60, 80);
        sprNamSign.setPosition(Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - 40);
        dud1 = new Dude(50, 100, 200, 250);
        txSign = new Texture("Sign.png");
        sprSign = new Sprite(txSign);
        sprSign.setPosition(150, 150);
        sprSign.setSize(50,50);
        sprSign.setFlip(false, true);
        txBox = new Texture("Textbox.png");
        sprBox = new Sprite(txBox);
        sprBox.setSize(300, 125);
        sprBox.setPosition(Gdx.graphics.getWidth()/2 - sprBox.getWidth()/2, 0);
        sprBox.setFlip(false, true);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float Delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1); //Cyan background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            dud1.setX(dud1.getX()-5);
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            dud1.setX(dud1.getX()+5);
        } if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            dud1.setY(dud1.getY()-5);
        } if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            dud1.setY(dud1.getY()+5);
        }

        if(isHitS(dud1, sprSign) && nTrig != 1){
            System.out.println("Read Sign");
            nTrig = 1;
        } else if(! isHitS(dud1, sprSign)){
            nTrig = 0;
        }
        
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnPlay.draw(batch);
        btnMenu.draw(batch);
        btnAni.draw(batch);
        sprNamSign.draw(batch);
        sprSign.draw(batch);
        dud1.draw(batch);
        if(nTrig == 1){
            sprBox.draw(batch);
        }
        batch.end();
    }

    /*
     * UpdateState(0) for Menu
     * UpdateState(1) for Play
     */
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
        txNamS.dispose();
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
            if (isHitB(screenX, screenY, btnPlay)) {
                gamMenu.updateState(1);
                System.out.println("Hit Play");
            } else if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
            } else if(isHitB(screenX, screenY, btnAni)){
                gamMenu.updateState(3);
                System.out.println("Hit Animation");
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

    public boolean isHitB(int nX, int nY, Sprite sprBtn) {
        if (nX > sprBtn.getX() && nX < sprBtn.getX() + sprBtn.getWidth() && nY > sprBtn.getY() && nY < sprBtn.getY() + sprBtn.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isHitS(Sprite spr1, Sprite spr2) {
        return spr1.getBoundingRectangle().overlaps(spr2.getBoundingRectangle());
    }
}