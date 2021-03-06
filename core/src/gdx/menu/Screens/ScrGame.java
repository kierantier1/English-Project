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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import gdx.menu.GamMenu;

public class ScrGame implements Screen, InputProcessor{
    SpriteBatch batch;
    GamMenu gamMenu;
    BitmapFont bmf;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    TextureRegion trTemp;
    Textbox tbDone;
    Texture txSheet, txMap, txSign, txTextbox1, txTextbox2, txTextbox3, txHouse;
    Sprite sprDude, sprAni, sprMap, sprSign, sprHouse;   //sprAni is a ghost, a sprite used for hit detection, maybe a bit redundant
    Sprite arsprTextbox[] = new Sprite[3];
    String sGame = "Since we only read to this point, That's the end!";
    int nFrame, nPos, nX = 100, nY = 100, nTrig = 0;
    Animation araniDude[];
    int fW, fH, fSx, fSy;
    Wall[] arWall = new Wall[4];
    
    
    public ScrGame(GamMenu _gamMenu) {
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bmf = new BitmapFont(true);
        tbDone = new Textbox(440, 125, Gdx.graphics.getWidth() / 2 - 220, -40);
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        btnMenu = new Button(100, 50, 0, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txSheet = new Texture("Vlad.png");
        txTextbox1 = new Texture("Textbox.png");
        txTextbox2 = new Texture("Textbox2.png");
        txTextbox3 = new Texture("Textbox3.png");
        arsprTextbox[0] = new Sprite(txTextbox1);
        arsprTextbox[1] = new Sprite(txTextbox2);
        arsprTextbox[2] = new Sprite(txTextbox3);
        for (int i = 0; i < arsprTextbox.length; i++) {
            arsprTextbox[i].setFlip(false, true);
            arsprTextbox[i].setSize(300, 125);
            arsprTextbox[i].setPosition(Gdx.graphics.getWidth()/2 - arsprTextbox[i].getWidth()/2, 0);
        }
        txMap = new Texture("Map so far.png");
        txHouse = new Texture("House.png");
        sprMap = new Sprite(txMap);
        sprMap.setScale(4);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 50);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 100);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 200, 0, 100);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 200, Gdx.graphics.getWidth() - 50, 100);    //Right Wall
        txSign = new Texture("Sign.png");
        sprSign = new Sprite(txSign);
        sprSign.setPosition(400, 270);
        sprSign.setSize(25, 25);
        sprSign.setFlip(false, true);
        sprHouse = new Sprite(txHouse);
        sprHouse.setFlip(false, true);
        sprHouse.setSize(75, 75);
        sprHouse.setPosition(96, 196);
        //Animation Stuff
        nFrame = 0;
        nPos = 4;
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
        sprAni = new Sprite(txSign, 0, 0, fW, fH);
        sprAni.setPosition(110, 273);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1); //Purple background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprAni.getX();
        float fSy = sprAni.getY();
        //Animation Stuff
        if(isHitS(sprAni, sprSign) && nTrig == 0){
            System.out.println("Read Sign");
            nTrig = 1;
        } else if(isHitS(sprAni, sprSign) && nTrig == 3){
            nTrig = 3;
        }else if(! isHitS(sprAni, sprSign)){
            nTrig = 0;
        } 
        if(isHitS(sprAni, sprHouse) && nTrig == 0){
            nTrig = 4;
        }
        if(nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            nTrig = 3;
        } 
        if(nTrig == 4 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            gamMenu.updateState(6);
        }
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniDude[nPos].getKeyFrame(nFrame, false);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            //nX = nX-=1;
            sprAni.setX(sprAni.getX() - 3);
            nPos = 7;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            //nX = nX+=1;
            sprAni.setX(sprAni.getX() + 3);
            nPos = 0;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            //nY = nY-=1;
            sprAni.setY(sprAni.getY() - 3);
            nPos = 1;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //nY = nY+=1;
            sprAni.setY(sprAni.getY() + 3);
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
        //sprMap.draw(batch);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        tbDone.draw(batch);
        bmf.setColor(Color.BLACK);
        bmf.draw(batch, sGame,Gdx.graphics.getWidth() / 2 - 140, 20);
        //sprSign.draw(batch);
        //sprHouse.draw(batch);
        batch.draw(trTemp, fSx, fSy);
        //for(int i = 0; i < arWall.length; i++){
        //arWall[i].draw(batch);
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
            //System.out.println(screenX +" " + screenY);
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
            } else if (isHitB(screenX, screenY, btnQuit)){
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
