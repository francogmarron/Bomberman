
import com.entropyinteractive.*;
import java.awt.*;
import java.awt.event.*; //eventos
import java.awt.image.*;  //imagenes
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.*;
import java.util.concurrent.atomic.*;
import javax.imageio.ImageIO;


public class JuegoBomberman extends JGame {
    private BufferedImage black;
    private BufferedImage Primera;
    private Timer timer;
    private Timer moment;
    private AtomicBoolean delay = new AtomicBoolean(true);
    private AtomicBoolean delay2 = new AtomicBoolean(true);
    Reloj reloj;
    Ranking ranking;
    Jugador jugador;
    
    private int ascii = 65;
    private int setName = 0;
    
    
    private AtomicInteger pantalla = new AtomicInteger(0); //0 MENU 1 JUGAR 2 verRANKING 3 guardarPuntaje
    private boolean vidaQuitada = false;

    private AtomicBoolean opcionMenu = new AtomicBoolean(false);
    
    BufferedImage img_fondo = null;

    Nivel nivel = Nivel.getInstance();
    Heroe heroe = new Heroe(nivel);
    Camara cam;
    Fondo fondo;

    public static void main(String[] args) {

        JuegoBomberman game = new JuegoBomberman();
        game.run(1.0 / 30.0);
        System.exit(0);
    }

    public JuegoBomberman() {

        super("Bomberman", 640, 480);

        System.out.println(appProperties.stringPropertyNames());

    }

    public void gameStartup() {

        try{            
            Sonido.init();
            reloj = new Reloj();
            ranking = new Ranking();
            jugador = new Jugador();
            ranking.lectura();
            Primera = ImageIO.read(getClass().getResource("imagenes/Main.png"));
            black = ImageIO.read(getClass().getResource("imagenes/blackScreen.png"));
            this.nivel = Nivel.getInstance();
            heroe.setPosicion(64, 96);
            cam = new Camara(0,0);
            cam.setVisible(640,480);
            fondo = new Fondo("imagenes/background.jpg");
            nivel.setLimitesMundo(fondo.getWidth(), fondo.getHeight());
            Sonido.MENU.playIf();
        }
        catch(Exception e){

        }  
    }

    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
         
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)){
            if(pantalla.get() == 1 && !Heroe.getQuemado()) {  //Juego
                if(heroe.posicion_validaY(heroe.getY() - heroe.getVelocidad() * delta)){
                    heroe.setY( heroe.getY() - heroe.getVelocidad() * delta);
                }
                    heroe.actualizarSprite(0);
                } 
                if(pantalla.get() == 3){ //Guardado Puntaje
                    if(ascii < 90 && delay.get()){
                        Sonido.SELECCION.play();
                        timer = new Timer();
                        delay.set(false);
                        timer.schedule(new Delay(), 200);
                        ascii++;
                    }
                }   
                if(pantalla.get() == 0){  //Menu Inicio
                    opcionMenu.set(false);
                    Sonido.SELECCION.play();
                }
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)){
            if(pantalla.get() == 1 && !Heroe.getQuemado()) { //Juego
                if(heroe.posicion_validaY(heroe.getY() + heroe.getVelocidad() * delta)){
                    heroe.setY( heroe.getY() + heroe.getVelocidad() * delta);
                }           
                    heroe.actualizarSprite(3);
            } 
            if(pantalla.get() == 3) {  //Guardado Puntos
                if(ascii > 65 && delay.get()){
                    Sonido.SELECCION.play();
                    timer = new Timer();
                    delay.set(false);
                    timer.schedule(new Delay(), 200);
                    ascii--;
                }
            }
            if(pantalla.get() == 0){    //Menu
                opcionMenu.set(true);
                Sonido.SELECCION.play();
            }
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)){
            if(pantalla.get() == 1 && !Heroe.getQuemado()) {    //Juego
                if(heroe.posicion_validaX(heroe.getX() - heroe.getVelocidad() * delta)){
                    heroe.setX( heroe.getX() - heroe.getVelocidad() * delta);
                }
                heroe.actualizarSprite(2);
            }
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)){
            if(pantalla.get() == 1 && !Heroe.getQuemado()) {    //Juego
                if(heroe.posicion_validaX(heroe.getX() + heroe.getVelocidad() * delta)){
                    heroe.setX( heroe.getX() + heroe.getVelocidad() * delta);
                }
                heroe.actualizarSprite(1);
            }
        }
        
        if (keyboard.isKeyPressed(KeyEvent.VK_X) && delay.get()){
            if(pantalla.get() == 1 && !Heroe.getQuemado()) {    //Juego
                timer = new Timer();
                delay.set(false);
                timer.schedule(new Delay(), 600);
                heroe.soltarBomba();
            }
        }
        
        if (keyboard.isKeyPressed(KeyEvent.VK_Z) && delay2.get() && Detonador.getStatus()){
            if(pantalla.get() == 1 && !Heroe.getQuemado()){    //Juego
                delay2.set(false);
                timer = new Timer();
                timer.schedule(new Delay2(), 600);
                heroe.Detonar();
            }
        }
        
        if (keyboard.isKeyPressed(KeyEvent.VK_ENTER) && delay.get()){
            if(pantalla.get() != 1){                            
                timer = new Timer();
                delay.set(false);
                timer.schedule(new Delay(), 500);
                if(!opcionMenu.get() && pantalla.get() == 0) {  //Si flecha indica Jugar
                    Nivel.setPasarLvl(true);
                    pantalla.set(1);
                    setName = 0;
                    jugador.reinicioPuntaje();
                    Nivel.setNumNivel(1);
                    Sonido.MENU.stop();
                }
                if(pantalla.get() == 2)     //Salida Ranking
                    pantalla.set(0);
                
                else if(opcionMenu.get() && pantalla.get() == 0){ //Si flecha indica Ranking
                    ranking.lectura();
                    pantalla.set(2);
                }
                
                if(pantalla.get() == 3){        //Guardado Puntos
                    Sonido.SELECCION.play();
                    jugador.setNickname(String.valueOf((char)ascii), setName);
                    setName++;
                    ascii=65;
                }
                
            }
        }
            
        
        // Esc fin del juego
        LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for (KeyEvent event: keyEvents) {
            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                Sonido.FONDO.stop();
                Sonido.MENU.stop();
                Sonido.TRANSICION.stop();
                Sonido.FIN.stop();
                stop();
            }
        }

        heroe.update(delta);
        cam.seguimiento(heroe); /// seguimiento al Personaje.
        
        if(Heroe.getQuemado() && !vidaQuitada) {
            Sonido.QUEMADO.playIf();
            heroe.morir();
            vidaQuitada = true;
        }
        //nivel.enemigo.get(0).update(10);
        //nivel.enemigo.get(1).update(10);
        //nivel.enemigo.get(2).update(10);
       // nivel.actualizarEnemigo(delta);
    }

    public void gameDraw(Graphics2D g) {
        if(pantalla.get() == 0){        //Menu Inicio
            g.drawImage(Primera,0,0,null);
            g.setFont(new Font("end",Font.BOLD,40));
            g.setColor(Color.WHITE);
            if(!opcionMenu.get()){
                g.drawString("\u2192",190,350);} //flecha start
            else if(opcionMenu.get()){
                g.drawString("\u2192",190,407);} //flecha ranking
        }
        
        if(pantalla.get() == 2) {       //Ranking
            g.drawImage(black, 0, 0,null);
            g.setFont(new Font("",Font.BOLD,20));
            g.setColor(Color.WHITE);
            g.drawString("Top 10 Players", 230, 50);
            for(int i = 0; i < 10; i++){
                g.drawString(String.valueOf(i+1), 140, 100+30*i);
                g.drawString(Ranking.lineaPlayer[i], 190, 100+30*i);
                g.drawString(Ranking.lineaPoints[i], 400, 100+30*i);
            }
        }
        
        if (pantalla.get() == 1){       //Juego

            reloj.correTiempo();

            g.drawImage(img_fondo,0,0,null);// imagen de fondo

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Nivel nivel = Nivel.getInstance();
            g.translate(cam.getX(),cam.getY());

            fondo.draw(g);
            nivel.draw(g);
            heroe.draw(g);


            g.translate(-cam.getX(),-cam.getY());

            g.setColor(Color.white);
            g.setFont(new Font("",Font.BOLD,14));
            g.drawString("Tiempo Restante:  "+reloj.getTiempoJuegable(),10,40);
            g.drawString("Puntos: "+ String.valueOf(Jugador.getPuntaje()), 280, 40);
            g.drawString("Nivel Actual: "+String.valueOf(Nivel.getNumNivel()), 490, 40);

            if(Heroe.getQuemado() && Heroe.getVidas()!=0 && Heroe.getAnimacionM() || Nivel.getPasarLvl()){
                g.drawImage(black, 0, 0,null);
                g.setFont(new Font("",Font.BOLD,15));
                g.drawString("Nivel "+ String.valueOf(Nivel.getNumNivel()), 290, 230);
                g.drawString("Vidas Restantes "+ String.valueOf(Heroe.getVidas()), 250, 265);
                nivel.regenerarMundo();
                heroe.setPosicion(64,96);
                reloj.reiniciarTiempo();
                Sonido.MENU.stop();
                Sonido.FONDO.stop();
                Sonido.TRANSICION.playIf();
                moment = new Timer();
                moment.schedule(new transicion(), 2700);
            } 

            if(Heroe.getVidas() == 0 && Heroe.getAnimacionM()){
                g.drawImage(black, 0, 0,null);
                g.setFont(new Font("end",Font.BOLD,20));
                g.drawString("GAME OVER", 260, 250);
                Sonido.MUERTE.stop();
                Sonido.FIN.playIf();
                moment = new Timer();
                moment.schedule(new reinicio(), 7000);
            } 
        }
        if(pantalla.get() == 3){            //Guardado Puntos
            g.drawImage(black, 0, 0,null);
            g.setFont(new Font("",Font.BOLD,20));
            g.setColor(Color.WHITE);
            g.drawString("Felicidades Obtuviste "+String.valueOf(Jugador.getPuntaje())+" Puntos", 150, 110);
            g.drawString("Ingresa tu Nick", 235, 200);
                if(setName > 0)
                g.drawString(jugador.getNickname(0), 290, 240);
                if(setName > 1)
                g.drawString(jugador.getNickname(1), 310, 240);
                if(setName <= 2)
                g.drawString(String.valueOf((char)ascii), 290+20*setName, 240);
                if(setName > 2 ){
                    g.drawString(jugador.getNickname(2), 330, 240);
                    g.drawString("Tu Puntaje Fue Guardado",205,330);
                }
                if(setName == 4){
                    ranking.store(jugador.getNickname(0),jugador.getNickname(1),jugador.getNickname(2), Jugador.getPuntaje(), ranking.buscarPosicion(Jugador.getPuntaje()));
                    pantalla.set(0);
                    Sonido.MENU.playIf();
                }
            }
    }
    
    public class Delay extends TimerTask {
        public void run() {
            delay.set(true);
        } 
    }
    
    public class Delay2 extends TimerTask {
        public void run() {
            delay2.set(true);
        } 
    }

    public void gameShutdown() {
       Log.info(getClass().getSimpleName(), ".....");
    }
    
    public class transicion extends TimerTask {
        public void run() {
            Heroe.setQuemado(false);
            Nivel.setPasarLvl(false);
            moment.cancel();
            vidaQuitada = false;
            Heroe.setAnimacionM(false);
            Sonido.FONDO.playIf();
        }
    } 
    
    public class reinicio extends TimerTask {
        public void run() {
            Heroe.setQuemado(false);
            moment.cancel();
            vidaQuitada = false;
            opcionMenu.set(false);
            Heroe.setAnimacionM(false);
            if(ranking.buscarPosicion(Jugador.getPuntaje()) != -1){
                pantalla.set(3);
            }
            else{
                pantalla.set(0);
                Sonido.MENU.playIf();
            }
            Sonido.FIN.stop();
            Heroe.setVidas(3);
        }
    }
}

