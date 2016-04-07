/**
 * Třída pro nahrávání obrázků
 * Funkce:  1) Načtení obrázku
 * @author Lukáš Hudec
 * @see othello.GameGUI
 */

package othello;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BufferedImageLoader
{
    private BufferedImage image;
    
    /**
     * Metoda, která načte obrázek z uvedeného řetězce s URL
     * @param path cesta k obrázku
     * @return načtený obrázek {@code BufferedImage}
     * @throws IOException pokud soubor neexistuje
     */
    public BufferedImage loadImage( String path ) throws IOException
    {
        image = ImageIO.read( getClass().getResource( path ) );
        return image;
    }

}
