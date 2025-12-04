//TERMINADO 03/12/2025
package vista;
//IMPORTACIÓN DE CLASES
import modelo.Tablero;
import java.util.Scanner;

public class VistaConsola  {

    //ENCAPSULAMIENTO DE SCANNER
    private final Scanner scanner;

    //===============CONSTRUCTOR INICIA SCANNER==========
    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    //MUESTRA EL TITULO CENTRADO
    public void mostrarTitulo() {
        int anchoConsola = 130; // AJUSTE DE VALOR SEGUN TAMAÑO DE CONSOLA  
        String titulo = "EXAMEN PRACTICO POO JUEGO BUSCA MINAS";
        
        // CALCULA LOS ESPACIOS ANTES DEL TÍTULO CENTRANDOLO
        int espacios = (anchoConsola - titulo.length()) / 2;
        String padding = " ".repeat(Math.max(0, espacios));

        // Imprime título decorado con líneas y centrado
        System.out.println("\n" + padding + "====================================");
        System.out.println(padding + titulo);
        System.out.println(padding + "====================================\n");
    }

    // TABLERO CENTRADO  Y ADQUIERE DATOS DE LOS METODOS TABLERO Y CASILLA
    public void mostrarTablero(Tablero tablero) {
        int anchoConsola = 130; 
        int tableroAncho = Tablero.COLUMNAS * 2 + 6;
        int espacios = (anchoConsola - tableroAncho) / 2;
        String padding = " ".repeat(Math.max(0, espacios));

        // Mostrar las coordenadas de las columnas (1 a 10)
        System.out.print(padding + "   "); // padding para centrar
        for (int col = 1; col <= Tablero.COLUMNAS; col++) System.out.print(col + " ");
        System.out.println();
        System.out.println(padding + "   ----------------------"); // línea decorativa

        // Mostrar filas con letras A-J
        for (int i = 0; i < Tablero.FILAS; i++) {
            char letraFila = (char) ('A' + i); // convierte índice a letra
            System.out.print(padding + letraFila + " - ");
            for (int j = 0; j < Tablero.COLUMNAS; j++) {
                // llama al método toString() de Casilla para mostrar contenido
                System.out.print(tablero.getCasilla(i, j).toString() + " ");
            }
            System.out.println("-");
        }
        System.out.println(padding + "    --------------------"); // línea decorativa al final
    }

    //Pide al usuario que ingrese una coordenada 
     // La entrada se limpia y se convierte a mayúscula.
     
    public String pedirCoordenada() {
        System.out.print("Ingrese coordenada (Ej: A5) ");
        return scanner.nextLine().trim().toUpperCase();
    }

   // Muestra cualquier mensaje centrado en la consola.
    public void mostrarMensaje(String mensaje) {
        int anchoConsola = 130;
        int espacios = (anchoConsola - mensaje.length()) / 2;
        String padding = " ".repeat(Math.max(0, espacios));
        System.out.println(padding + mensaje);
    }
}
