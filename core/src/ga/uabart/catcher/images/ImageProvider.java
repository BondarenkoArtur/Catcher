package ga.uabart.catcher.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageProvider {
    private int SCREEN_WIDTH = 800;
    private int SCREEN_HEIGHT = 480;
    private TextureAtlas atlas;
    private TextureAtlas textAtlas;
    private Texture mainBackground;
    private String locale;

    public void loadAtlas() {
        atlas = new TextureAtlas(Gdx.files.internal("game.atlas"));
        mainBackground = new Texture(Gdx.files.internal("background.png"));
    }

    public void loadLanguage(String locale) {
        this.locale = locale;
        if (locale.equals("ru")) {
            Gdx.app.log(locale, "loaded");
            textAtlas = new TextureAtlas(Gdx.files.internal("text_images_ru.atlas"));
        }
        else {
            Gdx.app.log(locale, "loaded");
            textAtlas  = new TextureAtlas(Gdx.files.internal("text_images.atlas"));
        }
    }

    public void dispose(){
        if (atlas != null)
            atlas.dispose();
        if (textAtlas != null)
            textAtlas.dispose();
        if (mainBackground != null)
            mainBackground.dispose();
    }

    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Texture getMainBackground() {
        return mainBackground;
    }

    public TextureRegion getBall() {
        return atlas.findRegion("Ball");
    }

    public TextureRegion getFlagEn() {
        return atlas.findRegion("FlagEn");
    }

    public TextureRegion getFlagRu() {
        return atlas.findRegion("FlagRu");
    }

    public TextureRegion getFlagOverlay() {
        return atlas.findRegion("FlagOverlay");
    }

    public TextureRegion getYes() {
        return atlas.findRegion("Yes");
    }

    public TextureRegion getNo() {
        return atlas.findRegion("No");
    }

    public TextureRegion getButton() {
        return atlas.findRegion("Button");
    }

    public TextureRegion getStart() {
        return textAtlas.findRegion("Start");
    }

    public TextureRegion getKids() {
        return textAtlas.findRegion("KidsMode");
    }

    public TextureRegion getLogo() {
        return textAtlas.findRegion("Logo");
    }

}
