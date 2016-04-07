/**
 * Třída pro grafické zobrazení informací o hře v grafickém uživatelském
 * rozhraní.
 * Funkce:  1) Inicializace GUI
 *          2) Vykreslení stránek na základě stavů hry
 * @author Lukáš Hudec
 * @see othello.Controller
 */
package othello;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;
import othello.Utility.Coords;

public class GameGUI extends Canvas implements Runnable
{
    public static final String TITLE = "2D Othello Game";
    /**
     * Pevně daná šířka a výška okna
     */
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    /**
     * Velikost mezery mezi jednotlivými poličky herní desky
     */
    public static final int GAP_SIZE = 2;
    /**
     * Velikosti políček v závislé na velikosti herní desky
     */
    public static final int FIELD_6_SIZE = 115;
    public static final int FIELD_8_SIZE = 86;
    public static final int FIELD_10_SIZE = 68;
    public static final int FIELD_12_SIZE = 56;
    
    private Thread thread;
    private Controller controller = null;
    private ArrayList<Coords> coords = null;
    private int boardSize = 0;    
    private String[] gameInfo = null;
    private String freezeInfo = null;
    
    /**
     * Stav hry
     */
    private STATE state = STATE.MENU;
    
    /**
     * Flagy, pro kontrolu běhu hry
     */
    private boolean running = false;
    public boolean computerPlayer;
    public boolean easy;
    public boolean finished;
    public boolean freezeTriggered = false;
    
    /**
     * Definice různých fontů
     */
    public static Font arial12 = new Font( "arial", Font.PLAIN, 12 );
    public static Font arial12bold = new Font( "arial", Font.BOLD, 12 );
    public static Font arial40bold = new Font( "arial", Font.BOLD, 40 );
    
    /**
     * Používáné obrázky
     */
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
    
    
    /**
     * Instance tříd, které reprezentují jednotlivé stránky
     */
    private MenuPage pgMenu;
    private CreateGamePage pgCreate;
    private GamePage pgGame;
    private CreditsPage pgCredits;
    private HowToPage pgHowTo;
            
    static JFrame frame;
    public static Graphics g;
    
    /**
     * Enum - stavy hry
     * - MENU    - hlavní menu
     * - CREATE  - volby hry
     * - GAME    - běžící hra
     * - CREDITS - o autorech
     * - HOWTO   - nápověda hry
     */
    public static enum STATE
    {
        MENU,
        CREATE,
        GAME,
        CREDITS,
        HOWTO
    };

    /**
     * Metoda, která provede inicializaci GUI:
     * - načtení obrázků
     * - inicializace jednotlivých stránek hry
     * - nastavení implicitní hodnoty velikosti hrací desky
     * - inicializace MouseListeneru {@code MenuButtonListener}
     */
    public void initialize()
    {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();
        
        try
        {
            menuBackground = loader.loadImage("/menubg.jpg");
            background = loader.loadImage("/bg.jpg");
            imgBlackDisk = loader.loadImage( "/black_disk.png");
            imgWhiteDisk = loader.loadImage( "/white_disk.png");
            
            imgEmptyField_6 = loader.loadImage( "/empty_field_6.png");
            imgEmptyField_8 = loader.loadImage( "/empty_field_8.png");
            imgEmptyField_10 = loader.loadImage( "/empty_field_10.png");
            imgEmptyField_12 = loader.loadImage( "/empty_field_12.png");
            
            imgWhiteField_6 = loader.loadImage( "/white_field_6.png");
            imgWhiteField_8 = loader.loadImage( "/white_field_8.png");
            imgWhiteField_10 = loader.loadImage( "/white_field_10.png");
            imgWhiteField_12 = loader.loadImage( "/white_field_12.png");
            
            imgBlackField_6 = loader.loadImage( "/black_field_6.png");
            imgBlackField_8 = loader.loadImage( "/black_field_8.png");
            imgBlackField_10 = loader.loadImage( "/black_field_10.png");
            imgBlackField_12 = loader.loadImage( "/black_field_12.png");
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
        coords = new ArrayList<>();
        
        this.addMouseListener( new MenuButtonListener( this ) );
    }
    
    /**
     * Metoda, která inicializuje a nastartuje vlákno
     */
    private synchronized void start()
    {
        if ( running )
            return;
        
        running = true;
        thread = new Thread( this );
        thread.start();
    }
    
    /**
     * Metdoa, která ukončí vlákno
     */
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
    
    /**
     * Přetížená metoda, která reprezentuje běh hry
     */
    @Override
    public void run()
    {
        initialize();
        
        while ( running )
        {
            render();
        }
        stop();
    }
    
    /**
     * Metoda, která vykresluje GUI v závislosti na stavu hry
     * - inicializace grafiky
     * - vykreslení pozadí
     * - vykreslení stránky
     */
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        
        if ( bs == null )
        {
            createBufferStrategy( 3 );
            return;
        }
        g = bs.getDrawGraphics();
        
        // Vykreslování
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
            default:
                break;
        }
        
        g.dispose();
        bs.show();
    }
    
    /**
     * Hlavní metoda, která spouští program v řežimu GUI
     * @param args Argumenty programu (Spouští se bez argumentů)
     */
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
    
    /**
     * Metoda, která vrací současný stav hry
     * @return stav hry
     */
    public STATE getState()
    {
        return this.state;
    }
    
    /**
     * Metoda, která nastavuje stav hry
     * @param state stav hry
     */
    public void setState( STATE state )
    {
        this.state = state;
    }
    
    /**
     * Metoda, která vrací velikost herní desky
     * @return velikost herní desky
     */
    public int getBoardSize()
    {
        return this.boardSize;
    }
    
    /**
     * Metoda, která nastavuje velikost herní desky
     * @param size velikost herní desky
     */
    public void setBoardSize( int size )
    {
        this.boardSize = size;
    }
    
    /**
     * Metoda, která vrací kontrolér
     * @return kontrolér
     */
    public Controller getController()
    {
        return this.controller;
    }
    
    /**
     * Metoda, která nastavuje pole řetězců s informacemi o hře
     * @param gameInfo pole řetězců {@code String} s informacemi
     */
    public void setGameInfo( String[] gameInfo )
    {
        this.gameInfo = gameInfo;
    }
    
    /**
     * Metoda, která vrací pole řetězců s informacemi o hře
     * @return pole řetězců {@code String}
     */
    public String[] getGameInfo()
    {
        return this.gameInfo;
    }
    
    /**
     * Metoda, která nastavuje řetězec s informací o zamrzlých kamenech
     * @param freezeInfo řetězec s informacemi o zamrzlých kamenech
     */
    public void setFreezeInfo( String freezeInfo )
    {
        this.freezeInfo = freezeInfo;
    }
    
    /**
     * Metoda, která vrací řetězec s informacemi o zamrzlých kamenech
     * @return řetězec
     */
    public String getFreezeInfo()
    {
        return this.freezeInfo;
    }
    
    /**
     * Metoda, která vrací hlavní vlákno hry
     * @return vlákno
     */
    public Thread  getThread()
    {
        return this.thread;
    }
    
    /**
     * Metoda, která vrací pole souřadnic kamenů {@code Coords}
     * @return pole souřadnic kamenů
     */
    public ArrayList<Coords> getStonesCoords()
    {
        return this.coords;
    }
}
