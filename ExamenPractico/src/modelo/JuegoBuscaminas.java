//Paulina 
//TERMINADO 3/12/2025
package modelo;

import java.io.*;


  //Clase que representa la lógica principal del juego Buscaminas.
 // Gestiona el tablero, el estado del juego y la persistencia (guardar/cargar partidas).
 
public class JuegoBuscaminas implements Serializable {

    private static final long serialVersionUID = 1L; 
    private Tablero tablero;                  
    private EstadoJuego estado;               
    private static final String ARCHIVO_GUARDADO = "buscaminas_guardado.bin"; 
   
    // Enumeración que define los posibles estados del juego. donde  inicializa las casillas y 
    public enum EstadoJuego { EN_CURSO, VICTORIA, DERROTA }   
    public JuegoBuscaminas() {
        this.tablero = new Tablero();
        this.estado = EstadoJuego.EN_CURSO;
    }

   // ********** MÉTODO MARCAR CASILLA **********
      true si el juego continúa, false si se descubre una mina (DERROTA).
    
      
      
     //*********METODO BOOLEAN****************
      
     //nos ayuda a verificar so eñ juego continua o termina u otra opcion 
    public boolean descubrirCasilla(int fila, int columna) throws Exception {
        boolean continua = tablero.descubrir(fila, columna);

        if (!continua) {
            this.estado = EstadoJuego.DERROTA; 
            return false;
        }

        if (tablero.esVictoria()) {
            this.estado = EstadoJuego.VICTORIA; 
        }

        return true; 
    }

    // Marca o desmarca una casilla con una bandera (flag).
    
    public void marcarCasilla(int fila, int columna) {
        tablero.getCasilla(fila, columna).marcar();
    }

    //Obtiene el tablero completo del juego.
     
    public Tablero getTablero() { return tablero; }
    
     //**********METODO GETTER *******************
     //Aquí, el getter permite que el controlador revise el estado del juego y tome decisiones
    
    public EstadoJuego getEstado() { return estado; }
    public boolean estaActivo() { return estado == EstadoJuego.EN_CURSO; }
    public void guardarJuego() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_GUARDADO))) {
            oos.writeObject(this);
            System.out.println("Partida guardada en: " + ARCHIVO_GUARDADO);
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    //************METODO DESCUBRIR CASILLA*******//
 // Carga la partida guardada, o crea una nueva si no existe
    public static JuegoBuscaminas cargarJuego() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_GUARDADO))) {
            JuegoBuscaminas j = (JuegoBuscaminas) ois.readObject();
            System.out.println("Partida cargada desde: " + ARCHIVO_GUARDADO);
            return j;
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró partida guardada, iniciando nueva partida.");
            return new JuegoBuscaminas();// Si no hay archivo, creo un juego nuevo
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se pudo cargar la partida, iniciando nueva partida: " + e.getMessage());
            return new JuegoBuscaminas();// Si hay error, creo un juego nuevo
        }
    }
}