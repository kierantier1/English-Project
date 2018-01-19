package gdx.menu;
import com.badlogic.gdx.Game;
import gdx.menu.Screens.ScrMenu;
import gdx.menu.Screens.ScrPlay;
import gdx.menu.Screens.ScrSign;
import gdx.menu.Screens.ScrAnimation;
import gdx.menu.Screens.ScrAniHit;
import gdx.menu.Screens.ScrGame;



public class GamMenu extends Game {
    ScrMenu scrMenu;
    ScrPlay scrPlay;
    ScrSign scrSign;
    ScrAnimation scrAnimation;
    ScrAniHit scrAniHit;
    ScrGame scrGame;
    int nScreen; // 0 for menu, 1 for play, 2 for Sign, 3 for Animation, 4 for AniHit, 5 for Game
    
    public void updateState(int _nScreen) {
        nScreen = _nScreen;
        if ( nScreen == 0) {
            setScreen(scrMenu);
        } else if (nScreen == 1) {
            setScreen(scrPlay);
        } else if (nScreen ==2) {
            setScreen(scrSign);
        } else if (nScreen == 3){
            setScreen(scrAnimation);
        } else if (nScreen == 4){
            setScreen(scrAniHit);
        } else if (nScreen == 5){
            setScreen(scrGame);
        }
    }

    @Override
    public void create() {
        nScreen = 0;
        // notice that "this" is passed to each screen. Each screen now has access to methods within the "game" master program
        scrMenu = new ScrMenu(this);
        scrPlay = new ScrPlay(this);
        scrSign = new ScrSign(this);
        scrAnimation = new ScrAnimation(this);
        scrAniHit = new ScrAniHit(this);
        scrGame = new ScrGame(this);
        updateState(0);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}