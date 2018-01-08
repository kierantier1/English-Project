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
    Button btnSign, btnAni, btnMenu;
    Wall[] arWall = new Wall[4];
    GamMenu gamMenu;
    OrthographicCamera oc;
    SpriteBatch batch;
    Texture txNamP, txWall;
    Sprite sprNamP;

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
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0);    //Top Wall
        arWall[1] = new Wall(50, Gdx.graphics.getHeight() - 50, Gdx.graphics.getWidth() - 50, 0);   //Right Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 50, 0, 0);     //Left Wall
        arWall[3] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 100);       //Bottom Wall
        batch = new SpriteBatch();
        txNamP = new Texture("P.jpg");
        sprNamP = new Sprite(txNamP);
        sprNamP.setSize(60, 80);
        sprNamP.setFlip(false, true);
        sprNamP.setPosition(Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - 40);
        dud1 = new Dude(50, 100, 200, 250);
        btnAni = new Button(100, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50, "Animation.jpg");
        btnSign = new Button(100, 50, 0, Gdx.graphics.getHeight() - 50, "SignB.png");
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, Gdx.graphics.getHeight()- 50, "Menu.jpg");
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
        if (isHitS(dud1, sprNamP)) {
            dud1.setPosition(fSx, fSy);
        }
        for (int i = 0; i < arWall.length; i++) {
            if (isHitS(dud1, arWall[i])) {
                dud1.setPosition(fSx, fSy);
            }
        }
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnAni.draw(batch);
        btnSign.draw(batch);
        btnMenu.draw(batch);
        sprNamP.draw(batch);
        dud1.draw(batch);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
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
        txNamP.dispose();
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
            if (isHitB(screenX, screenY, btnAni)) {
                gamMenu.updateState(3);
                System.out.println("Hit Tools");
            } else if (isHitB(screenX, screenY, btnSign)) {
                gamMenu.updateState(2);
                System.out.println("Hit Sign");
            } else if (isHitB(screenX, screenY, btnMenu)){
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
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