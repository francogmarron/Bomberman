
import java.util.LinkedList;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Nivel {
    static private int numNivel = 1;
    static private LinkedList<ParedPiedra> paredP;
    static private LinkedList<ParedLadrillo> paredL;
    static private LinkedList<Bomba> bomba;
    static private LinkedList<Alcance> bonusAlcance;
    static private LinkedList<MasVelocidad> bonusVelocidad;
    static private LinkedList<BombaExtra> bonusBombaPlus;
    static private LinkedList<VidaExtra> bonusVidaExtra;
    static private LinkedList<Puerta> puerta;
    static private LinkedList<Salto> bonusSalto;
    static private LinkedList<Detonador> bonusDetonate;
    private int[] paredHueca;

    static public LinkedList<Enemigo> enemigo;
    static private Nivel instancia = null;
    static private boolean bombaSinColision = true;
    static private int bombasActuales = 0;
    static Semaphore sem = new Semaphore(1, true);

    Integer posEX=640,posEY=320,posEY1=416,posEX1=704,posEX2=96,posEY2=224;
    String [] imagenemigo={"fantasma.png","fantasma.png","fantasma.png",};

    static private Rectangle2D heroeR;
    
    private static boolean pasarLvl = false;
    private static final int ANCHO_MUNDO=640*13;
    private static final int ALTO_MUNDO=480;


    private Rectangle2D nivel;

    private Nivel(){
        paredP = new LinkedList<ParedPiedra>(); 
        paredL = new LinkedList<ParedLadrillo>();
        bomba = new LinkedList<Bomba>();
        bonusAlcance = new LinkedList<Alcance>();
        bonusVelocidad = new LinkedList<MasVelocidad>();
        bonusBombaPlus = new LinkedList<BombaExtra>();
        bonusVidaExtra = new LinkedList<VidaExtra>();
        puerta = new LinkedList<Puerta>();
        bonusSalto = new LinkedList<Salto>();
        bonusDetonate = new LinkedList<Detonador>();
        enemigo=new LinkedList<Enemigo>();
        cargarParedesPiedra(23,13);
        cargarParedesLadrillo(23,13,3); //cantidad ladrillos en pantalla.
        ubicarBonus(3);
        cargarEnemigos(1);
        ubicarPuerta(3);
        nivel = new Rectangle2D.Double(0,0,ANCHO_MUNDO,ALTO_MUNDO);
    }

    private static void createInstance(){
        if (instancia == null) {
            // S�lo se accede a la zona sincronizada
            // cuando la instancia no est� creada
            synchronized(Nivel.class) {
                // En la zona sincronizada ser�a necesario volver
                // a comprobar que no se ha creado la instancia
                if (instancia == null) {
                    instancia = new Nivel();
                }
            }
        }
    }
    
    public static boolean getPasarLvl() {
        return pasarLvl;
    }
    
    public static void setPasarLvl(boolean bool) {
        pasarLvl = bool;
    }
    
    public static int getNumNivel() {
        return numNivel;
    }
    
    public static void setNumNivel(int x) {
        numNivel = x;
    }

    public static Nivel getInstance(){
        if(instancia == null){
            instancia = new Nivel();
        }
        return instancia;
    }

    public boolean contains(int x, int y){
        return nivel.contains(x,y);
    }

    public boolean contains(int x, int y, int w,  int h){
        return nivel.contains(x,y,w,h);
    }

    public void setLimitesMundo(int w,int h){
        nivel= new Rectangle2D.Double(0,0,w,h);
    }

    public Rectangle2D getRectangle(){
        return this.nivel;
    }

    public float getWidth(){
            return (float)nivel.getWidth();
    }

    public float getHeight(){
            return (float)nivel.getHeight();
    }

    public int getBombasActuales() {
        return bombasActuales;
    }
    
    private void cargarParedesPiedra(int ancho, int alto){
        // Bordes superior e inferior
        for(int i=1; i<=ancho; i++){
                paredP.add(new ParedPiedra(i*32, 32+32)); 
                paredP.add(new ParedPiedra(i*32, 32*alto+32)); 
        }
        // Bordes laterales
        for(int i=2; i<alto; i++){
                paredP.add(new ParedPiedra(32, i*32+32)); 
                paredP.add(new ParedPiedra(ancho*32, i*32+32)); 
        }
        //paredes interiores
        for(int i=3; i<alto; i++){
            for(int j=3; j<ancho; j++){
                if(i % 2 != 0 && j % 2 != 0)
                    paredP.add(new ParedPiedra(j*32,i*32+32));
            }
        }
    }

        private void cargarParedesLadrillo(int ancho, int alto, int porcentaje){
        Random r = new Random();
        for(int i=2; i<alto; i++){
            for(int j=2; j<ancho; j++){
                if(!(i == 2 && j == 2) && !(i == 2 && j == 3) && !(i == 3 && j == 2)
                && !(i % 2 != 0 && j % 2 != 0) && r.nextInt(10)<porcentaje){
                    if((posEY!=(j*32)||posEX!=(i*32+32))&&((posEY1!=(j*32)||posEX1!=(i*32+32)))&&
                    (posEY2!=(j*32)||posEX2!=(i*32+32)))
                        paredL.add(new ParedLadrillo(j*32,i*32+32));
                    
                }
            }
        }
        paredHueca = new int[paredL.size()];
    }


    private void ubicarBonus(int porcentaje) {
        Random r = new Random();
        int indice = 0;
        for(int i = 0; i < paredL.size(); i++){
            if(r.nextInt(30) < porcentaje) {
            switch(r.nextInt(6)){
                case 0:
                    bonusAlcance.add(new Alcance(paredL.get(i).getX(),paredL.get(i).getY()));
                    break;
                case 1:
                    bonusVelocidad.add(new MasVelocidad(paredL.get(i).getX(),paredL.get(i).getY()));
                    break;
                case 2:
                    bonusBombaPlus.add(new BombaExtra(paredL.get(i).getX(),paredL.get(i).getY()));
                    break;
                case 3:
                    bonusVidaExtra.add(new VidaExtra(paredL.get(i).getX(), paredL.get(i).getY()));
                    break;
                case 4:
                    bonusSalto.add(new Salto(paredL.get(i).getX(), paredL.get(i).getY()));
                    break;
                case 5:
                    bonusDetonate.add(new Detonador(paredL.get(i).getX(), paredL.get(i).getY()));
                    break;
                }
            }
            else {
                paredHueca[indice] = i;
                indice++;
            }
        }
    }
    
    
    public void ubicarPuerta(int porcentaje) {
        Random r = new Random();
        int indice = 0;
        while(puerta.isEmpty()){
            if(r.nextInt(30) < porcentaje) {
                puerta.add(new Puerta(paredL.get(paredHueca[indice]).getX(), paredL.get(paredHueca[indice]).getY()));
            }
            indice ++;
            if(paredHueca.length-1 == indice)
                indice = 0;
        }
    }
    
    public void nuevaBomba(double x, double y){
        bomba.add(new Bomba(x, y, this));
        bombaSinColision = true;
        bombasActuales++;
    }
    
    public void quitarBomba() {
        if(!bomba.isEmpty()){
            Sonido.FUEGO.play();
            bomba.getFirst().explotar();
            bomba.removeFirst();
            bombasActuales--;
        }
    }
    
    public void rectanguloHeroe(Rectangle2D r){
        heroeR = r;
    }
        
    public void draw(Graphics2D g){
        for(int i=0; i<paredP.size(); i++){
            paredP.get(i).draw(g);
        }
        for(int i=0; i<bonusAlcance.size(); i++){
            bonusAlcance.get(i).draw(g);
        }
        for(int i=0; i<bonusVelocidad.size(); i++){
            bonusVelocidad.get(i).draw(g);
        } 
        
        for(int i=0; i<bonusBombaPlus.size(); i++){
            bonusBombaPlus.get(i).draw(g);
        } 
        for(int i=0; i<bonusVidaExtra.size(); i++){
            bonusVidaExtra.get(i).draw(g);
        }
        
        for(int i=0; i<puerta.size(); i++){
            puerta.get(i).draw(g);
        } 
        for(int i=0; i<bonusSalto.size(); i++){
            bonusSalto.get(i).draw(g);
        }
        for(int i=0; i<bonusDetonate.size(); i++){
            bonusDetonate.get(i).draw(g);
        }
        
        for(int i=0; i<paredL.size(); i++){
            paredL.get(i).draw(g);
        } 
        for(int i=0; i<bomba.size(); i++){
            bomba.get(i).draw(g);
        }
        for(int i=0; i<enemigo.size(); i++){
            enemigo.get(i).draw(g);
        }
        
        
    }

    static public boolean colision(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0, j=0, l=0, k=0;
        while(puede && i < paredP.size()){
            if(rPosicion.intersects(paredP.get(i).getBounds())){
                puede = false;
            }
            i++;
        }
        while(puede && j < paredL.size()){
            if(rPosicion.intersects(paredL.get(j).getBounds())){
                puede = false;
            }
            j++;
        }
        if(!Salto.getStatus()){
            while(puede && l < bomba.size() && !bomba.isEmpty()){
                if(!rPosicion.intersects(bomba.getLast().getBounds())){
                    bombaSinColision = false;}
                if(rPosicion.intersects(bomba.get(l).getBounds()) && bombaSinColision == false){
                    puede = false;
                }
                l++;
            }
        }
        while(k < enemigo.size() && !enemigo.isEmpty()){
            if(rPosicion.intersects(enemigo.get(k).getBounds())){
                Heroe.setQuemado(true);
            }
            k++;
        }
        return puede;
    }
    
    static public boolean colisionEnemies(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0, j=0, l=0;
        while(puede && i < paredP.size()){
            if(rPosicion.intersects(paredP.get(i).getBounds())){
                puede = false;
            }
            i++;
        }
        while(puede && j < paredL.size()){
            if(rPosicion.intersects(paredL.get(j).getBounds())){
                puede = false;
            }
            j++;
        }
            while(puede && l < bomba.size() && !bomba.isEmpty()){
                if(rPosicion.intersects(bomba.getLast().getBounds()))
                    puede = false;
                l++;
            }
        return puede;
    }
    
   
    
    public void obtenerBonus(Rectangle2D rPosicion) {
        int i=0;
        while(i < bonusAlcance.size()) {
            if(rPosicion.intersects(bonusAlcance.get(i).getX()+15,bonusAlcance.get(i).getY()+15, 10,10)){
                bonusAlcance.get(i).activarEfecto();
                Sonido.BONUS.playIf();
                bonusAlcance.remove(i);  
            }
            i++;
        }
        i=0;
        while(i < bonusVelocidad.size()) {
            if(rPosicion.intersects(bonusVelocidad.get(i).getX()+15,bonusVelocidad.get(i).getY()+15, 10,10)){
                bonusVelocidad.get(i).activarEfecto();
                Sonido.BONUS.playIf();
                bonusVelocidad.remove(i); 
            }
            i++;
        }
        i=0;
        while(i < bonusBombaPlus.size()) {
            if(rPosicion.intersects(bonusBombaPlus.get(i).getX()+15,bonusBombaPlus.get(i).getY()+15, 10,10)){
                bonusBombaPlus.get(i).activarEfecto();
                Sonido.BONUS.playIf();
                bonusBombaPlus.remove(i);    
                
            }
            i++;
        }
        i=0;
        while(i < bonusVidaExtra.size()) {
            if(rPosicion.intersects(bonusVidaExtra.get(i).getX()+15,bonusVidaExtra.get(i).getY()+15, 10,10)){
                bonusVidaExtra.get(i).activarEfecto();
                Sonido.BONUS.playIf();
                bonusVidaExtra.remove(i);    
            }
            i++;
        }
        i=0;
        while(i < bonusSalto.size()) {
            if(rPosicion.intersects(bonusSalto.get(i).getX()+15,bonusSalto.get(i).getY()+15, 10,10)){
                bonusSalto.get(i).activarEfecto();
                Sonido.BONUS.playIf();
                bonusSalto.remove(i);    
            }
            i++;
        }
        i=0;
        while(i < bonusDetonate.size()) {
            if(rPosicion.intersects(bonusDetonate.get(i).getX()+15,bonusDetonate.get(i).getY()+15, 10,10)){
                bonusDetonate.get(i).activarEfecto();
                Sonido.BONUS.playIf();
                bonusDetonate.remove(i);    
            }
            i++;
        }
        i=0;
        while(i < puerta.size()) {
            if(rPosicion.intersects(puerta.get(i).getX()+15,puerta.get(i).getY()+15,10,10)){
                puerta.get(i).activarEfecto();
                Sonido.FONDO.stop();
                Sonido.PUERTA.playIf();
                try{
                    TimeUnit.SECONDS.sleep(3);
                }
                catch(InterruptedException ie){}
                puerta.clear();
                pasarLvl = true;
            }
            i++;
        }        
        
        
        
    } 

        public void explosion(Rectangle2D rPosicion){
        int i=0, j=0, k=0, indiceCercano = -1;
        boolean piedraCubierto = false;
        double xCercanoLadrillo = paredL.getFirst().getX(), yCercanoLadrillo = paredL.getFirst().getY();
        double distanciaXLadrillo, distanciaYLadrillo;
        double distanciaXHeroe = Math.abs(bomba.getFirst().getX() - heroeR.getX());
        double distanciaYHeroe = Math.abs(bomba.getFirst().getX() - heroeR.getY());
        

        while(i < paredL.size()){
            if(rPosicion.intersects(paredL.get(i).getBounds())){
                distanciaXLadrillo = Math.abs(bomba.getFirst().getX() - paredL.get(i).getX());
                distanciaYLadrillo = Math.abs(bomba.getFirst().getY() - paredL.get(i).getY());
                if(xCercanoLadrillo >= distanciaXLadrillo && yCercanoLadrillo >= distanciaYLadrillo){
                    indiceCercano = i;
                    xCercanoLadrillo = distanciaXLadrillo;
                    yCercanoLadrillo = distanciaYLadrillo;
                }
            }
            i++;
        }        
        double distanciaXPiedra, distanciaYPiedra;
        while(j < paredP.size()){
            if(rPosicion.intersects(paredP.get(j).getBounds())){
                distanciaXPiedra = Math.abs(bomba.getFirst().getX() - paredP.get(j).getX());
                distanciaYPiedra = Math.abs(bomba.getFirst().getY() - paredP.get(j).getY());
                if(xCercanoLadrillo >= distanciaXPiedra && yCercanoLadrillo >= distanciaYPiedra){
                    indiceCercano = -1;
                    if(rPosicion.intersects(heroeR.getBounds()) && distanciaXPiedra >= distanciaXHeroe && distanciaYPiedra >= distanciaYHeroe){
                        Heroe.setQuemado(true);
                    }else {
                        piedraCubierto = true;
                    }
                }
            }
            j++;
        }
        
        while(k < enemigo.size()){
            if(rPosicion.intersects(enemigo.get(k).getBounds())){
                enemigo.get(k).morir();
                enemigo.remove(k);
            }
            k++;
        }
        
        if(indiceCercano != -1){
            
            Thread t1 = new Thread(new hiloPared(indiceCercano, sem));
            t1.start();
        }
        if(rPosicion.intersects(heroeR.getBounds()) && !piedraCubierto){
            Heroe.setQuemado(true);
        }
    }

        
        //Hilo destruccion pared
        public class hiloPared implements Runnable {
            int indice;
            Semaphore semaforo;
            public hiloPared (int i, Semaphore s){
                indice = i;
                semaforo = s;
            }
        @Override
        public void run() {
            paredL.get(indice).destruir();
            ParedLadrillo aux = paredL.get(indice);
            while(!paredL.get(indice).finAnimacion()){}
                try{
                    semaforo.acquire();
                    paredL.remove(paredL.indexOf(aux)); 
                    semaforo.release();
                }catch(Exception e){}
            }
        }
        
    public void regenerarMundo() {
        paredP.clear();
        paredL.clear();
        bomba.clear();
        bonusAlcance.clear();
        bonusVelocidad.clear();
        bonusBombaPlus.clear();
        bonusVidaExtra.clear();
        bonusSalto.clear();
        bonusDetonate.clear();
		enemigo.clear();
        puerta.clear();
        cargarParedesPiedra(23,13);
        cargarParedesLadrillo(23,13,3);
        ubicarBonus(3);
        ubicarPuerta(3);
		cargarEnemigos(1);
		
    }
        
        public void cargarEnemigos( int cant){
            Random r = new Random();
            Integer tipo=r.nextInt(3);
            enemigo.add(new Enemigo(nivel,imagenemigo[tipo],posEX,posEY,tipo));
            enemigo.add(new Enemigo(nivel,imagenemigo[tipo],posEX2,(posEY2),tipo));
            enemigo.add(new Enemigo(nivel,imagenemigo[tipo],(posEX1),posEY1,tipo));
    }
        
        
        public void  actualizarEnemigo(double delta){
        for(int i=0;i<enemigo.size();i++){
            enemigo.get(i).update(delta);
        }
    }
    
}