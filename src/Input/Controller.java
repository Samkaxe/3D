package Input;

public class Controller {
    public double x , z , rotation ,xa , za , rotationa ;

    public void tick(boolean forward , boolean back ,boolean left ,boolean right , boolean turnleft , boolean turnright ){
        double rotationspeed = 1.0 ;
        double walkspeed = 1.0 ;
        double xmove = 0 ;
        double zmove = 0 ;
        if(forward) {
            zmove++;
        }
        if(back){
            zmove--;}
        if(right){
            xmove++;}
        if(left){
            xmove--;}
        if(turnleft){
            rotationa += rotationspeed;}
        if(turnright){
            rotationa -= rotationspeed ;}

        xa += (xmove * Math.cos(rotation) + zmove + Math.sin(rotation)) * walkspeed;
        za += (zmove * Math.cos(rotation)  - xmove + Math.sin(rotation)) * walkspeed;
        x += xa ;
        z += za ;

        xa *= 0.1;
        za *= 0.1 ;
        rotation += rotationa ;
        rotationa *= 0.8 ;

    }
}
