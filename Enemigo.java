
import java.util.Random;
import java.awt.geom.Rectangle2D;




public class Enemigo extends Personaje  {
    private int puntajeEnemigo;
    Double velocidad;
    Boolean explorar;
    Boolean cazar=false;
    Boolean huir=false;
    Integer  vida=1;
    public static final int FRAMES = 6;

   
    //constructor
   public Enemigo(Rectangle2D nivel, String string,int x,int y,int tipo) {
    super("imagenes/enemigos/"+string);
    this.puntajeEnemigo=100;
    this.velocidad=(tipo+2.0)*10;
    this.setPosicion(x, y);
    this.explorar=true;
    this.vida=1;
	}

    @Override
    public void update(double delta) {
        if(this.explorar){
            Random alet=new Random();
            Boolean mover=false;
            while(mover==false){
            int dires=alet.nextInt(4);
                if(dires==0||dires==1)
                    mover=moverVertical(delta, dires);  
                if(dires==2||dires==3)//DERECHA:
                    mover=moverHorizontal(delta, dires);
                if(!mover){
                    dires=alet.nextInt(4);
                }
            }
        }
    }
    
    public Boolean moverVertical(Double delta,int dires){
        Boolean mover=true;
        if(dires==0){//ARRIBA:
                if(this.posicion_validaYE(this.getY() - this.getVelocidad() * delta))
                    this.setY( this.getY() - this.getVelocidad() * delta);
                else
                     mover=false;
        }
        if(dires==1){//ABAJO:
                if(this.posicion_validaYE(this.getY() + this.getVelocidad() * delta))
                    this.setY( this.getY() + this.getVelocidad() * delta);
                else
                    mover=false; 
        }
        return mover;

    }
    public Boolean moverHorizontal(Double delta, int dires){
        Boolean mover=true;
        if(dires==2){//DERECHA:
            if(this.posicion_validaXE(this.getX() + this.getVelocidad() * delta))
                this.setX( this.getX() + this.getVelocidad() * delta);
            else
                mover=false;
        }
        if(dires==3){//IZQUIERDA:
            if(this.posicion_validaXE(this.getX() - this.getVelocidad() * delta))
                this.setX( this.getX() - this.getVelocidad() * delta);
            else
                mover=false;

        }
        return mover;
    }
   public void setPosicion(double x, double y) {
       // TODO Auto-generated method stub
       super.setPosicion(x, y);
   }
   @Override
   public Rectangle2D getBounds() {
       // TODO Auto-generated method stub
       return super.getBounds();
   }
   @Override
   public double getX() {
       // TODO Auto-generated method stub
       return super.getX();
   }
   @Override
   public double getY() {
       // TODO Auto-generated method stub
       return super.getY();
   }
   public int getPuntajenemigo() {
       return puntajeEnemigo;
   }
   @Override
   public double getVelocidad() {
       // TODO Auto-generated method stub
       return super.getVelocidad();
   }
   @Override
   public void setX(double x) {
       // TODO Auto-generated method stub
       super.setX(x);
   }
   @Override
   public void setY(double y) {
       // TODO Auto-generated method stub
       super.setY(y);
   }
   public void morir(){
    this.explorar=false;   
    this.vida=0;
    Jugador.sumarPuntaje(puntajeEnemigo);
   }

   


   




    
}
