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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gdx.menu.GamMenu;

public class ScrSign implements Screen, InputProcessor {
    Button btnMenu, btnQuit;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Texture txButtonM, txButtonQ, txSheet, txDoor;
    Animation araniDude[];
    TextureRegion trTemp;
    int fW, fH, fSx, fSy;
    int nFrame, nPos, nTrig = 0;
    int nX = 100, nY = 100;
    Sprite sprButtonMenu, sprDude, sprDoor;
    SpriteBatch batch;
    public ScrSign(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
         batch = new SpriteBatch();
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        //Buttons
        btnMenu = new Button(100, 50, 0, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txDoor = new Texture("Door.png");
        txSheet = new Texture("Vlad.png");
        
        sprDoor = new Sprite(txDoor);
        sprDoor.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() / 2 - 50);
        sprDoor.setSize(100, 100);
        sprDoor.setFlip(false, true);        
        //Animation Stuff
        nFrame = 0;
        nPos = 0;
        araniDude = new Animation[8];
        fW = txSheet.getWidth() / 8;
        fH = txSheet.getHeight() / 8;
        for (int i = 0; i < 8; i++) {
            Sprite[] arSprDude = new Sprite[8];
            for (int j = 0; j < 8; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprDude = new Sprite(txSheet, fSx, fSy, fW, fH);
                sprDude.setFlip(false, true);
                arSprDude[j] = new Sprite(sprDude);
            }
            araniDude[i] = new Animation(0.8f, arSprDude);

        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float Delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1); //Cyan background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Animation Stuff
        
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniDude[nPos].getKeyFrame(nFrame, false);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            nX = nX-=3;
            nPos = 7;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            nX = nX+=3;
            nPos = 0;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            nY = nY-=3;
            nPos = 1;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            nY = nY+=3;
            nPos = 4;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
            nPos = 3;
            nFrame--;
        } if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            nPos = 6;
            nFrame--;
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
            nPos = 2;
            nFrame--;
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            nPos = 5;
            nFrame--;
        }
        
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        batch.draw(trTemp, nX, nY);
        sprDoor.draw(batch);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
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
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
            }else if(isHitB(screenX, screenY, btnQuit)){
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
