
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;


public class Bomba extends ObjetoGrafico{
    private static int rango = 1;
    private static AtomicInteger cantidadBombas = new AtomicInteger(1);

    BufferedImage img[];

    public Bomba(double x, double y, Nivel nivel){
        super("/imagenes/bomba1.png");
        this.setPosicion(x, y);
        this.nivel =nivel;
        cargadoSprites();
    }
    
        private void cargadoSprites() {
        img = new BufferedImage[4];
        try{
           img[0] = ImageIO.read(getClass().getResource("imagenes/bomba1.png"));
           img[1] = ImageIO.read(getClass().getResource("imagenes/bomba2.png"));
           img[2] = ImageIO.read(getClass().getResource("imagenes/bomba3.png"));
           img[3] = ImageIO.read(getClass().getResource("imagenes/bomba2.png"));
            
        }catch(IOException e) {}
        this.cargarSpriteArray(img, 4);
    }

    public static int getRango() {
        return rango;
    }

   public static void setRango(int nuevoRango) {
        rango = nuevoRango;
    }

    public void explotar(){
        double x = Math.round(getX());
        double y = Math.round(getY());
        Rectangle2D arriba = new Rectangle2D.Double(x+10, y-35*rango, 10, 35*rango + 32);
        Rectangle2D abajo = new Rectangle2D.Double(x+10, y, 10, 35*rango);
        Rectangle2D izquierda = new Rectangle2D.Double(x-35*rango, y+10, 35*rango + 32, 10);
        Rectangle2D derecha = new Rectangle2D.Double(x, y+10, 35*rango, 10);
        
        nivel.explosion(arriba);
        nivel.explosion(abajo);
        nivel.explosion(izquierda);
        nivel.explosion(derecha);
    }
 
    
    public static int getCantidadBombas() {
        return cantidadBombas.get();
    }
    
    public static void setCantidadBombas(int cant) {
        cantidadBombas.set(cant);
    }
    
}