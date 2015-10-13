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
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import ga.uabart.catcher.Catcher;
import ga.uabart.catcher.images.ImageProvider;
import ga.uabart.catcher.network.SomeRequest;
import ga.uabart.catcher.network.SomeResponse;
import ga.uabart.catcher.view.Button;

import java.io.IOException;

public class ServerScreen implements Screen, InputProcessor{

    private String TAG = ServerScreen.class.getName();

    private ImageProvider imageProvider;

    private OrthographicCamera camera;

    private Button[] buttons;

    private BitmapFont font;

    private String message = "Do something already!";

    private Texture background;
    private SpriteBatch batch;
    private Catcher game;
    private int logoX;
    private int logoY;

    public ServerScreen(Catcher game) {
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
                        Server server = new Server();
                        Kryo kryoServer = server.getKryo();
                        kryoServer.register(SomeRequest.class);
                        kryoServer.register(SomeResponse.class);
                        server.start();
                        try {
                            server.bind(54555, 54777);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        message = "Server started 54555 54777";
                        server.addListener(new Listener() {
                            public void received (Connection connection, Object object) {
                                if (object instanceof SomeRequest) {
                                    SomeRequest request = (SomeRequest)object;
                                    System.out.println(request.text);
                                    message += "\n" + request.text;

                                    SomeResponse response = new SomeResponse();
                                    response.text = "\nThanks";
                                    connection.sendTCP(response);
                                }
                            }
                        });
                        break;
                    case 1:
                        Client client = new Client();
                        Kryo kryoClient = client.getKryo();
                        kryoClient.register(SomeRequest.class);
                        kryoClient.register(SomeResponse.class);
                        client.start();
                        try {
                            client.connect(5000, "10.42.0.12", 54555, 54777);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        message = "Client 10.42.0.12 54555 54777";

                        SomeRequest request = new SomeRequest();
                        request.text = "\nHere is the request";
                        client.sendTCP(request);

                        client.addListener(new Listener() {
                            public void received (Connection connection, Object object) {
                                if (object instanceof SomeResponse) {
                                    SomeResponse response = (SomeResponse)object;
                                    System.out.println(response.text);
                                    message += response.text;
                                }
                            }
                        });


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

        font = new BitmapFont(Gdx.files.internal("fonts/Mono.fnt"),
                Gdx.files.internal("fonts/Mono.png"), false);

        buttons = new Button[2];
        TextureRegion buttonBg = imageProvider.getButton();
        buttons[0] = new Button(buttonBg, imageProvider.getStart());
        buttons[1] = new Button(buttonBg, imageProvider.getKids());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, imageProvider.getScreenWidth(), imageProvider.getScreenHeight());

        batch = new SpriteBatch();

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
        for (Button button : buttons) {
            button.draw(batch);
        }
        font.draw(batch, message, 0, 480);
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
