/**
 * Třída pro grafické zobrazení stránky s nápovědou ke hraní
 * Funkce:  1) Vykreslení stránky
 * @author Lukáš Hudec
 * @see othello.GameGUI
 * @see othello.Page
 */
package othello;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class HowToPage extends Page
{
    public Rectangle backButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 650, 300, 75 );
    
    /**
     * Přetížená metoda, která vykresluje stránku
     * @param g grafika
     */
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        
        g.setFont( pageTitleFont );
        g.setColor( Color.white );
        g.drawString( "How to Play", GameGUI.WIDTH / 2 - 170, 100 );
        
        g.setFont( descriptionFont );
        g.drawString( "Reversi, je desková hra pro dva hráče, hraná na desce", 100, 180 );
        g.drawString( "[6x6][8x8][10x10][12x12] polí. Hráči na desku pokládají", 100, 220 );
        g.drawString( "kameny, které jsou z jedné strany bílé a z druhé černé", 100, 260 );
        g.drawString( "tak, aby mezi právě položený kámen a jiný kámen své", 100, 300 );
        g.drawString( "barvy uzavřeli souvislou řadu soupeřových kamenů; tyto", 100, 340 );
        g.drawString( "kameny se potom otočí a stanou se kameny druhého hráče.", 100, 380 );
        
        g.drawString( "Vítězí hráč, který po zaplnění desky na ní má více svých", 90, 460 );
        g.drawString( "kamenů. Pole se označují obdobně jako na šachovnici, tedy", 90, 500 );
        g.drawString( "sloupce písmeny, řady čísly. Lze také nechat náhodný počet", 90, 540 );
        g.drawString( "kamenů hráče zamrznout, pak nemohou být při žádném tahu", 90, 580 );
        g.drawString( "otočeny dokud opět nerozmrznou.", 90, 620 );
        
        renderButton( backButton, g, g2d, ( int )backButton.getX(), ( int )backButton.getY(), 300, 75, false );
        g.setFont( buttonFont );
        g.drawString( "Back", GameGUI.WIDTH / 2 - 40, 700);
    }
}
