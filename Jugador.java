
public class Jugador {
    private static String nickname[];
    private static int puntajePartida = 0;

    public Jugador() {
         nickname = new String[6];
    }

    public void setNickname(String nick, int posicion) {
        nickname[posicion] = nick;
    }
    
    public String getNickname(int posicion){
        return nickname[posicion];
    }

    public static void sumarPuntaje (int puntos){
        puntajePartida += puntos;
    }
    
    public void reinicioPuntaje(){
        puntajePartida = 0;
    }
    
    public static int getPuntaje () {
        return puntajePartida;
    } 
    
}