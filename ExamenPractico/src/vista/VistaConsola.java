//EN PROCESO POR MATEO 03/12/2025
package vista;
//IMPORTACIÓN DE CLASES
import modelo.Tablero;
import java.util.Scanner;

public class VistaConsola {

    //ENCAPSULAMIENTO DE SCANNER
    private final Scanner scanner;

    ===============CONSTRUCTOR INICIA SCANNER=========
    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    //Muestra el título centrado y decorado.
     // Todo lo visual debe ir en la Vista, no en el Controlador.
     
    public void mostrarTitulo() {
        int anchoConsola = 130; // Ajusta este valor según el tamaño de tu ventana de consola
        String titulo = "¡EXAMEN PRACTICO JUEGO BUSCA MINAS";
        
        // Calcula la cantidad de espacios antes del título para centrarlo
        int espacios = (anchoConsola - titulo.length()) / 2;
        String padding = " ".repeat(Math.max(0, espacios));

        // Imprime título decorado con líneas y centrado
        System.out.println("\n" + padding + "====================================");
        System.out.println(padding + titulo);
        System.out.println(padding + "====================================\n");
    }

    // Muestra el tablero centrado en la consola.
    //Utiliza los métodos del Modelo (Tablero y Casilla) para obtener los datos.
    
    public void mostrarTablero(Tablero tablero) {
        int anchoConsola = 130; // ancho aproximado de la consola
        int tableroAncho = Tablero.COLUMNAS * 2 + 6; // cálculo rápido para centrar columnas
        int espacios = (anchoConsola - tableroAncho) / 2;
        String padding = " ".repeat(Math.max(0, espacios));

        // Mostrar las coordenadas de las columnas (1 a 10)
        System.out.print(padding + "   "); // padding para centrar
        for (int col = 1; col <= Tablero.COLUMNAS; col++) System.out.print(col + " ");
        System.out.println();
        System.out.println(padding + "  -------------------------"); // línea decorativa

        // Mostrar filas con letras A-J
        for (int i = 0; i < Tablero.FILAS; i++) {
            char letraFila = (char) ('A' + i); // convierte índice a letra
            System.out.print(padding + letraFila + " | ");
            for (int j = 0; j < Tablero.COLUMNAS; j++) {
                // llama al método toString() de Casilla para mostrar contenido
                System.out.print(tablero.getCasilla(i, j).toString() + " ");
            }
            System.out.println("|");
        }
        System.out.println(padding + "  -------------------------"); // línea decorativa al final
    }

    //Pide al usuario que ingrese una coordenada o 'S' para salir/guardar.
     // La entrada se limpia y se convierte a mayúscula.
     
    public String pedirCoordenada() {
        System.out.print("Ingrese coordenada (Ej: A5) ");
        return scanner.nextLine().trim().toUpperCase();
    }

   // Muestra cualquier mensaje centrado en la consola.
   //Útil para errores, victoria, derrota o instrucciones.
   
    public void mostrarMensaje(String mensaje) {
        int anchoConsola = 130;
        int espacios = (anchoConsola - mensaje.length()) / 2;
        String padding = " ".repeat(Math.max(0, espacios));
        System.out.println(padding + mensaje);
    }
}
