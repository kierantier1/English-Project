
package gdx.menu.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Textbox extends Sprite{
    public Textbox(String sText){
        super(new Texture(Gdx.files.internal("Sign Template.png")));
        SpriteBatch batch = new SpriteBatch();
        BitmapFont bmf = new BitmapFont(true);
        setSize(300, 125);
        setPosition(Gdx.graphics.getWidth() / 2 - 150, 0);
        bmf.draw(batch, sText, Gdx.graphics.getWidth() / 2 - 125, 5);
    }
}
