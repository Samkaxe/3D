package Input;

import java.awt.event.*;

public class InputHandler implements KeyListener , FocusListener , MouseListener , MouseMotionListener {

   public boolean[] key = new boolean[68836];
   // mouse movment
   public static  int mouseX ;
   public static  int mouseY ;

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        for(int i = 0 ; i< key.length ; i++){
          key[i] = false ;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int  keyCode = e.getKeyCode();
       if(keyCode > 0 && keyCode < key.length ){
           key[keyCode]  = true ;
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
       if(keyCode > 0 && keyCode < key.length){
           key[keyCode] = false ;
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // getX get the scales of the App screen not the actual screen
        // e.getXOnScreen()
    mouseX = e.getX();
    mouseY = e.getY();


    }
}
