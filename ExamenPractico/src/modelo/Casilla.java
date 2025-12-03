package modelo;

import java.io.Serializable;

public class Casilla implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean tieneMina;
    private boolean estaDescubierta;
    private boolean estaMarcada;
    private int minasAdyacentes;
    private final int fila;
    private final int columna;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.tieneMina = false;
        this.estaDescubierta = false;
        this.estaMarcada = false;
        this.minasAdyacentes = 0;
    }

    public void colocarMina() { this.tieneMina = true; }
    public boolean tieneMina() { return tieneMina; }

    public void descubrir() {
        this.estaDescubierta = true;
        this.estaMarcada = false;
    }

    public boolean estaDescubierta() { return estaDescubierta; }

    public void marcar() {
        if (!estaDescubierta) this.estaMarcada = !this.estaMarcada;
    }

    public boolean estaMarcada() { return estaMarcada; }

    public void setMinasAdyacentes(int n) { this.minasAdyacentes = n; }
    public int getMinasAdyacentes() { return minasAdyacentes; }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }

    @Override
    public String toString() {
        if (estaMarcada) return "F";
        if (!estaDescubierta) return "■";
        if (tieneMina) return "X";
        if (minasAdyacentes > 0) return String.valueOf(minasAdyacentes);
        return " ";
    }
}