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
public class CreditsPage extends Page {
    
    public Rectangle backButton = new Rectangle( GameGUI.WIDTH / 2 - 150, 650, 300, 75 );
    
    @Override
    public void render( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        
        g.setFont( pageTitleFont );
        g.setColor( Color.white );
        g.drawString( "Credits", GameGUI.WIDTH / 2 - 125, 100 );
        
        g.setFont( descriptionFont );
        g.drawString( "Authors: ", GameGUI.WIDTH / 2 - 60, 250 );
        g.drawString( "Lukas Dibdak <xdibda00@stud.fit.vutbr.cz>", 200, 300 );
        g.drawString( "Lukas Hudec  <xhudec26@stud.fit.vutbr.cz>", 200, 340 );
        
        g.drawString( "Released Version:", GameGUI.WIDTH / 2 - 140, 420 );
        g.drawString( "Version: 1.1.2a", GameGUI.WIDTH / 2 - 110, 460 );
        g.drawString( "Java Game Project for subject IJA 2016", 220, 500 );
        
        renderButton( backButton, g, g2d, ( int )backButton.getX(), ( int )backButton.getY(), 300, 75, false );
        g.setFont( buttonFont );
        g.drawString( "Back", GameGUI.WIDTH / 2 - 40, 700);
    }
}
