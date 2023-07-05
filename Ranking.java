
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

public class Ranking {
    public static String lineaPlayer[];
    public static String lineaPoints[];
    
    public Ranking () {
        
        lineaPlayer = new String[11];
        lineaPoints = new String[11];
    }
    
    public void lectura() {
        try{
            File archivo = new File ("Ranking.txt");
            FileReader read = new FileReader(archivo);
            BufferedReader buff = new BufferedReader(read);
            String linea[] = new String[11];
            for(int i =0; i < 10; i++){
                linea[i] = buff.readLine();
                lineaPlayer[i] = linea[i].substring(0,3);
                lineaPoints[i] = linea[i].substring(5);
            }
            buff.close();
            read.close();
        } catch (Exception e) {}
    }
    
    public void store(String letra1, String letra2, String letra3, int Puntaje, int posicion) {
        try{
            FileWriter archivo = new FileWriter("Ranking.txt");
            PrintWriter printer = new PrintWriter(archivo);
            for(int i = 10; i > posicion; i--){
                lineaPlayer[i] = lineaPlayer[i-1];
                lineaPoints[i] = lineaPoints[i-1];
            }
            lineaPlayer[posicion] = letra1+letra2+letra3;
            lineaPoints[posicion] = String.valueOf(Puntaje);
            
            for(int i = 0; i<10; i++){
                printer.println(lineaPlayer[i]+"  "+lineaPoints[i]);
            }
            printer.close();
            archivo.close();
        } catch (Exception e) {}
    }
    
    public int buscarPosicion (int Puntaje){
        int pos = -1;
        try{
            for(int i =0; i < 10; i++){
                if(Puntaje > Integer.parseInt(lineaPoints[i])){
                    pos = i;
                    break;
                }
            }
        } catch (Exception e) {}

        return pos;
    }
    
/*    private static final String NOMBRE_BASEDATOS = "Ranking.db";
    private static final String SQL_TABLA ="CREATE TABLE IF NOT EXISTS 	jugador \n"+
    "(id_jugador INTEGER PRIMARY KEY AUTOINCREMENT , nickName TEXT, puntaje INTEGER, fecha TEXT)";
    private Connection conn;
    private Statement statemt;
    private ResultSet results;
    private PreparedStatement prepState;

    public Ranking(){
       this.conn = null; 
       this.statemt = null;
       this.results = null;
       this.prepState= null;
       this.inicializar();
    }
    
    private void inicializar() {
        try { 
            String url = "jdbc:sqlite:"+NOMBRE_BASEDATOS;
            conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos
            statemt = conn.createStatement();
            String sql =SQL_TABLA;
            statemt.executeUpdate(sql);
            System.out.println("Conexion exitosa");
            statemt.close();  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en inicializar()");
                ex.printStackTrace();
            }
        
        }
    }
    
    private void cerrar(){
        try{
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }  
    }
    
    public void insertarJugadorAlRanking(String nickName, int puntaje){
        try{
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
            String sql = "INSERT INTO jugador(nickName,puntaje,fecha) VALUES(?,?,?)";
            this.prepState = conn.prepareStatement(sql);
            this.prepState.setString(1, nickName);
            this.prepState.setInt(2, puntaje);
            this.prepState.setString(4,formateador.format(new Date()));
            this.prepState.executeUpdate();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en insertarJugadorAlRanking");
                ex.printStackTrace();
            }
        }

    }
    
    public Vector<String> verRanking(){
         // la mejor forma de usar Vector<String> tableRanking;
        try{
                Vector<String> tableRanking=new Vector();
                String sql ="Select * from jugador order by(puntaje) desc";
                results = statemt.executeQuery(sql);
                while(results.next()){
                    tableRanking.add(((Integer)(results.getInt("id_jugador"))).toString());//1
                    tableRanking.add(results.getString("nombre o pseudonimo"));                       //2
                    tableRanking.add(results.getString("fecha"));                          //4
                    tableRanking.add(((Integer)(results.getInt("puntaje"))).toString());   //5
                }
                return tableRanking;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error en mostrarRanking()");
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    public void borrarRanking(){

        try{
            statemt = conn.createStatement();
            statemt.executeUpdate("drop table if exists jugador");

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    */
}