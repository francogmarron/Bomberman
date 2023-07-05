

public class Pared extends ObjetoGrafico{
    protected boolean destruible;

    public Pared(String img, double X, double Y) {
        super(img);
        this.setPosicion(X, Y);
    }
}