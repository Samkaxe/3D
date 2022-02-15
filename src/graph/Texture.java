package graph;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {
    // load th image and return the image

    public static  Render floor = loadBitmap("/res/brick.png");

    private static Render loadBitmap(String filename) {
        try{
            BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
            int width = image.getWidth();
            int height = image.getHeight();
            Render result = new Render(width , height );
            // get the image set it in array , in pisel array
            image.getRGB(0 ,0,width ,height ,result.pixel , 0 , width);
            return result ;

        }catch (IOException e) {
            System.out.println("crash");
            throw new RuntimeException();
        }
    }
}
