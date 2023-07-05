
import java.util.Timer;
import java.util.TimerTask;



public class Salto extends Bonus{
    
    private static boolean activado = false;
    Timer salto;
    public Salto(double X, double Y) {
        super("imagenes/bonusSalto.png", X, Y);
    }
    
    public void activarEfecto () {
        activado = true;
        salto = new Timer();
        salto.schedule(new desactivar(), 8000);
        Jugador.sumarPuntaje(50);
        
    }
    
    private class desactivar extends TimerTask {
        public void run() {
            activado = false;
            salto.cancel();
        }
    }
    
    public static boolean getStatus() {
        return activado;
    }
    
    public static void setStatus(boolean x) {
        activado = false;
    }
    
}
