package gdx.menu.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;

public class Dude extends Sprite{
    public Dude(int nW, int nH, int nX, int nY, String sFilename){
        super(new Texture(Gdx.files.internal(sFilename))); //https://stackoverflow.com/questions/32654526/libgdx-sprite-from-custom-class-is-not-showing-up
        setSize(nW, nH);
        setPosition(nX, nY);    //You know, I only used this in ScrPlay and ScrSign, seems like a waste
        setFlip(false, true);
    }
}
