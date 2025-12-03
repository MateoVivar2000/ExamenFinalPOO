//PAULINA
package modelo;

import java.io.*;
// Importa las excepciones personalizadas o estándar que usarás aquí
// import modelo.excepciones.MinaSeleccionadaException; 

/**
 * Clase principal del Modelo que gestiona el estado general del juego
 * y maneja la persistencia (guardar/cargar).
 */
public class JuegoBuscaminas implements Serializable {
    
    // El Tablero es la parte más grande del juego, por eso va dentro de esta clase
    private Tablero tablero; 
    
    // El estado del juego: ayuda al Controlador a saber qué hacer.
    private EstadoJuego estado;
    
    // Enumeración interna para un manejo de estado limpio
    public enum EstadoJuego {
        EN_CURSO, VICTORIA, DERROTA
    }

    // --- Constructor ---
    
    public JuegoBuscaminas() {
        // Al iniciar, crea un nuevo tablero y el estado es EN_CURSO
        this.tablero = new Tablero(); 
        this.estado = EstadoJuego.EN_CURSO;
    }

    // --- Métodos de Control del Juego (Usados por el Controlador) ---
    
    /**
     * Intenta descubrir una casilla, actualiza el estado del juego si es necesario.
     * El Controlador llamará a este método para avanzar.
     */
    public boolean descubrirCasilla(int fila, int columna) throws Exception {
        // Delegamos la lógica de bajo nivel al Tablero
        boolean continua = tablero.descubrir(fila, columna);
        
        if (!continua) {
            // El Tablero informó que el jugador pisó una mina
            this.estado = EstadoJuego.DERROTA;
            return false;
        }

        if (tablero.esVictoria()) {
            this.estado = EstadoJuego.VICTORIA;
        }
        
        return true; // El juego sigue EN_CURSO
    }
    
    /**
     * Delega la acción de marcar una casilla con una bandera (Flag).
     */
    public void marcarCasilla(int fila, int columna) {
        // Puedes agregar aquí la lógica si el Tablero no la gestiona directamente
        tablero.getCasilla(fila, columna).marcar();
    }
    
    // --- Métodos para Persistencia de Datos (Requisito) ---
    
    private static final String ARCHIVO_GUARDADO = "buscaminas_guardado.bin";

    /**
     * Guarda el estado actual del juego utilizando serialización de objetos.
     */
    public void guardarJuego() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_GUARDADO))) {
            oos.writeObject(this); // Guarda el objeto JuegoBuscaminas completo
            System.out.println("Juego guardado exitosamente en: " + ARCHIVO_GUARDADO);
        } catch (IOException e) {
            System.err.println("Error al guardar el juego: " + e.getMessage());
        }
    }
    
    /**
     * Carga un estado de juego previamente guardado.
     * @return La instancia de JuegoBuscaminas cargada.
     */
    public static JuegoBuscaminas cargarJuego() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_GUARDADO))) {
            JuegoBuscaminas juegoCargado = (JuegoBuscaminas) ois.readObject();
            System.out.println("Juego cargado exitosamente.");
            return juegoCargado;
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró un archivo de juego guardado. Iniciando juego nuevo.");
            return new JuegoBuscaminas();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el juego. Iniciando juego nuevo. Detalle: " + e.getMessage());
            return new JuegoBuscaminas();
        }
    }

    // --- Getters ---
    
    public Tablero getTablero() {
        return tablero;
    }
    
    public EstadoJuego getEstado() {
        return estado;
    }

    public boolean estaActivo() {
        return estado == EstadoJuego.EN_CURSO;
    }
}