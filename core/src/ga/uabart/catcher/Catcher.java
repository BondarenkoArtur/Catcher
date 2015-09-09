package ga.uabart.catcher;

import com.badlogic.gdx.Game;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.screens.LangScreen;

public class Catcher extends Game {

    private ImageProvider imageProvider;
    private LangScreen langScreen;

    public void gotoLangScreen() {
        setScreen(new LangScreen(this));
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

        gotoLangScreen();
	}

    @Override
    public void dispose() {
        imageProvider.dispose();
        langScreen.dispose();
    }
}
