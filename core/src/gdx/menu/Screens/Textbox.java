
package gdx.menu.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Textbox extends Sprite{
    public Textbox(int nW, int nH, int nX, int nY){
        super(new Texture(Gdx.files.internal("Sign Template.png")));
        setSize(nW, nH);
        setPosition(nX, nY);
        
    }
}
