package modelo;

import java.io.Serializable;
import java.util.Random;

public class Tablero implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int FILAS = 10;
    public static final int COLUMNAS = 10;
    public static final int NUM_MINAS = 10;

    private Casilla[][] casillas;
    private int casillasSegurasRestantes;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        casillasSegurasRestantes = FILAS * COLUMNAS - NUM_MINAS;

        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                casillas[i][j] = new Casilla(i, j);

        colocarMinasAleatoriamente();
        calcularMinasAdyacentes();
    }

    private void colocarMinasAleatoriamente() {
        Random random = new Random();
        int minasColocadas = 0;
        while (minasColocadas < NUM_MINAS) {
            int fila = random.nextInt(FILAS);
            int columna = random.nextInt(COLUMNAS);
            if (!casillas[fila][columna].tieneMina()) {
                casillas[fila][columna].colocarMina();
                minasColocadas++;
            }
        }
    }

    private void calcularMinasAdyacentes() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (casillas[i][j].tieneMina()) continue;
                int conteo = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) continue;
                        int ni = i + di;
                        int nj = j + dj;
                        if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {
                            if (casillas[ni][nj].tieneMina()) conteo++;
                        }
                    }
                }
                casillas[i][j].setMinasAdyacentes(conteo);
            }
        }
    }

    /**
     * Descubre la casilla indicada.
     * @return false si se descubrió una mina (fin de juego), true si continúa.
     * @throws Exception si la casilla ya está descubierta o está marcada.
     */
    public boolean descubrir(int fila, int columna) throws Exception {
        Casilla c = casillas[fila][columna];
        if (c.estaDescubierta()) throw new Exception("Casilla ya descubierta.");
        if (c.estaMarcada()) throw new Exception("La casilla está marcada. Desmárcala para descubrirla.");

        c.descubrir();

        if (c.tieneMina()) {
            return false;
        }

        casillasSegurasRestantes--;

        if (c.getMinasAdyacentes() == 0) {
            expandirCasillasVacias(fila, columna);
        }

        return true;
    }

    private void expandirCasillasVacias(int fila, int columna) {
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                int ni = fila + di;
                int nj = columna + dj;
                if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {
                    Casilla vecino = casillas[ni][nj];
                    if (!vecino.estaDescubierta() && !vecino.tieneMina() && !vecino.estaMarcada()) {
                        vecino.descubrir();
                        casillasSegurasRestantes--;
                        if (vecino.getMinasAdyacentes() == 0) expandirCasillasVacias(ni, nj);
                    }
                }
            }
        }
    }

    public void marcarCasilla(int fila, int columna) {
        casillas[fila][columna].marcar();
    }

    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

    public boolean esVictoria() {
        return casillasSegurasRestantes == 0;
    }

    public void revelarTodo() {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                casillas[i][j].descubrir();
    }
}