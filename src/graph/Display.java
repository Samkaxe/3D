package graph;

import Input.Controller;
import Input.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable {
    public static final int width = 800 ;
    public static final int  height = 600 ;
    public static final String title = "Demo";

    private Thread thread ;
    private Boolean running = false ;
    private Game game ;
    private BufferedImage img ;
    private Screen screen ;
    private int[] pixel ;

    private InputHandler inputHandler ;
    // location tracker
    private  int newX = 0 ;
    private int oldX = 0  ;
    private int frames ;

    public static void main(String[] args) {
        BufferedImage cursor = new BufferedImage(16 ,16 ,BufferedImage.TYPE_INT_ARGB);
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor ,new Point(0,0),"blank");
        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
        frame.getContentPane().setCursor(blank);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        display.start();


    }
    // constructue
    public Display(){

    Dimension size = new Dimension(width,height);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    screen = new Screen(width ,height);
    game = new Game();
    img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    pixel = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    inputHandler = new InputHandler();

    addKeyListener(inputHandler);
    addFocusListener(inputHandler);
    addMouseListener(inputHandler);
    addMouseMotionListener(inputHandler);
    }

    public void start(){
        if(running)
            return;
        running = true ;
        thread = new Thread(this);
        thread.start();
    }


    public void run() {
        int fps = 0 ;
        double unprocessedsecunds = 0 ;
        double secondspertick = 1 / 60.0 ;
        int tickcount = 0 ;
        boolean ticked = false ;
        Long privouestime = System.nanoTime();
        while(running){
         long currnttime = System.nanoTime();
         long passedtime = currnttime - privouestime ;
         privouestime = currnttime ;
         unprocessedsecunds += passedtime/ 1000_000_000.0 ;
         requestFocus();
         while (unprocessedsecunds > secondspertick){
             tick();
             unprocessedsecunds -= secondspertick ;
             ticked = true ;
             tickcount++ ;
             if(tickcount % 60 ==0){
                 System.out.println(fps);
                frames = fps ;
                 privouestime += 1000 ;
                 fps = 0 ;
             }
         }
         if(ticked){
             render();
             fps++ ;
         }

         render();
         fps++ ;
         // tracking thr oldx and replace it by the newX
         newX = InputHandler.mouseX;
         if(newX > oldX){
             Controller.turnright =true ;
         }

        if(newX < oldX){
            Controller.turnleft = true ;
        }
        if(newX == oldX){
            Controller.turnleft = false ;
            Controller.turnright = false ;
        }
        oldX = newX ;
          //  System.out.println("X "+ InputHandler.mouseX);
            //System.out.println("Y "+InputHandler.mouseY);
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            // set on 2 if you are seeking 2D game
           createBufferStrategy(3);
           return;
        }

        screen.render(game);

        for(int i = 0 ; i< width * height ; i++ ){
            pixel[i] = screen.pixel[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img ,0 ,0 ,width ,height,null);
        g.setFont(new Font("Verdana",2,30));
        g.setColor(Color.yellow);
        g.drawString(frames+ "FPS",680,580);
        g.dispose();
        bs.show();
    }

    private void tick() {
        game.tick(inputHandler.key);
    }

    public void stop(){
       if(!running)
           return;
       running = false ;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
