package ga.uabart.catcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import ga.uabart.catcher.Catcher;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.sound.SoundManager;

public class GameScreen implements Screen, InputProcessor {

    private SpriteBatch batch;

    private OrthographicCamera camera;

    private Catcher game;

    private ImageProvider imageProvider;
    private Texture background;
    private TextureRegion isAccel;
    private TextureRegion flag;
    private TextureRegion ball;

    private float startRot = 0;

    private float startX = 0, startY = 0;

    private BitmapFont font;

    private SoundManager soundManager;

    public GameScreen(Catcher game) {
        super();
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
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
        boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if (available) {
            isAccel = imageProvider.getYes();
        } else {
            isAccel = imageProvider.getNo();
        }
        flag = imageProvider.getFlagEn();
        ball = imageProvider.getBall();

        font = new BitmapFont(Gdx.files.internal("fonts/poetsen.fnt"),
                Gdx.files.internal("fonts/poetsen.png"), false);

        soundManager = game.getSoundManager();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(isAccel, 50, 50);
        Matrix4 matrix = new Matrix4();
        Gdx.input.getRotationMatrix(matrix.val);
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();

        float movementRot = (accelY - startRot) * 0.1f + startRot;

        float movementX = startX + accelY * 0.5f;
        float movementY = startY - accelX * 0.5f;
        if (movementX > 800) movementX = 800;
        if (movementX < 0) movementX = 0;
        if (movementY > 480) movementY = 480;
        if (movementY < 0) movementY = 0;
        batch.draw(ball, movementX, movementY);

        batch.draw(flag, 300, 200, 250, 132, 250, 132, 1, 1, movementRot * 9.5f);
        font.draw(batch, "XYZ:" + accelX + "|" + accelY + "|" + accelZ, 5, 470);
        batch.end();
        startRot = movementRot;
        startX = movementX;
        startY = movementY;
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

    }
}
