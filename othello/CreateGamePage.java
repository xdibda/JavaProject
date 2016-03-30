/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lukáš
 */
public class CreateGamePage extends Page
{
    private GameGUI gui = null;
    
    public Rectangle size6Button = new Rectangle( GameGUI.WIDTH / 2 - 230, 350, 100, 100 );
    public Rectangle size8Button = new Rectangle( GameGUI.WIDTH / 2 - 110, 350, 100, 100 );
    public Rectangle size10Button = new Rectangle( GameGUI.WIDTH / 2 + 10, 350, 100, 100 );
    public Rectangle size12Button = new Rectangle( GameGUI.WIDTH / 2 + 130, 350, 100, 100 );
    
    public Rectangle nextButton = new Rectangle( GameGUI.WIDTH / 2 - 160, 650, 150, 75 );
    public Rectangle backButton = new Rectangle( GameGUI.WIDTH / 2 + 10, 650, 150, 75 );

    public CreateGamePage( GameGUI gui )
    {
        this.gui = gui;
    }
    
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        
        switch ( gui.getState() )
        {
            case CREATE_SIZE_OPTIONS:
            {
                renderSizeOptions( g, g2d );
                break;
            }
            case CREATE_FREEZE_OPTIONS:
            {
                renderFreezeOptions( g, g2d );
                break;
            }
        }
        
        renderButton( nextButton, g, g2d, ( int )nextButton.getX(), ( int )nextButton.getY(), 150, 75, false );
        renderButton( backButton, g, g2d, ( int )backButton.getX(), ( int )backButton.getY(), 150, 75, false );
        
        g.setFont( buttonFont );
        g.drawString( "Back", GameGUI.WIDTH / 2 + 40, 700);
        g.drawString( "Next", GameGUI.WIDTH / 2 - 130, 700);
    }
    
    public void renderSizeOptions( Graphics g, Graphics2D g2d )
    {
        g.setFont( pageTitleFont );
        g.setColor( Color.white );
        g.drawString( "Select size", GameGUI.WIDTH / 2 - 175, 100 );
        
        switch ( gui.getBoardSize() )
        {
            case 6:
            {
                renderButton( size6Button, g, g2d, ( int )size6Button.getX(), ( int )size6Button.getY(), 100, 100, true );
                renderButton( size8Button, g, g2d, ( int )size8Button.getX(), ( int )size8Button.getY(), 100, 100, false );
                renderButton( size10Button, g, g2d, ( int )size10Button.getX(), ( int )size10Button.getY(), 100, 100, false );
                renderButton( size12Button, g, g2d, ( int )size12Button.getX(), ( int )size12Button.getY(), 100, 100, false );
                break;
            }
            case 8:
            {
                renderButton( size6Button, g, g2d, ( int )size6Button.getX(), ( int )size6Button.getY(), 100, 100, false );
                renderButton( size8Button, g, g2d, ( int )size8Button.getX(), ( int )size8Button.getY(), 100, 100, true );
                renderButton( size10Button, g, g2d, ( int )size10Button.getX(), ( int )size10Button.getY(), 100, 100, false );
                renderButton( size12Button, g, g2d, ( int )size12Button.getX(), ( int )size12Button.getY(), 100, 100, false );
                break;
            }
            case 10:
            {
                renderButton( size6Button, g, g2d, ( int )size6Button.getX(), ( int )size6Button.getY(), 100, 100, false );
                renderButton( size8Button, g, g2d, ( int )size8Button.getX(), ( int )size8Button.getY(), 100, 100, false );
                renderButton( size10Button, g, g2d, ( int )size10Button.getX(), ( int )size10Button.getY(), 100, 100, true );
                renderButton( size12Button, g, g2d, ( int )size12Button.getX(), ( int )size12Button.getY(), 100, 100, false );
                break;
            }
            case 12:
            {
                renderButton( size6Button, g, g2d, ( int )size6Button.getX(), ( int )size6Button.getY(), 100, 100, false );
                renderButton( size8Button, g, g2d, ( int )size8Button.getX(), ( int )size8Button.getY(), 100, 100, false );
                renderButton( size10Button, g, g2d, ( int )size10Button.getX(), ( int )size10Button.getY(), 100, 100, false );
                renderButton( size12Button, g, g2d, ( int )size12Button.getX(), ( int )size12Button.getY(), 100, 100, true );
                break;
            }
        }
        
        g.setFont( buttonFont );
        g.drawString( "6", GameGUI.WIDTH / 2 - 190, 415);
        g.drawString( "8", GameGUI.WIDTH / 2 - 70, 415);
        g.drawString( "10", GameGUI.WIDTH / 2 + 40, 415);
        g.drawString( "12", GameGUI.WIDTH / 2 + 160, 415);
        
    }
    
    public void renderFreezeOptions( Graphics g, Graphics2D g2d )
    {
        g.setFont( pageTitleFont );
        g.setColor( Color.white );
        
        g.drawString( "Freeze options", GameGUI.WIDTH / 2 - 230, 100 );
    }
    
}
