package ga.uabart.catcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import ga.uabart.catcher.Catcher;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.view.Button;

/**
 * Created by Arthur on 9/9/2015.
 */
public class LangScreen implements Screen, InputProcessor{

    private String TAG = LangScreen.class.getName();

    private ImageProvider imageProvider;

    private OrthographicCamera camera;

    private Button[] buttons;

    private Texture background;
    private SpriteBatch batch;
    private Catcher game;

    public LangScreen(Catcher game) {
        super();
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            Gdx.app.exit();
            return true;
        }
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);

        for(int i=0;i<buttons.length;i++) {
            if (buttons[i].isPressed(touchPos)) {
                Gdx.app.log(TAG, "Button " + (i+1) + " pressed");
                switch (i) {
                    case 0: game.loadLanguage("en");
                        break;
                    case 1: game.loadLanguage("ru");
                        break;
                    default:
                        Gdx.app.exit();
                }
                break;
            }
        }
        return true;
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

    @Override
    public void show() {
        imageProvider = game.getImageProvider();
        background = imageProvider.getMainBackground();
        TextureRegion flagEn = imageProvider.getFlagEn();
        TextureRegion flagRu = imageProvider.getFlagRu();
        TextureRegion flagOverlay = imageProvider.getFlagOverlay();
        buttons = new Button[2];
        buttons[0] = new Button(flagEn);
        buttons[1] = new Button(flagRu);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());
        batch = new SpriteBatch();
        buttons[0].setPos(100, 200);
        buttons[1].setPos(450, 200);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        for (Button button : buttons) {
            button.draw(batch);
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
        if (batch != null) {
            batch.dispose();
        }
    }
}
