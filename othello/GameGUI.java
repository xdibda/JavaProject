/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.io.IOException;

/**
 *
 * @author Lukáš
 */
public class GameGUI extends Canvas implements Runnable
{
    public static final String TITLE = "2D Othello Game";
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int GAP_SIZE = 2;
    public static final int FIELD_6_SIZE = 115;
    public static final int FIELD_8_SIZE = 86;
    public static final int FIELD_10_SIZE = 68;
    public static final int FIELD_12_SIZE = 56;
    
    private boolean running = false;
    
    public boolean computerPlayer;
    public boolean easy;
    public boolean finished;
    private Thread thread;
    
    private final BufferedImage image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
    private BufferedImage menuBackground = null;
    private BufferedImage background = null;
    
    public BufferedImage imgBlackDisk = null;
    public BufferedImage imgWhiteDisk = null;
    
    public BufferedImage imgEmptyField_6 = null;
    public BufferedImage imgEmptyField_8 = null;
    public BufferedImage imgEmptyField_10 = null;
    public BufferedImage imgEmptyField_12 = null;
    
    public BufferedImage imgWhiteField_6 = null;
    public BufferedImage imgWhiteField_8 = null;
    public BufferedImage imgWhiteField_10 = null;
    public BufferedImage imgWhiteField_12 = null;
    
    public BufferedImage imgBlackField_6 = null;
    public BufferedImage imgBlackField_8 = null;
    public BufferedImage imgBlackField_10 = null;
    public BufferedImage imgBlackField_12 = null;
    
    private Controller controller = null;
    private Player p;
    
    private MenuPage pgMenu;
    private CreateGamePage pgCreate;
    private GamePage pgGame;
    private CreditsPage pgCredits;
    private HowToPage pgHowTo;
            
    public JOptionPane op;
    static JFrame frame;
    public static Graphics g;
    
    public static enum STATE
    {
        MENU,
        CREATE,
        GAME,
        SAVE,
        CREDITS,
        HOWTO
    };
    
    private STATE state = STATE.MENU;
    
    private int boardSize = 0;    
    private String[] gameInfo;
    
    public void initialize()
    {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();

        try
        {
            menuBackground = loader.loadImage("/resources/menubg.jpg");
            background = loader.loadImage("/resources/bg.jpg");
            imgBlackDisk = loader.loadImage( "/resources/black_disk.png");
            imgWhiteDisk = loader.loadImage( "/resources/white_disk.png");

            imgEmptyField_6 = loader.loadImage( "/resources/empty_field_6.png");
            imgEmptyField_8 = loader.loadImage( "/resources/empty_field_8.png");
            imgEmptyField_10 = loader.loadImage( "/resources/empty_field_10.png");
            imgEmptyField_12 = loader.loadImage( "/resources/empty_field_12.png");

            imgWhiteField_6 = loader.loadImage( "/resources/white_field_6.png");
            imgWhiteField_8 = loader.loadImage( "/resources/white_field_8.png");
            imgWhiteField_10 = loader.loadImage( "/resources/white_field_10.png");
            imgWhiteField_12 = loader.loadImage( "/resources/white_field_12.png");

            imgBlackField_6 = loader.loadImage( "/resources/black_field_6.png");
            imgBlackField_8 = loader.loadImage( "/resources/black_field_8.png");
            imgBlackField_10 = loader.loadImage( "/resources/black_field_10.png");
            imgBlackField_12 = loader.loadImage( "/resources/black_field_12.png");
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        controller = new Controller();
        
        pgMenu = new MenuPage();
        pgCreate = new CreateGamePage( this );
        pgGame = new GamePage( this );
        pgCredits = new CreditsPage();
        pgHowTo = new HowToPage();
        
        setBoardSize( 8 );
        
        this.addMouseListener( new MenuButtonListener( this ) );
    }
    
    private synchronized void start()
    {
        if ( running )
            return;
        
        running = true;
        thread = new Thread( this );
        thread.start();
    }
    
    private synchronized void stop()
    {
        if ( !running )
            return;
        running = false;
        
        try
        {
            thread.join();
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
        System.exit( 1 );
    }
    
    @Override
    public void run()
    {
        initialize();
        
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        
        while ( running )
        {
            long now = System.nanoTime();
            delta += ( now - lastTime ) / ns;
            lastTime = now;
            if ( delta >= 1 )
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
            
            if ( System.currentTimeMillis() - timer > 1000 )
            {
                timer += 1000;
//                System.out.println( "FPS: " + frames + " updates: " + updates );
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }
    
    // TODO animations (maybe)
    private void tick()
    {
        if ( state == STATE.GAME )
        {
            // render...
        }
    }
    
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        
        if ( bs == null )
        {
            createBufferStrategy( 3 );
            return;
        }
        
        g = bs.getDrawGraphics();
        //---------------
        
        g.drawImage( image, 0, 0, getWidth(), getHeight(), this );
        
        g.drawImage( background, 0, 0, null );
        
        switch( state )
        {
            case MENU:
            {
                g.drawImage( menuBackground, 0, 0, null );
                pgMenu.render( g );
                break;
            }
            case CREATE:
            {
                pgCreate.render( g );
                break;
            }
            case GAME:
            {
                pgGame.render( g );
                break;
            }
            case CREDITS:
            {
                pgCredits.render( g );
                break;
            }
            case HOWTO:
            {
                pgHowTo.render( g );
                break;
            }
            case SAVE:
            {
                break;
            }
            default:
                break;
        }
        
        //---------------
        g.dispose();
        bs.show();
    }
    
    public static void main( String[] args )
    {
        GameGUI gui = new GameGUI();
        
        gui.setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
        gui.setMaximumSize( new Dimension( WIDTH, HEIGHT ) );
        gui.setMinimumSize( new Dimension( WIDTH, HEIGHT ) );
        frame = new JFrame( GameGUI.TITLE );
        frame.add( gui );
        frame.pack();
        
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable( false );
        frame.setLayout( new BorderLayout() );
        frame.setVisible( true );
        
        gui.start();
    }
    
    public STATE getState()
    {
        return this.state;
    }
    
    public void setState( STATE state )
    {
        this.state = state;
    }
    
    public int getBoardSize()
    {
        return this.boardSize;
    }
    
    public void setBoardSize( int size )
    {
        this.boardSize = size;
    }
    
    public Controller getController()
    {
        return this.controller;
    }
    
    public void setGameInfo( String[] gameInfo )
    {
        this.gameInfo = gameInfo;
    }
    
    public String[] getGameInfo()
    {
        return this.gameInfo;
    }
}
