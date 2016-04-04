package othello;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Lukáš
 */
public class MenuPage extends Page{
    
    public Rectangle newGameButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 250, 300, 75 );
    public Rectangle loadGameButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 350, 300, 75 );
    public Rectangle creditsButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 450, 300, 75 );
    public Rectangle howToPlayButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 550, 300, 75 );
    public Rectangle quitButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 650, 300, 75 );
      
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        g.setFont( gameTitleFont );
        
        Color clr = new Color( 0, 0, 0, 150 );
        
        g.setColor( Color.white );
        g.setFont( buttonFont );
        renderButton( newGameButton, g, g2d, newGameButton.x, newGameButton.y, 300, 75, false );
        g.drawString( "New Game", GameGUI.WIDTH / 2 - 100, 300);
        
        renderButton( loadGameButton, g, g2d, loadGameButton.x, loadGameButton.y, 300, 75, false );
        g.drawString( "Load Game", GameGUI.WIDTH / 2 - 105, 400);
        
        renderButton( creditsButton, g, g2d, creditsButton.x, creditsButton.y, 300, 75, false );
        g.drawString( "Credits", GameGUI.WIDTH / 2 - 65, 500);
        
        renderButton( howToPlayButton, g, g2d, howToPlayButton.x, howToPlayButton.y, 300, 75, false );
        g.drawString( "How to Play", GameGUI.WIDTH / 2 - 110, 600);        

        renderButton( quitButton, g, g2d, quitButton.x, quitButton.y, 300, 75, false );
        g.drawString( "Quit", GameGUI.WIDTH / 2 - 40, 700);
    }
}
