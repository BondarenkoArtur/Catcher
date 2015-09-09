package ga.uabart.catcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import ga.uabart.catcher.Catcher;

public class DesktopLauncher {
	public static void main (String[] arg) {
        // Create two run configurations
        // 1. For texture packing. Pass 'texturepacker' as argument and use desktop/src
        //    as working directory
        // 2. For playing game with android/assets as working directory
        if (arg.length == 1 && arg[0].equals("texturepacker")) {
            String outdir = "android/assets";
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            TexturePacker.process(settings, "images/game", outdir, "game");
            TexturePacker.process(settings, "images/text-images", outdir, "text_images");
            TexturePacker.process(settings, "images/text-images-ru", outdir, "text_images_ru");
        }
        else {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.title = "Catcher";
            config.width = 800;
            config.height = 480;
            new LwjglApplication(new Catcher(), config);
        }
	}
}
