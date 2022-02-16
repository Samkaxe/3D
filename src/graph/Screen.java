package graph;

import java.util.Random;

public class Screen extends Render {
    private Render test;
    private Render3D render3D ;
    public Screen(int width, int height) {
        super(width, height);
        render3D = new Render3D(width ,height);
        Random random = new Random();
        test = new Render(256,256);

        for(int i = 0 ; i < 256*256 ;i++){
            test.pixel[i] = random.nextInt() * (random.nextInt(5)/4);

        }
    }

    public void render( Game game){
        for(int i = 0 ; i < width*height ; i++){
            pixel[i] = 0 ;
        }
        for(int i = 0 ; i < 100 ; i++) {
            //Math.sin((System.currentTimeMillis()+i *8) % 2000.0 / 2000 * Math.PI * 2) * 200
            //(System.currentTimeMillis()+i *8) % 2000.0 / 2000 * Math.PI * 2
            int anim =  (int) (Math.sin((game.time + i * 2 ) % 1000.0 / 100) * 100) ;
            int animy = (int) (Math.cos((game.time + i * 2 ) % 1000.0 / 100) * 100);
           // draw(test, (width - 256) / 2 + anim, (height - 256) / 2 + animy);
        }
        render3D.floor(game);

        render3D.renderDictanceLimiter();
       // render3D.walls();
        draw(render3D ,0 , 0);
    }
}
