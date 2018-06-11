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
import gdx.menu.GamMenu;

public class ScrPlay implements Screen, InputProcessor {
    Dude dud1;
    Button btnMenu, btnQuit;
    Wall[] arWall = new Wall[4];
    GamMenu gamMenu;
    OrthographicCamera oc;
    SpriteBatch batch;
    Texture txWall, txDoor;
    Sprite sprDoor;
    
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
        txDoor = new Texture("Door.png");
        sprDoor = new Sprite(txDoor, 50, 50, Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight()/2);
        batch = new SpriteBatch();
        dud1 = new Dude(50, 100, 200, 250);
        btnMenu = new Button(100, 50, 0, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1); //blue background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = dud1.getX();
        float fSy = dud1.getY();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dud1.setX(dud1.getX() - 5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dud1.setX(dud1.getX() + 5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dud1.setY(dud1.getY() + 5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dud1.setY(dud1.getY() - 5);
        }
        if(isHitS(dud1, sprDoor)){
            
        }
        /*for (int i = 0; i < arWall.length; i++) {
            if (isHitS(dud1, arWall[i])) {
                dud1.setPosition(fSx, fSy);
            }
        }*/
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        dud1.draw(batch);
        /*for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }*/
        sprDoor.draw(batch);
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