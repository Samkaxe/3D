package graph;

public class Render3D extends Render {

    public Render3D(int width, int height) {
        super(width, height);
    }

    public void floor(Game game){

        double floorpostion = 8 ;
        double ceelingpostion = 8 ;

        double forward = game.controls.z ;
        double right = game.controls.x ;

        double rotation = 0;

        double cosine  = Math.cos(rotation);
        double sine = Math.sin(rotation);

       for( int y = 0 ; y < height ; y++ ){
           double celling = (y - height / 2.0) / height ;

            double  z = floorpostion  / -celling ;
                if(celling < 0 ){
                 z = ceelingpostion / -celling ;
                }

           for(int x = 0 ; x < width ; x++){
            double dipth = ( x - width / 2.0) / height ;
            dipth *= z ;
             double xx =  dipth * cosine + z * sine ; // the and oprator  bitwise oprator
             double yy = z * cosine - dipth * sine ;
             int xpic = (int) (xx + right  );
             int ypic = (int) (yy + forward );
             pixel[ x + y * width] = ((xpic & 15) * 16) | ((ypic & 15 ) * 16) << 8 ;
           }
       }
    }
}
