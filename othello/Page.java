/**
 * Abstraktní třída pro stránky
 * Obsahuje:  1) definice fontů
 *            2) definice barev
 *            3) deklaraci metody Render()
 *            4) Abstraktní metodu vykreslující tlačítko
 * @author Lukáš Hudec
 * @see othello.GameGUI
 */
package othello;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
    
    /**
     * Metoda, která vykresluje tlačítko
     * @param r čtverec, ohraničující tlačítko
     * @param g grafika
     * @param g2d 2D grafika
     * @param x x-ová souřadnice čtverce
     * @param y y-ová souřadnice čtverce
     * @param width šířka tlačítka
     * @param height výška tlačítka
     * @param selected flag, zda je tlačítko vybráno
     */
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
