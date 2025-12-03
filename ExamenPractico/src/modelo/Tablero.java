package modelo;

import java.util.Random;

public class Tablero {
    
    // =================ATRIBUTOS CONSTANTE============
	//Constantes (tamaño fijo 10x10 y 10 minas)
    public static final int FILAS = 10; // CUANDO ES FINAL SE DEBE NOMBRAR EN MAYUSCULAS
    public static final int COLUMNAS = 10;
    public static final int NUM_MINAS = 10;

    //=======ASOCIACION DE TABLERO CON LAS CASILLAS========
    private Casilla[][] casillas; //[][]  DEFINE FILAS Y COLUMNAS
    private int casillasSegurasRestantes; // CONDICIÓN DE VICTORIA
    
    
    
    

    /*-------------------NO 
     * 					TOCAR
     * 					MATEO
     * 					CONTINUA--------------------------------
     * 
     * 
     * 
     * 
     */
    
    /**
     * Constructor del Tablero: Inicializa la matriz, coloca minas y calcula adyacencias.
     */
    public Tablero() {
        this.casillas = new Casilla[FILAS][COLUMNAS];
        // Total de casillas seguras = Total - Minas
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
     * Coloca el número fijo de minas en posiciones aleatorias.
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
     * Recorre el tablero y establece el conteo de minas adyacentes.
     */
    private void calcularMinasAdyacentes() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!casillas[i][j].tieneMina()) {
                    int conteo = 0;
                    // Recorrer las 8 casillas adyacentes (incluye diagonales)
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
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

    // --- Lógica de Interacción (Método principal usado por el Controlador) ---

    /**
     * Intenta descubrir una casilla. Debe lanzar excepciones personalizadas aquí.
     * @return true si el juego debe continuar, false si es una derrota.
     */
    public boolean descubrir(int fila, int columna) throws Exception { 
        Casilla casilla = casillas[fila][columna];

        if (casilla.estaDescubierta() || casilla.estaMarcada()) {
            // Requisito: Lanzar una excepción personalizada
            throw new Exception("CasillaYaDescubiertaException: Ya está revelada o marcada."); 
        }

        casilla.descubrir();
        
        if (casilla.tieneMina()) {
            // Requisito: Derrota
            return false; // ¡Juego perdido!
        }
        
        casillasSegurasRestantes--;

        // Si es una casilla vacía (0 minas adyacentes), inicia la expansión recursiva
        if (casilla.getMinasAdyacentes() == 0) {
            expandirCasillasVacias(fila, columna);
        }

        return true; // Juego continúa
    }

    // Método para la expansión recursiva (Flood Fill)
    private void expandirCasillasVacias(int fila, int columna) {
        // Implementación requerida de la lógica recursiva
        if (casillas[fila][columna].getMinasAdyacentes() != 0) {
            // Se detiene si encuentra un número (borde)
            return;
        }

        // Recorre las 8 casillas adyacentes
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                int ni = fila + di;
                int nj = columna + dj;

                // 1. Verificar límites
                if (ni >= 0 && ni < FILAS && nj >= 0 && nj < COLUMNAS) {
                    Casilla adyacente = casillas[ni][nj];

                    // 2. Si no ha sido descubierta y no tiene una mina
                    if (!adyacente.estaDescubierta() && !adyacente.tieneMina()) {
                        adyacente.descubrir();
                        casillasSegurasRestantes--;

                        // 3. Si la adyacente también es vacía (0), llama a la función recursivamente
                        if (adyacente.getMinasAdyacentes() == 0) {
                            expandirCasillasVacias(ni, nj);
                        }
                    }
                }
            }
        }
    }

    // --- Getters para la Vista ---

    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }
    
    public boolean esVictoria() {
        // Requisito: Victoria si todas las casillas seguras han sido reveladas
        return casillasSegurasRestantes == 0;
    }
}