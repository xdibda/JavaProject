package othello;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Lukáš
 */
public class GamePage extends Page
{
    private GameGUI gui = null;
    public Rectangle boardBackground = new Rectangle( 34, 34, 700, 700 );
    public Rectangle userPanelBackground = new Rectangle ( 768, 34, 222, 700 );
    
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
    }
    
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        
        g.setColor( Color.white );
        
        // p1 score
        //g.drawString( gui.getGameInfo()[0], 770, 50);
        // p2 score
        //g.drawString( gui.getGameInfo()[1], 770, 70);
        // kdo hraje
        //g.drawString( gui.getGameInfo()[2], 770, 90);
        // vizualice desky
        //g.drawString( gui.getGameInfo()[3], 700, 110);
        
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
        for ( j = 0; j < gui.getBoardSize(); j++ )
        {
            for ( i = 0; i < gui.getBoardSize(); i++ )
            {
                
                switch ( gui.getGameInfo()[ 3 ].charAt( i + j * gui.getBoardSize() ) )
                {
                    case 'W':
                        g.drawImage( imgWhiteField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        break;
                    case 'B':
                        g.drawImage( imgBlackField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        break;
                    default:
                        g.drawImage( imgEmptyField, i * ( imgSize + GameGUI.GAP_SIZE ) + 34, j * ( imgSize + GameGUI.GAP_SIZE ) + 34, null );
                        break;
                }
                g.drawString( Integer.toString( i + j * gui.getBoardSize() ), i * ( imgSize + GameGUI.GAP_SIZE ) + 35, j * ( imgSize + GameGUI.GAP_SIZE ) + 45 );
            }
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
        
        //g.drawString( gui.getGameInfo()[2], 772, 300 );
        g.drawString( gui.getGameInfo()[2].substring(7, 13), 850, 310 );
        if ( gui.getGameInfo()[2].substring(16, 21).equals( "BLACK" ) )
        {
            g.drawImage( gui.imgBlackDisk, 810, 350, null );
        }
        else
        {
            g.drawImage( gui.imgWhiteDisk, 810, 350, null );
        }
        
        renderButton( undoButton, g, g2d, ( int )undoButton.getX(), ( int )undoButton.getY(), 200, 50, false );
        renderButton( saveButton, g, g2d, ( int )saveButton.getX(), ( int )saveButton.getY(), 200, 50, false );
        renderButton( quitButton, g, g2d, ( int )quitButton.getX(), ( int )quitButton.getY(), 200, 50, false );
        
        g.setFont( super.descriptionFont );
        g.drawString( "Undo", 842, 588);
        g.drawString( "Save", 845, 649);
        g.drawString( "Quit", 849, 710);
    }
    
    public void renderCoords( Graphics g, int col, int row )
    {
        if ( row == 0 || row == gui.getBoardSize() )
        {
            
        }
    }
    
}
