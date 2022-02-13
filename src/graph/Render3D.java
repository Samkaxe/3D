package graph;

public class Render3D extends Render {

    public Render3D(int width, int height) {
        super(width, height);
    }
    double time = 0 ;
    public void floor(){
       for( int y = 0 ; y < height ; y++ ){
           double celling = (y - height / 2.0) / height ;
                if(celling < 0 ){
                 celling = -celling ;
                }
           double z = 8 / celling ;
           time += 0.00005 ;
           for(int x = 0 ; x < width ; x++){
            double dipth = ( x - width / 2.0) / height ;
            dipth *= z ;
             double xx =  dipth ; // the and oprator  bitwise oprator
             double yy = z + time ;
             int xpic = (int) (xx);
             int ypic = (int) (yy);
             pixel[ x + y * width] = ((xpic & 15) * 16) | ((ypic & 15 ) * 16) << 8 ;
           }
       }
    }
}
