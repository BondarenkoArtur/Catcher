package ga.uabart.catcher;

import com.badlogic.gdx.Game;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.screens.LangScreen;
import ga.uabart.catcher.screens.MenuScreen;

public class Catcher extends Game {

    private ImageProvider imageProvider;
    private LangScreen langScreen;
    private MenuScreen menuScreen;

    public void gotoLangScreen() {
        setScreen(new LangScreen(this));
    }
    public void gotoMenuScreen() {
        setScreen(new MenuScreen(this));
    }

    public ImageProvider getImageProvider() {
        return imageProvider;
    }

    public void loadLanguage(String locale){
        imageProvider.loadLanguage(locale);
    }

	@Override
	public void create () {
        imageProvider = new ImageProvider();
        imageProvider.loadAtlas();
        langScreen = new LangScreen(this);
        menuScreen = new MenuScreen(this);

        gotoLangScreen();
	}

    @Override
    public void dispose() {
        imageProvider.dispose();
        langScreen.dispose();
    }
}
