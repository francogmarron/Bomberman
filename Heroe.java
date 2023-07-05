
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class Heroe extends Personaje{  
    static private int vidas = 3;
    static private boolean quemado = false;

    private Timer timer;
    BufferedImage spriteSheet;
    BufferedImage img[];
    private int animacionActual=1;
    private int smoothSprite=0;
    private static boolean animacionM = false;
    
    public Heroe(Nivel nivel){
        super("/imagenes/heroe.png");
        vivo = true;
        this.nivel = nivel;
        cargadoSprites();
    }
    
    public void cargadoSprites() {
        int fila = 0;
        img = new BufferedImage[12];
        try{
           spriteSheet = ImageIO.read(getClass().getResource("imagenes/spriteSheetHero.png"));
            for(int i = 0 ; i < 2; i++) {
                for(int j = 0; j < 6 ; j++){
                    img[fila + j] = spriteSheet.getSubimage(4+(j*24), 20+(i*35), 23, 31);
                }
                fila = fila+6; 
            }
        }catch(IOException e) {}
        this.cargarSpriteArray(img, 12);
    }
    
    private void spriteMuerte() {
        img = new BufferedImage[5];
        img[0] = spriteSheet.getSubimage(4, 92, 23, 31);
        img[1] = spriteSheet.getSubimage(29, 92, 26, 33);
        img[2] = spriteSheet.getSubimage(62, 92, 40, 35);
        img[3] = spriteSheet.getSubimage(110, 95, 44, 35);
        img[4] = spriteSheet.getSubimage(156, 95, 45, 35);
        this.cargarSpriteArray(img, 5);
    }

    public void soltarBomba(){
        double x = getX() - Math.round(getX()%32);
        double y = getY() - Math.round(getY()%32);
        Rectangle2D r = new Rectangle2D.Double(x, y, 30, 30);
        if(Nivel.colision(r) && nivel.getBombasActuales() < Bomba.getCantidadBombas()){
            Sonido.BOMBA.play();
            nivel.nuevaBomba(x, y);
            timer = new Timer();
            timer.schedule(new fired(), 3000); 
        }
    }
    
        public class fired extends TimerTask {
        public void run() {
            nivel.quitarBomba();
        }
    }
        
    public void Detonar(){
        timer.cancel();
        nivel.quitarBomba();
    }
        
    @Override
     public void draw(Graphics2D g){
        if(!quemado)
            g.drawImage(img[animacionActual],(int)this.getX(),(int)this.getY(),null);
        
        
        else{
            if(smoothSprite > 8){
                smoothSprite = 0;
                if(animacionActual < 4)
                    animacionActual++;
                
            }else{
                smoothSprite++;
            }
            g.drawImage(img[animacionActual],(int)this.getX(),(int)this.getY(),null);
            if(animacionActual == 4 && smoothSprite == 8 && !animacionM){
                Sonido.FONDO.stop();
                Sonido.MUERTE.playIf();
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception e){}
                cargadoSprites();
                Sonido.MUERTE.stop();
                animacionM = true;
            }
        }
     }
        
         public void actualizarSprite (int estado){
        switch(estado) { 
            case 0:
                if((animacionActual > 4 && smoothSprite > 3)|| animacionActual < 3 || animacionActual > 5){
                    animacionActual = 3;
                    smoothSprite = 0;
                    Sonido.MOVERY.playIf();
                }
                else {
                    if(smoothSprite > 3){
                        animacionActual++;
                        smoothSprite = 0;
                        Sonido.MOVERY.play();}
                    else{
                        smoothSprite++;
                    }
                }
                break;
            case 1:
                if((animacionActual > 10 && smoothSprite > 3) || animacionActual < 9){
                    animacionActual = 9;
                    smoothSprite = 0;
                    Sonido.MOVERX.playIf();
                }
                else {
                    if(smoothSprite > 3){
                        animacionActual++;
                        smoothSprite = 0;
                        Sonido.MOVERX.play(); }
                    else{
                        smoothSprite++;
                    }
                }
                break;
            case 2:
                if((animacionActual > 7 && smoothSprite > 3) || animacionActual < 6 || animacionActual > 8 ){
                    animacionActual = 6;
                    smoothSprite = 0;
                    Sonido.MOVERX.playIf();
                }
                else {
                    if(smoothSprite > 3){
                        animacionActual++;
                        smoothSprite = 0;
                        Sonido.MOVERX.play();}
                    else{
                        smoothSprite++;
                    }
                }           
                break;
            case 3:
                if(animacionActual > 1  && smoothSprite > 3 || animacionActual > 2){
                    animacionActual = 0;
                    smoothSprite = 0;
                    Sonido.MOVERY.playIf();
                }
                else {
                    if(smoothSprite > 3){
                        animacionActual++;
                        smoothSprite = 0;
                        Sonido.MOVERY.play();}
                    else{
                        smoothSprite++;
                    }
                }              
                break;
            default: 
        }
    }
     
    public void morir() {
        Heroe.restarVida();
        animacionActual = 0;
        smoothSprite = 0;
        spriteMuerte();
        Bomba.setRango(1);
        Bomba.setCantidadBombas(1);
        this.setVelocidad(100.00);
        Detonador.setStatus(false);
        Salto.setStatus(false);
    }
    
    public static boolean getQuemado () {
        return quemado;
    }
    
    public static void setQuemado (boolean bool) {
        quemado = bool;
    }
    
    public static boolean getAnimacionM () {
        return animacionM;
    }
    
    public static void setAnimacionM (boolean bool) {
        animacionM = bool;
    }
    
    public static void setVidas(int v){
        vidas = v;
    }
    
    public static int getVidas(){
        return vidas;
    }
    
    public static void sumarVida(){
        vidas = vidas+1;
    }
    
    public static void restarVida(){
        if(vidas > 0)
            vidas = vidas -1;
    }
    
}