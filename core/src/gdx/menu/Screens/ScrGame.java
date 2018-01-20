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

public class ScrGame implements Screen, InputProcessor{
    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnSign, btnPlay, btnAni, btnQuit, btnAH;
    TextureRegion trTemp;
    Texture txSheet, txNamGame, txMap, txSign, txTextbox1, txTextbox2;
    Sprite sprNamGame, sprDude, sprAni, sprMap, sprSign;   //sprAni is a ghost, a sprite used for hit detection, maybe a bit redundant
    Sprite arsprTextbox[] = new Sprite[2];
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
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnSign = new Button(100, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50, "SignB.png");
        btnPlay = new Button(100, 50, 0, Gdx.graphics.getHeight() - 50, "Play.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        btnAni = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, 0, "Animation.jpg");
        btnAH = new Button(100, 50, 0, 0, "AniHit.png");
        txNamGame = new Texture("G.png");
        txSheet = new Texture("Vlad.png");
        sprNamGame = new Sprite(txNamGame);
        sprNamGame.setFlip(false, true);
        sprNamGame.setSize(60, 80);
        sprNamGame.setPosition(Gdx.graphics.getWidth()/2 - 30, Gdx.graphics.getHeight()/2 - 40);
        txTextbox1 = new Texture("Textbox.png");
        txTextbox2 = new Texture("Textbox2.png");
        arsprTextbox[0] = new Sprite(txTextbox1);
        arsprTextbox[1] = new Sprite(txTextbox2);
        for (int i = 0; i < arsprTextbox.length; i++) {
            arsprTextbox[i].setFlip(false, true);
            arsprTextbox[i].setSize(300, 125);
            arsprTextbox[i].setPosition(Gdx.graphics.getWidth()/2 - arsprTextbox[i].getWidth()/2, 0);
        }
        txMap = new Texture("Map so far.png");
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
        sprAni = new Sprite(txNamGame, 0, 0, fW, fH);
        sprAni.setPosition(200, 200);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1); //Purple background.
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
        if(nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            nTrig = 3;
        }
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
        sprMap.draw(batch);
        btnMenu.draw(batch);
        btnSign.draw(batch);
        btnPlay.draw(batch);
        btnQuit.draw(batch);
        sprNamGame.draw(batch);
        btnAni.draw(batch);
        btnAH.draw(batch);
        sprSign.draw(batch);
        batch.draw(trTemp, fSx, fSy);
        for(int i = 0; i < arWall.length; i++){
        arWall[i].draw(batch);
        }
        if(nTrig == 1){
            arsprTextbox[0].draw(batch);
        } else if(nTrig == 3){
            arsprTextbox[1].draw(batch);
        }
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
        txNamGame.dispose();
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
            } else if (isHitB(screenX, screenY, btnSign)) {
                gamMenu.updateState(2);
                System.out.println("Hit Sign");
            } else if (isHitB(screenX, screenY, btnPlay)){
                gamMenu.updateState(1);
                System.out.println("Hit Play");
            } else if (isHitB(screenX, screenY, btnQuit)){
                System.out.println("Quit");
                System.exit(0);
            } else if (isHitB(screenX, screenY, btnAni)){
                System.out.println("Hit Animation");
                gamMenu.updateState(3);
            } else if (isHitB(screenX, screenY, btnAH)){
                System.out.println("Hit AniHit");
                gamMenu.updateState(4);
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
