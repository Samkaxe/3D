package graph;

public class Render {
    public final int width ;
    public final int height ;
    public final int[] pixel ;

    public Render(int width, int height){
        this.width = width;
        this.height = height;
       pixel = new int[width * height];
    }
    public void draw( Render render ,int xOffset , int yOffset){
        for(int y = 0 ; y< render.height ; y++  ){
          int ypix = y + yOffset ;
          if(ypix < 0 || ypix >= Display.height  ){
              continue;
          }
            for(int x = 0 ; x < render.width ; x++  ){
                int xpix = x + xOffset ;
                if(xpix < 0 || xpix >= Display.width){
                    continue;
                }
                int alpha =  render.pixel[x+y*render.width];
                if(alpha > 0)
                pixel[xpix + ypix * width] = alpha ;
            }
        }
    }
}