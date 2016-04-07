/**
 * Třída pro ovládání hry myší
 * Funkce:  1) Ovládání hlavního menu
 *          2) Ovládání nastavování hry
 *          3) Ovládání hrací desky
 *          4) Ovládání hračského panelu
 *          5) Výběr souboru pro načtení uložené hry
 * @author Lukáš Hudec
 * @see othello.GameGUI
 * @see othello.Controller
 */
package othello;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileView;
import javax.swing.JOptionPane;

public class MenuButtonListener implements MouseListener 
{
    private GameGUI gui;
    
    /**
     * Konstruktor, který vytvoří nový MenuButtonListener
     * a propojí ho s GUI
     * @param gui GUI
     */
    public MenuButtonListener( GameGUI gui )
    {
        this.gui = gui;
        gui.computerPlayer = false;
        gui.easy = true;
    }
    
    /**
     * Přetížená metoda, která se invokuje po kliknutí myší
     * @param e událost myši
     */
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
    
    /**
     * Metoda, která zpracovává události myši na stránce hlavní menu
     * @param mouseX x-ová souřadnice myši
     * @param mouseY y-ová souřadnice myši
     */
    public void menuHandler( int mouseX, int mouseY )
    {
        if ( mouseX >= GameGUI.WIDTH / 2 - 150 && mouseX <= GameGUI.WIDTH / 2 + 150 )
        {
        // New Game Button
            if ( mouseY >= 250 && mouseY <= 325 )
            {
                gui.setState( GameGUI.STATE.CREATE );
            }
        // Load Game Button
            else if ( mouseY >= 350 && mouseY <= 425 )
            {
                // Nastavení, aby se otevřela složka save a znemožnění přístupu do jiných adresářů
                final File dirToLock = new File("./save");
                final JFileChooser fc = new JFileChooser( dirToLock );
                
                fc.setFileView( new FileView() 
                {
                    @Override
                    public Boolean isTraversable(File f)
                    {
                         return dirToLock.equals(f);
                    }
                });
                
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
        // Credits Button
            else if ( mouseY >= 450 && mouseY <= 525 )
            {
                gui.setState( GameGUI.STATE.CREDITS );
            }
        // How to play Button
            else if ( mouseY >= 550 && mouseY <= 625 )
            {
                gui.setState( GameGUI.STATE.HOWTO );
            }
        // Quit Button
            else if ( mouseY >= 650 && mouseY <= 725 )
            {
                System.exit( 0 );
            }
        }
    }
    
    /**
     * Metoda, která zpracovává události na stránce s nastavením hry
     * @param mouseX x-ová souřadnice myši
     * @param mouseY y-ová souřadnice myši
     */
    public void gameOptionsHandler( int mouseX, int mouseY )
    {
        if ( mouseY >= 650 && mouseY <= 725 )
        {
        //  Back Button
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                gui.setState( GameGUI.STATE.MENU );
            }
        //  Next Button
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
                gui.getStonesCoords().clear();
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
                gui.computerPlayer = false;
            }
        //  AI
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 230 )
            {
                gui.computerPlayer = true;
            }
        }
        else if( gui.computerPlayer && mouseY >= 500 && mouseY <= 575 )
        {
        //  Easy
            if ( mouseX >= GameGUI.WIDTH / 2 - 230 && mouseX <= GameGUI.WIDTH / 2 - 10 )
            {
                gui.easy = true;
            }
        //  Hard
            else if ( mouseX >= GameGUI.WIDTH / 2 + 10 && mouseX <= GameGUI.WIDTH / 2 + 230 )
            {
                gui.easy = false;
            }
        }
    }
    
    /**
     * Metoda, která zpracovává události na jednodušší stránce s jedním tlačítkem
     * @param mouseX x-ová souřadnice myši
     * @param mouseY y-ová souřadnice myši
     */
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
    
    /**
     * Metoda, která zpracovává události na hráčském panelu
     * @param mouseX x-ová souřadnice myši
     * @param mouseY y-ová souřadnice myši
     */
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
                        gui.getStonesCoords().clear();
                        gui.setGameInfo( gui.getController().freezeStones( gui.getStonesCoords() ) );
                        gui.setFreezeInfo( gui.getGameInfo()[ 4 ] );
                        gui.freezeTriggered = true;
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
                        gui.freezeTriggered = false;
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
                            gui.freezeTriggered = false;
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
    
    /**
     * Metoda, která zpracovává události na herní desce
     * @param mouseX x-ová souřadnice myši
     * @param mouseY y-ová souřadnice myši
     */
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
                            gui.freezeTriggered = false;
                        }
                        catch ( GameIsNotStartedException | MoveNotAvailableException e )
                        {
                            JOptionPane.showMessageDialog( GameGUI.frame, e, "Warning", JOptionPane.ERROR_MESSAGE );
                        }
                    }
                }
            }
        }
    }
}
