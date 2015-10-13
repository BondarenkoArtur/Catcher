package ga.uabart.catcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.screens.GameScreen;
import ga.uabart.catcher.screens.LangScreen;
import ga.uabart.catcher.screens.MenuScreen;
import ga.uabart.catcher.screens.ServerScreen;
import ga.uabart.catcher.sound.SoundManager;

public class Catcher extends Game {

    private ImageProvider imageProvider;
    private LangScreen langScreen;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private ServerScreen serverScreen;
    private SoundManager soundManager;


    public void gotoLangScreen() {
        setScreen(new LangScreen(this));
    }
    public void gotoMenuScreen() {
        setScreen(new MenuScreen(this));
    }
    public void gotoServerScreen() {
        setScreen(new ServerScreen(this));
    }
    public void gotoGameScreen() {
        setScreen(new GameScreen(this));
    }

    public ImageProvider getImageProvider() {
        return imageProvider;
    }

    public void loadLanguage(String locale){
        long startTime = System.currentTimeMillis();
        imageProvider.loadLanguage(locale);
        Gdx.app.log("Time", "Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");
    }

	@Override
	public void create () {
        imageProvider = new ImageProvider();
        imageProvider.loadAtlas();
        langScreen = new LangScreen(this);
        menuScreen = new MenuScreen(this);
        soundManager = new SoundManager();

        gotoLangScreen();
	}

    @Override
    public void dispose() {
        imageProvider.dispose();
        langScreen.dispose();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}
