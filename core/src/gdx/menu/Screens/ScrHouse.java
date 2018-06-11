package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import gdx.menu.GamMenu;

public class ScrHouse implements Screen, InputProcessor{
    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnQuit;
    TextureRegion trTemp;
    Texture txSheet, txFloor, txTextBox;
    Sprite sprDude, sprDoor, sprAni, sprFloor, sprBox;   //sprAni is a ghost, a sprite used for hit detection
    int nFrame, nPos, nX = 100, nY = 100, nTrig;
    Animation araniDude[];
    int fW, fH, fSx, fSy;
    Wall[] arWall = new Wall[5];
    
    
    public ScrHouse(GamMenu _gamMenu) {
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txSheet = new Texture("Vlad.png");
        txTextBox = new Texture("Textbox4.png");
        txFloor = new Texture("InHouse.png");
        sprFloor = new Sprite(txFloor);
        sprFloor.setScale(2.3f);
        sprFloor.setPosition(Gdx.graphics.getWidth() / 2 - sprFloor.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprFloor.getHeight() / 2 + 2);
        sprFloor.setFlip(false, true);
        sprBox = new Sprite(txTextBox);
        sprBox.setFlip(false, true);
        sprBox.setSize(300, 125);
        sprBox.setPosition(Gdx.graphics.getWidth()/2 - sprBox.getWidth()/2, 0);
        sprDoor = new Sprite(txTextBox);
        nTrig = 0;
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth() / 2 - 35, 50, 0, Gdx.graphics.getHeight() - 50);    //Bottom Left Wall
        arWall[4] = new Wall(Gdx.graphics.getWidth() / 2 - 35, 50, Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 + 35, Gdx.graphics.getHeight() - 50);  //Bottom Right Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 100, 0, 50);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() - 50, 50);    //Right Wall
        
        //Animation Stuff
        nFrame = 0;
        nPos = 1;
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
        sprAni = new Sprite(txSheet, 0, 0, fW, fH);
        sprAni.setPosition(Gdx.graphics.getWidth() / 2 - fW / 2, 375);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1); //Purple background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprAni.getX();
        float fSy = sprAni.getY();
        
        //hit detection for door
        if(sprAni.getY() > Gdx.graphics.getHeight() - 90){
            nTrig = 1;
        } else {
            nTrig = 0;
        }
        if(nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            gamMenu.updateState(5);
        }
        //Animation Stuff
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniDude[nPos].getKeyFrame(nFrame, false);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            //nX = nX-=1;
            sprAni.setX(sprAni.getX() - 1);
            nPos = 7;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            //nX = nX+=1;
            sprAni.setX(sprAni.getX() + 1);
            nPos = 0;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            //nY = nY-=1;
            sprAni.setY(sprAni.getY() - 1);
            nPos = 1;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //nY = nY+=1;
            sprAni.setY(sprAni.getY() + 1);
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
        for (int i = 0; i < arWall.length; i++) {
           if(isHitS(sprAni, arWall[i])){
                sprAni.setPosition(fSx, fSy);
            }
        }
        
        
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        sprFloor.draw(batch);
        btnQuit.draw(batch);
        batch.draw(trTemp, fSx, fSy);
        if(nTrig == 1){
        sprBox.draw(batch);
        }
        /*for(int i = 0; i < arWall.length; i++){
        arWall[i].draw(batch);
        }*/
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
        txSheet.dispose();
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
            //System.out.println(screenX +" " + screenY);
             if (isHitB(screenX, screenY, btnQuit)){
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
