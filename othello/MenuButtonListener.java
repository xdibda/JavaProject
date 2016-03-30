/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static othello.GameCommandLine.showMoveInfo;

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
            case CREATE_SIZE_OPTIONS:
            {

                sizeOptionsHandler( mouseX, mouseY );
                break;
            }
            case CREATE_FREEZE_OPTIONS:
            {
                freezeOptionsHandler( mouseX, mouseY );
                break;
            }
            case LOAD:
            {
                commonHandler( mouseX, mouseY );
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
                System.out.println( "New Game Button pressed...");
                gui.setState( GameGUI.STATE.CREATE_SIZE_OPTIONS );
            }
        // Load Game
            else if ( mouseY >= 350 && mouseY <= 425 )
            {
                System.out.println( "Load Game Button pressed...");
                gui.setState( GameGUI.STATE.LOAD );
            }
        // Credits
            else if ( mouseY >= 450 && mouseY <= 525 )
            {
                System.out.println( "Credits Button pressed...");
                gui.setState( GameGUI.STATE.CREDITS );
            }
        // How to play
            else if ( mouseY >= 550 && mouseY <= 625 )
            {
                System.out.println( "How to Play Button pressed...");
                gui.setState( GameGUI.STATE.HOWTO );
            }
        // Quit
            else if ( mouseY >= 650 && mouseY <= 725 )
            {
                System.out.println( "Quit Button pressed...");
                System.exit( 0 );
            }
        }
    }
    
    public void sizeOptionsHandler( int mouseX, int mouseY )
    {
        if ( mouseY >= 650 && mouseY <= 725 )
        {
        //  Next
            if ( mouseX >= GameGUI.WIDTH / 2 - 160 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                System.out.println( "Next button pressed on Create Game Page." );
                gui.setState( GameGUI.STATE.CREATE_FREEZE_OPTIONS );
            }
        //  Back
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 160 )
            {
                System.out.println( "Back button pressed on Create Game Page." );
                gui.setState( GameGUI.STATE.MENU );
            }
        }

        if( mouseY >= 350 && mouseY <= 450 )
        {
        //  6
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 130 )
            {
                System.out.println( "Board size 6 selected." );
                gui.setBoardSize( 6 );
            }
        //  8
            else if ( mouseX >= GameGUI.WIDTH / 2 - 110 &&  mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                System.out.println( "Board size 8 selected." );
                gui.setBoardSize( 8 );
            }
        //  10
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 &&  mouseX <= GameGUI.WIDTH / 2 + 110 )
            {
                System.out.println( "Board size 10 selected." );
                gui.setBoardSize( 10 );
            }
        //  12
            else if ( mouseX >= GameGUI.WIDTH / 2 + 130 &&  mouseX <= GameGUI.WIDTH / 2 + 230  )
            {
                System.out.println( "Board size 12 selected." );
                gui.setBoardSize( 12 );
            }
        }
    }
    
    public void freezeOptionsHandler( int mouseX, int mouseY )
    {
        if ( mouseY >= 650 && mouseY <= 725 )
        {
        //  Next
            if ( mouseX >= GameGUI.WIDTH / 2 - 160 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                System.out.println( "Next button pressed on Create Game Page." );
                gui.setGameInfo( gui.getController().createNewGame( gui.getBoardSize() ) );
                gui.setState( GameGUI.STATE.GAME );
            }
        //  Back
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 160 )
            {
                System.out.println( "Back button pressed on Create Game Page." );
                gui.setState( GameGUI.STATE.MENU );
            }
        }
    }
    
    public void commonHandler( int mouseX, int mouseY )
    {
        if( mouseX >= GameGUI.WIDTH / 2 - 150 && mouseX <= GameGUI.WIDTH / 2 + 150 )
        {
            if ( mouseY >= 650 && mouseY <= 725 )
            {
                System.out.println( "Back button pressed." );
                gui.setState( GameGUI.STATE.MENU );
            }
        }
    }
    
    public void playerPanelHandler( int mouseX, int mouseY )
    {
        if( mouseX >= 779 && mouseX <= 979 )
        {
        // Undo
            if ( mouseY >= 551 && mouseY <= 601 )
            {
                try
                {
                    gui.setGameInfo( gui.getController().undoMove() );
                }
                catch( GameIsNotStartedException | NoMoreMovesToUndoException e )
                {
                    System.out.println( e );
                }
                System.out.println( "Undo Button pressed...");
            }
        // Save
            else if ( mouseY >= 612 && mouseY <= 662 )
            {
                System.out.println( "Save Game Button pressed...");
            }
        // Quit
            else if ( mouseY >= 673 && mouseY <= 723 )
            {
                System.out.println( "Quit Button pressed...");
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
                            try {
                                gui.getController().analyzeNextTurn();
                            }
                            catch (GameEndedException endOfGame)
                            {
                                showMoveInfo(endOfGame.getInfoStrings());
                            }
                            catch (ComputerHasPlayed computerTurn)
                            {
                                showMoveInfo(computerTurn.getInfoStrings()[4], computerTurn.getInfoStrings()[5],computerTurn.getInfoStrings());
                                continue;
                            }
                            
                            gui.setGameInfo( gui.getController().makeMove( tmp ) );
                        }
                        catch ( GameIsNotStartedException | MoveNotAvailableException e )
                        {
                            System.out.println( e );
                        }
                        
                        System.out.println( "Coords: " + i + " " + j );
                    }
                }
            }
        }
    }
}
