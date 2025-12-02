package modelo;

import java.util.Random;

public class Tablero {
    
    // Constantes (tamaño fijo 10x10 y 10 minas)
    public static final int FILAS = 10; 
    public static final int COLUMNAS = 10;
    public static final int NUM_MINAS = 10;

    // Asociación: El Tablero tiene una matriz de objetos Casilla
    private Casilla[][] casillas;
    private int casillasSegurasRestantes; // Para condición de victoria

    /**
     * Constructor del Tablero
     */
    public Tablero() {
        this.casillas = new Casilla[FILAS][COLUMNAS];
        this.casillasSegurasRestantes = FILAS * COLUMNAS - NUM_MINAS;

        // 1. Inicializar la matriz con objetos Casilla
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
        
        // 2. Colocar minas y calcular adyacencias
        colocarMinasAleatoriamente();
        calcularMinasAdyacentes();
    }

    /**
     * Coloca 10 minas en posiciones aleatorias.
     */
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

    /**
     * Recorre el tablero y establece el conteo de minas adyacentes para cada casilla.
     */
    private void calcularMinasAdyacentes() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                // Solo calculamos para casillas sin mina
                if (!casillas[i][j].tieneMina()) {
                    int conteo = 0;
                    // Recorrer las 8 casillas adyacentes (incluye diagonales)
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            // Saltar la casilla actual (0, 0)
                            if (di == 0 && dj == 0) continue; 
                            
                            int ni = i + di; // Nueva fila
                            int nj = j + dj; // Nueva columna

                            // Verificar límites del tablero
                            if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {
                                if (casillas[ni][nj].tieneMina()) {
                                    conteo++;
                                }
                            }
                        }
                    }
                    casillas[i][j].setMinasAdyacentes(conteo);
                }
            }
        }
    }

    // --- Lógica de Interacción (Usada por el Controlador) ---

    /**
     * Intenta descubrir una casilla. Si es una mina, devuelve false.
     * Requiere lanzar excepciones personalizadas (no implementado aquí, pero es un requisito).
     * @return true si la casilla es segura, false si es una mina.
     */
    public boolean descubrir(int fila, int columna) {
        Casilla casilla = casillas[fila][columna];

        if (casilla.estaDescubierta() || casilla.estaMarcada()) {
            // Aquí deberías lanzar CasillaYaDescubiertaException
            System.out.println("Error: Casilla ya descubierta o marcada.");
            return true; // Continúa el juego si no es una acción fatal
        }

        casilla.descubrir();
        
        if (casilla.tieneMina()) {
            return false; // ¡Juego perdido!
        }
        
        casillasSegurasRestantes--;

        // Si es una casilla vacía (0 minas adyacentes), inicia la expansión recursiva
        if (casilla.getMinasAdyacentes() == 0) {
            expandirCasillasVacias(fila, columna);
        }

        return true; // Juego continúa
    }

    // Método a implementar: Lógica de expansión recursiva (Flood Fill)
    public void expandirCasillasVacias(int fila, int columna) {
        // **Implementación de recursividad y validaciones de límites aquí**
    }

    // --- Getters para la Vista ---

    /**
     * Obtiene la casilla en la posición dada (usado principalmente por la Vista).
     */
    public Casilla getCasilla(int fila, int columna) {
        // Aquí puedes manejar ArrayIndexOutOfBoundsException si lo necesitas.
        return casillas[fila][columna];
    }
    
    /**
     * Verifica la condición de victoria.
     */
    public boolean esVictoria() {
        return casillasSegurasRestantes == 0;
    }
}