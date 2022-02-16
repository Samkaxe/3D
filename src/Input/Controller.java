package Input;

public class Controller {
    public double x , z ,y , rotation ,xa , za , rotationa ;
    public static  boolean turnleft  = false;
    public static boolean turnright = false;
    public static  boolean walk = false ;
    public static  boolean crouchwalk = false ;
    public static boolean runwalk = false ;

    public void tick(boolean forward , boolean back ,boolean left ,boolean right ,boolean jump , boolean crouch , boolean run){
        double rotationspeed = 0.025 ;
        double walkspeed = 0.5 ;
        double jumpheight = 0.5 ;
        double crouchheight = 0.3 ; // prone = 0.75 ;
        double xmove = 0 ;
        double zmove = 0 ;


        if(forward) {
            zmove++;
            walk = true;
        }
        if(back){
            zmove--;
            walk = true;
        }
        if(right){
            xmove++;
            walk = true;
        }
        if(left){
            xmove--;
            walk = true;
        }
        if(jump){
            y += jumpheight ;
        }

        if(crouch){
            y -= crouchheight ;
            run = false ;
            crouchwalk = true ;
            walkspeed = 0.2 ;
        }
        if(run){
            walkspeed = 2 ;
            walk = true;
            runwalk = true ;
        }
        if(!run){
            runwalk = false ;
        }

        if(turnleft){
            rotationa -= rotationspeed;

        }
        if(turnright){
            rotationa += rotationspeed ;

        }
        if(!forward && !back && !left && !right){
             walk = false ;
        }
        if(!crouchwalk){
            crouchwalk = false ;
        }
        xa += (xmove * Math.cos(rotation) + zmove * Math.sin(rotation)) * walkspeed;
        za += (zmove * Math.cos(rotation)  - xmove * Math.sin(rotation)) * walkspeed;

        x += xa ;
        y *= 0.9 ;
        z += za ;

        xa *= 0.1;
        za *= 0.1 ;

        rotation += rotationa ;
        rotationa *= 0.0001 ;



    }
}
