package graph;

import Input.Controller;

import java.util.Random;

public class Render3D extends Render {

    public double[] zBuffer ;
    private double renderDictance = 6000 ;
    private double forwardGlobal ;

    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
    }

    public void floor(Game game){
                    // old value 8 //Math.sin(game.time / 10) + 10
        double floorpostion =  10 ;
        double ceelingpostion = 20 ;
        double forward = game.controls.x ;  //game.controls.z ;
        forwardGlobal = forward ;
        double right = game.controls.x ;
        double up = game.controls.y ;
        double walking = Math.sin(game.time / 6.0) * 0.5 ;
        if(Controller.crouchwalk){
            walking = Math.sin(game.time / 6.0) * 0.25 ;
        }
        if(Controller.runwalk){
            walking = Math.sin(game.time / 6.0) * 0.8 ;
        }
        double rotation =  game.controls.rotation;
        double cosine  = Math.cos(rotation);
        double sine = Math.sin(rotation);


       for( int y = 0 ; y < height ; y++ ){
           double celling = (y + -height / 2.0) / height ;

            double  z = (floorpostion + up )  / celling ;
            if(Controller.walk == true){
                  z = (floorpostion + up + walking )  / celling ;
            }
                if(celling < 0 ){
                 z = (ceelingpostion - up  ) / -celling ;
                 if(Controller.walk){
                     z = (ceelingpostion - up - walking  ) / -celling ;
                 }
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
    // render 3D render the wall in screen class
    public void walls(){
        Random random = new Random(100);
        for(int i = 0 ; i < 20000 ; i++  ){
            double xx = random.nextDouble() -1;
            double yy = random.nextDouble();
            double zz = 2 - forwardGlobal / 16 ;

            int xPixel = (int) (xx/ zz * height / 2  + width /2 );
            int yPixel = (int) (yy / zz * height  /2  + height /2 );

            if( xPixel >= 0 && yPixel >= 0  && xPixel < width && yPixel < height )
                pixel[xPixel + yPixel * width] = 0xfffff ;
        }
        for(int i = 0 ; i < 20000 ; i++  ){
            double xx = random.nextDouble() -1 ;
            double yy = random.nextDouble() -1;
            double zz = 2 -forwardGlobal / 16 ;

            int xPixel = (int) (xx/ zz * height / 2  + width /2 );
            int yPixel = (int) (yy / zz * height  /2  + height /2 );

            if( xPixel >= 0 && yPixel >= 0  && xPixel < width && yPixel < height )
                pixel[xPixel + yPixel * width] = 0xfffff ;
        }
        for(int i = 0 ; i < 20000 ; i++  ){
            double xx = random.nextDouble();
            double yy = random.nextDouble()-1;
            double zz = 2 -forwardGlobal / 16 ;

            int xPixel = (int) (xx/ zz * height / 2  + width /2 );
            int yPixel = (int) (yy / zz * height  /2  + height /2 );

            if( xPixel >= 0 && yPixel >= 0  && xPixel < width && yPixel < height )
                pixel[xPixel + yPixel * width] = 0xfffff ;
        }
        for(int i = 0 ; i < 20000 ; i++  ){
            double xx = random.nextDouble();
            double yy = random.nextDouble();
            double zz = 2 -forwardGlobal / 16 ;

            int xPixel = (int) (xx/ zz * height / 2  + width /2 );
            int yPixel = (int) (yy / zz * height  /2  + height /2 );

            if( xPixel >= 0 && yPixel >= 0  && xPixel < width && yPixel < height )
                pixel[xPixel + yPixel * width] = 0xfffff ;
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
