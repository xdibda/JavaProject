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
    
    public Rectangle size6Button = new Rectangle( GameGUI.WIDTH / 2 - 230, 200, 100, 100 );
    public Rectangle size8Button = new Rectangle( GameGUI.WIDTH / 2 - 110, 200, 100, 100 );
    public Rectangle size10Button = new Rectangle( GameGUI.WIDTH / 2 + 10, 200, 100, 100 );
    public Rectangle size12Button = new Rectangle( GameGUI.WIDTH / 2 + 130, 200, 100, 100 );
    
    public Rectangle humanButton = new Rectangle( GameGUI.WIDTH / 2 - 230, 350, 220, 75 );
    public Rectangle AIButton = new Rectangle( GameGUI.WIDTH / 2 + 10, 350, 220, 75 );
    
    public Rectangle easyButton = new Rectangle( GameGUI.WIDTH / 2 - 230, 500, 220, 75 );
    public Rectangle hardButton = new Rectangle( GameGUI.WIDTH / 2 + 10, 500, 220, 75 );
    
    
    public Rectangle nextButton = new Rectangle( GameGUI.WIDTH / 2 - 230, 650, 220, 75 );
    public Rectangle backButton = new Rectangle( GameGUI.WIDTH / 2 + 10, 650, 220, 75 );

    public CreateGamePage( GameGUI gui )
    {
        this.gui = gui;
        this.gui.computerPlayer = false;
        this.gui.easy = true;
    }
    
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        
        renderSizeOptions( g, g2d );
        renderOpponentOptions( g, g2d );
        
        renderButton( nextButton, g, g2d, ( int )nextButton.getX(), ( int )nextButton.getY(), 220, 75, false );
        renderButton( backButton, g, g2d, ( int )backButton.getX(), ( int )backButton.getY(), 220, 75, false );
        
        g.setFont( buttonFont );
        g.drawString( "Back", GameGUI.WIDTH / 2 - 170, 700 );
        g.drawString( "Next", GameGUI.WIDTH / 2 + 80, 700 );
    }
    
    private void renderSizeOptions( Graphics g, Graphics2D g2d )
    {
        g.setFont( pageTitleFont );
        g.setColor( Color.white );
        g.drawString( "Game options", GameGUI.WIDTH / 2 - 225, 100 );
        
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
        g.drawString( "6", GameGUI.WIDTH / 2 - 190, 265 );
        g.drawString( "8", GameGUI.WIDTH / 2 - 70, 265 );
        g.drawString( "10", GameGUI.WIDTH / 2 + 40, 265 );
        g.drawString( "12", GameGUI.WIDTH / 2 + 160, 265 );
    }
    
    private void renderOpponentOptions( Graphics g, Graphics2D g2d )
    {
        g.setColor( Color.white );
        g.setFont( buttonFont );
        
        renderButton( humanButton, g, g2d, ( int )humanButton.getX(), ( int )humanButton.getY(), 220, 75, !gui.computerPlayer );
        renderButton( AIButton, g, g2d, ( int )AIButton.getX(), ( int )AIButton.getY(), 220, 75, gui.computerPlayer );
        
        g.drawString( "Human", GameGUI.WIDTH / 2 - 190, 400 );
        g.drawString( "AI", GameGUI.WIDTH / 2 + 100, 400 );
        
        if ( gui.computerPlayer )
        {
            renderButton( easyButton, g, g2d, ( int )easyButton.getX(), ( int )easyButton.getY(), 220, 75, gui.easy );
            renderButton( hardButton, g, g2d, ( int )hardButton.getX(), ( int )hardButton.getY(), 220, 75, !gui.easy );

            g.drawString( "Easy", GameGUI.WIDTH / 2 - 170, 550 );
            g.drawString( "Hard", GameGUI.WIDTH / 2 + 80, 550 );
        }
    }
}
