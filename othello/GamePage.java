package othello;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *  TODO RenderCoords()
 * @author Lukáš
 */
public class GamePage extends Page
{
    private GameGUI gui = null;
    
    private String congratulations = null;
    private String playerName = null;
    
    public Rectangle boardBackground = new Rectangle( 34, 34, 700, 700 );
    public Rectangle userPanelBackground = new Rectangle ( 768, 34, 222, 700 );
    
    public Rectangle freezeButton = new Rectangle( 779, 490, 200, 50 );
    public Rectangle undoButton = new Rectangle( 779, 551, 200, 50 );
    public Rectangle saveButton = new Rectangle( 779, 612, 200, 50 );
    public Rectangle quitButton = new Rectangle( 779, 673, 200, 50 );
    
    private int imgSize;
    private BufferedImage imgEmptyField;
    private BufferedImage imgWhiteField;
    private BufferedImage imgBlackField;
    
    public GamePage( GameGUI gui )
    {
        this.gui = gui;
        gui.finished = false;
    }
    
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        
        g.setColor( Color.white );
        
        prepareImage();
        renderGameBoard( g );
        renderUserPanel( g, g2d );
    }
    
    public void prepareImage()
    {
        switch ( gui.getBoardSize() )
        {
            case 6:
            {
                imgSize = GameGUI.FIELD_6_SIZE;
                imgEmptyField = gui.imgEmptyField_6;
                imgWhiteField = gui.imgWhiteField_6;
                imgBlackField = gui.imgBlackField_6;
                break;
            }
            case 8:
            {
                imgSize = GameGUI.FIELD_8_SIZE;
                imgEmptyField = gui.imgEmptyField_8;
                imgWhiteField = gui.imgWhiteField_8;
                imgBlackField = gui.imgBlackField_8;
                break;
            }
            case 10:
            {
                imgSize = GameGUI.FIELD_10_SIZE;
                imgEmptyField = gui.imgEmptyField_10;
                imgWhiteField = gui.imgWhiteField_10;
                imgBlackField = gui.imgBlackField_10;
                break;
            }
            case 12:
            {
                imgSize = GameGUI.FIELD_12_SIZE;
                imgEmptyField = gui.imgEmptyField_12;
                imgWhiteField = gui.imgWhiteField_12;
                imgBlackField = gui.imgBlackField_12;
                break;
            }
        }
    }
    
    public void renderGameBoard( Graphics g)
    {
        int i, j;
        char colorCheck = 'W';
        for ( j = 0; j < gui.getBoardSize(); j++ )
        {
            for ( i = 0; i < gui.getBoardSize(); i++ )
            {
                switch ( gui.getGameInfo()[ 3 ].charAt( i + j * gui.getBoardSize() ) )
                {
                    case 'W':
                        g.drawImage( imgWhiteField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        colorCheck = 'W';
                        break;
                    case 'B':
                        g.drawImage( imgBlackField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        colorCheck = 'B';
                        break;
                    case 'K':
                        g.drawImage( imgBlackField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        g.setColor(transparentWhite);
                        g.fillRect(i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, imgSize, imgSize );
                        g.setColor( Color.white );
                        colorCheck = 'K';
                        break;
                    case 'E':
                        g.drawImage( imgWhiteField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        g.setColor(transparentWhite);
                        g.fillRect(i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, imgSize, imgSize );
                        g.setColor( Color.white );
                        colorCheck = 'E';
                        break;
                    default:
                        g.drawImage( imgEmptyField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        break;
                }
                
                if( gui.freezeTriggered )
                {
                    for ( Utility.Coords coord : gui.getStonesCoords() )
                    {
                        if ( coord.getX() == i && coord.getY() == j )
                        {
                            if ( colorCheck == 'B' || colorCheck == 'K' )
                                g.setColor( Color.white );
                            else
                                g.setColor( Color.black );

                            g.setFont( GameGUI.arial40bold );
                            g.drawString( "F", i * ( imgSize + GameGUI.GAP_SIZE ) + 25 + imgSize / 2, j * ( imgSize + GameGUI.GAP_SIZE ) + 50 + imgSize / 2 );
                            g.setFont( GameGUI.arial12 );
                            g.setColor( Color.white );
                        }
                    }
                }
                
                g.drawString( Integer.toString( i + j * gui.getBoardSize() ), i * ( imgSize + GameGUI.GAP_SIZE ) + 35, j * ( imgSize + GameGUI.GAP_SIZE ) + 45 );
            }
        }
        if( !gui.finished )
        {
            try
            {
                gui.setGameInfo( gui.getController().analyzeNextTurn() );
            }
            catch ( GameEndedException endOfGame )
            {
                JOptionPane.showMessageDialog( GameGUI.frame, endOfGame.getInfoStrings()[ 2 ] );
                playerName = "Vyhral " + endOfGame.getInfoStrings()[ 2 ].substring( 
                        endOfGame.getInfoStrings()[ 2 ].indexOf( "[" ), endOfGame.getInfoStrings()[ 2 ].indexOf( "]" ) + 1 );
                gui.finished = true;
            }
            catch ( ComputerHasPlayed computerTurn )
            {
                gui.setGameInfo( computerTurn.getInfoStrings() );
                //showMoveInfo(computerTurn.getInfoStrings()[4], computerTurn.getInfoStrings()[5],computerTurn.getInfoStrings());
            }
            catch ( GameIsNotStartedException e ) {}
            
            if ( gui.computerPlayer )
            {
                try
                {
                    gui.getThread().sleep( 500 );
                }
                catch ( InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
        else
        {
            g.setFont( super.pageTitleFont );
            g.setColor( transparentBlack );
            g.fillRect( 34, 34, 700, 700 );
            g.setColor( Color.white );
            g.drawString( "Hra skoncila.", 160, 350 );
            g.drawString( playerName, 140, 450 );
        }
    }
    
    public void renderUserPanel( Graphics g, Graphics2D g2d )
    {
        g.setColor( super.transparentWhite );
        g2d.fillRect( 768, 34, 222, 700 );
        
        g.setColor( Color.white );
        g2d.draw( userPanelBackground );
        
        g.setFont( super.descriptionFont );
        g.drawString( "Scoreboard", 795, 85);
        
        g.setFont( super.buttonFont );
        g.drawString( gui.getGameInfo()[0] + " : " + gui.getGameInfo()[1], 830, 180 );
        g.setFont( super.basicFont );
        
//        g.drawString( gui.getGameInfo()[2].substring(1, 8), 772, 300 );
        if( ( gui.getGameInfo()[2].substring(1, 8) ).equals("pocitac") )
        {
            g.drawString( "Pocitac", 850, 250 );
        }
        else
        {
            g.drawString( gui.getGameInfo()[2].substring(1, 7), 850, 250 );
        }
        
        if ( gui.getGameInfo()[2].substring(10, 15).equals( "BLACK" ) )
        {
            g.drawImage( gui.imgBlackDisk, 810, 300, null );
        }
        else
        {
            g.drawImage( gui.imgWhiteDisk, 810, 300, null );
        }
        
        renderButton( freezeButton, g, g2d, ( int )freezeButton.getX(), ( int )freezeButton.getY(), 200, 50, gui.finished );
        renderButton( undoButton, g, g2d, ( int )undoButton.getX(), ( int )undoButton.getY(), 200, 50, gui.finished );
        renderButton( saveButton, g, g2d, ( int )saveButton.getX(), ( int )saveButton.getY(), 200, 50, gui.finished );
        renderButton( quitButton, g, g2d, ( int )quitButton.getX(), ( int )quitButton.getY(), 200, 50, false );
        
        if( gui.freezeTriggered )
        {
            g.setFont( GameGUI.arial12bold );
            g.drawString( gui.getFreezeInfo().substring( 0, 31 ), 784, 450 );
            g.drawString( gui.getFreezeInfo().substring( 31, gui.getFreezeInfo().length() ), 777, 470 );
        }

        g.setFont( super.descriptionFont );
        g.drawString( "Freeze", 832, 527);
        g.drawString( "Undo", 842, 588);
        g.drawString( "Save", 845, 649);
        g.drawString( "Quit", 849, 710);
    }
    
    // TODO
    public void renderCoords( Graphics g, int col, int row )
    {
        if ( row == 0 || row == gui.getBoardSize() )
        {
            
        }
    }
    
}
