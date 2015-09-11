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

public class MenuScreen implements Screen, InputProcessor{

    private String TAG = MenuScreen.class.getName();

    private ImageProvider imageProvider;

    private OrthographicCamera camera;

    private Button[] buttons;

    private Texture background;
    private TextureRegion logo;
    private SpriteBatch batch;
    private Catcher game;
    private int logoX;
    private int logoY;

    public MenuScreen(Catcher game) {
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
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);

        for(int i=0;i<buttons.length;i++) {
            if (buttons[i].isPressed(touchPos)) {
                Gdx.app.log(TAG, "Button " + (i+1) + " pressed");
                switch (i) {
                    case 0:
                        game.loadLanguage("en");
                        game.gotoMenuScreen();
                        break;
                    case 1:
                        game.loadLanguage("ru");
                        game.gotoMenuScreen();
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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

        buttons = new Button[2];
        TextureRegion buttonBg = imageProvider.getButton();
        buttons[0] = new Button(buttonBg, imageProvider.getStart());
        buttons[1] = new Button(buttonBg, imageProvider.getKids());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());

        batch = new SpriteBatch();

        logo = imageProvider.getLogo();
        logoX = (imageProvider.getScreenWidth() - logo.getRegionWidth()) / 2;
        logoY = (imageProvider.getScreenHeight() - logo.getRegionHeight() - 10)-50;

        buttons[0].setPos(275, 200);
        buttons[1].setPos(275, 100);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(logo, logoX, logoY);
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
