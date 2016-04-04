/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Lukáš
 */
public class MenuButtonListener implements MouseListener 
{
    private GameGUI gui;
    
    public MenuButtonListener( GameGUI gui )
    {
        this.gui = gui;
        gui.computerPlayer = false;
        gui.easy = true;
    }
    

    @Override
    public void mousePressed( MouseEvent e )
    {
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        switch( gui.getState() )
        {
            case MENU:
            {
                menuHandler( mouseX, mouseY );
                break;
            }
            case CREDITS:
            {
                commonHandler( mouseX, mouseY );
                break;
            }
            case HOWTO:
            {
                commonHandler( mouseX, mouseY );
                break;
            }
            case CREATE:
            {
                gameOptionsHandler( mouseX, mouseY );
                break;
            }
            case GAME:
            {
                playerPanelHandler( mouseX, mouseY );
                gameHandler( mouseX, mouseY );
                break;
            }
        }
    }
        
    @Override
    public void mouseClicked( MouseEvent e ) {}

    @Override
    public void mouseReleased( MouseEvent e ) {}

    @Override
    public void mouseEntered( MouseEvent e ) {}

    @Override
    public void mouseExited( MouseEvent e ) {}
    
    public void menuHandler( int mouseX, int mouseY )
    {
        if ( mouseX >= GameGUI.WIDTH / 2 - 150 && mouseX <= GameGUI.WIDTH / 2 + 150 )
        {
        // New Game
            if ( mouseY >= 250 && mouseY <= 325 )
            {
                gui.setState( GameGUI.STATE.CREATE );
            }
        // Load Game
            else if ( mouseY >= 350 && mouseY <= 425 )
            {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog( GameGUI.frame );
                
                if ( returnVal == JFileChooser.APPROVE_OPTION )
                {
                    File file = fc.getSelectedFile();
                    try
                    {
                        gui.setGameInfo( gui.getController().loadGame( file.getName().substring( 0, file.getName().indexOf( ".txt" ) ) ) );
                        gui.setBoardSize( Board.SIZE );
                    }
                    catch( GameLoadingNameNotFoundException | GameLoadingFailureException e )
                    {
                        JOptionPane.showMessageDialog( GameGUI.frame, e, "Warning", JOptionPane.ERROR_MESSAGE );
                    }
                    gui.finished = false;
                    gui.setState( GameGUI.STATE.GAME );
                } 
                else 
                {
                    gui.setState( GameGUI.STATE.MENU );
                }
            }
        // Credits
            else if ( mouseY >= 450 && mouseY <= 525 )
            {
                gui.setState( GameGUI.STATE.CREDITS );
            }
        // How to play
            else if ( mouseY >= 550 && mouseY <= 625 )
            {
                gui.setState( GameGUI.STATE.HOWTO );
            }
        // Quit
            else if ( mouseY >= 650 && mouseY <= 725 )
            {
                System.exit( 0 );
            }
        }
    }
    
    public void gameOptionsHandler( int mouseX, int mouseY )
    {
        if ( mouseY >= 650 && mouseY <= 725 )
        {
        //  Back
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                gui.setState( GameGUI.STATE.MENU );
            }
        //  Next
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 230 )
            {
                if ( !gui.computerPlayer )
                {
                    gui.setGameInfo( gui.getController().createNewGame( gui.getBoardSize() ) );
                }
                else
                {
                    if ( gui.easy )
                    {
                        gui.setGameInfo( gui.getController().createNewGame( gui.getBoardSize(), Utility.TypeOfGame.EASY ) );
                    }
                    else
                    {
                        gui.setGameInfo( gui.getController().createNewGame( gui.getBoardSize(), Utility.TypeOfGame.HARD ) );
                    }
                   
                }
                gui.finished = false;
                gui.setState( GameGUI.STATE.GAME );
            }
        }

        if( mouseY >= 200 && mouseY <= 300 )
        {
        //  6
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 130 )
            {
                gui.setBoardSize( 6 );
            }
        //  8
            else if ( mouseX >= GameGUI.WIDTH / 2 - 110 &&  mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                gui.setBoardSize( 8 );
            }
        //  10
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 &&  mouseX <= GameGUI.WIDTH / 2 + 110 )
            {
                gui.setBoardSize( 10 );
            }
        //  12
            else if ( mouseX >= GameGUI.WIDTH / 2 + 130 &&  mouseX <= GameGUI.WIDTH / 2 + 230  )
            {
                gui.setBoardSize( 12 );
            }
        }
        else if( mouseY >= 350 && mouseY <= 425 )
        {
        //  Human
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
//                System.out.println( "HUMAN" );
                gui.computerPlayer = false;
            }
        //  AI
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 230 )
            {
//                System.out.println( "AI" );
                gui.computerPlayer = true;
            }
        }
        
        if( gui.computerPlayer && mouseY >= 500 && mouseY <= 575 )
        {
        //  Easy
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
//                System.out.println( "HUMAN" );
                gui.easy = true;
            }
        //  Hard
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 230 )
            {
//                System.out.println( "AI" );
                gui.easy = false;
            }
        }
    }
    
    public void commonHandler( int mouseX, int mouseY )
    {
        if( mouseX >= GameGUI.WIDTH / 2 - 150 && mouseX <= GameGUI.WIDTH / 2 + 150 )
        {
            if ( mouseY >= 650 && mouseY <= 725 )
            {
                gui.setState( GameGUI.STATE.MENU );
            }
        }
    }
    
    public void playerPanelHandler( int mouseX, int mouseY )
    {
        if( mouseX >= 779 && mouseX <= 979 )
        {
        // Freeze
            if ( mouseY >= 490 && mouseY <= 540 )
            {
                if ( !gui.finished )
                {
                    try
                    {
                        ArrayList<Utility.Coords> stonesCoords = new ArrayList<>();
                        gui.setGameInfo( gui.getController().freezeStones(stonesCoords) );
                    }
                    catch( GameIsNotStartedException e )
                    {
                        JOptionPane.showMessageDialog( GameGUI.frame, e, "Warning", JOptionPane.ERROR_MESSAGE );
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( GameGUI.frame, "Game is already finished.", "Warning", JOptionPane.ERROR_MESSAGE );
                }
            }            
        // Undo
            else if ( mouseY >= 551 && mouseY <= 601 )
            {
                if ( !gui.finished )
                {
                    try
                    {
                        gui.setGameInfo( gui.getController().undoMove() );
                    }
                    catch( GameIsNotStartedException | NoMoreMovesToUndoException e )
                    {
                        JOptionPane.showMessageDialog( GameGUI.frame, e, "Warning", JOptionPane.ERROR_MESSAGE );
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( GameGUI.frame, "Game is already finished.", "Warning", JOptionPane.ERROR_MESSAGE );
                }
            }
        // Save
            else if ( mouseY >= 612 && mouseY <= 662 )
            {
                if ( !gui.finished )
                {
                    String fileName = ( String )JOptionPane.showInputDialog( GameGUI.frame, "Napiste jmeno ulozeneho souboru", "Save Game Dialog", JOptionPane.PLAIN_MESSAGE, null, null, "ulozenahra" );
                    if ( fileName != null )
                    {
                        try
                        {
                            gui.getController().saveGame( fileName );
                        }
                        catch ( GameIsNotStartedException | GameSavingFailureException e)
                        {
                            JOptionPane.showMessageDialog( GameGUI.frame, e, "Warning", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( GameGUI.frame, "Game is already finished.", "Warning", JOptionPane.ERROR_MESSAGE );
                }
                
            }
        // Quit
            else if ( mouseY >= 673 && mouseY <= 723 )
            {
                gui.setState( GameGUI.STATE.MENU );
            }
        }
    }
    
    public void gameHandler( int mouseX, int mouseY )
    {
        int startX, startY;
        int imgSize = 0;
        
        startX = startY = 34;
        
        switch ( gui.getBoardSize() )
        {
            case 6:
                imgSize = GameGUI.FIELD_6_SIZE;
                break;
            case 8:
                imgSize = GameGUI.FIELD_8_SIZE;
                break;
            case 10:
                imgSize = GameGUI.FIELD_10_SIZE;
                break;
            case 12:
                imgSize = GameGUI.FIELD_12_SIZE;
                break;
        }
        
        for ( int j = 0; j < gui.getBoardSize(); j++ )
        {
            for ( int i = 0; i < gui.getBoardSize(); i++ )
            {
                if ( mouseX > startX + i * ( imgSize + GameGUI.GAP_SIZE ) && mouseX < startX + imgSize + i * ( imgSize + GameGUI.GAP_SIZE ) )
                {
                    if ( mouseY > startY + j * ( imgSize + GameGUI.GAP_SIZE ) && mouseY < startY + imgSize + j * ( imgSize + GameGUI.GAP_SIZE ) )
                    {
                        Utility.Coords tmp = new Utility.Coords( i , j );
                        try
                        {
                            gui.setGameInfo( gui.getController().makeMove( tmp ) );
                        }
                        catch ( GameIsNotStartedException | MoveNotAvailableException e )
                        {
                            JOptionPane.showMessageDialog( GameGUI.frame, e, "Warning", JOptionPane.ERROR_MESSAGE );
                        }
                        //System.out.println( "Coords: " + i + " " + j );
                    }
                }
            }
        }
    }
}
