



public class Detonador extends Bonus{
    
    private static boolean detonate = false;
    
    public Detonador(double X, double Y) {
        super("imagenes/bonusDetonador.png", X, Y);
    }
    
    public void activarEfecto () {
        detonate = true;
        Jugador.sumarPuntaje(50);
    }
    
    public static boolean getStatus() {
        return detonate;
    }
    
    public static void setStatus(boolean x) {
        detonate = x;
    }
}
