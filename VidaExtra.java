


public class VidaExtra extends Bonus{
    public VidaExtra(double X, double Y){
        super("/imagenes/bonusVida.png", X, Y);
    }
    
    
    public void activarEfecto () {
        Heroe.setVidas(Heroe.getVidas()+1);
        Jugador.sumarPuntaje(50);
    }
  
}