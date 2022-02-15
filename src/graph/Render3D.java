package graph;

public class Render3D extends Render {

    public double[] zBuffer ;
    private double renderDictance = 5000 ;
    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
    }

    public void floor(Game game){

        double floorpostion = 8 ;
        double ceelingpostion = 8 ;
        double forward = game.controls.z ;
        double right = game.controls.x ;
        double rotation = game.controls.rotation;
        double cosine  = Math.cos(rotation);
        double sine = Math.sin(rotation);

       for( int y = 0 ; y < height ; y++ ){
           double celling = (y + -height / 2.0) / height ;

            double  z = floorpostion  / celling ;

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
             zBuffer[ x + y * width] = z ;
             pixel[ x + y * width] = Texture.floor.pixel[(xpic & 7) + (ypic & 7) * 8 ] ;
                //((xpic & 15) * 16) | ((ypic & 15 ) * 16) << 8
             if(z > 500){
                   pixel[ x + y * width]  = 0 ;
               }
           }

       }
    }

    public void renderDictanceLimiter(){
        for(int i = 0 ; i < width * height ; i++){
            int colour = pixel[i] ;
            int brightness = (int) (renderDictance / (zBuffer[i]));

            // brightness can go below zero
            if(brightness < 0){
                brightness = 0 ;
            }
            if(brightness > 255){
                brightness = 255 ;
            }
            int r = (colour >> 16) & 0xff ;
            int g = (colour >> 8) & 0xff ;
            int b = (colour ) & 0xff ;

            r = r * brightness / 255;
            g = g * brightness / 255 ;
            b = b * brightness / 255 ;
            pixel[i] = r << 16 | g << 8 | b ;
        }
    }
}
