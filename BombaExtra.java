

public class BombaExtra extends Bonus{
    public BombaExtra(double X, double Y){
        super("/imagenes/bonusBombaExtra.png", X, Y);
    }
    
    public void activarEfecto() {
        if(Bomba.getCantidadBombas() < 3)
            Bomba.setCantidadBombas(Bomba.getCantidadBombas()+1);
        Jugador.sumarPuntaje(50);
    }
}