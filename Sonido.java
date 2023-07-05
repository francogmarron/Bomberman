

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum Sonido {
   BOMBA("Bomba.wav"),
   FUEGO("Fuego.wav"),
   PUERTA("Puerta.wav"),
   BONUS("Bonus.wav"),
   MENU("bombermanMenu.wav"),
   FONDO("fondoEscenario.wav"),
   MOVERX("movimientoX.wav"),
   MOVERY("movimientoY.wav"),
   TRANSICION("Transicion.wav"),
   SELECCION("opcionMenu.wav"),
   QUEMADO("Quemado.wav"),
   MUERTE("Muerte.wav"),
   FIN("GameOver.wav"),

   ;
   

   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   private Clip clip;

   Sonido(String wav) {
      try {
         URL url = this.getClass().getClassLoader().getResource("sonidos/"+wav);

         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

         clip = AudioSystem.getClip();

         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void playIf() {
      if (volume != Volume.MUTE) {
          if(!clip.isRunning()){
         	clip.setFramePosition(0);
        	clip.start();
          }
      }
   }
   
   public void play() {
       if (volume != Volume.MUTE) {
            clip.setFramePosition(0);
            clip.start();
       }
   }
   

   public void stop(){
        if (clip.isRunning()){
            clip.stop();
        }
   }
   
   public boolean isPlaying() {
       return clip.isRunning();
   }

   static void init() {
      values();
   }
}