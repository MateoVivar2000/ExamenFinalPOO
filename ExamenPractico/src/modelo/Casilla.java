package modelo;

/* -Representa una celda individual en el tablero del Buscaminas.
   -Implementa el encapsulamiento para proteger los estados internos.*/
 
public class Casilla {

    // Atributos 
    private boolean tieneMina;
    private boolean estaDescubierta;
    private boolean estaMarcada; 
    private int minasAdyacentes;
    private final int fila;
    private final int columna;

//     * Constructor
     /* @param fila Fila de la casilla (0-9)
     * @param columna Columna de la casilla (0-9)*/
     */
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.tieneMina = false;
        this.estaDescubierta = false;
        this.estaMarcada = false;
        this.minasAdyacentes = 0;
    }

    // --- Métodos Modificadores (Mutators/Setters) ---

    public void colocarMina() {
        this.tieneMina = true;
    }

    public void setMinasAdyacentes(int minasAdyacentes) {
        this.minasAdyacentes = minasAdyacentes;
    }
    
    // Método clave que cambia el estado al ser seleccionada
    public void descubrir() {
        this.estaDescubierta = true;
    }
    
    // Método para marcar/desmarcar con bandera (requisito del juego)
    public void marcar() {
        if (!estaDescubierta) { // Solo se puede marcar si no ha sido descubierta
             this.estaMarcada = !this.estaMarcada; 
        }
    }

    // --- Métodos Accesores (Accessors/Getters) ---

    public boolean tieneMina() {
        return tieneMina;
    }

    public boolean estaDescubierta() {
        return estaDescubierta;
    }

    public boolean estaMarcada() {
        return estaMarcada;
    }

    public int getMinasAdyacentes() {
        return minasAdyacentes;
    }
    
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve la representación en String de la casilla para la consola.
     * Esta lógica será usada por la Vista.
     */
    @Override
    public String toString() {
        if (estaMarcada) {
            return "F"; // Bandera (Flag)
        }
        if (!estaDescubierta) {
            return "☐"; // Casilla cubierta
        }
        if (tieneMina) {
            return "X"; // Ubicación de una mina
        }
        if (minasAdyacentes > 0) {
            return String.valueOf(minasAdyacentes); // Muestra el número
        }
        // Si es descubierta y tiene 0 minas adyacentes
        return " "; // Espacio vacío seleccionado
    }
}