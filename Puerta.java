
public class Puerta extends Bonus{
    public Puerta(double X, double Y){
        super("/imagenes/puerta.png",X,Y);
    }
    
    public void activarEfecto(){
        Nivel.setNumNivel(Nivel.getNumNivel()+1);
        Jugador.sumarPuntaje(200);
    }
    
}