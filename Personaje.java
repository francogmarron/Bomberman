
import java.awt.image.*;  //imagenes

public class Personaje extends ObjetoGrafico{
    protected boolean vivo; 
    protected double velocidad; 

    public Personaje(String img){
        super(img);
    }

    public void setImagen(BufferedImage img){
        this.imagen[0] = img;
    }

    public void update(double delta){ 
        
    }

}