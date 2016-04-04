/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lukáš
 */
public abstract class Page
{
    Font gameTitleFont = new Font( "arial", Font.BOLD, 90 );
    Font pageTitleFont = new Font( "arial", Font.BOLD, 70 );
    Font buttonFont = new Font( "arial", Font.BOLD, 40 );
    Font descriptionFont = new Font( "arial", Font.BOLD, 30 );
    Font basicFont = new Font( "arial", Font.BOLD, 20 );
    Color transparentBlack = new Color( 0, 0, 0, 150 );
    Color transparentWhite = new Color( 255, 255, 255, 100 );
    
    abstract public void render( Graphics g );
    
    public void renderButton( Rectangle r, Graphics g, Graphics2D g2d, int x, int y, int width, int height, boolean selected )
    {
        g2d.draw( r );
        
        if ( selected )
            g.setColor( transparentWhite );
        else
            g.setColor( transparentBlack );
        
        g2d.fillRect( x + 1, y + 1, width - 1 , height - 1);
        g.setColor( Color.white );
    }
}
