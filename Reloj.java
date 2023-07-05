
import java.util.Calendar;
import java.util.Date;




public class Reloj {
    
    private Calendar hoy = Calendar.getInstance();
    private long t = hoy.getTimeInMillis();
    private Date dInit = new Date(t + 200000);
    private Date dAhora;
    private long tiempoJugable;
    
    public void correTiempo() {
        dAhora= new Date( );
        long dateDiff = dInit.getTime() - dAhora.getTime();
        tiempoJugable = dateDiff / 1000;
        if(tiempoJugable == 0){
            Heroe.setQuemado(true);
        }
    }
    
    public void reiniciarTiempo() {
        hoy = Calendar.getInstance();
        t = hoy.getTimeInMillis();
        dInit = new Date(t + 200000);
    }
    
    public long getTiempoJuegable() {
        return tiempoJugable;
    }
    
    
}
    