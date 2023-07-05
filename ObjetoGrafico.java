
import java.awt.*;
import java.awt.image.*;  //imagenes
import javax.imageio.*; //imagenes
import java.io.IOException;
import java.awt.geom.*;

public class ObjetoGrafico {
    private Rectangle2D.Double posicion = new Rectangle2D.Double();
    protected BufferedImage imagen[] = null; 
    protected Nivel nivel;
    private int spriteActual =0;
    private int smoothSprite = 0;
    
    private static double velocidad = 100.00;
    
    public ObjetoGrafico(String img){
        imagen = new BufferedImage[1];
        try{
            imagen[0] = ImageIO.read(getClass().getResource(img));
        } catch(IOException e){
            System.out.println("Error al cargar la imagen:");
            System.out.println(img+"+>"+this.getClass().getResource(img));
        }
    }
    
        public void cargarSpriteArray(BufferedImage buffer[], int cantidadSprites){
            imagen = new BufferedImage[cantidadSprites];
            for(int i = 0; i < cantidadSprites; i++)
                imagen[i] = buffer[i];
    }

    public int getWidth(){
	return imagen[0].getWidth();
    }
    
    public int getHeight(){
	return imagen[0].getHeight();
    }

    public void paint(BufferedImage img){
        this.imagen[0]=img;
    }

    public void setX(double x){
        posicion.x=x;
    }

    public void setY(double y){
        posicion.y=y;
    }
    
    public double getX(){
        return posicion.getX(); 
    }

    public double getY(){
        return posicion.getY(); 
    }
        
    public double getVelocidad() {
        return velocidad;
    }
    
    public void setVelocidad(double nuevaVelocidad) {
        velocidad = nuevaVelocidad;
    }
    
    public void setPosicion(double x, double y){
        posicion.setFrame(x, y, 28, 28);
    }
    
    public void setAncho(int w, int h) {
        posicion.setFrame(posicion.getX(),posicion.getY(), w, h);
    }

    public void draw(Graphics2D g){
        if(smoothSprite > 3){
            smoothSprite = 0;
            if(spriteActual < imagen.length-1){
                spriteActual++;
            }
            else{
                spriteActual = 0;
            }
        
        }else{
            smoothSprite++;
        }
        g.drawImage(imagen[spriteActual],(int)posicion.getX(),(int)posicion.getY(),null);
        
    }

    public Rectangle2D getBounds(){
        return posicion.getBounds2D();
    }

    public boolean posicion_validaX(double delta){
        
        boolean esValido = true;
        if((delta > 800) || (delta < 0) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(delta, this.getY(), 23, 28);
        nivel.obtenerBonus(n);
        nivel.rectanguloHeroe(n);
        if(!Nivel.colision(n)){
            esValido = false;
        }
        return esValido;
    } 
    
    public boolean posicion_validaY(double delta){
        boolean esValido = true;
        if((delta > 600) || (delta < 15) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(this.getX(), delta, 23, 28);
        nivel.obtenerBonus(n);
        nivel.rectanguloHeroe(n);
        if(!Nivel.colision(n)){
            esValido = false;
        }
        return esValido;
    }
    
    public boolean finAnimacion(){
        if(smoothSprite == 3 && spriteActual == imagen.length-1){
        return true;
        }
        return false;
    }

    public boolean posicion_validaXE(double delta){
        boolean esValido = true;
        if((delta > 800) || (delta < 0) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(delta, this.getY(), 23, 28);
        if(!Nivel.colisionEnemies(n)){
            esValido = false;
        }
        return esValido;
}
    
public boolean posicion_validaYE(double delta){
        boolean esValido = true;
        if((delta > 600) || (delta < 15) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(this.getX(), delta, 23, 28);
        if(!Nivel.colisionEnemies(n)){
            esValido = false;
        }
        return esValido;
    }
    
/*        public boolean movimiento_valido(double delta){
        boolean valides=true;
        Rectangle2D m = new Rectangle2D.Double(delta, this.getX(), 3, 2);
        Rectangle2D n = new Rectangle2D.Double(delta, this.getY(), 3, 2);
        if((Nivel.colision(n))||(Nivel.colision(m))){
            valides = false;
        }
        return valides;
    } */
}