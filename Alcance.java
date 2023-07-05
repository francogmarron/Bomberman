
public class Alcance extends Bonus{
    public Alcance (double X, double Y){
        super("/imagenes/bonusFuego.png", X, Y);
    }
    
    
    public void activarEfecto(){
        if(Bomba.getRango() < 4)
            Bomba.setRango(Bomba.getRango()+1);
        Jugador.sumarPuntaje(50);
    }
    
}