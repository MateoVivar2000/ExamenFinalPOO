//TERMINADO 03/12/2025
package modelo;
//IMPORTACIÓN DE SERIABLE
import java.io.Serializable;

public class Casilla implements Serializable { // INDICA QUE LOS OBJETOS DE LA CLASE SON SERIALIZADOS
    private static final long serialVersionUID = 1L;

    
    //========ATRIBUTTOS=========
    private boolean tieneMina;
    private boolean estaDescubierta;
    private boolean estaMarcada;
    private int minasAdyacentes;
    private final int fila;
    private final int columna;

    
    //======CONSTRUCTOR=======
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        // INICIALIZA TODO EN FALSO O 0
        this.tieneMina = false;
        this.estaDescubierta = false;
        this.estaMarcada = false;
        this.minasAdyacentes = 0;
    }
    
    //METODO PARA COLOCAR MINA Y COMPROBAR SI EXISTE
    public void colocarMina() { this.tieneMina = true; }
    public boolean tieneMina() { return tieneMina; }
    //METODO QUE DESCUBRE 
    public void descubrir() {
        this.estaDescubierta = true;
        this.estaMarcada = false;
    }
    //CONDICIONALES DE JUEGO
    public boolean estaDescubierta() { return estaDescubierta; }

    public void marcar() {
        if (!estaDescubierta) this.estaMarcada = !this.estaMarcada;
    }

    public boolean estaMarcada() { return estaMarcada; }
    
    public void setMinasAdyacentes(int n) { this.minasAdyacentes = n; }
    public int getMinasAdyacentes() { return minasAdyacentes; }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }

   //SOBRESCRITURA
    @Override
    public String toString() {
        if (estaMarcada) return "F";
        if (!estaDescubierta) return "■";
        if (tieneMina) return "X";
        if (minasAdyacentes > 0) return String.valueOf(minasAdyacentes);
        return " ";
    }
}