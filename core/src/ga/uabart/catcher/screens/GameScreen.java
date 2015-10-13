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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import ga.uabart.catcher.Catcher;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.sound.SoundManager;
import javafx.geometry.Orientation;

public class GameScreen implements Screen, InputProcessor {

    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;

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

    private String message = "Do something already!";
    private float highestY = 0.0f;

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

        font = new BitmapFont(Gdx.files.internal("fonts/Mono.fnt"),
                Gdx.files.internal("fonts/Mono.png"), false);

        soundManager = game.getSoundManager();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());

        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        float deviceAngle = Gdx.input.getRotation();
        Input.Orientation orientation = Gdx.input.getNativeOrientation();
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();
        float azimuth = Gdx.input.getAzimuth();
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        if(accelY > highestY)
            highestY = accelY;
        message = "Device rotated to:" + Float.toString(deviceAngle) + " degrees\n";
        message += "Device orientation is ";
        switch(orientation){
            case Landscape:
                message += " landscape.\n";
                break;
            case Portrait:
                message += " portrait. \n";
                break;
            default:
                message += " complete crap!\n";
                break;
        }

        message += "Device Resolution: " + Integer.toString(w) + "," + Integer.toString(h) + "\n";
        message += "XYZ:" + accelX + "|" + accelY + "|" + accelZ + " \n";
        message += "Highest Y value: " + Float.toString(highestY) + " \n";
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)){
            if(accelY < -7){
                Gdx.input.vibrate(100);
            }
        }

        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass)){
            message += "Azmuth:" + Float.toString(azimuth) + "\n";
            message += "Pitch:" + Float.toString(Gdx.input.getPitch()) + "\n";
            message += "Roll:" + Float.toString(Gdx.input.getRoll()) + "\n";
        }
        else{
            message += "No compass available\n";
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float movementRot = (accelY - startRot) * 0.1f + startRot;

        float movementX = startX + accelY * 0.5f;
        float movementY = startY - accelX * 0.5f;
        if (movementX > 800) movementX = 800;
        if (movementX < 0) movementX = 0;
        if (movementY > 480) movementY = 480;
        if (movementY < 0) movementY = 0;

        batch.draw(background, 0, 0);

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rectLine(400, 240, 400 - MathUtils.cos(azimuth * MathUtils.PI / 180) * 200, 240 - MathUtils.sin(azimuth * MathUtils.PI / 180) * 200, 10);

        batch.draw(isAccel, 50, 50);

        batch.draw(ball, movementX, movementY);

        //batch.draw(flag, 300, 200, 250, 132, 250, 132, 1, 1, movementRot * 9.5f);
        //font.draw(batch, "XYZ:" + accelX + "|" + accelY + "|" + accelZ, 5, 470);

        font.draw(batch, message, 0, 480);
        batch.end();
        shapeRenderer.end();

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
        batch.dispose();
        font.dispose();
    }
}
