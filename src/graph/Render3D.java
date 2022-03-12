package graph;

import Input.Controller;

import java.util.ArrayList;
import java.util.Random;

public class Render3D extends Render {

    public double[] zBuffer ;
    private double renderDictance = 5000 ;
    private double forward , right , cosine , sine , up ,walking ;

    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
    }

    public void floor(Game game){
                    // old value 8 //Math.sin(game.time / 10) + 10
        double floorpostion =  8 ;
        double ceelingpostion = 8 ;
         forward = game.controls.z ;
         right = game.controls.x ;
         up = game.controls.y ;
         walking =0 ;
        double rotation = game.controls.rotation;
        cosine  = Math.cos(rotation);
        sine = Math.sin(rotation);
        // walking = Math.sin(game.time / 6.0) * 0.5 ;

        if(Controller.crouchwalk){
            walking = Math.sin(game.time / 6.0) * 0.25 ;
        }
        if(Controller.runwalk){
            walking = Math.sin(game.time / 6.0) * 0.8 ;
        }


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
   // THIS IS FREAKING USELESSS  maybe zahra cna upgrade it
    /*
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

     */
    public void renderWalls(double xLeft , double xRight , double zDistance ,double yHeight) {
     // x left , right repesnet the wall postion  , y the hight of the wall , and z is the depth
    double xcLeft = ((xLeft) - right) * 2;
    double zcLeft = ((zDistance) - forward) * 2 ;

    double rotLeftSideX = xcLeft * cosine - zcLeft * sine ; // checked
    double yCornerTL = ((-yHeight)- up)* 2 ;
    double yCornerBl = ((+0.5 - yHeight)- up) * 2 ; // checked
    double rotLeftSideZ = zcLeft * cosine + xcLeft * sine ;

    double xcRight = ((xRight) - right) * 2 ;
    double zcRight = ((zDistance) - forward) * 2 ;

    double rotRightSideX = xcRight * cosine - zcRight * sine;

    double yCornerTR = ((-yHeight) - up) * 2 ;
    double yCornerBR = ((+0.5 - yHeight) - up) * 2 ;
    double rotRightSidez = zcRight *  cosine + xcRight * sine ;

    double xPixelLeft =(rotLeftSideX / rotLeftSideZ * height + width / 2 );
    double xPixelRight = (rotRightSideX / rotRightSidez * height + width / 2) ;

   if(xPixelLeft >= xPixelRight){
      //  return;
        // method doesnot return anything
        // but this return will skip this method is case no more rander
        }

    int xPixelLeftint = (int) (xPixelLeft);
    int xPixelRightInt = (int) (xPixelRight);

    if(xPixelLeftint < 0 ){
        xPixelLeftint = 0;
    }
    if(xPixelRightInt > width){
        xPixelRightInt = width ;
    }

    double yPixelLeftTop =  yCornerTL / rotLeftSideZ * height + height / 2.0;
    double yPixelLeftBottun =  yCornerBl / rotLeftSideZ * height + height / 2.0;
    double yPixelRightTOP =  yCornerTR / rotRightSidez * height + height / 2.0 ;
    double yPixelRightBottun =  yCornerBR / rotRightSidez * height + height / 2.0;

        double tex1 = 1 / rotLeftSideZ ;
        double tex2 = 1 / rotRightSidez ;
        double tex3 = 0 / rotLeftSideZ ;
        double tex4 = 8 /  rotRightSidez - tex3 ;
    for(int x = xPixelLeftint ; x <  xPixelRightInt ; x++ ){

      double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
        // texture got motion problem when moving
      int xTexture = (int) ((tex3 + tex4 * pixelRotation)/ (tex1 + (tex2 - tex1) * pixelRotation));

      double yPixelTop = yPixelLeftTop + (yPixelRightTOP - yPixelLeftTop) * pixelRotation;
      double yPixelBottum = yPixelLeftBottun + (yPixelRightBottun - yPixelLeftBottun) * pixelRotation ;

      int  ypixelTopint = (int) ( yPixelTop);
      int yPixelBottomint = (int)  (yPixelBottum);

        if (ypixelTopint < 0) {
            ypixelTopint = 0 ;
        }
        if (yPixelBottomint > height) {
            yPixelBottomint = height ;
        }
        // add rgb
        int yellow  = 0xd9e01b ;
        int blue  = 0x4287f5 ;
        ArrayList<Integer> coloers = new ArrayList<>();
        coloers.add(blue);
        coloers.add(yellow);
        for(int y = ypixelTopint ; y < yPixelBottomint ; y++){
                    double pixelRotationY = (y - yPixelTop)/ (yPixelBottum - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                pixel[x + y * width] = xTexture * 100 + yTexture * 100 * 256 ;
            zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;
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
