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
public class LoadGamePage extends Page
{
    
    public Rectangle backButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 650, 300, 75 );

    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        g.setColor( Color.white );
        renderButton( backButton, g, g2d, ( int )backButton.getX(), ( int )backButton.getY(), 300, 75, false );
        g.setFont( buttonFont );
        g.drawString( "Back", GameGUI.WIDTH / 2 - 40, 700);
    }
    
} 
