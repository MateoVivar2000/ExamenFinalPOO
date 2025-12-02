package modelo;

// Clase para representar una casilla individual en el tablero
public class Casilla {

    // Atributos privados (Encapsulamiento)
    private boolean tieneMina;
    private boolean estaDescubierta;
    private boolean estaMarcada;
    private int minasAdyacentes;
    private final int fila;
    private final int columna;

    /**
     * Constructor
     * @param fila Fila de la casilla (0-9)
     * @param columna Columna de la casilla (0-9)
     */
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.tieneMina = false; // Por defecto no tiene mina
        this.estaDescubierta = false; // Por defecto está cubierta
        this.estaMarcada = false; // Por defecto no está marcada
        this.minasAdyacentes = 0; // Por defecto cero minas alrededor
    }

    // --- Métodos Modificadores (Setters) ---

    public void colocarMina() {
        this.tieneMina = true;
    }

    public void setMinasAdyacentes(int minasAdyacentes) {
        this.minasAdyacentes = minasAdyacentes;
    }
    
    // Método clave que cambia el estado y se usa en el Controlador
    public void descubrir() {
        this.estaDescubierta = true;
    }
    
    public void marcar() {
        this.estaMarcada = !this.estaMarcada; // Alterna el estado de marcado
    }

    // --- Métodos Accesores (Getters) ---

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
     * Devuelve la representación de la casilla para la consola.
     * La lógica de 'V' y 'X' se puede refinar en el Tablero o en la Vista.
     */
    @Override
    public String toString() {
        if (estaMarcada) {
            return "F"; // Bandera (Flag)
        }
        if (!estaDescubierta) {
            return "☐"; // Casilla no descubierta (Cuadro blanco)
        }
        if (tieneMina) {
            return "X"; // Ubicación de una mina
        }
        if (minasAdyacentes > 0) {
            return String.valueOf(minasAdyacentes); // Muestra el número
        }
        // Si no tiene mina y es 0
        return " "; // Espacio vacío seleccionado
    }
}