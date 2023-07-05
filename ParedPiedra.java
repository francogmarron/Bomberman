public class ParedPiedra extends Pared {
    public ParedPiedra(double X, double Y){
        super("imagenes/pared_piedra.png", X, Y); 
        destruible = false;
    }
}