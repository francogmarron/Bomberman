
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
public class LanzadorJuego extends JFrame implements ItemListener,ActionListener{
    /**
     
     */
    private static final long serialVersionUID = 1L;
    private JPanel panelJuego,panelList;
    private List juegos;
    private JPanel panelImagen, panelAccion;
    private JButton iniciar , configuracion;
    private BorderLayout borderImagen;
    CardLayout cardlayout;

    public LanzadorJuego() throws IOException {
            super("Lanzador de juego");
            //---------------------crear objetos
            panelJuego= new JPanel();
            panelList= new JPanel();
            juegos = new List(10, false);
            juegos.add("Arkanoid");
            juegos.add("Bomberman");
            juegos.add("mortal kombat");
            juegos.add("Castlevania");
            juegos.select(0);
            panelImagen = new JPanel();
            panelAccion = new JPanel();
            iniciar = new JButton("Iniciar");
            borderImagen = new BorderLayout();
            configuracion = new JButton("Ajustes");
            juegos.addItemListener(this);
            configuracion.addActionListener(this);
            iniciar.addActionListener(this);

            panelJuego.setLayout(borderImagen);
	        cardlayout=new CardLayout();
	        panelJuego.add(panelImagen,BorderLayout.CENTER);
	        panelJuego.add(panelAccion,BorderLayout.SOUTH);
	        panelList.add(juegos);
	        panelAccion.add(iniciar);
	        panelAccion.add(configuracion);
	        panelImagen.setLayout(cardlayout);
                panelImagen.add("Castlevania",new Diapositiva("imagenes/juegos/castlevania.jpg"));
	        panelImagen.add("Bomberman",new Diapositiva("imagenes/juegos/bomberman.jpg"));
	        panelImagen.add("mortal kombat",new Diapositiva("imagenes/juegos/4960135.jpg"));
                panelImagen.add("Arkanoid",new Diapositiva("imagenes/juegos/imagesd.jpg"));
            
            this.add(panelList,BorderLayout.WEST);
            this.add(panelJuego,BorderLayout.EAST);
            
            this.addWindowListener(new java.awt.event.WindowAdapter(){
                public void windowClosing(java.awt.event.WindowEvent e){
                    System.exit(0);
                }
            });

            this.pack();
	        this.setVisible(true);
            this.setLocationRelativeTo(null);
       }
       public void iniciar(String juego){
        if (juego.equals("Bomberman")){
            System.out.print("iniciar juego");
            JuegoBomberman game = new JuegoBomberman();
            Thread t = new Thread() {
                public void run() {
                    game.run(1.0 / 30.0);
                }
            };
            t.start();
        }
        else{
            
        }
    }
    
    public void itemStateChanged(ItemEvent e){
        cardlayout.show(panelImagen,(String)this.juegos.getSelectedItem());
    }
    public static void main(String a[]) throws IOException{
        LanzadorJuego instance = new LanzadorJuego();
    }
    
    public void actionPerformed(ActionEvent evt){
        if (evt.getActionCommand()==configuracion.getActionCommand()){
                PantallaOps instance = new PantallaOps();
        }
        if (evt.getActionCommand()==iniciar.getActionCommand()){
            this.iniciar(this.juegos.getSelectedItem());
        }
    }
    
}