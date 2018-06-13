package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gdx.menu.GamMenu;

public class ScrPlay implements Screen, InputProcessor {
    Button btnMenu, btnQuit;
    Dude dudGhost;
    Wall[] arWall = new Wall[4];
    GamMenu gamMenu;
    BitmapFont bmf;
    String sGhost, sDoor;
    OrthographicCamera oc;
    Textbox tbDoor;
    SpriteBatch batch;
    Texture txWall, txDoor, txSheet;
    TextureRegion trTemp;
    Animation araniDude[];
    Sprite sprDoor, sprDude, sprHD;  //sprHD is for hit detection of the animation
    int nTrig = 0; //Trigger for Door
    int nFrame = 0, nPos = 0;
    int fW, fH, fSx, fSy;
    int nX, nY;
    
    public ScrPlay(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        txWall = new Texture("Wall.jpg");
        //Setting up Walls
        /*arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 50);    //Top Wall
        arWall[1] = new Wall(50, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth() - 50, 50);   //Right Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 100, 0, 50);     //Left Wall
        arWall[3] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 100);*/       //Bottom Wall
        bmf = new BitmapFont(true);
        //Put text in, position it
        sDoor = "Press Enter to go through";
        sGhost = "Hamlet my son, you must avenge me!";
        dudGhost = new Dude(25, 50, 200, 200, "hamlet sr.png");
        txSheet = new Texture("Vlad.png");
        txDoor = new Texture("Door.png");
        sprDoor = new Sprite(txDoor);
        
        sprDoor.setSize(75, 75);
        sprDoor.setFlip(false, true);
        sprDoor.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight()/2);
        tbDoor = new Textbox(350, 125, Gdx.graphics.getWidth() / 2 - 175, -40);
        batch = new SpriteBatch();
        btnMenu = new Button(100, 50, 0, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
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
        sprHD = new Sprite(txDoor, 0, 0, fW, fH);
        sprHD.setPosition(0, Gdx.graphics.getHeight() / 2);
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1); //blue background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float fSx = sprHD.getX();
        float fSy = sprHD.getY();
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniDude[nPos].getKeyFrame(nFrame, false);
        
        if(isHitS(sprHD, sprDoor) && nTrig == 0){
            nTrig = 1;
        }
        if(isHitS(sprHD, dudGhost)){
            nTrig = 2;
        } 
        if(! isHitS(sprHD, dudGhost) && ! isHitS(sprHD, sprDoor)){
            nTrig = 0;
        }
        if(nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            gamMenu.updateState(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprHD.setX(sprHD.getX() - 3);
            nPos = 7;
            nFrame++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprHD.setX(sprHD.getX() + 3);
            nPos = 0;
            nFrame++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sprHD.setY(sprHD.getY() + 3);
            nPos = 4;
            nFrame++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sprHD.setY(sprHD.getY() - 3);
            nPos = 1;
            nFrame++;
        }if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)){
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
        
        /*for (int i = 0; i < arWall.length; i++) {
            if (isHitS(dud1, arWall[i])) {
                dud1.setPosition(fSx, fSy);
            }
        }*/
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        batch.draw(trTemp, fSx, fSy);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        sprDoor.draw(batch);
        dudGhost.draw(batch);
        //dud1.draw(batch);
        if(nTrig == 1){
            tbDoor.draw(batch);
            bmf.setColor(Color.BLACK);
            bmf.draw(batch, sDoor, Gdx.graphics.getWidth() / 2 - 70, 20);
        }else if(nTrig == 2){
            tbDoor.draw(batch);
            bmf.setColor(Color.BLACK);
            bmf.draw(batch, sGhost, Gdx.graphics.getWidth() / 2 - 110, 20);
        }
        /*for (int i = 0; i < arWall.length; i++) {
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
            
            if (isHitB(screenX, screenY, btnMenu)){
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