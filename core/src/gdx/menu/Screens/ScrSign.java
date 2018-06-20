package gdx.menu.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import gdx.menu.GamMenu;

public class ScrSign implements Screen, InputProcessor {
    Button btnMenu, btnQuit;
    Textbox tbDoor;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Texture txButtonM, txButtonQ, txSheet, txDoor, txTB;
    Animation araniDude[];
    BitmapFont bmf;
    Dude dudKing, dudThrone, dudQueen;
    String sDoor, sKing1, sKing2, sQueen;
    TextureRegion trTemp;
    int fW, fH, fSx, fSy;
    int nFrame, nPos, nTrig = 0;
    int nX = 100, nY = 200;
    Wall[] arWall = new Wall[4];
    Sprite sprButtonMenu, sprDude, sprDoor, sprTB, sprHD;   //HD = hit detection
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
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 25, 0, 120);    //Top Wall
        arWall[1] = new Wall(25, Gdx.graphics.getHeight() - 300, Gdx.graphics.getWidth() - 25, 120);   //Right Wall
        arWall[2] = new Wall(25, Gdx.graphics.getHeight() - 300, 0, 120);     //Left Wall
        arWall[3] = new Wall(Gdx.graphics.getWidth(), 25, 0, 300);       //Bottom Wall
        bmf = new BitmapFont(true);
        sDoor = "Press Enter to go through";
        sKing1 = "Hamlet my boy, you've been brooding all month!";
        sKing2 = "Rosencrantz and Guildenstern will cheer you up.";
        sQueen = "Hamlet dear, there are some actors outside.";
        btnMenu = new Button(100, 50, 0, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txDoor = new Texture("Door.png");
        txSheet = new Texture("Vlad.png");
        dudKing = new Dude(75, 100, 300, 100, "King.png");
        dudThrone = new Dude(75, 100, 200, 100, "Throne.png");
        dudQueen = new Dude(50, 75, 450, 100, "Queen.png");
        tbDoor = new Textbox(440, 125, Gdx.graphics.getWidth() / 2 - 220, -40);
        sprDoor = new Sprite(txDoor);
        sprDoor.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() / 2 - 50);
        sprDoor.setSize(75, 75);
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
        sprHD = new Sprite(txDoor, 0, 0, fW, fH);
        sprHD.setPosition(nX, nY);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float Delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1); //Blue background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float fSx = sprHD.getX();
        float fSy = sprHD.getY();
        //Animation Stuff
        
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniDude[nPos].getKeyFrame(nFrame, false);
        if(isHitS(sprHD, sprDoor) && nTrig == 0){
            nTrig = 1;
        }else if(isHitS(sprHD, dudKing)){
            nTrig = 2;
        }else if(isHitS(sprHD, dudQueen)){
            nTrig = 3;
        }else if(! isHitS(sprHD, sprDoor) && ! isHitS(sprHD, dudKing) && ! isHitS(sprHD, dudQueen)){
            nTrig = 0;
        }
        if(nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            gamMenu.updateState(3);
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            sprHD.setX(sprHD.getX() - 3);
            nPos = 7;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            sprHD.setX(sprHD.getX() + 3);
            nPos = 0;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sprHD.setY(sprHD.getY() - 3);
            nPos = 1;
            nFrame++;
        } if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sprHD.setY(sprHD.getY() + 3);
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
            if (isHitS(sprHD, arWall[i])) {
                sprHD.setPosition(fSx, fSy);
            }
        }
        
        batch.begin();
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);            
        }
        dudThrone.draw(batch);
        dudKing.draw(batch);
        dudQueen.draw(batch);
        batch.setProjectionMatrix(oc.combined);
        batch.draw(trTemp, fSx, fSy);
        sprDoor.draw(batch);
        btnMenu.draw(batch);
        
        if(nTrig == 1){
            tbDoor.draw(batch);
            bmf.setColor(Color.BLACK);
            bmf.draw(batch, sDoor, Gdx.graphics.getWidth() / 2 - 70, 20);
        }else if(nTrig == 2){
            tbDoor.draw(batch);
            bmf.setColor(Color.BLACK);
            bmf.draw(batch, sKing1, Gdx.graphics.getWidth() / 2 - 140, 20);
            bmf.draw(batch, sKing2, Gdx.graphics.getWidth() / 2 - 140, 40);
        }else if(nTrig == 3){
            tbDoor.draw(batch);
            bmf.setColor(Color.BLACK);
            bmf.draw(batch, sQueen, Gdx.graphics.getWidth() / 2 - 120, 20);
        }
        
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
