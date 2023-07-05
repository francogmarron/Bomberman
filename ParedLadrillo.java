
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ParedLadrillo extends Pared{
    private BufferedImage[] img;
    private BufferedImage spriteSheet;
    
    public ParedLadrillo(double X, double Y){
        super("imagenes/pared_ladrillo.png", X, Y); 
        destruible = true;
        spritesDestruir();
    }
    
    
    public void spritesDestruir() {
        try{
            spriteSheet = ImageIO.read(getClass().getResource("imagenes/Destruccion.png"));
            img = new BufferedImage[5];
            for(int i = 0; i < 5; i++) {
                img[i] = spriteSheet.getSubimage(i*32, 0, 32, 32);
            }
        }catch(IOException e) {}
    }
    
    
    public void destruir(){
        this.cargarSpriteArray(img, 4);
    }
}
