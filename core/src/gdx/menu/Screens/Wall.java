package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;

public class Wall extends Sprite{
    public Wall(int nW, int nH, int nX, int nY){
        super(new Texture(Gdx.files.internal("CastleWall.png")));
        setPosition(nX, nY);
        setSize(nW, nH);
        setFlip(false, true);
    }
}
