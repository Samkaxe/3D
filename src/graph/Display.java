package graph;

import javax.swing.*;
import java.awt.*;
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

    public static void main(String[] args) {
        Display display = new Display();
        JFrame frame = new JFrame();
        frame.add(display);
        frame.pack();
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
         while (unprocessedsecunds > secondspertick){
             tick();
             unprocessedsecunds -= secondspertick ;
             ticked = true ;
             tickcount++ ;
             if(tickcount % 60 ==0){
                 System.out.println(fps + "frame per secunds");
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
        g.dispose();
        bs.show();
    }

    private void tick() {
        game.tick();
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
