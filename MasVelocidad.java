

public class MasVelocidad extends Bonus{
    public MasVelocidad(double X, double Y){
        super("/imagenes/bonusVelocidad.png", X, Y);
    }
    
    public void activarEfecto() {
        this.setVelocidad(this.getVelocidad()*1.10); //10% de aumento de velocidad
        Jugador.sumarPuntaje(50);
    }    
}