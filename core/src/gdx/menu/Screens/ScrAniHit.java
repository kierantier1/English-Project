package gdx.menu.Screens;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import gdx.menu.GamMenu;

public class ScrAniHit implements Screen, InputProcessor{
    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    TextureRegion trTemp;
    BitmapFont bmf;
    int nTrig = 0;
    String sDoor;
    Dude dudDoor, dudPolo;
    Texture txSheet;
    Textbox tbDoor;
    Sprite sprDude, sprAni;   //sprAni is a ghost, a sprite used for hit detection
    int nFrame, nPos, nX = 100, nY = 100;   //nX and nY coordinates for sprAni
    Animation araniDude[];
    int fW, fH, fSx, fSy;
    Wall[] arWall = new Wall[4];
    
    
    public ScrAniHit(GamMenu _gamMenu) {
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        sDoor = "Press Enter to go through";
        bmf = new BitmapFont(true);
        tbDoor = new Textbox(440, 125, Gdx.graphics.getWidth() / 2 - 220, -40);
        //Buttons
        dudDoor = new Dude(75, 75, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() / 2 - 50, "Door.png");
        btnMenu = new Button(100, 50, 0, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txSheet = new Texture("Vlad.png");
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 25, 0, 120);    //Top Wall
        arWall[1] = new Wall(25, Gdx.graphics.getHeight() - 300, Gdx.graphics.getWidth() - 25, 120);   //Right Wall
        arWall[2] = new Wall(25, Gdx.graphics.getHeight() - 300, 0, 120);     //Left Wall
        arWall[3] = new Wall(Gdx.graphics.getWidth(), 25, 0, 300);       //Bottom Wall
        
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
        sprAni = new Sprite(txSheet, 0, 0, fW, fH); //this sprite is never drawn, just used for Hit detection of the animation
        sprAni.setPosition(200, 200);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1); //Blue background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprAni.getX();
        float fSy = sprAni.getY();
        //Animation Stuff
        
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
        if(isHitS(sprAni, dudDoor)){
            nTrig = 1;
        }else{
            nTrig = 0;
        }
        if(nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            gamMenu.updateState(5);
        }
        batch.begin();
        dudDoor.draw(batch);
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        batch.draw(trTemp, fSx, fSy);
        for(int i = 0; i < arWall.length; i++){
            arWall[i].draw(batch);
        }
        if(nTrig == 1){
            tbDoor.draw(batch);
            bmf.setColor(Color.BLACK);
            bmf.draw(batch, sDoor, Gdx.graphics.getWidth() / 2 - 70, 20);
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
