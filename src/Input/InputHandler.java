package Input;

import java.awt.event.*;

public class InputHandler implements KeyListener  {

   public boolean[] key = new boolean[68836];

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
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

}
